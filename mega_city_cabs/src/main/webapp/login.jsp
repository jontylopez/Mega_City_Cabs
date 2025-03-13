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
        <link rel="icon" type="image/svg+xml" href="./images/favicon.svg">
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
        <script src="./js/login.js" defer></script>
    </body>
</html>
