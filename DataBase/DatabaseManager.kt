package com.example.baseconvert

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.MessageDigest

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "BaseConverterDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD_HASH = "password_hash"

        // Conversion History Table
        private const val TABLE_CONVERSION_HISTORY = "conversion_history"
        private const val COLUMN_CONVERSION_ID = "id"
        private const val COLUMN_CONVERTED_VALUE = "converted_value"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT NOT NULL UNIQUE,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_PASSWORD_HASH TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createUsersTable)

        val createConversionHistoryTable = """
            CREATE TABLE $TABLE_CONVERSION_HISTORY (
                $COLUMN_CONVERSION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CONVERTED_VALUE TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createConversionHistoryTable)

        db.execSQL("CREATE INDEX idx_username ON $TABLE_USERS($COLUMN_USERNAME)")
        db.execSQL("CREATE INDEX idx_email ON $TABLE_USERS($COLUMN_EMAIL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONVERSION_HISTORY")
        db.execSQL("DROP INDEX IF EXISTS idx_username")
        db.execSQL("DROP INDEX IF EXISTS idx_email")
        onCreate(db)
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun registerUser(username: String, email: String, password: String): Boolean {
        val db = writableDatabase
        try {
            val hashedPassword = hashPassword(password)

            val values = ContentValues().apply {
                put(COLUMN_USERNAME, username)
                put(COLUMN_EMAIL, email)
                put(COLUMN_PASSWORD_HASH, hashedPassword)
            }

            val result = db.insertWithOnConflict(TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_FAIL)
            return result != -1L
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            db.close()
        }
    }

    fun loginUser(username: String, password: String): Boolean {
        val db = readableDatabase
        var cursor: android.database.Cursor? = null
        try {
            cursor = db.rawQuery(
                "SELECT $COLUMN_PASSWORD_HASH FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ?",
                arrayOf(username)
            )
            if (cursor.moveToFirst()) {
                val storedHash = cursor.getString(0)
                val inputHash = hashPassword(password)
                return storedHash == inputHash
            }
            return false
        } finally {
            cursor?.close()
            db.close()
        }
    }

    fun userExists(username: String, email: String): Pair<Boolean, Boolean> {
        val db = readableDatabase
        var usernameCursor: android.database.Cursor? = null
        var emailCursor: android.database.Cursor? = null
        try {
            var usernameExists = false
            var emailExists = false

            usernameCursor = db.rawQuery(
                "SELECT COUNT(*) FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ?",
                arrayOf(username)
            )
            if (usernameCursor.moveToFirst()) {
                usernameExists = usernameCursor.getInt(0) > 0
            }

            emailCursor = db.rawQuery(
                "SELECT COUNT(*) FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?",
                arrayOf(email)
            )
            if (emailCursor.moveToFirst()) {
                emailExists = emailCursor.getInt(0) > 0
            }

            return Pair(usernameExists, emailExists)
        } finally {
            usernameCursor?.close()
            emailCursor?.close()
            db.close()
        }
    }

    fun insertConversion(conversionValue: String): Boolean {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put(COLUMN_CONVERTED_VALUE, conversionValue)
            }
            val result = db.insert(TABLE_CONVERSION_HISTORY, null, values)
            result != -1L
        } finally {
            db.close()
        }
    }

    fun getConversionHistory(): List<String> {
        val db = readableDatabase
        val conversionList = mutableListOf<String>()
        val cursor = db.rawQuery("SELECT $COLUMN_CONVERTED_VALUE FROM $TABLE_CONVERSION_HISTORY", null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val conversion = it.getString(0)
                    conversionList.add(conversion)
                } while (it.moveToNext())
            }
        }
        db.close()
        return conversionList
    }

    fun clearConversionHistory() {
        val db = writableDatabase
        db.delete(TABLE_CONVERSION_HISTORY, null, null)
        db.close()
    }
}
