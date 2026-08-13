package com.ems.android_mobile_training_app

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emarsys.Emarsys
import com.ems.android_mobile_training_app.ui.theme.AndroidmobiletrainingappTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 0)
        }
        setContent {
            AndroidmobiletrainingappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    val initialMessages = listOf("Message 1", "Message 2", "Message 3")
    val sampleMessages = remember { mutableStateListOf(*initialMessages.toTypedArray()) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Hello Emarsys!",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp)
        )

        val context = LocalContext.current

        Button(onClick = {
            Toast.makeText(context, "Login clicked!", Toast.LENGTH_SHORT).show()
            Emarsys.setContact(100010824, "ac55b39a2cd7482bb0a6998017cd71de")
        }) {
            Text("Login")
        }

        Button(onClick = {
            Toast.makeText(context, "Logout clicked!", Toast.LENGTH_SHORT).show()
            Emarsys.clearContact()
        }) {
            Text("Logout")
        }

        Button(onClick = {
            Emarsys.trackCustomEvent("test_event", mapOf())
            Toast.makeText(context, "Custom event clicked!", Toast.LENGTH_SHORT).show()
        }) {
            Text("Trigger custom event")
        }

        Button(onClick = {
            Toast.makeText(context, "Fetch inbox clicked!", Toast.LENGTH_SHORT).show()
            Emarsys.messageInbox.fetchMessages { inbox ->
                inbox.result.let { result ->
                    val messages = result?.messages ?: emptyList()
                    sampleMessages.clear()
                    sampleMessages.addAll(messages.map { it.id + " | " + it.title + " | " + it.tags })
                }
            }
        }) {
            Text("Fetch inbox messages")
        }

        Button(onClick = {
            Toast.makeText(context, "Add inbox tag clicked!", Toast.LENGTH_SHORT).show()
            Emarsys.messageInbox.addTag("opened", "12802360654")
        }) {
            Text("Add inbox tag")
        }

        Button(onClick = {
            Toast.makeText(context, "Remove inbox tag clicked!", Toast.LENGTH_SHORT).show()
            Emarsys.messageInbox.removeTag("opened", "12802360654")
        }) {
            Text("Remove inbox tag")
        }

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(sampleMessages) { message ->
                Text(text = message, modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidmobiletrainingappTheme {
        Greeting()
    }
}