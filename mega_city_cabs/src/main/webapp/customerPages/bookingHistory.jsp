<%-- 
    Document   : bookingHistory
    Created on : Feb 7, 2025, 10:13:42 AM
    Author     : Janith
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Booking History</title>
<link rel="stylesheet" href="../css/bookingHistory.css">
</head>
<body>
    <div class="booking-container">
        <h2 class="booking-title">Booking History</h2>
        <p class="booking-description">View all your past rides here.</p>
        
        <div class="refresh-container">
            <button class="btn btn-refresh" onclick="loadBookingHistory()">
                <i class="fa fa-refresh"></i> Refresh
            </button>
        </div>
        
        <div class="table-responsive">
            <table class="booking-table">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Pickup Location</th>
                        <th>Vehicle</th>
                        <th>Driver</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="bookingHistoryTable">
                    <!-- Table content will be dynamically loaded here -->
                    <tr>
                        <td colspan="8" class="no-data-message">Loading booking history...</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    
    <script>
        // Load booking history when page loads
        document.addEventListener('DOMContentLoaded', function() {
            loadBookingHistory();
        });
    </script>
</body>
</html>