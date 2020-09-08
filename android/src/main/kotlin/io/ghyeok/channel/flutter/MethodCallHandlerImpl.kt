package io.ghyeok.channel.flutter

import android.app.Activity
import android.content.Context
import com.zoyi.channel.plugin.android.ChannelIO
import com.zoyi.channel.plugin.android.ChannelPluginSettings
import com.zoyi.channel.plugin.android.Profile
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MethodCallHandlerImpl(private val context: Context) : MethodChannel.MethodCallHandler {

    var activity: Activity? = null

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "boot" -> boot(call, result)
            "show" -> show(call, result)
            "hide" -> hide(call, result)
            "shutdown" -> shutdown(call, result)
            "open" -> open(call, result)
            "handlePushNotification" -> handlePushNotification(call, result)
            "initPushToken" -> initPushToken(call, result)
            "isChannelPushNotification" -> isChannelPushNotification(call, result)
            "showPushNotification" -> showPushNotification(call, result)
            else -> result.notImplemented()
        }
    }

    private fun boot(call: MethodCall, result: MethodChannel.Result) {

        // PluginKey 설정되어있지 않는경우
        if (!call.hasArgument("pluginKey")) {
            result.error("ARGUMENT_ERROR", "plugin key is not set", "플리그인 키가 설정되어있지 않습니다.")
            return
        }

        // Plugin Setting
        val pluginKey: String? = call.argument("pluginKey")
        val settings = ChannelPluginSettings(pluginKey).apply {
            memberId = call.argument("memberId") as? String
            memberHash = call.argument("memberHash") as? String
        }

        // Profile Info
        val profileArgument: Map<String, String>? = call.argument("profile")
        val profile: Profile? = profileArgument?.run {
            val profile = Profile.create()

            val defaultProperties = arrayOf("name", "email", "avatarUrl", "mobileNumber")

            // 기본 정보를 제외한 다른 정보는 Properties 처리
            this.filterKeys { !defaultProperties.contains(it) }
                    .entries
                    .forEach { profile.setProperty(it.key, it.value) }

            // Set Default Property
            profile.name = get("name")
            profile.email = get("email")
            profile.avatarUrl = get("avatarUrl")
            profile.mobileNumber = get("mobileNumber")

            return@run profile
        }

        // Boot ChannelIO
        ChannelIO.boot(settings, profile)

        result.success(true)
    }

    private fun open(call: MethodCall, result: MethodChannel.Result) {
        if (this.activity == null) {
            result.error("ARGUMENT_ERROR", "activity is null", "activity is null")
            return
        }

        ChannelIO.open(activity)
        result.success(true)
    }

    private fun handlePushNotification(call: MethodCall, result: MethodChannel.Result) {
        if (this.activity == null) {
            result.error("ARGUMENT_ERROR", "activity is null", "activity is null")
            return
        }

        ChannelIO.handlePushNotification(this.activity)
        result.success(true)
    }

    private fun initPushToken(call: MethodCall, result: MethodChannel.Result) {
        if (!call.hasArgument("token")) {
            result.error("ARGUMENT_ERROR", "token is not set", "토큰이 설정되어있지 않습니다.")
            return
        }

        ChannelIO.initPushToken(call.argument("token"))
        result.success(true)
    }

    private fun isChannelPushNotification(call: MethodCall, result: MethodChannel.Result) {
        if (!call.hasArgument("message")) {
            result.error("ARGUMENT_ERROR", "message is not set", "메세지가 설정되어있지 않습니다.")
            return
        }

        val messageArgument: Map<String, String>? = call.argument("message")
        result.success(ChannelIO.isChannelPushNotification(messageArgument))
    }

    private fun showPushNotification(call: MethodCall, result: MethodChannel.Result) {
        if (!call.hasArgument("message")) {
            result.error("ARGUMENT_ERROR", "message is not set", "메세지가 설정되어있지 않습니다.")
            return
        }

        val messageArgument: Map<String, String>? = call.argument("message")
        ChannelIO.showPushNotification(context, messageArgument)

        result.success(true)
    }

    private fun show(call: MethodCall, result: MethodChannel.Result) {
        ChannelIO.show()

        result.success(true)
    }

    private fun hide(call: MethodCall, result: MethodChannel.Result) {
        ChannelIO.hide()

        result.success(true)
    }

    private fun shutdown(call: MethodCall, result: MethodChannel.Result) {
        ChannelIO.shutdown()

        result.success(true)
    }
}