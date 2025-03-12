
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Profile | Mega City Cabs</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/profile.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Edit Profile</h2>
        <p class="text-center">Update your contact details or change your password.</p>

        <!-- 🔹 Profile Edit Form -->
        <div class="card shadow-sm p-4">
            <h5>Update Contact Information</h5>
            <form id="profileForm">
                <input type="hidden" id="userId">

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone</label>
                    <input type="text" class="form-control" id="phone" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <textarea class="form-control" id="address" required></textarea>
                </div>

                <button type="submit" class="btn btn-success"><i class="bi bi-save"></i> Save Changes</button>
            </form>
        </div>

        <!-- 🔹 Change Password Form -->
        <div class="card shadow-sm p-4 mt-4">
            <h5>Change Password</h5>
            <form id="passwordForm">
                <div class="mb-3">
                    <label class="form-label">Current Password</label>
                    <input type="password" class="form-control" id="currentPassword" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">New Password</label>
                    <input type="password" class="form-control" id="newPassword" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Confirm New Password</label>
                    <input type="password" class="form-control" id="confirmPassword" required>
                </div>

                <button type="submit" class="btn btn-warning"><i class="bi bi-lock"></i> Change Password</button>
            </form>
        </div>
    </div>
<script src="../js/admin.js"></script>

</body>
</html>
