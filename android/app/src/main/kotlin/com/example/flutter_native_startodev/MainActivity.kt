package com.example.flutter_native_startodev

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.util.Log
import android.widget.ImageView
import com.yhao.floatwindow.FloatWindow
import com.yhao.floatwindow.Screen

class MainActivity: FlutterActivity() {
    companion object {
        const val CHANNEL = "floating_button"
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        channel.setMethodCallHandler {
                call, result ->

            when(call.method) {
                "create" -> {
                    Log.d("APP_MESSAGE", "*** Method CREATE ****")
                    val imageView = ImageView(applicationContext)
                    imageView.setImageResource(R.drawable.plus)
                    imageView.setOnClickListener {
                        Log.d("APP_MESSAGE", "*** Adding one from Kotlin code ****")
                        channel.invokeMethod("touch", null)
                    }

                    FloatWindow.with(applicationContext)
                        .setView(imageView)
                        .setWidth(Screen.width,.15F)
                        .setHeight(Screen.width,.15F)
                        .setX(Screen.width,.8F)
                        .setY(Screen.height,.3F)
                        .setDesktopShow(true)
                        .build()

                    result.success("OK")
                }
                "show" -> {
                    Log.d("APP_MESSAGE", "*** Method SHOW ****")
                    FloatWindow.get().show()
                }
                "hide" -> FloatWindow.get().hide()
                "isShowing" -> result.success(FloatWindow.get().isShowing)

                else -> {
                    Log.d("APP_MESSAGE", "*** Method not implemented ****")
                    result.notImplemented()
                }
            }

            /*
            if(call.method == "getRandomNumber") {
                val rand = Random.nextInt(100)
                result.success(rand)
            }
            else {
                result.notImplemented()
            }*/
        }
    }

    override fun onDestroy() {
        FloatWindow.destroy()
        super.onDestroy()
    }
}
