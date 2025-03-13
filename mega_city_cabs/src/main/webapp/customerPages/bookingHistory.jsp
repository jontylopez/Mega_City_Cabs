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
        <!-- 🔹 Rating Modal -->
        <div id="ratingModal" class="modal-overlay" style="display: none;">
            <div class="modal-box">
                <span class="close" onclick="closeRatingModal()">&times;</span>
                <h4><i class="bi bi-star-fill"></i> Rate Your Trip</h4>
                <hr>

                <input type="hidden" id="ratingReservationId">

                <label>Trip Rating:</label>
                <input type="number" class="form-control" id="tripRating" min="1" max="5" step="0.1">

                <label>Vehicle Rating:</label>
                <input type="number" class="form-control" id="vehicleRating" min="1" max="5" step="0.1">

                <label>Driver Rating:</label>
                <input type="number" class="form-control" id="driverRating" min="1" max="5" step="0.1">

                <label>Comment:</label>
                <textarea id="ratingComment" class="form-control" rows="3"></textarea>

                <div class="modal-buttons text-center">
                    <button class="btn btn-success" onclick="submitRating()">
                        <i class="bi bi-check-circle"></i> Submit Rating
                    </button>
                    <button class="btn btn-secondary" onclick="closeRatingModal()">
                        <i class="bi bi-x-circle"></i> Cancel
                    </button>
                </div>
            </div>
        </div>

        <script src="../js/customer.js"></script>
    </body>
</html>
