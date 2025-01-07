package com.example.compose_websocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_websocket.WebSocketManager.connectionState
import com.example.compose_websocket.ui.theme.Compose_WebSocketTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * you need to change serverURL to your serverURL.
         * you can change it with 'changeServerURL' method of WebSocketManager
         * or change default serverURL in WebSocketManager.kt File
         */
        //WebSocketManager.changeServerURL("yourIP:yourPortNum/yourURI")
        //
        setContent {
            Compose_WebSocketTheme {
                Column(modifier = Modifier.fillMaxSize()
                ) {
                    SampleScreen()
                    Spacer(modifier = Modifier.weight(1f))
                    SampleSendScreen()
                    Spacer(modifier = Modifier.weight(1f))
                    SampleReceiveScreen()
                }
            }
        }
    }
}

@Composable
fun SampleScreen(){
    val connectionState = WebSocketManager.connectionState.collectAsState().value
    val urlToChange = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .background(color = Color(0xfffad7ef))
    ){
        Text("WebSocket Sample", fontSize = 30.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(5.dp))
        Text("Target URL: ${WebSocketManager.serverURL}")
        TextField(value = urlToChange.value,
            onValueChange = {urlToChange.value = it},
            label = { Text("URL to Change" )})
        TextButton(
            onClick = { WebSocketManager.changeServerURL(urlToChange.value) }
        ) { Text("Change URL") }
        Divider(color = Color.Gray, thickness = 1.dp)
        Text("Connection state : $connectionState")
        Row() {
            TextButton(
                onClick = { WebSocketManager.connect() }
            ) { Text("Connect to Server") }
            TextButton(
                onClick = { WebSocketManager.disconnect() }
            ) { Text("Disconnect to Server") }
        }
    }
}
@Composable
fun SampleSendScreen(){
    val dataToSend = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .background(color = Color(0xfff5f5bc))
    ){
        Text("Send Data", fontSize = 30.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(5.dp))
        TextField(value = dataToSend.value,
            onValueChange = {dataToSend.value = it},
            label = { Text("Data to Send" )})
        TextButton(
            onClick = { WebSocketManager.send(dataToSend.value) }
        ) { Text("send to Server") }
    }
}

@Composable
fun SampleReceiveScreen(){
    val nowData = WebSocketManager.processedData.collectAsState().value
    Column(
        modifier = Modifier.fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .background(color = Color(0xffcffce6))
    ){
        Text("Received Data", fontSize = 30.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(5.dp))
        Text("nowReceived: $nowData")

    }
}
@Preview(showBackground = true)
@Composable
fun SampleScreenPreview(){
    Compose_WebSocketTheme {
        Column(modifier = Modifier.fillMaxSize()
        ) {
            SampleScreen()
            Spacer(modifier = Modifier.weight(1f))
            SampleSendScreen()
            Spacer(modifier = Modifier.weight(1f))
            SampleReceiveScreen()
        }
    }
}
