import 'package:flutter/foundation.dart';
import '../services/api_service.dart';

class SyncProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  bool _isSyncing = false;
  DateTime? _lastSyncTime;
  String? _errorMessage;
  
  bool get isSyncing => _isSyncing;
  DateTime? get lastSyncTime => _lastSyncTime;
  String? get errorMessage => _errorMessage;
  
  Future<void> syncPrograms() async {
    _isSyncing = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      await _apiService.getSavedPrograms(since: _lastSyncTime);
      _lastSyncTime = DateTime.now();
      _isSyncing = false;
      notifyListeners();
    } catch (e) {
      _errorMessage = e.toString();
      _isSyncing = false;
      notifyListeners();
    }
  }
  
  Future<void> saveProgram(Map<String, dynamic> program) async {
    _isSyncing = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      await _apiService.saveProgram(program);
      _isSyncing = false;
      notifyListeners();
    } catch (e) {
      _errorMessage = e.toString();
      _isSyncing = false;
      notifyListeners();
    }
  }
  
  void clearError() {
    _errorMessage = null;
    notifyListeners();
  }
}