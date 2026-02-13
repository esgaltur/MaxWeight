# Analytics API Endpoints

## Overview
Analytics endpoints provide workout statistics, progress tracking, and data visualization support for mobile/web clients.

## Endpoints

### POST /api/analytics/workout-sessions
Record a completed workout session.

**Request Body:**
```json
{
  "weekNumber": 1,
  "dayNumber": 1,
  "maxWeight": 100.0,
  "exercises": [
    {
      "setNumber": 1,
      "plannedWeight": 80.0,
      "plannedReps": 5,
      "actualWeight": 80.0,
      "actualReps": 5,
      "completed": true
    }
  ],
  "duration": 3600,
  "notes": "Felt strong today"
}
```

**Response (201 Created):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "userId": "user123",
  "weekNumber": 1,
  "dayNumber": 1,
  "completedAt": "2024-01-15T10:30:00Z",
  "totalSets": 5,
  "totalReps": 25,
  "totalVolume": 2000.0,
  "duration": 3600
}
```

### GET /api/analytics/workout-sessions
Get all workout sessions for the current user.

**Query Parameters:**
- `startDate` (optional): Filter by start date (ISO 8601)
- `endDate` (optional): Filter by end date (ISO 8601)
- `weekNumber` (optional): Filter by week number

**Response (200 OK):**
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "weekNumber": 1,
    "dayNumber": 1,
    "completedAt": "2024-01-15T10:30:00Z",
    "totalVolume": 2000.0,
    "duration": 3600
  }
]
```

### GET /api/analytics/stats
Get overall workout statistics.

**Response (200 OK):**
```json
{
  "totalWorkouts": 42,
  "totalVolume": 84000.0,
  "averageWorkoutDuration": 3600,
  "currentStreak": 7,
  "longestStreak": 14,
  "workoutsThisWeek": 3,
  "workoutsThisMonth": 12,
  "lastWorkoutDate": "2024-01-15T10:30:00Z"
}
```

### GET /api/analytics/progress
Get progress data for charts/graphs.

**Query Parameters:**
- `period`: `week`, `month`, `year`, `all`
- `metric`: `volume`, `weight`, `reps`, `duration`

**Response (200 OK):**
```json
{
  "period": "month",
  "metric": "volume",
  "dataPoints": [
    {
      "date": "2024-01-01",
      "value": 2000.0
    },
    {
      "date": "2024-01-08",
      "value": 2200.0
    }
  ],
  "trend": "increasing",
  "percentageChange": 10.0
}
```

### GET /api/analytics/personal-records
Get personal records for exercises.

**Response (200 OK):**
```json
{
  "maxWeightLifted": 120.0,
  "maxVolumeSession": 3000.0,
  "maxRepsInSet": 12,
  "records": [
    {
      "weekNumber": 3,
      "dayNumber": 1,
      "weight": 120.0,
      "achievedAt": "2024-01-15T10:30:00Z"
    }
  ]
}
```