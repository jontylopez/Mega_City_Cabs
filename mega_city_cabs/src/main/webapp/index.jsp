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
        <link rel="stylesheet" href="./css/index.css">
        <link rel="icon" type="image/svg+xml" href="./images/favicon.svg">
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
