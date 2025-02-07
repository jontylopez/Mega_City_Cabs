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
            <div class="card-header text-blqck" style="background-color: gold;">Add New Category</div>
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
                            <th>Extra Km</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="categoryTableBody">
                        <!-- Dynamic Data Will Be Loaded Here -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            fetchCategories();

            document.getElementById("categoryForm").addEventListener("submit", function (e) {
                e.preventDefault();
                let formData = {
                    catName: document.getElementById("catName").value,
                    maxPsngr: document.getElementById("maxPsngr").value,
                    perDayValue: document.getElementById("perDayValue").value,
                    maxKmPerDay: document.getElementById("maxKmPerDay").value,
                    milePkg1: document.getElementById("milePkg1").value,
                    milePkg2: document.getElementById("milePkg2").value,
                    waitingPerHr: document.getElementById("waitingPerHr").value,
                    extraKm: document.getElementById("extraKm").value,
                    active: document.getElementById("active").value
                };

                fetch("http://localhost:8080/restAPIMCCabs/api/categories/create", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(formData)
                })
                .then(response => response.json())
                .then(() => {
                    fetchCategories();
                    document.getElementById("categoryForm").reset();
                })
                .catch(error => console.error("Error:", error));
            });

            function fetchCategories() {
                fetch("http://localhost:8080/restAPIMCCabs/api/categories")
                .then(response => response.json())
                .then(data => {
                    let tableBody = document.getElementById("categoryTableBody");
                    tableBody.innerHTML = "";
                    data.forEach(category => {
                        let row = `
                            <tr>
                                <td>${category.id}</td>
                                <td>${category.catName}</td>
                                <td>${category.maxPsngr}</td>
                                <td>${category.perDayValue}</td>
                                <td>${category.maxKmPerDay}</td>
                                <td>${category.extraKm}</td>
                                <td><span class="badge bg-" + (category.active === "Active" ? "success" : "danger") + '">' + category.active + '</span></td>
                                <td>
                                    <button class="btn btn-warning btn-sm"><i class="bi bi-pencil"></i></button>
                                    <button class="btn btn-danger btn-sm" onclick="deleteCategory(${category.id})"><i class="bi bi-trash"></i></button>
                                </td>
                            </tr>`;
                        tableBody.innerHTML += row;
                    });
                })
                .catch(error => console.error("Error fetching categories:", error));
            }

            function deleteCategory(id) {
                if (confirm("Are you sure you want to delete this category?")) {
                    fetch(`http://localhost:8080/restAPIMCCabs/api/categories/${id}`, {
                        method: "DELETE"
                    })
                    .then(response => response.json())
                    .then(() => fetchCategories())
                    .catch(error => console.error("Error deleting category:", error));
                }
            }
        });
    </script>

</body>
</html>
