<%-- 
    Document   : adminDash
    Created on : Mar 12, 2025
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard | Mega City Cabs</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/adminDash.css">
</head>
<body>
    <div class="container">
        <h1 class="mb-4 text-center">🚖 Admin Dashboard</h1>

        <!-- 🔹 Reservations Table -->
        <h3 class="mt-4">🔹 Pending Finalization Reservations</h3>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>Reservation ID</th>
                        <th>Customer</th>
                        <th>Final Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="reservationTable">
                    <tr>
                        <td colspan="5" class="text-center">Loading reservations...</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- 🔹 Finalization Modal -->
    <div id="finalizeModal" class="modal-overlay" style="display: none;">
        <div class="modal-box">
            <span class="close" onclick="closeFinalizeModal()">&times;</span>
            <h4><i class="bi bi-clipboard-check"></i> Finalize Trip</h4>
            <hr>
<input type="hidden" id="finalizeReservationId">
            <p><strong>Start Date:</strong> <span id="modalStartDate"></span></p>
            <p><strong>End Date:</strong> <span id="modalEndDate"></span></p>

            <div class="mb-3">
                <label>Extra KM Used:</label>
                <input type="number" class="form-control" id="extraKm" min="0">
            </div>

            <div class="mb-3">
                <label>Extra Waiting Hours:</label>
                <input type="number" class="form-control" id="extraHrs" min="0">
            </div>

            <p><strong>Estimated Additional Charges:</strong> <span id="calculatedCharges">Rs. 0.00</span></p>

            <div class="modal-buttons text-center">
                <button class="btn btn-success" onclick="finalizeTrip()">
                    <i class="bi bi-check-circle"></i> Finalize
                </button>
                <button class="btn btn-secondary" onclick="closeFinalizeModal()">
                    <i class="bi bi-x-circle"></i> Cancel
                </button>
            </div>
        </div>
    </div>

    <script src="../js/admin.js"></script>
</body>
</html>
