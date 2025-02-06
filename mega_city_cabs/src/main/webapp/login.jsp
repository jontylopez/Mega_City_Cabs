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
        <link rel="stylesheet" href="./css/login.css">
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
                            window.location.href = "./adminPages/adminHome.jsp";
                        } else if (responseData.uRole === "cus") {
                            alert("✅ Login Successful! Redirecting to Customer Dashboard...");
                            window.location.href = "./customerPages/customerHome.jsp";
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
