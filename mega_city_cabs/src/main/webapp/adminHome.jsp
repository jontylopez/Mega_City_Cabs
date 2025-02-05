<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | Mega City Cabs</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <style>
        body {
            min-height: 100vh;
            display: flex;
        }
        .sidebar {
            width: 280px;
            height: 100vh;
            background-color: #343a40; /* Dark background */
            color: white;
            padding: 15px;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
        }
        .sidebar .nav-link:hover {
            background-color: #495057;
        }
        .main-content {
            flex-grow: 1;
            padding: 20px;
            background: #f8f9fa;
        }
        .dashboard-card {
            border-left: 4px solid #ffcc00;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>

    <!-- Sidebar -->
    <div class="sidebar d-flex flex-column flex-shrink-0 p-3">
        <a href="adminHome.jsp" class="d-flex align-items-center mb-3 text-white text-decoration-none">
            <i class="bi bi-speedometer2 me-2"></i>
            <span class="fs-4">Admin Panel</span>
        </a>
        <hr>
        <ul class="nav nav-pills flex-column mb-auto">
            <li class="nav-item">
                <a href="adminHome.jsp" class="nav-link active">
                    <i class="bi bi-house-door me-2"></i> Home
                </a>
            </li>
            <li>
                <a href="#" class="nav-link text-white">
                    <i class="bi bi-bar-chart me-2"></i> Dashboard
                </a>
            </li>
            <li>
                <a href="#" class="nav-link text-white">
                    <i class="bi bi-clipboard-check me-2"></i> Orders
                </a>
            </li>
            <li>
                <a href="#" class="nav-link text-white">
                    <i class="bi bi-box me-2"></i> Products
                </a>
            </li>
            <li>
                <a href="#" class="nav-link text-white">
                    <i class="bi bi-people me-2"></i> Customers
                </a>
            </li>
        </ul>
        <hr>
        <div class="dropdown">
            <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" data-bs-toggle="dropdown">
                <img src="https://github.com/mdo.png" alt="" width="32" height="32" class="rounded-circle me-2">
                <strong id="userText">User</strong>
            </a>
            <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                <li><a class="dropdown-item" href="#">Settings</a></li>
                <li><a class="dropdown-item" href="#">Profile</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#" onclick="logout()">Sign out</a></li>
            </ul>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <h1 class="mb-4">Dashboard</h1>
        <div class="row">
            <div class="col-md-4">
                <div class="dashboard-card">
                    <h4><i class="bi bi-person-fill"></i> Total Users</h4>
                    <p class="fs-3">245</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dashboard-card">
                    <h4><i class="bi bi-car-front-fill"></i> Active Rides</h4>
                    <p class="fs-3">87</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dashboard-card">
                    <h4><i class="bi bi-cash-stack"></i> Total Earnings</h4>
                    <p class="fs-3">$12,430</p>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
