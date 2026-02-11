import 'package:flutter/foundation.dart';
import '../models/training_program.dart';
import '../services/api_service.dart';

class ProgramProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  List<TrainingProgram> _programs = [];
  TrainingProgram? _currentProgram;
  bool _isLoading = false;
  String? _errorMessage;
  
  List<TrainingProgram> get programs => _programs;
  TrainingProgram? get currentProgram => _currentProgram;
  bool get isLoading => _isLoading;
  String? get errorMessage => _errorMessage;
  
  Future<void> loadProgram(int weekNumber, double maxWeight) async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      final data = await _apiService.getProgram(weekNumber, maxWeight);
      _currentProgram = TrainingProgram.fromJson(data);
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      _errorMessage = e.toString();
      _isLoading = false;
      notifyListeners();
    }
  }
  
  Future<void> loadPrograms(int fromWeek, int toWeek, double maxWeight) async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();
    
    try {
      final data = await _apiService.getPrograms(fromWeek, toWeek, maxWeight);
      _programs = data.map((p) => TrainingProgram.fromJson(p)).toList();
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