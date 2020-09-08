import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class ChannelIO {
  factory ChannelIO() => _instance;

  ChannelIO.private(MethodChannel channel) : _channel = channel;

  static final ChannelIO _instance =
      ChannelIO.private(MethodChannel('GwonHyeok/flutter_channel_io'));

  final MethodChannel _channel;

  Future<bool> boot({
    @required String pluginKey,
    String memberId,
    String memberHash,
    Profile profile,
  }) async {
    return await _channel.invokeMethod('boot', {
      'pluginKey': pluginKey,
      'memberId': memberId,
      'memberHash': memberHash,
      'profile': profile?.toMap(),
    });
  }

  Future<bool> show() async {
    return await _channel.invokeMethod('show');
  }

  Future<bool> hide() async {
    return await _channel.invokeMethod('hide');
  }

  Future<bool> open() async {
    return await _channel.invokeMethod('open');
  }

  Future<bool> handlePushNotification() async {
    return await _channel.invokeMethod('handlePushNotification');
  }

  Future<bool> isChannelPushNotification(
      {@required Map<String, String> message}) async {
    return await _channel.invokeMethod(
      'isChannelPushNotification',
      {'message': message},
    );
  }

  Future<bool> showPushNotification(
      {@required Map<String, String> message}) async {
    return await _channel.invokeMethod(
      'showPushNotification',
      {'message': message},
    );
  }

  Future<bool> initPushToken({
    @required String token,
  }) async {
    return await _channel.invokeMethod('initPushToken', {
      'token': token,
    });
  }
}

class Profile {
  final String name;
  final String email;
  final String avatarUrl;
  final String mobileNumber;

  final Map<String, String> properties;

  Profile({
    this.name,
    this.email,
    this.avatarUrl,
    this.mobileNumber,
    this.properties,
  });

  Map<String, dynamic> toMap() {
    Map<String, dynamic> _map = {};
    if (name != null) _map['name'] = name;
    if (email != null) _map['email'] = email;
    if (avatarUrl != null) _map['avatarUrl'] = avatarUrl;
    if (mobileNumber != null) _map['mobileNumber'] = mobileNumber;
    if (properties != null) {
      properties.forEach((key, value) {
        _map[key] = value;
      });
    }

    return _map;
  }
}
