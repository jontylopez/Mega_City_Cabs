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
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let registerForm = document.getElementById("registerForm");
                if (!registerForm) {
                    console.error("❌ ERROR: registerForm not found!");
                    return;
                }

                registerForm.addEventListener("submit", async function (event) {
                    event.preventDefault();

                    let fullName = document.getElementById("fullName").value.trim();
                    let address = document.getElementById("address").value.trim();
                    let phone = document.getElementById("phone").value.trim();
                    let email = document.getElementById("email").value.trim();
                    let username = document.getElementById("username").value.trim();
                    let pWord = document.getElementById("pWord").value.trim();
                    let confirmPassword = document.getElementById("confirmPassword").value.trim();

                    if (pWord !== confirmPassword) {
                        showMessage("❌ Passwords do not match!", "danger");
                        return;
                    }

                    if (!fullName || !address || !phone || !email || !username || !pWord) {
                        showMessage("⚠️ Please fill in all required fields!", "warning");
                        return;
                    }

                    let requestData = {
                        username: username,
                        pWord: pWord,
                        uRole: "cus",
                        fullName: fullName,
                        address: address,
                        phone: phone,
                        email: email
                    };

                    try {
                        let response = await fetch("http://localhost:8080/restAPIMCCabs/api/users/create", {
                            method: "POST",
                            headers: {"Content-Type": "application/json"},
                            body: JSON.stringify(requestData)
                        });

                        let responseData = await response.json();

                        if (!response.ok) {
                            if (response.status === 409) {
                                showMessage("⚠️ Username or Email is already taken!", "warning");
                            } else {
                                throw new Error(responseData.message || "Registration failed");
                            }
                            return;
                        }

                        // ✅ Alert for successful registration
                        alert("✅ User Registration Successful!");
                        showMessage("✅ User registered successfully! Redirecting to login...", "success");

                        setTimeout(() => window.location.href = "login.jsp", 2000);

                    } catch (error) {
                        showMessage(error.message || "❌ Registration failed! Please try again.", "danger");
                    }
                });

                function showMessage(message, type) {
                    let messageBox = document.getElementById("messageBox");
                    if (messageBox) {
                        messageBox.className = `alert alert-${type} mt-3`;
                        messageBox.textContent = message;
                        messageBox.classList.remove("d-none");
                    }
                }
            });
        </script>


    </body>
</html>
