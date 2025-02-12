<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category Manager | Mega City Cabs</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
</head>
<body class="p-4">

    <div class="container">
        <h2 class="mb-4"><i class="bi bi-tags"></i> Category Manager</h2>

        <!-- Add Category Form -->
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-warning text-dark">Add New Category</div>
            <div class="card-body">
                <form id="categoryForm">
                    <div class="row">
                        <div class="col-md-6">
                            <label class="form-label">Category Name</label>
                            <input type="text" class="form-control" id="catName" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Max Passengers</label>
                            <input type="number" class="form-control" id="maxPsngr" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Per Day Value</label>
                            <input type="number" class="form-control" id="perDayValue" step="0.01" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Max Km Per Day</label>
                            <input type="number" class="form-control" id="maxKmPerDay" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Mile Package 1</label>
                            <input type="number" class="form-control" id="milePkg1" step="0.01" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Mile Package 2</label>
                            <input type="number" class="form-control" id="milePkg2" step="0.01" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Waiting Per Hour</label>
                            <input type="number" class="form-control" id="waitingPerHr" step="0.01" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Extra Km Charge</label>
                            <input type="number" class="form-control" id="extraKm" step="0.01" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Status</label>
                            <select class="form-control" id="active">
                                <option value="Active">Active</option>
                                <option value="Inactive">Inactive</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-success mt-3"><i class="bi bi-plus-circle"></i> Add Category</button>
                </form>
            </div>
        </div>

        <!-- Category List -->
        <div class="card shadow-sm">
            <div class="card-header bg-dark text-white">Category List</div>
            <div class="card-body">
                <table class="table table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Max Passengers</th>
                            <th>Per Day</th>
                            <th>Max Km/Day</th>
                            <th>Mile Pkg 1</th>
                            <th>Mile Pkg 2</th>
                            <th>Waiting Per Hr</th>
                            <th>Extra Km</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="categoryTableBody">
                        <!-- Categories will be dynamically inserted here -->
                    </tbody>
                </table>
            </div>
        </div>
        
        <!-- Update Category Form -->
<div class="card mb-4 shadow-sm" id="updateForm" style="display: none;">
    <div class="card-header bg-primary text-white">Edit Category</div>
    <div class="card-body">
        <input type="hidden" id="updateCategoryId">
        <div class="mb-3">
            <label class="form-label">Category Name</label>
            <input type="text" class="form-control" id="updateCatName">
        </div>
        <div class="mb-3">
            <label class="form-label">Max Passengers</label>
            <input type="number" class="form-control" id="updateMaxPsngr">
        </div>
        <div class="mb-3">
            <label class="form-label">Per Day Value</label>
            <input type="number" class="form-control" id="updatePerDayValue" step="0.01">
        </div>
        <div class="mb-3">
            <label class="form-label">Max Km Per Day</label>
            <input type="number" class="form-control" id="updateMaxKmPerDay">
        </div>
        <div class="mb-3">
            <label class="form-label">Mile Package 1</label>
            <input type="number" class="form-control" id="updateMilePkg1" step="0.01">
        </div>
        <div class="mb-3">
            <label class="form-label">Mile Package 2</label>
            <input type="number" class="form-control" id="updateMilePkg2" step="0.01">
        </div>
        <div class="mb-3">
            <label class="form-label">Waiting Per Hour</label>
            <input type="number" class="form-control" id="updateWaitingPerHr" step="0.01">
        </div>
        <div class="mb-3">
            <label class="form-label">Extra Km Charge</label>
            <input type="number" class="form-control" id="updateExtraKm" step="0.01">
        </div>
        <div class="mb-3">
            <label class="form-label">Status</label>
            <select class="form-control" id="updateActive">
                <option value="Active">Active</option>
                <option value="Inactive">Inactive</option>
            </select>
        </div>
        <button class="btn btn-primary" onclick="submitUpdate()">Update Category</button>
        <button class="btn btn-secondary" onclick="cancelUpdate()">Cancel</button>
    </div>
</div>

    </div>

    <script src="../js/admin.js"></script>
</body>
</html>
