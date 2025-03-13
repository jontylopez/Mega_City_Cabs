<%-- 
    Document   : register
    Created on : Feb 4, 2025, 10:44:46 PM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register - Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="./css/register.css">
        <link rel="icon" type="image/svg+xml" href="./images/favicon.svg">
    </head>
    <body class="d-flex align-items-center vh-100 my-50 bg-dark">

        <main class="form-register">
            <!-- Home Link -->
            <div class="home-link">
                <a href="index.jsp">← Back to Home</a>
            </div>

            <form id="registerForm" action="RegisterServlet" method="POST" ">
                <img class="mb-4" src="./images/taxi_logo.png" alt="Mega City Cabs Logo">
                <h1 class="h3 mb-3 fw-normal">Create an Account</h1>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="fullName" name="name" placeholder="Full Name" required>
                    <label for="name">Full Name</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="address" name="address" placeholder="Address" required>
                    <label for="address">Address</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="phone" name="phone" placeholder="Phone Number" required>
                    <label for="phone">Phone Number</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="email" name="email" placeholder="Email Address" required>
                    <label for="email">Email Address</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
                    <label for="username">Username</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="pWord" name="password" placeholder="Password" required>
                    <label for="password">Password</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="confirmPassword" placeholder="Confirm Password" required>
                    <label for="confirmPassword">Re-enter Password</label>
                </div>

                <button class="btn btn-custom w-100 py-2" type="submit">Register</button>

                <p class="login-link">
                    Already have an account? <a href="login.jsp">Sign in here</a>
                </p>

                <p class="mt-5 mb-3 text-body-secondary">© 2025 Mega City Cabs</p>
            </form>
            <div id="messageBox" class="alert d-none"></div>
        </main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="./js/register.js" defer></script>


    </body>
</html>
