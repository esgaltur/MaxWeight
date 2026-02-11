class Exercise {
  final int setNumber;
  final double weight;
  final int repetitions;
  final int percentage;

  Exercise({
    required this.setNumber,
    required this.weight,
    required this.repetitions,
    required this.percentage,
  });

  factory Exercise.fromJson(Map<String, dynamic> json) {
    return Exercise(
      setNumber: json['setNumber'],
      weight: json['weight'].toDouble(),
      repetitions: json['repetitions'],
      percentage: json['percentage'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'setNumber': setNumber,
      'weight': weight,
      'repetitions': repetitions,
      'percentage': percentage,
    };
  }
}

class Day {
  final int dayNumber;
  final List<Exercise> exercises;

  Day({
    required this.dayNumber,
    required this.exercises,
  });

  factory Day.fromJson(Map<String, dynamic> json) {
    return Day(
      dayNumber: json['dayNumber'],
      exercises: (json['exercises'] as List)
          .map((e) => Exercise.fromJson(e))
          .toList(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'dayNumber': dayNumber,
      'exercises': exercises.map((e) => e.toJson()).toList(),
    };
  }
}

class TrainingProgram {
  final int weekNumber;
  final List<Day> days;

  TrainingProgram({
    required this.weekNumber,
    required this.days,
  });

  factory TrainingProgram.fromJson(Map<String, dynamic> json) {
    return TrainingProgram(
      weekNumber: json['weekNumber'],
      days: (json['days'] as List).map((d) => Day.fromJson(d)).toList(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'weekNumber': weekNumber,
      'days': days.map((d) => d.toJson()).toList(),
    };
  }
}