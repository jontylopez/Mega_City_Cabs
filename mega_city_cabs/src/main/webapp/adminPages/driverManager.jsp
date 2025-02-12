<%-- 
    Document   : driverManager
    Created on : Feb 6, 2025, 11:42:25 AM
    Author     : Janith
--%>

<%-- 
    Document   : driverManager
    Created on : Feb 6, 2025, 11:42:25 AM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Driver Manager | Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    </head>
    <body class="p-4">

        <div class="container">
            <h2 class="mb-4"><i class="bi bi-person-badge"></i> Driver Manager</h2>

<!-- Add Driver Form -->
<div class="card mb-4 shadow-sm">
    <div class="card-header bg-warning text-dark">Add New Driver</div>
    <div class="card-body">
        <form id="driverForm">
            <div class="mb-3">
                <label class="form-label">Driver Name</label>
                <input type="text" class="form-control" id="dName" placeholder="Enter Driver Name" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Address</label>
                <textarea class="form-control" id="dAddress" rows="2" placeholder="Enter Address" required></textarea>
            </div>

            <!-- ✅ Use Bootstrap Grid for structured layout -->
            <div class="row">
                <div class="col-md-4">
                    <label class="form-label">Telephone</label>
                    <input type="text" class="form-control" id="dTel" placeholder="Enter Telephone Number" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label">License Number</label>
                    <input type="text" class="form-control" id="dLNum" placeholder="Enter License Number" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label">License Expiry Date</label>
                    <input type="date" class="form-control" id="dLExpDate" required>
                </div>
            </div>

            <div class="text-center mt-3">
                <button type="submit" class="btn btn-primary"><i class="bi bi-plus-circle"></i> Add Driver</button>
            </div>
        </form>
    </div>
</div>


            <!-- Driver List -->
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">Driver List</div>
                <div class="card-body">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Address</th>
                                <th>Telephone</th>
                                <th>License Number</th>
                                <th>License Expiry</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="driverTableBody">
                            <!-- Drivers will be dynamically inserted here -->
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Update Driver Form -->
            <div class="card mb-4 shadow-sm" id="updateDriverForm" style="display: none;">
                <div class="card-header bg-primary text-white">Edit Driver</div>
                <div class="card-body">
                    <input type="hidden" id="updateDriverId">
                    <div class="mb-3">
                        <label class="form-label">Driver Name</label>
                        <input type="text" class="form-control" id="updateDName">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Address</label>
                        <textarea class="form-control" id="updateDAddress" rows="2"></textarea>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Telephone</label>
                        <input type="text" class="form-control" id="updateDTel">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">License Number</label>
                        <input type="text" class="form-control" id="updateDLNum">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">License Expiry Date</label>
                        <input type="date" class="form-control" id="updateDLExpDate">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Status</label>
                        <select class="form-control" id="updateStat">
                            <option value="Active">Active</option>
                            <option value="Suspended">Suspended</option>
                        </select>
                    </div>
                    <button class="btn btn-primary" onclick="submitDriverUpdate()"><i class="bi bi-pencil-square"></i> Update Driver</button>
                    <button class="btn btn-secondary" onclick="cancelDriverUpdate()"><i class="bi bi-x-circle"></i> Cancel</button>
                </div>
            </div>

        </div>

        <script src="../js/admin.js"></script>
    </body>
</html>
