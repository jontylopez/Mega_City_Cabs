<%-- 
    Document   : userManager
    Created on : Feb 6, 2025, 11:45:13 AM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>User Manager | Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
        <style>
            /* ✅ Search Bar */
            .search-container {
                max-width: 400px;
                margin: 0 auto 20px auto;
            }
        </style>
    </head>
    <body class="p-4 bg-dark">

        <div class="container discount-container">
            <div class="card discount-card">
                <div class="card-header">
                    <h3><i class="bi bi-tag"></i> User Manager</h3>
                </div>
                
                <div class="card-body">
                    <!-- 🔍 Search Bar -->
                    <div class="search-container">
                        <input type="text" id="searchInput" class="form-control" placeholder="Search by ID, Name and Numbers..." onkeyup="filterUsers()">
                    </div>
                    <!-- Users Table -->
                    <div class="table-responsive">
                        <table class="table table-striped table-hover" id="userTable">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Role</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="userTableBody">
                                <!-- Users will be loaded dynamically here -->
                            </tbody>
                        </table>
                    </div>

                    <!-- Edit User Role Modal -->
                    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header bg-warning">
                                    <h5 class="modal-title" id="editUserModalLabel">Edit User Role</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form id="editUserForm">
                                        <input type="hidden" id="editUserId">
                                        <div class="mb-3">
                                            <label for="editFullName" class="form-label">Full Name</label>
                                            <input type="text" class="form-control" id="editFullName" disabled>
                                        </div>
                                        <div class="mb-3">
                                            <label for="editEmail" class="form-label">Email</label>
                                            <input type="email" class="form-control" id="editEmail" disabled>
                                        </div>
                                        <div class="mb-3">
                                            <label for="editUserRole" class="form-label">User Role</label>
                                            <select class="form-select" id="editUserRole">
                                                <option value="cus">Customer</option>
                                                <option value="adm">Admin</option>
                                            </select>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            <button type="button" class="btn btn-warning" onclick="updateUserRole()">Save Changes</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <script src="../js/admin.js"></script>
                </div>
            </div>

            <script src="../js/admin.js"></script>
    </body>
</html>





