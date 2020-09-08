import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter_channel_io/flutter_channel_io.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _channelIO = ChannelIO();

  bool _isBooted = false;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    // Boot ChannelIO
    await _channelIO.boot(
      pluginKey: "CHANNEL_IO_PLUGIN_KEY",
      profile: Profile(name: "GwonHyeok", email: "me@ghyeok.io"),
    );

    setState(() {
      _isBooted = true;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('ChannelIO example app'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Center(child: Text('ChannelIO is booted : $_isBooted')),
            Padding(
              padding:
              const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8),
              child: Wrap(
                children: [
                  RaisedButton(
                    child: Text('show'),
                    onPressed: () {
                      _channelIO.show();
                    },
                  ),
                  RaisedButton(
                    child: Text('hide'),
                    onPressed: () {
                      _channelIO.hide();
                    },
                  ),
                  RaisedButton(
                    child: Text('open'),
                    onPressed: () {
                      _channelIO.open();
                    },
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
