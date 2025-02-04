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
        <style>
            /* Centered Form with Elevation */
            .form-register {
                min-width: 500px;
                padding: 20px;
                margin: auto;
                text-align: center;
                background: rgba(255, 255, 255, 0.95); /* Light contrast */
                border-radius: 10px;
                border: 2px solid #ffcc00; /* Gold border */
                box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.2);
            }

            /* Logo */
            .form-register img {
                width: 80px;
                height: auto;
            }

            /* Input Fields */
            .form-control {
                background: #fff;
                border: 1px solid #ddd;
                color: #333;
            }
            .form-control:focus {
                border-color: #ffcc00;
                box-shadow: 0 0 10px #ffcc00;
            }

            /* Submit Button */
            .btn-custom {
                background: #ffcc00;
                color: black;
                font-weight: bold;
                border: none;
                padding: 12px;
                font-size: 18px;
                transition: 0.3s;
                border-radius: 5px;
            }
            .btn-custom:hover {
                background: #ffaa00;
                color: white;
                box-shadow: 0px 4px 10px rgba(255, 204, 0, 0.5);
            }

            /* Home Link */
            .home-link {
                text-align: center;
                margin-bottom: 20px;
            }
            .home-link a {
                color: #ffcc00;
                font-weight: bold;
                text-decoration: none;
                font-size: 16px;
            }
            .home-link a:hover {
                color: #ffaa00;
                text-decoration: underline;
            }

            /* Login Link */
            .login-link {
                margin-top: 15px;
            }
            .login-link a {
                color: #ffcc00;
                text-decoration: none;
                font-weight: bold;
            }
            .login-link a:hover {
                color: #ffaa00;
                text-decoration: underline;
            }
        </style>
        <link rel="icon" href="data:,">
    </head>
    <body class="d-flex align-items-center vh-100 bg-light">

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
