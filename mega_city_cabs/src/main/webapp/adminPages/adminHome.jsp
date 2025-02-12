<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard | Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/adminHome.css">
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

                <li>
                    <a href="adminDash.jsp" class="nav-link text-white">
                        <i class="bi bi-house-door me-2"></i> Home</a>
                </li>
                <li>
                    <a href="bookingManager.jsp" class="nav-link text-white">
                        <i class="bi bi-calendar-week me-2"></i> Booking Manager</a>
                </li>
                <li>
                    <a href="categoryManager.jsp" class="nav-link text-white">
                        <i class="bi bi-tags me-2"></i> Category Manager</a>
                </li>
                <li>
                    <a href="vehicleManager.jsp" class="nav-link text-white">
                        <i class="bi bi-car-front me-2"></i> Vehicle Manager</a>
                </li>
                <li>
                    <a href="driverManager.jsp" class="nav-link text-white">
                        <i class="bi bi-person-vcard me-2"></i> Driver Manager</a>
                </li>
                <li>
                    <a href="discountManager.jsp" class="nav-link text-white">
                        <i class="bi bi-percent me-2"></i> Discount Manager</a>
                </li>
                <li>
                    <a href="userManager.jsp" class="nav-link text-white">
                        <i class="bi bi-people me-2"></i> User Manager</a>
                </li>
            </ul>
            <hr>
            <div class="dropdown">
                <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" data-bs-toggle="dropdown">
                    <img src="https://github.com/mdo.png" alt="" width="32" height="32" class="rounded-circle me-2">
                    <strong id="userText">User</strong>
                </a>
                <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                    <li><a class="dropdown-item profile-link" href="../userPages/profile.jsp">Profile</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#" onclick="logout()">Sign out</a></li>
                </ul>
            </div>

        </div>

        <!-- Main Content -->
        <div class="main-content">

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="../js/admin.js" defer></script>



    </body>
</html>
