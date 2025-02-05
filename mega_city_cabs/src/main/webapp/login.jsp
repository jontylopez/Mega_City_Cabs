<%-- 
    Document   : login
    Created on : Feb 4, 2025, 10:29:12 PM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="icon" href="data:,">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login - Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            body{
                background: #1a1a2e; /* Dark Navy Blue */

            }
            /* Center the form with elevation */
            .form-signin {
                min-width: 450px;
                padding: 20px;
                margin: auto;
                text-align: center;
                elevation: 20px;
                border-radius: 10px;
                background: rgba(245, 245, 245, 0.95);

                border: 2px solid #ffcc00; /* Gold border to highlight */
                box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.2); /* Subtle shadow for elevation */
            }



            /* Custom Button */
            .btn-custom {
                background: #ffcc00;
                color: black;
                font-weight: bold;
                border: none;
                padding: 10px;
                font-size: 18px;
                transition: 0.3s;
                border-radius: 5px;
            }
            .btn-custom:hover {
                background: #ffaa00;
                color: white;
                box-shadow: 0px 4px 10px rgba(255, 204, 0, 0.5); /* Glow effect */
            }

            /* Register Link */
            .register-link {
                margin-top: 15px;
            }
            .register-link a {
                color: #ffcc00;
                text-decoration: none;
                font-weight: bold;
                text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.8); /* Adds a soft black outline */
            }
            .register-link a:hover {
                color: #ffaa00;
                text-decoration: underline;
                text-shadow: 2px 2px 5px rgba(0, 0, 0, 1); /* Slightly stronger shadow on hover */
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
                text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.8); /* Adds a soft black outline */
                font-size: 16px;
                border-radius: 15px;

            }
            .home-link a:hover {
                color: #ffaa00;
                text-decoration: underline;
                text-shadow: 2px 2px 5px rgba(0, 0, 0, 1); /* Slightly stronger shadow on hover */
            }
            /* Logo */
            .form-signin img {
                width: 80px;
                height: auto;
            }

        </style>
    </head>
    <body class="d-flex align-items-center vh-100 bg-dark">

        <main class="form-signin">
            <div class="home-link">
                <a href="index.jsp"> ← Mega City Cabs Home </a>
            </div>

            <form  method="POST">
                <img class="mb-4" src="./images/taxi_logo.png" alt="Mega City Cabs Logo">
                <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="floatingInput" name="email" placeholder="name@example.com" required>
                    <label for="floatingInput">Email address</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="floatingPassword" name="password" placeholder="Password" required>
                    <label for="floatingPassword">Password</label>
                </div>
                <div id="messageBox"></div> 
                <button class="btn btn-custom w-100 py-2" type="submit">Sign in</button>

                <p class="register-link">
                    Don't have an account? <a href="register.jsp">Register here</a>
                </p>

                <p class="mt-5 mb-3 text-body-secondary">© 2025 Mega City Cabs</p>
            </form>
        </main>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            // Login logic
            document.addEventListener("DOMContentLoaded", function () {
                let loginForm = document.querySelector("form");
                if (!loginForm) {
                    console.error("❌ ERROR: loginForm not found!");
                    return;
                }

                loginForm.addEventListener("submit", async function (event) {
                    event.preventDefault(); // Prevent default form submission

                    let email = document.getElementById("floatingInput").value.trim();
                    let password = document.getElementById("floatingPassword").value.trim();
                    if (!email || !password) {
                        showMessage("⚠️ Please enter both email and password!", "warning");
                        return;
                    }

                    let requestData = {
                        email: email,
                        pWord: password
                    };
                    try {
                        let response = await fetch("http://localhost:8080/restAPIMCCabs/api/users/login", {
                            method: "POST",
                            headers: {"Content-Type": "application/json"},
                            body: JSON.stringify(requestData)
                        });
                        let responseData = await response.json();
                        console.log("🔍 API Response: ", responseData); // Debugging line

                        if (!response.ok) {
                            throw new Error(responseData.message || "❌ Invalid email or password!");
                        }

                        // ✅ Store userId and uRole in session storage
                        sessionStorage.setItem("userId", responseData.userId);
                        sessionStorage.setItem("uRole", responseData.uRole);
                        console.log("✅ Session storage set:");
                        console.log("userId:", responseData.userId);
                        console.log("uRole:", responseData.uRole);
                        // ✅ Redirect Based on User Role
                        if (responseData.uRole === "adm") {
                            alert("✅ Login Successful! Redirecting to Admin Dashboard...");
                            window.location.href = "adminHome.jsp";
                        } else if (responseData.uRole === "cus") {
                            alert("✅ Login Successful! Redirecting to Customer Dashboard...");
                            window.location.href = "customerHome.jsp";
                        } else {
                            showMessage("❌ Unauthorized role detected!", "danger");
                        }

                    } catch (error) {
                        console.error("🚨 ERROR:", error.message);
                        showMessage(error.message || "❌ Login failed! Please try again.", "danger");
                    }
                });
                function showMessage(message, type) {
                    let messageBox = document.getElementById("messageBox");
                    if (!messageBox) {
                        messageBox = document.createElement("div");
                        messageBox.id = "messageBox";
                        messageBox.className = `alert alert-${type} mt-3`;
                        loginForm.prepend(messageBox);
                    } else {
                        messageBox.className = `alert alert-${type} mt-3`;
                    }
                    messageBox.textContent = message;
                }
            });
        </script>
    </body>
</html>
