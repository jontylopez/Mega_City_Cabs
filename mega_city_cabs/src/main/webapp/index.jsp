<%-- 
    Document   : index
    Created on : Feb 4, 2025, 7:22:43 PM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mega City Cabs - Home</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
        <style>
            /* General Styles */
            body {
                font-family: 'Arial', sans-serif;
                margin: 0;
                padding: 0;
                background: url('cab-bg.jpg') no-repeat center center fixed;
                background-size: cover;
                color: white;
            }
            /* Logo Styling */
            .logo {
                height: 60px;
                width: auto;
                max-height: 100px;
            }

            /* Navigation Bar */
            .navbar {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                background: linear-gradient(to right, #FFD700, #FFCC00);
                padding: 15px;
                z-index: 1000; /* Ensures it's always above other elements */
            }

            /* Navbar Brand (Logo + Text) */
            .navbar-brand {
                font-size: 26px;
                font-weight: bold;
                color: #000000; /* Black for strong contrast */
                text-transform: uppercase;
                letter-spacing: 1px;
            }
            .navbar-brand:hover {
                color: #ffffff; /* White for visibility */
            }

            /* Navigation Links */
            .nav-link {
                color: #000000; /* Black for high visibility */
                font-size: 18px;
                font-weight: bold;
                margin-right: 20px;
                 padding: 12px 20px; /* Same as nav-link */
                min-width: 130px; /* Ensures width consistency */
                cursor: pointer;
                text-align:  center;
                padding: 8px 15px; /* Adds padding for better hover effect */
                border-radius: 15px; /* Slight rounding */
                transition: 0.3s;
            }
            .nav-link:hover {
                background: #ffffff; /* White background for pop-out effect */
                color: #000000; /* Black text for contrast */
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2);
            }

            /* Login Button - Matching Nav Links */
            .login-btn {
                background: #000000;
                color: #ffffff;
                font-weight: bold;
                padding: 12px 20px; /* Same as nav-link */
                min-width: 130px; /* Ensures width consistency */
                border-radius: 15px;
                transition: 0.3s;
                border: 2px solid #ffffff;
                text-align: center;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .login-btn:hover {
                background: #ffaa00;
                color: black;
                border: 2px solid #000000;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.4);
            }


            /* Hero Section */
            .hero {
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
                padding: 50px;
                background: rgba(0, 0, 0, 0.6);
            }
            .hero h1 {
                font-size: 48px;
                font-weight: bold;
                margin-bottom: 20px;
            }
            .hero p {
                font-size: 22px;
                margin-bottom: 30px;
            }
            .book-btn {
                background: #ffcc00;
                color: black;
                font-size: 20px;
                padding: 12px 25px;
                border-radius: 5px;
                text-decoration: none;
                font-weight: bold;
                transition: 0.3s;
            }
            .book-btn:hover {
                background: #ffaa00;
                color: white;
            }

            /* Services Section (Full Height) */
            .services {
                min-height: 100vh;
                background: #f8f9fa;
                padding: 50px 20px;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .carousel-inner img {
                width: 100%;
                height: 90vh; /* Adjusted for full height */
                object-fit: cover;
            }

            /* Contact Section (Full Height) */
            .contact {
                min-height: 100vh;
                background: #222;
                color: white;
                padding: 50px 20px;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
            }
            .contact h2 {
                margin-bottom: 20px;
            }
            .contact-info {
                font-size: 18px;
                line-height: 1.8;
            }
            /* Fix Overlapping Issue */
            .hero, .services, .contact {
                padding-top: 80px; /* Adjusted to avoid overlap with navbar */
            }
            /* Footer */
            .footer {
                text-align: center;
                padding: 20px;
                background: rgba(0, 0, 0, 0.8);
                color: white;
                width: 100%;
            }

            /* Smooth Scroll */
            html {
                scroll-behavior: smooth;
            }
        </style>
    </head>
    <body>

        <!-- Navigation Bar -->
        <nav class="navbar navbar-expand-lg">
            <div class="container">
                <a class="navbar-brand d-flex align-items-center" href="#">
                    <img class="logo me-2" src="./images/taxi_logo.png" alt="taxi logo">
                    Mega City Cabs
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item"><a class="nav-link" href="#">Home</a></li>
                        <li class="nav-item"><a class="nav-link" href="#services">Services</a></li>
                        <li class="nav-item"><a class="nav-link" href="#contact">Contact</a></li>
                        <li class="nav-item"><a class="nav-link login-btn" href="login.jsp">Login</a></li>
                    </ul>
                </div>
            </div>
        </nav>


        <!-- Hero Section -->
        <div class="hero">
            <div>
                <h1>Reliable & Fast Cab Service in Your City</h1>
                <p>Book a ride with Mega City Cabs and experience comfort & safety.</p>
                <a href="booking.jsp" class="book-btn">Book a Ride</a>
            </div>
        </div>

        <!-- Services Section -->
        <section id="services" class="services">
            <div id="myCarousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="./images/service1.jpg" class="d-block w-100" alt="Service 1">
                    </div>
                    <div class="carousel-item">
                        <img src="./images/service2.jpg" class="d-block w-100" alt="Service 2">
                    </div>
                    <div class="carousel-item">
                        <img src="./images/service3.jpg" class="d-block w-100" alt="Service 3">
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon"></span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon"></span>
                </button>
            </div>
        </section>

        <!-- Contact Section -->
        <section id="contact" class="contact">
            <div class="container">
                <h2>Contact Us</h2>
                <p class="contact-info">
                    📍 Address: 123 Main Street, Cityville, Country <br>
                    📞 Phone: +1 234 567 890 <br>
                    📧 Email: support@megacitycabs.com
                </p>
            </div>
        </section>

        <!-- Footer -->
        <div class="footer">
            &copy; 2025 Mega City Cabs. All rights reserved.
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
