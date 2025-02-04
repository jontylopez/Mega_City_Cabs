<%-- 
    Document   : login
    Created on : Feb 4, 2025, 10:29:12 PM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login - Mega City Cabs</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <style>
            /* Center the form with elevation */
            .form-signin {
                min-width: 450px;
                padding: 20px;
                margin: auto;
                text-align: center;
                background: rgba(255, 255, 255, 0.95); /* Light background for contrast */
                border-radius: 10px;
                border: 2px solid #ffcc00; /* Gold border to highlight */
                box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.2); /* Subtle shadow for elevation */
            }

            /* Logo */
            .form-signin img {
                width: 80px;
                height: auto;
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
            }
            .register-link a:hover {
                color: #ffaa00;
                text-decoration: underline;
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

        </style>
    </head>
    <body class="d-flex align-items-center vh-100 bg-light">

        <main class="form-signin">
            <div class="home-link">
                <a href="index.jsp">← Back to Home</a>
            </div>

            <form action="LoginServlet" method="POST">
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

                <button class="btn btn-custom w-100 py-2" type="submit">Sign in</button>

                <p class="register-link">
                    Don't have an account? <a href="register.jsp">Register here</a>
                </p>

                <p class="mt-5 mb-3 text-body-secondary">© 2025 Mega City Cabs</p>
            </form>
        </main>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
