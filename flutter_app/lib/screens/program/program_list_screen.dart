import 'package:flutter/material.dart';

class ProgramListScreen extends StatelessWidget {
  const ProgramListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Training Programs'),
      ),
      body: Center(
        child: Text(
          'Training Programs - Coming Soon',
          style: Theme.of(context).textTheme.headlineSmall,
        ),
      ),
    );
  }
}