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
                background: url('./images/bgImage.jpg') no-repeat center center/cover;
                position: relative;
            }

            .hero::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5); /* Dark overlay */
            }
            .hero div {
                position: relative;
                color: white;
                z-index: 2;
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

            /* Scroll Wrapper */
            .scroll-wrapper {
                overflow: hidden;
                position: relative;
            }

            /* Scrollable Services Container */
            .services-container {
                display: flex;
                gap: 15px;
                padding-bottom: 10px;
                white-space: nowrap;
                scroll-behavior: smooth;
                overflow-x: auto;
                cursor: grab;
                align-items: center; /* Ensures all cards align properly */
                 user-select: none; /* Disable text selection */
                -webkit-user-select: none; /* Safari */
                -moz-user-select: none; /* Firefox */
                -ms-user-select: none; /* IE */
            }

            /* Hide Scrollbar */
            .services-container::-webkit-scrollbar {
                display: none;
            }

            /* Individual Service Card */
            .service-card {
                flex: 0 0 auto;
                width: 250px;
                height: 320px; /* Fixed height */
                padding: 20px;
                color: black;
                background: #ffffff;
                border-radius: 10px;
                text-align: center;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.15);
                scroll-snap-align: center;
                transition: transform 0.3s ease-in-out;

                /* Ensuring content stays inside */
                overflow: hidden;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                align-items: center; /* Center everything */
            }

            /* Service Card Image */
            .service-card img {
                width: 80px;
                height: auto;
                object-fit: contain; /* Prevents image distortion */
                margin-bottom: 10px;
            }

            /* Service Card Title */
            .service-card h5 {
                font-size: 16px;
                font-weight: bold;
                color: #333;
                margin-bottom: 5px;
                text-align: center;
                white-space: normal;
                overflow: hidden;
                text-overflow: ellipsis;
                max-width: 90%; /* Ensures it fits within the card */
            }

            /* Service Card Text */
            .service-card p {
                font-size: 14px;
                color: #666;
                white-space: normal;
                text-align: center;
                margin: 0;
                padding: 0 10px;
                line-height: 1.4;

                /* Preventing text from breaking out */
                word-wrap: break-word;
                overflow: hidden;
                text-overflow: ellipsis;
                max-height: 60px; /* Prevents too much text overflow */
            }
         
           

            /* Prevent image dragging */
            .service-card img {
                pointer-events: none; /* Disable image interactions */
                user-drag: none; /* Disable default image dragging */
                -webkit-user-drag: none; /* Safari */
            }


            /* Hover Effect */
            .service-card:hover {
                transform: scale(1.05);
            }

            /* Scroll Buttons */
            .scroll-btn {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background: rgba(0, 0, 0, 0.6);
                color: white;
                border: none;
                font-size: 24px;
                padding: 10px;
                cursor: pointer;
                border-radius: 50%;
                z-index: 10;
            }

            /* Left Scroll Button */
            .left {
                left: 20px;
            }

            /* Right Scroll Button */
            .right {
                right: 20px;
            }

            /* RESPONSIVE DESIGN */
            @media (max-width: 1200px) {
                .service-card {
                    width: 200px;
                    height: 280px;
                }
            }

            @media (max-width: 992px) {
                .service-card {
                    width: 180px;
                    height: 260px;
                }
            }

            @media (max-width: 768px) {
                .service-card {
                    width: 160px;
                    height: 250px;
                }
            }

            @media (max-width: 576px) {
                .service-card {
                    width: 150px;
                    height: 240px;
                }

                /* Show only 2 cards */
                .services-container {
                    gap: 5px;
                }
            }


            .text-center{
                color: black;
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
        <section id="services" class="services py-5 bg-light">
            <div class="container position-relative">
                <h2 class="text-center mb-4">Why Choose Mega City Cabs?</h2>
                <p class="text-center text-muted mb-4">Experience the best cab service with these top features</p>

                <!-- Scroll Left Button -->
                <button onclick="scrollLeft()" class="scroll-btn left">❮</button>

                <!-- Scrollable Wrapper -->
                <div class="scroll-wrapper">
                    <div class="services-container">
                        <!-- Vast Range of Vehicles -->
                        <div class="service-card">
                            <img src="./images/wideRange.png" alt="Vast Range of Vehicles">
                            <h5>Vast Range of Vehicles</h5>
                            <p>Choose from a variety of vehicles to suit your every need.</p>
                        </div>

                        <!-- Baby Friendly -->
                        <div class="service-card">
                            <img src="./images/babySeat.png" alt="Baby Seat">
                            <h5>Baby Friendly</h5>
                            <p>Travel safely with your little ones using our secure baby seats.</p>
                        </div>

                        <!-- Payment Methods -->
                        <div class="service-card">
                            <img src="./images/paymentMethods.png" alt="Various Payment Methods">
                            <h5>Various Payment Methods</h5>
                            <p>Enjoy hassle-free payments with multiple options including cash, credit/debit cards, and secure online transactions.</p>
                        </div>

                        <!-- Free Cancellation -->
                        <div class="service-card">
                            <img src="./images/cancelFree.png" alt="Free Cancellation">
                            <h5>Free Cancellation</h5>
                            <p>Cancel at no cost, anytime you need.</p>
                        </div>

                        <!-- Name Board -->
                        <div class="service-card">
                            <img src="./images/nameBoard.png" alt="Name Board">
                            <h5>Name Board Service</h5>
                            <p>Easily spot your ride with our personalized name board service.</p>
                        </div>

                        <!-- Professional Drivers -->
                        <div class="service-card">
                            <img src="./images/profDriver.png" alt="Professional Drivers">
                            <h5>Professional Drivers</h5>
                            <p>Travel with confidence knowing that safety is our top priority.</p>
                        </div>
                    </div>
                </div>

                <!-- Scroll Right Button -->
                <button onclick="scrollRight()" class="scroll-btn right">❯</button>
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
        <script>
                    const container = document.querySelector(".services-container");

                    function scrollLeft() {
                        container.scrollBy({left: -250, behavior: "smooth"});
                    }

                    function scrollRight() {
                        container.scrollBy({left: 250, behavior: "smooth"});
                    }

// Enable click and drag scrolling
                    let isDown = false;
                    let startX;
                    let scrollLeftVal;

                    container.addEventListener("mousedown", (e) => {
                        isDown = true;
                        container.classList.add("active");
                        startX = e.pageX - container.offsetLeft;
                        scrollLeftVal = container.scrollLeft;
                    });

                    container.addEventListener("mouseleave", () => {
                        isDown = false;
                        container.classList.remove("active");
                    });

                    container.addEventListener("mouseup", () => {
                        isDown = false;
                        container.classList.remove("active");
                    });

                    container.addEventListener("mousemove", (e) => {
                        if (!isDown)
                            return;
                        e.preventDefault();
                        const x = e.pageX - container.offsetLeft;
                        const walk = (x - startX) * 2; // Increase multiplier for faster scroll
                        container.scrollLeft = scrollLeftVal - walk;
                    });

        </script>
    </body>
</html>
