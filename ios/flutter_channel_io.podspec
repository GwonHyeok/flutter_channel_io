#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint flutter_channel_io.podspec' to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'flutter_channel_io'
  s.version          = '0.0.1'
  s.summary          = 'flutter plugin for channel io'
  s.description      = <<-DESC
A new flutter plugin project.
                       DESC
  s.homepage         = 'https://github.com/GwonHyeok/flutter_channel_io'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'GwonHyeok' => 'me@ghyeok.io' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.dependency 'Flutter'
  s.dependency 'ChannelIO', '~> 7.2'
  s.ios.deployment_target = '10.0'
  s.platform = :ios, '10.0'

  # Flutter.framework does not contain a i386 slice. Only x86_64 simulators are supported.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'VALID_ARCHS[sdk=iphonesimulator*]' => 'x86_64' }
  s.swift_version = '5.0'
end
