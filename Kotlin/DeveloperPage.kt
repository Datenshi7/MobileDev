package com.example.baseconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseconvert.ui.theme.BaseConvertTheme

class DeveloperActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseConvertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF58d68d),
                ) {
                    DeveloperScreen()
                }
            }
        }
    }
}

@Composable
fun DeveloperScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top area occupied with dark green
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0B5345))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Meet the Team",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Team Members
        TeamMember(
            profileImage = R.drawable.x1,
            name = "Cudera, Xianne\n      Jewel S.",
            bio = "A 'fake it till you make it' girly and runs by the motto 'it is what it is'.",
            funFact = "Loves sleeping and has a hidden talent for playing the guitar!"
        )

        Spacer(modifier = Modifier.height(16.dp))

        TeamMember(
            profileImage = R.drawable.wyben,
            name = "Daal, Wyben C.",
            bio = "An experienced frontend developer who thrives on solving complex problems and optimizing performance.",
            funFact = "Has a black belt in martial arts and is a coffee connoisseur."
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Team Vision Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF0B5345)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Team Vision",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "\"Innovating the future, one step at a time\"",
                    style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Our team is dedicated to building innovative and user-friendly solutions that make a difference in people's lives. We believe in continuous learning, collaboration, and striving for excellence in everything we do.",
            style = MaterialTheme.typography.bodyLarge, color = Color(0xFF0B5345),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun TeamMember(profileImage: Int, name: String, bio: String, funFact: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF98FB98).copy(alpha = 0.2f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = profileImage),
                contentDescription = "Profile Picture of $name",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF0B5345)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = bio,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF0B5345)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Fun Fact: $funFact",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF0B5345)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeveloperScreenPreview() {
    BaseConvertTheme {
        DeveloperScreen()
    }
}
