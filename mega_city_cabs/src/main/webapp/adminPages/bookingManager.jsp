<%-- 
    Document   : bookingManager
    Created on : Feb 6, 2025, 11:40:15 AM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Manager | Mega City Cabs</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/bookingManager.css">
</head>
<body>

<div class="container">
    <h2 class="mb-4 text-center">📋 Booking Manager</h2>

    <!-- 🔍 Search Bar -->
    <div class="search-container">
        <input type="text" id="searchInput" class="form-control" placeholder="Search by ID, User, Vehicle, Driver, Status..." onkeyup="filterReservations()">
    </div>

    <!-- 📌 Reservations Table -->
    <div class="table-responsive mt-3">
        <table class="table table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Booking ID</th>
                    <th>Customer</th>
                    <th>Vehicle Category</th>
                    <th>Driver</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Final Price</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody id="reservationTable">
                <tr>
                    <td colspan="8" class="text-center">Loading reservations...</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script src="../js/admin.js"></script>

</body>
</html>
