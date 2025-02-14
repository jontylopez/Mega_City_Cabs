<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Book a Ride | Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
        <link rel="stylesheet" href="../css/booking.css">
    </head>
    <body class="p-4 bg-dark">

        <div class="container booking-container">
            <div class="card booking-card">
                <div class="card-header">
                    <h3><i class="bi bi-car-front"></i> Book a Ride</h3>
                </div>
                <div class="card-body">

                    <!-- Select Category -->
                    <div class="text-center mb-3">
                        <h5><i class="bi bi-tags"></i> Select a Category</h5>
                        <div class="d-flex flex-wrap justify-content-center" id="categoryList">
                            <!-- Categories will be loaded dynamically -->
                        </div>
                    </div>

                    <!-- Hidden Input to Store Selected Category -->
                    <input type="hidden" id="selectedCategoryId">

                    <!-- Booking Options (Hidden by Default) -->
                    <div id="bookingOptions" class="mt-4" style="display: none;">
                        <h4><i class="bi bi-calendar-check"></i> Select Booking Type</h4>

                        <!-- Booking Type Selection -->
                        <div class="btn-group w-100">
                            <button type="button" class="btn btn-gold" onclick="showPerDayBooking()">Per Day</button>
                            <button type="button" class="btn btn-gold" onclick="showPerMileageBooking()">Per Mileage</button>
                        </div>

                        <!-- Per Day Booking Details -->
                        <div id="perDayDetails" class="mt-3" style="display: none;">
                            <h5 class="text-center"><i class="bi bi-calendar"></i> Per Day Booking</h5>


                            <div class="mb-3">
                                <label>Per Day Charge:</label>
                                <input type="text" id="perDayPrice" class="form-control" disabled>
                            </div>

                            <div class="mb-3">
                                <label>Maximum Kilometers Per Day:</label>
                                <input type="text" id="maxKmPerDay" class="form-control" disabled>
                            </div>

                            <div class="mb-3">
                                <label>Extra Km Charge:</label>
                                <input type="text" id="extraKmCharge" class="form-control" disabled>
                                <small class="text-danger">* Additional kms will be charged at Rs <span id="extraKmRate"></span> per km</small>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <label>Start Date:</label>
                                    <input type="date" class="form-control" id="startDate" onchange="updateBookingDate()">
                                </div>
                                <div class="col-md-6">
                                    <label>End Date:</label>
                                    <input type="date" class="form-control" id="endDate">
                                </div>
                            </div>
                        </div>

                        <!-- Per Mileage Booking Details -->
                        <div id="perMileageDetails" class="mt-3" style="display: none;">
                            <h5 class="text-center"><i class="bi bi-speedometer2"></i> Per Mileage Booking</h5>

                            <div class="row">
                                <!-- Package 1 -->
                                <div class="col-md-6">
                                    <div class="card p-3 mb-3 text-center package-option" id="package1" onclick="selectPackage(1)">
                                        <h6 class="fw-bold">Package 1</h6>
                                        <p>Up to 50 Km</p>
                                        <p>Included Waiting Hours: <span id="pkg1Hrs"></span> hrs</p>
                                        <p>Waiting Charge: Rs <span id="waitingCharge1"></span>/hr</p>
                                        <p>Extra Km Charge: Rs <span id="extraKmCharge1"></span>/Km</p>
                                        <h5>Rs <span id="milePkg1Price"></span></h5>
                                    </div>
                                </div>

                                <!-- Package 2 -->
                                <div class="col-md-6">
                                    <div class="card p-3 mb-3 text-center package-option" id="package2" onclick="selectPackage(2)">
                                        <h6 class="fw-bold">Package 2</h6>
                                        <p>Up to 100 Km</p>
                                        <p>Included Waiting Hours: <span id="pkg2Hrs"></span> hrs</p>
                                        <p>Waiting Charge: Rs <span id="waitingCharge2"></span>/hr</p>
                                        <p>Extra Km Charge: Rs <span id="extraKmCharge2"></span>/Km</p>
                                        <h5>Rs <span id="milePkg2Price"></span></h5>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <!-- Final Booking Details -->
                        <div id="finalBookingDetails" class="mt-3">
                            <h5 class="text-center"><i class="bi bi-pencil-square"></i> Final Booking Details</h5>

                            <div class="d-flex flex-wrap gap-3">
                                <div class="flex-grow-1">
                                    <label>Booking Date:</label>
                                    <input type="date" class="form-control" id="bookingDate">
                                </div>
                                <div class="flex-grow-1">
                                    <label>Booking Time:</label>
                                    <input type="time" class="form-control" id="bookingTime">
                                </div>
                            </div>
                        </div>


                        <div class="mb-3">
                            <label>Pickup Address:</label>
                            <input type="text" class="form-control" id="pickupAddress" placeholder="Enter pickup location">
                        </div>
                        <!-- Check Availability Button -->
                        <div class="text-center">
                            <button type="button" class="btn btn-gold" onclick="checkAvailability()">
                                <i class="bi bi-search"></i> Check Availability
                            </button>
                        </div>

                        <!-- Availability Results in One Line -->
                        <div class="mt-3 d-flex justify-content-center gap-3">
                            <p id="vehicleAvailability" class="text-warning mb-0"></p>
                            <p id="driverAvailability" class="text-warning mb-0"></p>
                        </div>
                        <!-- Reserve the Booking Button (Initially Hidden) -->
                        <div class="text-center mt-3">
                            <button id="showSummaryBtn" class="btn btn-success" style="display: none;" onclick="showBookingSummary()">
                                <i class="bi bi-receipt"></i> Show Booking Summary
                            </button>
                        </div>

                    </div>

                </div>
            </div>
        </div>
        <!-- Booking Summary Modal -->
        <div id="bookingSummary" class="modal-overlay" style="display: none;">
            <div class="modal-box">
                <h4><i class="bi bi-receipt"></i> Booking Summary</h4>
                <hr>

                <div class="summary-content">
                    <p><strong>Category:</strong> <span id="summaryCategory"></span></p>
                    <p><strong>Start Date:</strong> <span id="summaryStartDate"></span></p>
                    <p><strong>End Date:</strong> <span id="summaryEndDate"></span></p>
                    <p><strong>Number of Days:</strong> <span id="summaryDays"></span></p>
                    <p><strong>Total Amount:</strong> Rs <span id="summaryAmount"></span></p>
                </div>

                <!-- Terms & Conditions -->
                <h5 class="text-center mt-3"><i class="bi bi-exclamation-triangle"></i> Terms & Conditions</h5>
                <ul class="terms-list">
                    <li>You will be charged the full amount upon confirmation.</li>
                    <li>Additional kilometers will be charged separately.</li>
                    <li>Waiting charges apply if you exceed included hours.</li>
                    <li>No refund upon cancellation within 24 hours.</li>
                </ul>

                <!-- Checkbox for Terms & Conditions -->
                <div class="text-center mt-2">
                    <input type="checkbox" id="termsCheckbox" onchange="togglePayButton()">
                    <label for="termsCheckbox"> I agree to the Terms & Conditions</label>
                </div>

                <!-- Buttons -->
                <div class="modal-buttons text-center">
                    <button id="confirmPayBtn" class="btn btn-success" disabled onclick="confirmPayment()">
                        <i class="bi bi-check-circle"></i> Confirm & Pay
                    </button>
                    <button class="btn btn-secondary" onclick="closeBookingSummary()">
                        <i class="bi bi-arrow-left"></i> Back
                    </button>
                </div>
            </div>
        </div>


        <script src="../js/customer.js"></script>
    </body>
</html>
