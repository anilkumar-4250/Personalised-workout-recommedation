<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fitness Planner</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 70px;
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 600px;
            margin: auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/fitness/">Fitness Planner</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
</nav>

<div class="container form-container mt-5">
    <h1 class="mb-4 text-center">Personal Fitness Planner</h1>
    <form method="post" action="/fitness/recommend">
        <div class="mb-3">
            <label for="fitnessLevelofuser" class="form-label">Fitness Level</label>
            <select class="form-select" id="fitnessLevelofuser" name="fitnessLevelofuser" required>
                <option value="beginner">Beginner</option>
                <option value="intermediate">Intermediate</option>
                <option value="advanced">Advanced</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="exerciseDetailsofuser" class="form-label">exerciseDetails</label>
            <textarea class="form-control" id="exerciseDetailsofuser" name="exerciseDetailsofuser" rows="3" placeholder="Describe your Target Muscle" required></textarea>
        </div>
        <div class="mb-3">
            <label for="usersGoals" class="form-label">Fitness Goals</label>
            <textarea class="form-control" id="usersGoals" name="usersGoals" rows="2" placeholder="E.g., weight loss, muscle gain" required></textarea>
        </div>
        <div class="mb-3">
            <label for="injuryNotesandlimitations" class="form-label">Injury/Health Notes</label>
            <textarea class="form-control" id="injuryNotesandlimitations" name="injuryNotesandlimitations" rows="2" placeholder="Mention any injuries or limitations"></textarea>
        </div>
        <div class="d-grid">
            <button type="submit" class="btn btn-primary btn-lg">Generate Plan</button>
        </div>
    </form>
</div>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
