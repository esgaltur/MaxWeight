class ApiConfig {
  // Backend API base URL
  // Change this to your Matter preview URL or production URL
  static const String baseUrl = 'http://localhost:8080';
  
  // API endpoints
  static const String authRegister = '/api/auth/register';
  static const String authLogin = '/api/auth/login';
  static const String authRefresh = '/api/auth/refresh';
  
  static const String userProfile = '/api/users/profile';
  
  static const String programs = '/api/programs';
  static String programByWeek(int weekNumber) => '/api/programs/$weekNumber';
  
  static const String syncPrograms = '/api/sync/programs';
  static String syncProgramByWeek(int weekNumber) => '/api/sync/programs/$weekNumber';
  
  static const String analyticsWorkoutSessions = '/api/analytics/workout-sessions';
  static const String analyticsStats = '/api/analytics/stats';
  static const String analyticsProgress = '/api/analytics/progress';
  static const String analyticsRecords = '/api/analytics/personal-records';
  
  // Timeouts
  static const Duration connectTimeout = Duration(seconds: 30);
  static const Duration receiveTimeout = Duration(seconds: 30);
  
  // Storage keys
  static const String accessTokenKey = 'access_token';
  static const String refreshTokenKey = 'refresh_token';
  static const String userDataKey = 'user_data';
}