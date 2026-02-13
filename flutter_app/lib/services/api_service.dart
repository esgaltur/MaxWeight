import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import '../config/api_config.dart';

class ApiService {
  final _storage = const FlutterSecureStorage();
  
  Future<String?> _getAccessToken() async {
    return await _storage.read(key: ApiConfig.accessTokenKey);
  }
  
  Future<void> _saveTokens(String accessToken, String refreshToken) async {
    await _storage.write(key: ApiConfig.accessTokenKey, value: accessToken);
    await _storage.write(key: ApiConfig.refreshTokenKey, value: refreshToken);
  }
  
  Future<void> _clearTokens() async {
    await _storage.delete(key: ApiConfig.accessTokenKey);
    await _storage.delete(key: ApiConfig.refreshTokenKey);
  }
  
  Map<String, String> _getHeaders({bool includeAuth = true}) {
    final headers = {
      'Content-Type': 'application/json',
    };
    return headers;
  }
  
  Future<Map<String, String>> _getAuthHeaders() async {
    final token = await _getAccessToken();
    return {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };
  }
  
  // Authentication
  Future<Map<String, dynamic>> register({
    required String username,
    required String email,
    required String password,
    double? maxWeight,
  }) async {
    final response = await http.post(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.authRegister}'),
      headers: _getHeaders(includeAuth: false),
      body: jsonEncode({
        'username': username,
        'email': email,
        'password': password,
        'maxWeight': maxWeight,
      }),
    );
    
    if (response.statusCode == 201) {
      final data = jsonDecode(response.body);
      await _saveTokens(data['accessToken'], data['refreshToken']);
      return data;
    } else {
      throw Exception('Registration failed: ${response.body}');
    }
  }
  
  Future<Map<String, dynamic>> login({
    required String username,
    required String password,
  }) async {
    final response = await http.post(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.authLogin}'),
      headers: _getHeaders(includeAuth: false),
      body: jsonEncode({
        'username': username,
        'password': password,
      }),
    );
    
    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      await _saveTokens(data['accessToken'], data['refreshToken']);
      return data;
    } else {
      throw Exception('Login failed: ${response.body}');
    }
  }
  
  Future<void> logout() async {
    await _clearTokens();
  }
  
  // User Profile
  Future<Map<String, dynamic>> getUserProfile() async {
    final response = await http.get(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.userProfile}'),
      headers: await _getAuthHeaders(),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get profile: ${response.body}');
    }
  }
  
  Future<Map<String, dynamic>> updateUserProfile({
    String? email,
    double? maxWeight,
    String? currentPassword,
    String? newPassword,
  }) async {
    final body = <String, dynamic>{};
    if (email != null) body['email'] = email;
    if (maxWeight != null) body['maxWeight'] = maxWeight;
    if (currentPassword != null) body['currentPassword'] = currentPassword;
    if (newPassword != null) body['newPassword'] = newPassword;
    
    final response = await http.put(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.userProfile}'),
      headers: await _getAuthHeaders(),
      body: jsonEncode(body),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to update profile: ${response.body}');
    }
  }
  
  // Training Programs
  Future<Map<String, dynamic>> getProgram(int weekNumber, double maxWeight) async {
    final response = await http.get(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.programByWeek(weekNumber)}?maxWeight=$maxWeight'),
      headers: await _getAuthHeaders(),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get program: ${response.body}');
    }
  }
  
  Future<List<dynamic>> getPrograms(int fromWeek, int toWeek, double maxWeight) async {
    final response = await http.get(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.programs}?fromWeek=$fromWeek&toWeek=$toWeek&maxWeight=$maxWeight'),
      headers: await _getAuthHeaders(),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get programs: ${response.body}');
    }
  }
  
  // Analytics
  Future<Map<String, dynamic>> recordWorkoutSession(Map<String, dynamic> session) async {
    final response = await http.post(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.analyticsWorkoutSessions}'),
      headers: await _getAuthHeaders(),
      body: jsonEncode(session),
    );
    
    if (response.statusCode == 201) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to record workout: ${response.body}');
    }
  }
  
  Future<List<dynamic>> getWorkoutSessions({
    DateTime? startDate,
    DateTime? endDate,
    int? weekNumber,
  }) async {
    var url = '${ApiConfig.baseUrl}${ApiConfig.analyticsWorkoutSessions}';
    final params = <String>[];
    
    if (startDate != null) params.add('startDate=${startDate.toIso8601String()}');
    if (endDate != null) params.add('endDate=${endDate.toIso8601String()}');
    if (weekNumber != null) params.add('weekNumber=$weekNumber');
    
    if (params.isNotEmpty) {
      url += '?${params.join('&')}';
    }
    
    final response = await http.get(
      Uri.parse(url),
      headers: await _getAuthHeaders(),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get workout sessions: ${response.body}');
    }
  }
  
  Future<Map<String, dynamic>> getOverallStats() async {
    final response = await http.get(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.analyticsStats}'),
      headers: await _getAuthHeaders(),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get stats: ${response.body}');
    }
  }
  
  Future<Map<String, dynamic>> getProgressData(String period, String metric) async {
    final response = await http.get(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.analyticsProgress}?period=$period&metric=$metric'),
      headers: await _getAuthHeaders(),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get progress data: ${response.body}');
    }
  }
  
  Future<Map<String, dynamic>> getPersonalRecords() async {
    final response = await http.get(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.analyticsRecords}'),
      headers: await _getAuthHeaders(),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get personal records: ${response.body}');
    }
  }
  
  // Sync
  Future<List<dynamic>> getSavedPrograms({DateTime? since}) async {
    var url = '${ApiConfig.baseUrl}${ApiConfig.syncPrograms}';
    if (since != null) {
      url += '?since=${since.toIso8601String()}';
    }
    
    final response = await http.get(
      Uri.parse(url),
      headers: await _getAuthHeaders(),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to get saved programs: ${response.body}');
    }
  }
  
  Future<Map<String, dynamic>> saveProgram(Map<String, dynamic> program) async {
    final response = await http.post(
      Uri.parse('${ApiConfig.baseUrl}${ApiConfig.syncPrograms}'),
      headers: await _getAuthHeaders(),
      body: jsonEncode(program),
    );
    
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to save program: ${response.body}');
    }
  }
}