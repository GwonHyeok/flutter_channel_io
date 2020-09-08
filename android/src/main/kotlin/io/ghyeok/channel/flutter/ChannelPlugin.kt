package io.ghyeok.channel.flutter

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import com.zoyi.channel.plugin.android.ChannelIO
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry.Registrar

/** FlutterChannelIoPlugin */
public class ChannelPlugin : FlutterPlugin, ActivityAware {

  private lateinit var channel: MethodChannel

  private lateinit var handler: MethodCallHandlerImpl

  companion object {

    const val CHANNEL_NAME = "GwonHyeok/flutter_channel_io"

    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channelPlugin = ChannelPlugin()
      channelPlugin.setupChannel(registrar.messenger(), registrar.context())
    }
  }

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    setupChannel(flutterPluginBinding.binaryMessenger, flutterPluginBinding.applicationContext)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private fun setupChannel(messenger: BinaryMessenger, context: Context) {
    channel = MethodChannel(messenger, CHANNEL_NAME)
    handler = MethodCallHandlerImpl(context)
    channel.setMethodCallHandler(handler)

    // Initialize ChannelIO
    val application = context.applicationContext as Application
    ChannelIO.initialize(application)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    this.handler.activity = binding.activity
  }

  override fun onDetachedFromActivity() {
    this.handler.activity = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    this.handler.activity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {
    this.handler.activity = null
  }
}