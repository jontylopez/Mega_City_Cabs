<%-- 
    Document   : vehicleManager
    Created on : Feb 6, 2025, 11:42:09 AM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Vehicle Manager | Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    </head>
    <body class="p-4">

        <div class="container">
            <h2 class="mb-4"><i class="bi bi-car-front"></i> Vehicle Manager</h2>

            <!-- Add Vehicle Form -->
            <div class="card mb-4 shadow-sm border-0">
                <div class="card-header bg-warning text-dark fw-bold">
                    <i class="bi bi-plus-circle"></i> Add New Vehicle
                </div>
                <div class="card-body">
                    <form id="vehicleForm" class="row g-3">

                        <!-- Category Selection -->
                        <div class="col-md-6">
                            <label for="vehicleCategory" class="form-label fw-semibold">
                                <i class="bi bi-list"></i> Select Category
                            </label>
                            <select class="form-select" id="vehicleCategory" required></select>
                        </div>

                        <!-- Vehicle Number -->
                        <div class="col-md-6">
                            <label for="vehicleNo" class="form-label fw-semibold">
                                <i class="bi bi-car-front"></i> Vehicle Number
                            </label>
                            <input type="text" class="form-control" id="vehicleNo" placeholder="Enter Vehicle Number" required>
                        </div>

                        <!-- Registration Expiry Date -->
                        <div class="col-md-6">
                            <label for="regExpDate" class="form-label fw-semibold">
                                <i class="bi bi-calendar-check"></i> Registration Expiry Date
                            </label>
                            <input type="date" class="form-control" id="regExpDate" required>
                        </div>

                        <!-- Submit Button -->
                        <div class="col-md-6 d-flex align-items-end">
                            <button type="submit" class="btn btn-success w-100">
                                <i class="bi bi-plus-lg"></i> Add Vehicle
                            </button>
                        </div>

                    </form>
                </div>
            </div>


            <!-- Vehicle List -->
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">Vehicle List</div>
                <div class="card-body">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Category</th>
                                <th>Vehicle No</th>
                                <th>Reg Expiry Date</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="vehicleTableBody">
                            <!-- Vehicles will be dynamically inserted here -->
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Update Vehicle Form -->
            <div class="card mb-4 shadow-sm" id="updateVehicleForm" style="display: none;">
                <div class="card-header bg-primary text-white">Edit Vehicle</div>
                <div class="card-body">
                    <input type="hidden" id="updateVehicleId">
                    <div class="mb-3">
                        <label class="form-label">Category</label>
                        <select class="form-control" id="updateVehicleCategory"></select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Vehicle Number</label>
                        <input type="text" class="form-control" id="updateVehicleNo">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Registration Expiry Date</label>
                        <input type="date" class="form-control" id="updateRegExpDate">


                    </div>
                    <div class="mb-3">
                        <label class="form-label">Status</label>
                        <select class="form-control" id="updateStat">
                            <option value="Active">Active</option>
                            <option value="Suspended">Suspended</option>
                        </select>
                    </div>
                    <button class="btn btn-primary" onclick="submitVehicleUpdate()">Update Vehicle</button>
                    <button class="btn btn-secondary" onclick="cancelVehicleUpdate()">Cancel</button>
                </div>
            </div>

        </div>

        <script src="../js/admin.js"></script>
    </body>
</html>

