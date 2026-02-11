class User {
  final String id;
  final String username;
  final String email;
  final double? maxWeight;
  final DateTime createdAt;

  User({
    required this.id,
    required this.username,
    required this.email,
    this.maxWeight,
    required this.createdAt,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'].toString(),
      username: json['username'],
      email: json['email'],
      maxWeight: json['maxWeight']?.toDouble(),
      createdAt: DateTime.parse(json['createdAt']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'username': username,
      'email': email,
      'maxWeight': maxWeight,
      'createdAt': createdAt.toIso8601String(),
    };
  }
}