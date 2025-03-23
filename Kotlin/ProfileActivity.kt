package com.example.baseconvert

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baseconvert.ui.theme.BaseConvertTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseConvertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFC4C4)
                ) {
                    ProfileScreen(
                        onLogoutConfirmed = { navigateToLoginScreen() },
                        onSettingsClick = { navigateToSettingsScreen() }
                    )
                }
            }
        }
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSettingsScreen() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun ProfileScreen(onLogoutConfirmed: () -> Unit, onSettingsClick: () -> Unit) {
    var username by remember { mutableStateOf("john_doe") }
    var email by remember { mutableStateOf("john_doe@example.com") }
    var isEditing by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout Confirmation") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogoutConfirmed()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E4))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = username,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF850E35)
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSettingsClick() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.x1), // Replace with your drawable resource
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = email,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF850E35)

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { isEditing = true },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEE6983))
        ) {
            Text(text = "Edit Profile", fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Rounded corner boxes for 'Notes' and 'History'
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFFC4C4).copy(alpha = 0.4f))
                .clickable { /* Handle Notes click */ },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Notes", style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFFC4C4).copy(alpha = 0.4f))
                .clickable { /* Handle History click */ },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "History", style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    BaseConvertTheme {
        ProfileScreen({}, {})
    }
}
