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
    </head>
    <body class="d-flex align-items-center vh-100 bg-light">

        <main class="form-register">
            <!-- Home Link -->
            <div class="home-link">
                <a href="index.jsp">← Back to Home</a>
            </div>

            <form action="RegisterServlet" method="POST" onsubmit="return validatePassword()">
                <img class="mb-4" src="./images/taxi_logo.png" alt="Mega City Cabs Logo">
                <h1 class="h3 mb-3 fw-normal">Create an Account</h1>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="name" name="name" placeholder="Full Name" required>
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
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
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
        </main>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
        
    document.getElementById("registerForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent default form submission

        // Get form values
        var name = document.getElementById("name").value;
        var address = document.getElementById("address").value;
        var phone = document.getElementById("phone").value;
        var email = document.getElementById("email").value;
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;

        // Password Validation
        if (password !== confirmPassword) {
            showMessage("Passwords do not match!", "danger");
            return;
        }

        // Create User JSON (For users table)
        var userData = {
            userName: username,
            password: password,
            uRole: "cus" // Default role: customer
        };

        // Step 1: Register User in users table
       fetch("http://localhost:8080/restAPIMCCabs/api/users/create", {

            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.userId) {  // Check if userId is returned
                let userId = data.userId;

                // Create User Details JSON (For user_details table)
                var userDetailsData = {
                    userId: userId,
                    name: name,
                    address: address,
                    phone: phone,
                    email: email
                };

                // Step 2: Insert User Details in user_details table
                fetch("http://localhost:8080/restAPIMCCabs/api/userDetails/create", {

                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(userDetailsData)
                });
            } else {
                throw new Error("User registration failed");
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.message.includes("successfully")) {
                showMessage("User registered successfully!", "success");
                setTimeout(() => window.location.href = "login.jsp", 2000);
            } else {
                throw new Error("User details registration failed");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            showMessage(error.message || "Registration failed! Please try again.", "danger");
        });
    });

    // Function to Show Success/Error Message
    function showMessage(message, type) {
        var messageBox = document.getElementById("messageBox");
        messageBox.className = `alert alert-${type} mt-3`;
        messageBox.textContent = message;
        messageBox.classList.remove("d-none");
    }
</script>

    </body>
</html>
