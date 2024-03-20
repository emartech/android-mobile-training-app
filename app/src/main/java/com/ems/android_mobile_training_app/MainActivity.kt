package com.ems.android_mobile_training_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.ems.android_mobile_training_app.ui.theme.AndroidmobiletrainingappTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidmobiletrainingappTheme {
                // A surface container using the 'background' color from the theme
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

    val sampleMessages = listOf("Message 1", "Message 2", "Message 3")
    val messages = remember { mutableStateListOf<String>() }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Hello Emarsys!",
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp)
        )

        val context = LocalContext.current

        Button(onClick = {
            Toast.makeText(context, "Login clicked!", Toast.LENGTH_SHORT).show()
        }) {
            Text("Login")
        }

        Button(onClick = {
            Toast.makeText(context, "Logout clicked!", Toast.LENGTH_SHORT).show()
        }) {
            Text("Logout")
        }

        Button(onClick = {
            Toast.makeText(context, "Custom event clicked!", Toast.LENGTH_SHORT).show()
        }) {
            Text("Trigger custom event")
        }

        Button(onClick = {
            Toast.makeText(context, "Fetch inbox clicked!", Toast.LENGTH_SHORT).show()
        }) {
            Text("Fetch inbox messages")
            CoroutineScope(Dispatchers.Main).launch {
                // Simulate fetching data with a delay
                delay(1000) // Simulate network delay
                messages.clear()
                messages.addAll(sampleMessages)
            }
        }

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(messages) { message ->
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