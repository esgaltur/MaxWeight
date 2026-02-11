import 'package:flutter/foundation.dart';
import '../services/api_service.dart';

class AnalyticsProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  Map<String, dynamic>? _overallStats;
  Map<String, dynamic>? _progressData;
  Map<String, dynamic>? _personalRecords;
  List<dynamic> _workoutSessions = [];
  bool _isLoading = false;
  String? _errorMessage;
  
  Map<String, dynamic>? get overallStats => _overallStats;
  Map<String, dynamic>? get progressData => _progressData;
  Map<String, dynamic>? get personalRecords => _personalRecords;
  List<dynamic> get workoutSessions => _workoutSessions;
  bool get isLoading => _isLoading;
  String? get errorMessage => _errorMessage;
  
  Future<void> recordWorkout(Map<String, dynamic> session) async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      await _apiService.recordWorkoutSession(session);
      await loadOverallStats();
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _errorMessage = e.toString();
      _isLoading = false;
      notifyListeners();
    }
  }
  
  Future<void> loadWorkoutSessions({
    DateTime? startDate,
    DateTime? endDate,
    int? weekNumber,
  }) async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      _workoutSessions = await _apiService.getWorkoutSessions(
        startDate: startDate,
        endDate: endDate,
        weekNumber: weekNumber,
      );
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _errorMessage = e.toString();
      _isLoading = false;
      notifyListeners();
    }
  }
  
  Future<void> loadOverallStats() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      _overallStats = await _apiService.getOverallStats();
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _errorMessage = e.toString();
      _isLoading = false;
      notifyListeners();
    }
  }
  
  Future<void> loadProgressData(String period, String metric) async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      _progressData = await _apiService.getProgressData(period, metric);
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _errorMessage = e.toString();
      _isLoading = false;
      notifyListeners();
    }
  }
  
  Future<void> loadPersonalRecords() async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      _personalRecords = await _apiService.getPersonalRecords();
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _errorMessage = e.toString();
      _isLoading = false;
      notifyListeners();
    }
  }
  
  void clearError() {
    _errorMessage = null;
    notifyListeners();
  }
}