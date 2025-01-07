package com.example.compose_websocket

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

enum class ConnectionState {Connected, Closing, DisConnected, Failed}

object WebSocketManager {
    /**
     * you need to modify this part according to your data format (YourDataFormatClass)
     * you should modify
     * 1) "MutableStateFlow(String())" to "MutableStateFlow(YourDataFormatClass())"
     * 2) "StateFlow<String>" to "StateFlow<YourDataFormatClass>
     */
    private val _processedData = MutableStateFlow(String())
    val processedData: StateFlow<String> = _processedData
    //

    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()
    var serverURL : String = "IP:portNum/URI"
        private set
    private val _connectionState = MutableStateFlow(ConnectionState.DisConnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    fun send(data: String) {
        /**
         * data must be JSON format in my Test Server
         * if you need to raw string, change "Gson().toJson(data)" to "data"
         */
        Log.d("WebSocket", "try to Send with WebSocket")
        if (_connectionState.value == ConnectionState.Connected) {
            scope.launch {
                webSocket!!.send(Gson().toJson(data))
                Log.d("WebSocket", "Data sent: $data")
            }
        } else {
            Log.d("WebSocket", "Not connected to server")
        }
    }
    fun connect() {
        Log.d("WebSocketManager","Try to Conect")
        if (_connectionState.value == ConnectionState.Connected) return
        val request = Request.Builder().url("ws://$serverURL").build()
        webSocket = client.newWebSocket(request, CustomWebSocketListener)
    }

    fun disconnect() {
        webSocket?.close(1000, "Disconnected by user")
        webSocket = null
        _connectionState.value = ConnectionState.DisConnected
    }
    fun changeServerURL(url:String){
        if(_connectionState.value == ConnectionState.Connected) {
            disconnect()
        }
        serverURL = url
        connect()
    }
    private object CustomWebSocketListener: WebSocketListener(){
        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("CustomWebSocketListener", "received: \"$text\"")
            scope.launch {
                try{
                    /**
                     * you need to modify this part according to your data format (YourDataFormatClass)
                     * you should just modify "String::class.java" to "YourDataFormatClass::class.java"
                     */
                    val data = Gson().fromJson(text, String::class.java)
                    _processedData.emit(data)
                }catch(e: JsonSyntaxException){
                    Log.d("asdf","Json Passing Error: ${e.message}")
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.d("CustomWebSocketListener", "received $bytes bytes")
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            _connectionState.value = ConnectionState.Connected // 연결 성공
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            _connectionState.value = ConnectionState.Closing // 연결 종료 중
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            _connectionState.value = ConnectionState.DisConnected // 연결 종료됨
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            //_connectionState.value = "Failed: ${t.message}" // 연결 실패
            _connectionState.value = ConnectionState.Failed
            Log.e("WebSocketListener","Failed: ${t.message}")
        }
    }
}
