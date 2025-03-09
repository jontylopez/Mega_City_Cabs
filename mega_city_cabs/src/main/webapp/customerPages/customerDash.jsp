<%-- 
    Document   : customerDash
    Created on : Feb 7, 2025, 10:11:58 AM
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="../css/customerDash.css">
</head>
<body>
    <!-- Header Section with Background -->
    <div class="dashboard-header">
        <div class="container">
            <h1 class="welcome-heading">Welcome to Mega City Cabs</h1>
            <p class="welcome-subtext">Book your ride now and enjoy a smooth journey across the city</p>
        </div>
    </div>

    <div class="container dashboard-container">
        <div class="row">
            <!-- Your Next Trip Card -->
            <div class="col-lg-8 mb-4">
                <div class="card next-trip-card">
                    <div class="card-header">
                        <h3><i class="fas fa-car-side"></i> Your Next Trip</h3>
                    </div>
                    <div class="card-body">
                        <form id="tripDetailsForm" class="trip-details-form">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <div class="trip-detail-item">
                                        <label for="tripId">Trip ID</label>
                                        <input type="text" id="tripId" class="form-control" readonly>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <div class="trip-detail-item">
                                        <label for="tripStatus">Status</label>
                                        <input type="text" id="tripStatus" class="form-control status-input" readonly>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <div class="trip-detail-item">
                                        <label for="tripStartDate"><i class="far fa-calendar"></i> Start Date</label>
                                        <input type="text" id="tripStartDate" class="form-control" readonly>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <div class="trip-detail-item">
                                        <label for="tripEndDate"><i class="far fa-calendar-check"></i> End Date</label>
                                        <input type="text" id="tripEndDate" class="form-control" readonly>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <div class="trip-detail-item">
                                        <label for="tripStartTime"><i class="far fa-clock"></i> Pickup Time</label>
                                        <input type="text" id="tripStartTime" class="form-control" readonly>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <div class="trip-detail-item">
                                        <label for="tripLocation"><i class="fas fa-map-marker-alt"></i> Pickup Location</label>
                                        <input type="text" id="tripLocation" class="form-control" readonly>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <div class="trip-detail-item">
                                        <label for="tripVehicle"><i class="fas fa-taxi"></i> Vehicle Category</label>
                                        <input type="text" id="tripVehicle" class="form-control" readonly>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <div class="trip-detail-item">
                                        <label for="tripDriver"><i class="fas fa-user"></i> Driver</label>
                                        <input type="text" id="tripDriver" class="form-control" readonly>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            
            <!-- Quick Actions Card -->
            <div class="col-lg-4 mb-4">
                <div class="card action-card">
                    <div class="card-header">
                        <h3><i class="fas fa-bolt"></i> Quick Actions</h3>
                    </div>
                    <div class="card-body">
                        <div class="action-buttons">
                            <button class="btn btn-gold w-100 mb-3" onclick="loadPage('bookRide.jsp')">
                                <i class="fas fa-plus-circle"></i> Book a New Ride
                            </button>
                            <button class="btn btn-outline-secondary w-100 mb-3" onclick="loadPage('bookingHistory.jsp')">
                                <i class="fas fa-history"></i> View Booking History
                            </button>
                            <button class="btn btn-outline-info w-100" onclick="refreshDashboard()">
                                <i class="fas fa-sync-alt"></i> Refresh Dashboard
                            </button>
                        </div>
                    </div>
                </div>
                
                <!-- Customer Support Card -->
                <div class="card support-card mt-4">
                    <div class="card-header">
                        <h3><i class="fas fa-headset"></i> Need Help?</h3>
                    </div>
                    <div class="card-body">
                        <p>Our support team is available 24/7</p>
                        <a href="#" class="btn btn-outline-success w-100">
                            <i class="fas fa-phone-alt"></i> Contact Support
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Function to refresh dashboard data
        function refreshDashboard() {
            // You can implement this function in your customer.js file
            console.log("Refreshing dashboard data...");
            // Add code to refresh trip details
        }
    </script>
    <script src="../js/customer.js"></script>
</body>
</html>