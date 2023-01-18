import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Home(),
    );
  }
}

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  static const platform = MethodChannel('floating_button');

  int count = 0;

  @override
  void initState() {
    super.initState();
    platform.setMethodCallHandler((call) async {
      if (call.method == 'touch') {
        setState(() => count += 1);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Floating Button Demo'),
        centerTitle: true,
      ),
      body: Padding(
        padding: EdgeInsets.all(8),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              '$count',
              textAlign: TextAlign.center,
              style: TextStyle(fontSize: 50),
            ),
            ElevatedButton(
              onPressed: () {
                platform.invokeMethod('create');
              },
              child: Text('Create'),
            ),
            ElevatedButton(
              onPressed: () {
                platform.invokeMethod('show');
              },
              child: Text('Show'),
            ),
            ElevatedButton(
              onPressed: () {
                platform.invokeMethod('hide');
              },
              child: Text('Hide'),
            ),
            ElevatedButton(
              onPressed: () {
                platform.invokeMethod('not_implemented_native_method');
              },
              child: Text('Not implemented'),
            ),
            ElevatedButton(
              onPressed: () {
                platform
                    .invokeMethod('isShowing')
                    .then((isShowing) => print(isShowing));
              },
              child: Text('Float Button is visible?'),
            ),
          ],
        ),
      ),
    );
  }
}
