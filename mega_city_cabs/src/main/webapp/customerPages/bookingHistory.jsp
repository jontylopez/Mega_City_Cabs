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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
                            <th>ID</th>
                            <th>Vehicle Category</th>
                            <th>Driver</th>
                            <th>Start Date</th>
                            <th>Start Time</th>
                            <th>Pickup Location</th>
                            <th>End Date</th>
                            <th>Discount (%)</th>
                            <th>Status </th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="bookingHistoryTable">
                        <tr>
                            <td colspan="9" class="no-data-message">Loading booking history...</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <script src="../js/customer.js"></script>
    </body>
</html>
