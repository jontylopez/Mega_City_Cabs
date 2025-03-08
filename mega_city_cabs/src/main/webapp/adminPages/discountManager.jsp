<%-- 
    Document   : discountManager
    Created on : Feb 6, 2025, 11:42:53 AM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Discount Manager | Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

    </head>
    <body class="p-4 bg-dark">

        <div class="container discount-container">
            <div class="card discount-card">
                <div class="card-header">
                    <h3><i class="bi bi-tag"></i> Discount Manager</h3>
                </div>
                <div class="card-body">

                    <!-- Add Discount Form -->
                    <h5 class="text-center"><i class="bi bi-plus-circle"></i> Add New Discount</h5>
                    <form id="discountForm" class="mb-4">
                        <div class="row">
                            <div class="col-md-3">
                                <label>Discount Percentage:</label>
                                <input type="number" step="0.01" class="form-control" id="discountPercentage" placeholder="Enter %">
                            </div>
                            <div class="col-md-3">
                                <label>Start Date:</label>
                                <input type="date" class="form-control" id="startDate">
                            </div>
                            <div class="col-md-3">
                                <label>End Date:</label>
                                <input type="date" class="form-control" id="endDate">
                            </div>
                            <div class="col-md-3">
                                <label>Status:</label>
                                <select class="form-control" id="discountStatus">
                                    <option value="Active">Active</option>
                                    <option value="Inactive">Inactive</option>
                                </select>
                            </div>
                        </div>
                        <div class="text-center mt-3">
                            <button type="button" class="btn btn-gold" onclick="addDiscount()">
                                <i class="bi bi-check-circle"></i> Create Discount
                            </button>
                        </div>
                    </form>

                    <!-- ✅ Update Discount Form (Hidden by Default) -->
                    <div class="card mt-4" id="updateDiscountForm" style="display: none;">
                        <div class="card-header bg-success text-white">
                            <h5><i class="bi bi-pencil-square"></i> Update Discount</h5>
                        </div>
                        <div class="card-body">
                            <form id="discountUpdateForm">
                                <input type="hidden" id="updateDiscountId"> <!-- Stores the Discount ID -->

                                <div class="row">
                                    <div class="col-md-4">
                                        <label>Discount Percentage:</label>
                                        <input type="number" step="0.01" class="form-control" id="updateDiscountPercentage" placeholder="Enter %">
                                    </div>
                                    <div class="col-md-4">
                                        <label>Start Date:</label>
                                        <input type="date" class="form-control" id="updateStartDate">
                                    </div>
                                    <div class="col-md-4">
                                        <label>End Date:</label>
                                        <input type="date" class="form-control" id="updateEndDate">
                                    </div>
                                </div>

                                <div class="text-center mt-3">
                                    <button type="button" class="btn btn-success" onclick="submitDiscountUpdate()">
                                        <i class="bi bi-save"></i> Update Discount
                                    </button>
                                    <button type="button" class="btn btn-secondary" onclick="cancelDiscountUpdate()">
                                        <i class="bi bi-x-circle"></i> Cancel
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Active Discounts Table -->
                    <h5 class="text-center"><i class="bi bi-list-check"></i> Active Discounts</h5>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Discount Code</th>
                                    <th>Percentage</th>
                                    <th>Start Date</th>
                                    <th>End Date</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="discountTableBody">
                                <!-- Active discounts will be loaded dynamically -->
                            </tbody>
                        </table>
                    </div>

                    <!-- Expired Discounts Table -->
                    <h5 class="text-center mt-4"><i class="bi bi-x-circle"></i> Expired Discounts</h5>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="table-danger">
                                <tr>
                                    <th>ID</th>
                                    <th>Discount Code</th>
                                    <th>Percentage</th>
                                    <th>Start Date</th>
                                    <th>End Date</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody id="expiredDiscountTable">
                                <!-- Expired discounts will be loaded dynamically -->
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>

        <script src="../js/admin.js"></script>
    </body>
</html>