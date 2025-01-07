# WebSocketManager for Jetpack Compose

`WebSocketManager` is a module based on okhttp3 library, designed to simplify the use of Websocket Communication in Jetpack Compose applications.

## Features

- Easy connection and data handling for WebSocket Server
- Seamless integration with Jetpack Compose
- Optimized for Compose while retaining all core functionalities of `OKhttp3`

## Installation

To use `WebSocketManager`, you can either:
1. **Use this project as a base for your application**  
   Clone or fork this repository and build your application on top of it.

2. **Add `WebSocketManager` to your existing project**  
   Follow these steps to integrate:

### Gradle Setup
Add the following dependencies to your Gradle build file:

```build.gradle.kts
dependencies {
    implementation ("com.squareup.okhttp3:okhttp:4.9.1") //core library for websocket communication.
    implementation ("com.google.code.gson:gson:2.8.8") //to send data with JSON format.
```

### Permission Setup
```AndroidManifest.xml
<manifest
    ...
    <uses-permission android:name="android.permission.INTERNET" /> /add this
    ...
   <application
      ...
   ...
</manifest>
```

### Copy Files
Copy the following files from this project into your own:
- `WebSocketManager.kt`

### Optional(in ws, not wss)
Because of security policy in android, CleartextTraffic like http or ws(websocket) is not permitted.
It isn't recommended because security issue. but https or wss(websocket secure) is incomfortable for extra settings.
So if you going to http or ws for testbed server(My Test server is Also ws), add

```AndroidManifest.xml
    <application
    ...
    android:usesCleartextTraffic="true"
    ...
    /application>
```

---

## Usage

### 1. Set the websocket server address.
#### Set default server address
change default value of `serverURL` (**"IP:portNum/URI"**) to your default server address:
```
    var serverURL : String = "IP:portNum/URI" //change this default value
        private set
```

### 2. Connect and Disconnect
- **Connect**: Call `WebSocketManager.connect()`
- **Disconnect**: Call `WebSocketManager.disconnect()`

### 3. Send or Receive
#### Send
- Call `WebSocketManager.send(data)`.

#### Receive
- Refer WebSocketmanager.processedData.value
```
   val receivedData = WebSocketmanager.processedData.value
   Log.d("WebSocketData", receivedData)
```
- If you need to receive data in Composable function, then Refer WebSocketmanager.processData.collectAsState().value
```
@Composable
fun SomeComposableFunction(){
   val receivedData = WebSocketManager.processedData.collectAsState().value
   Text(receivedData)
}
```
---

## Dependencies
This module use okhttp3, which is android internal library.
---

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
