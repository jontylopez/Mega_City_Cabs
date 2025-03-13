<%-- 
    Document   : customerHome
    Created on : Feb 4, 2025, 10:55:33 PM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Customer Dashboard | Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/customerHome.css">
        <link rel="icon" type="image/svg+xml" href="../images/favicon.svg">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>

    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar d-flex flex-column flex-shrink-0 p-3">
            <a href="customerHome.jsp" class="d-flex align-items-center mb-3 text-white text-decoration-none">
                <i class="bi bi-speedometer2 me-2"></i>
                <span class="fs-4">Customer Panel</span>
            </a>
            <hr>
            <ul class="nav nav-pills flex-column mb-auto">
                <li><a href="customerDash.jsp" class="nav-link text-white"><i class="bi bi-house-door me-2"></i> Home</a></li>
                <li><a href="bookRide.jsp" class="nav-link text-white"><i class="bi bi-car-front me-2"></i> Book a Ride</a></li>
                <li><a href="bookingHistory.jsp" class="nav-link text-white"><i class="bi bi-clock-history me-2"></i> Booking History</a></li>
            </ul>
            <hr>
            <div class="dropdown">
                <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" data-bs-toggle="dropdown">
                    <img src="https://github.com/mdo.png" alt="" width="32" height="32" class="rounded-circle me-2">
                    <strong id="userText">User</strong>
                </a>
                <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                    <li><a class="dropdown-item profile-link" href="../customerPages/customerProfile.jsp">Profile</a></li>
                    <li><a class="dropdown-item profile-link" href="../customerPages/customerHelp.jsp">Help</a></li> <!-- ✅ Added Help Link -->
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#" onclick="logout()">Sign out</a></li>
                </ul>
            </div>
        </div>

        <!-- Main Content -->
        <div class="main-content"></div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script src="../js/customer.js" defer></script>

    </body>
</html>

