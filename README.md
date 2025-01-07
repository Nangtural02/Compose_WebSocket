# WebSocketManager for Jetpack Compose

`WebSocketManager` is a library built on top of [usb-serial-for-android](https://github.com/mik3y/usb-serial-for-android), designed to simplify the use of USB serial devices in Jetpack Compose applications.
This library leverages Android USB Host Mode (OTG) to communicate with USB serial devices and makes managing USB connections and data processing straightforward within the Jetpack Compose environment.

## Features

- Easy connection and data handling for USB serial devices
- Seamless integration with Jetpack Compose
- Optimized for Compose while retaining all core functionalities of `usb-serial-for-android`
- only support CdcACM device now.(I will update soon)

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

### 1. Initialize and Release `SerialManager`
#### Initialization
Call `SerialManager.initialize(context, blockHandler)` in your `MainActivity.onCreate()` (or wherever you start the serial connection):
- **`context`**: Provide the application (or activity) context.
- **`blockHandler`**: Define a callback function to handle incoming block data from the serial device.

#### Release
Call `SerialManager.release()` in your `MainActivity.onDestroy()` (or wherever you stop the serial connection).

### 2. Connect and Disconnect
- **Connect**: Use `SerialManager.connectSerialDevice()` to establish a connection with a USB serial device.
- **Disconnect**: Use `SerialManager.disconnectSerialDevice()` to terminate the connection.

Once connected, the `blockHandler` you registered will be triggered for every new block of data received.

---

## Dependencies

This library is built on top of `usb-serial-for-android`.  
All features and limitations of the original library apply. For more details, refer to the original library:  
[usb-serial-for-android GitHub page](https://github.com/mik3y/usb-serial-for-android)

---

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).  
The license for `usb-serial-for-android` can be found in its [MIT License](https://github.com/mik3y/usb-serial-for-android/blob/master/LICENSE).