# MaxWeight Flutter App

A cross-platform mobile application for the MaxWeight training program system, built with Flutter.

## Features

- 🔐 JWT Authentication (Login/Register)
- 💪 Training Program Generation (Verkhoshansky Method)
- 📊 Workout Analytics & Progress Tracking
- 🏆 Achievement System with Badges
- 📱 Offline-First with Data Sync
- 📈 Visual Progress Charts
- 🎯 Personal Records Tracking
- ⏰ Workout Reminders

## Prerequisites

- Flutter SDK 3.0 or higher
- Dart 2.17 or higher
- Android Studio / Xcode (for mobile development)
- Chrome (for web development)

## Installation

1. **Install Flutter SDK**
   ```bash
   # Follow instructions at https://flutter.dev/docs/get-started/install
   ```

2. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd flutter_app
   ```

3. **Install dependencies**
   ```bash
   flutter pub get
   ```

4. **Configure API endpoint**
   Edit `lib/config/api_config.dart` and set your backend URL:
   ```dart
   static const String baseUrl = 'http://localhost:8080'; // or your Matter preview URL
   ```

## Running the App

### Mobile (Android/iOS)
```bash
flutter run
```

### Web
```bash
flutter run -d chrome
```

### Specific Device
```bash
# List available devices
flutter devices

# Run on specific device
flutter run -d <device_id>
```

## Project Structure

```
lib/
├── config/           # API configuration
├── models/          # Data models
├── services/        # API services and business logic
├── providers/       # State management (Provider pattern)
├── screens/         # UI screens
│   ├── auth/       # Login/Register screens
│   ├── home/       # Home dashboard
│   ├── program/    # Training program screens
│   ├── analytics/  # Analytics and progress
│   └── profile/    # User profile
├── widgets/         # Reusable widgets
└── main.dart       # App entry point
```

## Backend Connection

This Flutter app connects to the MaxWeight REST API backend. Make sure the backend is running:

1. Start the Spring Boot backend (see main README)
2. Note the API URL (e.g., `http://localhost:8080` or Matter preview URL)
3. Update `lib/config/api_config.dart` with the correct URL

## State Management

The app uses Provider for state management:
- `AuthProvider` - User authentication state
- `ProgramProvider` - Training programs
- `AnalyticsProvider` - Workout analytics and stats
- `SyncProvider` - Data synchronization

## Offline Support

- Local data storage with Hive
- Automatic sync when online
- Conflict resolution for concurrent updates
- Queue for offline operations

## Building for Production

### Android APK
```bash
flutter build apk --release
```

### Android App Bundle
```bash
flutter build appbundle --release
```

### iOS
```bash
flutter build ios --release
```

### Web
```bash
flutter build web --release
```

## Testing

```bash
# Run all tests
flutter test

# Run with coverage
flutter test --coverage

# Run integration tests
flutter test integration_test/
```

## Contributing

1. Create a feature branch
2. Make your changes
3. Test thoroughly
4. Submit a pull request

## License

See LICENSE file in the root repository.