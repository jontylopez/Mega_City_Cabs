document.addEventListener("DOMContentLoaded", function () {
    console.log("📌 Customer Dashboard Loaded!");

    // ✅ Load Default Customer Dashboard
    loadPage("customerDash.jsp");

    // ✅ Attach Sidebar Event Listeners
    setupLinks();

    // ✅ Check if user is logged in and load trip details if on dashboard
    const userId = getSessionUserId();
    if (userId && window.location.href.includes("customerDash.jsp")) {
        setTimeout(() => loadTripDetails(), 300); // Short delay to ensure elements exist
    }
});

let currentPage = ""; // ✅ Track the currently loaded page

function loadPage(url) {
    if (currentPage === url) {
        console.log(`🔹 ${url} is already loaded. Skipping reload.`);
        return;
    }

    console.log(`🔄 Loading Page: ${url}`);
    currentPage = url;

    fetch(url)
            .then(response => response.text())
            .then(html => {
                const mainContent = document.querySelector(".main-content");
                if (!mainContent) {
                    console.error("❌ Main content element not found!");
                    return;
                }

                mainContent.innerHTML = html;
                setupLinks(); // ✅ Ensure sidebar links are clickable

                console.log("✅ Page Loaded: ", url);

                // ✅ Load Next Trip for Customer Dashboard
                if (url.includes("customerDash.jsp")) {
                    console.log("📌 Customer Dashboard Loaded! Fetching Next Trip...");
                    setTimeout(() => loadTripDetails(), 300); // Short delay to ensure elements exist
                    setTimeout(() => loadPendingPayments(), 300);
                }

                // ✅ Load Booking Page & Categories
                if (url.includes("bookRide.jsp")) {
                    console.log("📌 Booking Page Loaded! Fetching Categories...");
                    loadCategoriesForBooking();
                    loadAvailableDiscounts();
                }

                // ✅ Load Booking History Page
                if (url.includes("bookingHistory.jsp")) {
                    console.log("📌 Booking History Page Loaded! Fetching Trips...");
                    loadBookingHistory();
                }
                if (url.includes("customerProfile.jsp")) {
                    console.log("📌 Profile Page Loaded! Fetching user details...");
                    setTimeout(loadUserProfile, 500);  // Ensure it runs after content is loaded
                }
            })
            .catch(error => console.error("❌ Error loading page:", error));
}

// 🔹 Setup Click Event for Sidebar Links
function setupLinks() {
    document.querySelectorAll(".sidebar .nav-link, .profile-link").forEach(link => {
        link.addEventListener("click", function (e) {
            e.preventDefault();
            let pageUrl = this.getAttribute("href");
            if (pageUrl !== "#") {
                loadPage(pageUrl);
            }
        });
    });
}

const reservationApiUrl = "http://localhost:8080/restAPIMCCabs/api/reservations/";
const userApiUrl = "http://localhost:8080/restAPIMCCabs/api/users";
const vehicleApiUrl = "http://localhost:8080/restAPIMCCabs/api/vehicles";
const driverApiUrl = "http://localhost:8080/restAPIMCCabs/api/drivers/";
const discountApiUrl = "http://localhost:8080/restAPIMCCabs/api/discounts/";
const categoryApiUrl = "http://localhost:8080/restAPIMCCabs/api/categories";
const jointApiUrl = "http://localhost:8080/restAPIMCCabs/api/joint";
const vehicleAvailabilityApiUrl = "http://localhost:8080/restAPIMCCabs/api/vehicle_availability/";
const driverAvailabilityApiUrl = "http://localhost:8080/restAPIMCCabs/api/driver_availability/";
const discountAvailabilityApiUrl = "http://localhost:8080/restAPIMCCabs/api/discount_availability/";
const reservationFinalizeApiUrl = "http://localhost:8080/restAPIMCCabs/api/reservation_finalize";
// ==========================================
// 🔹 CATEGORY MANAGEMENT FOR BOOKING PAGE
// ==========================================

// 🔹 Fetch Categories Data
async function getCategories() {
    console.log("📌 Fetching categories...");

    try {
        const response = await fetch(categoryApiUrl);
        if (!response.ok)
            throw new Error(`❌ HTTP Error: ${response.status}`);

        const categories = await response.json();
        console.log("✅ API Response Data:", categories);
        return categories; // ✅ Return data for other functions
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
        return []; // ✅ Return empty array if fetch fails
    }
}

// 🔹 Load Categories Dynamically
async function loadCategoriesForBooking() {
    console.log("📌 Fetching categories for Booking Page...");
    const categories = await getCategories(); // ✅ Fetch categories

    const categoryList = document.getElementById("categoryList");
    if (!categoryList) {
        console.error("❌ categoryList element not found!");
        return;
    }

    categoryList.innerHTML = ""; // ✅ Clear previous content

    categories.forEach(category => {
        // ✅ Construct Image Path
        const imagePath = `../images/${category.catName}.svg`;

        // ✅ Create Category Button with Image and Passenger Info
        const card = `
        <div class="category-option" 
             onclick="selectCategory(${category.id}, '${category.catName}', ${category.perDayValue}, ${category.maxKmPerDay}, ${category.extraKm}, ${category.milePkg1}, ${category.pkg1Hrs}, ${category.milePkg2}, ${category.pkg2Hrs}, ${category.waitingPerHr})">
            <img src="${imagePath}" alt="${category.catName}" class="category-img"
                 onerror="this.onerror=null; this.src='../images/default.svg';">
            <p>${category.catName}</p>
            <span class="category-passengers"><i class="bi bi-people-fill"></i> ${category.maxPsngr} </span>
        </div>`;

        categoryList.innerHTML += card; // ✅ Append card to categoryList
    });

    console.log("✅ Categories Loaded on Booking Page!");
}
/**
 * ✅ Fetch Available Discounts for a User
 */
async function loadAvailableDiscounts() {
    const userId = getSessionUserId();
    if (!userId)
        return;

    console.log(`📌 Fetching available discounts for user ${userId}`);

    try {
        const response = await fetch(`${jointApiUrl}/availableDiscounts/${userId}`);
        if (!response.ok)
            throw new Error("🚨 Failed to fetch available discounts!");

        const discounts = await response.json();
        console.log("✅ Available Discounts:", discounts);

        const discountContainer = document.getElementById("discountList");
        if (!discountContainer) {
            console.error("❌ discountList element not found!");
            return;
        }

        discountContainer.innerHTML = ""; // Clear previous content

        if (discounts.length === 0) {
            discountContainer.innerHTML = `<p class="text-muted">No active discounts available.</p>`;
            return;
        }

        discounts.forEach(discount => {
            const discountCard = `
            <div class="discount-option card p-3 mb-2 text-center" onclick="selectDiscount(${discount.id}, ${discount.percentage})">
                <h6 class="fw-bold">Discount: ${discount.percentage}% OFF</h6>
                <p>Valid Until: ${discount.endDate}</p>
            </div>`;

            discountContainer.innerHTML += discountCard;
        });

    } catch (error) {
        console.error("🚨 Error Fetching Discounts:", error);
    }
}
// 🔹 Function to Handle Category Selection
function selectCategory(id, name, perDayValue, maxKmPerDay, extraKm, milePkg1, pkg1Hrs, milePkg2, pkg2Hrs, waitingPerHr) {
    console.log(`📌 Selected Category: ${name} (ID: ${id})`);

    // ✅ Remove selected class from all categories
    document.querySelectorAll(".category-option").forEach(el => el.classList.remove("selected"));

    // ✅ Add selected class to clicked category
    const selectedCategory = event.currentTarget;
    selectedCategory.classList.add("selected");

    // ✅ Store Selected Category in Hidden Input
    document.getElementById("selectedCategoryId").value = id;

    // ✅ Show Booking Options
    document.getElementById("bookingOptions").style.display = "block";

    // ✅ Populate Per Day Booking Details
    document.getElementById("perDayPrice").value = `Rs ${perDayValue}`;
    document.getElementById("maxKmPerDay").value = `${maxKmPerDay} km`;
    document.getElementById("extraKmCharge").value = `Rs ${extraKm} per extra km`;
    document.getElementById("extraKmRate").innerText = extraKm; // Update charge note

    // ✅ Populate Package Pricing, Included Waiting Hours, and Additional Charges
    document.getElementById("milePkg1Price").innerText = milePkg1;
    document.getElementById("milePkg2Price").innerText = milePkg2;
    document.getElementById("pkg1Hrs").innerText = pkg1Hrs;  // ✅ Added Package 1 included waiting hours
    document.getElementById("pkg2Hrs").innerText = pkg2Hrs;  // ✅ Added Package 2 included waiting hours
    document.getElementById("waitingCharge1").innerText = waitingPerHr;
    document.getElementById("waitingCharge2").innerText = waitingPerHr;
    document.getElementById("extraKmCharge1").innerText = extraKm;
    document.getElementById("extraKmCharge2").innerText = extraKm;
}


// ✅ Function to handle Package Selection
function selectPackage(packageType) {
    // Remove 'selected' class from all package options
    document.querySelectorAll(".package-option").forEach(el => el.classList.remove("selected"));

    // Add 'selected' class to the clicked package
    document.getElementById(`package${packageType}`).classList.add("selected");

    console.log(`📌 Selected Package: ${packageType}`);
}

// ✅ Function to safely toggle between booking types
function toggleBookingType(showId, hideId) {
    const showElement = document.getElementById(showId);
    const hideElement = document.getElementById(hideId);
    const finalBookingDetails = document.getElementById("finalBookingDetails");

    if (!showElement || !hideElement || !finalBookingDetails) {
        console.error(`❌ Missing elements: ${showId}, ${hideId}, or finalBookingDetails`);
        return;
    }

    showElement.style.display = "block";
    hideElement.style.display = "none";
    finalBookingDetails.style.display = "block"; // Show final booking details

    // ✅ Ensure correct button stays highlighted
    document.querySelectorAll(".btn-group .btn").forEach(btn => btn.classList.remove("selected"));
    event.target.classList.add("selected");
}

// ✅ Function to handle Per Day booking selection
function showPerDayBooking(event) {
    if (!document.getElementById("perDayDetails") || !document.getElementById("perMileageDetails")) {
        console.error("❌ Per Day / Per Mileage elements not found!");
        return;
    }

    toggleBookingType("perDayDetails", "perMileageDetails");

    // ✅ Make Booking Date field unselectable (Disabled)
    const bookingDateField = document.getElementById("bookingDate");
    const startDateField = document.getElementById("startDate");

    if (bookingDateField && startDateField) {
        bookingDateField.value = startDateField.value;
        bookingDateField.setAttribute("disabled", "true");
    }
}

// ✅ Function to handle Per Mileage booking selection
function showPerMileageBooking(event) {
    if (!document.getElementById("perDayDetails") || !document.getElementById("perMileageDetails")) {
        console.error("❌ Per Day / Per Mileage elements not found!");
        return;
    }

    toggleBookingType("perMileageDetails", "perDayDetails");

    // ✅ Enable Booking Date Selection
    const bookingDateField = document.getElementById("bookingDate");
    if (bookingDateField) {
        bookingDateField.removeAttribute("disabled");
    }
}

// Function to update Booking Date when Start Date is selected
function updateBookingDate() {
    let startDate = document.getElementById("startDate").value;
    document.getElementById("bookingDate").value = startDate;
    document.getElementById("endDate").setAttribute("min", startDate); // Prevent selecting a past end date
}


/**
 * ✅ Check Vehicle & Driver Availability using API
 */
async function checkAvailability() {
    const categoryId = document.getElementById("selectedCategoryId").value;
    const startDate = document.getElementById("bookingDate").value;
    let endDate = document.getElementById("endDate").value || startDate;

    if (!categoryId || !startDate) {
        alert("❌ Please select a category and a booking date!");
        return;
    }

    console.log("🔍 Checking availability for Category:", categoryId, "From:", startDate, "To:", endDate);

    try {
        // ✅ Check Available Vehicles for the Category & Date Range
        const vehicleResponse = await fetch(`${jointApiUrl}/availableVehicles/${categoryId}/${startDate}/${endDate}`);
        if (!vehicleResponse.ok)
            throw new Error("🚨 Failed to fetch vehicle availability!");
        const availableVehicles = await vehicleResponse.json();

        // ✅ Check Available Drivers for the Date Range
        const driverResponse = await fetch(`${jointApiUrl}/availableDrivers/${startDate}/${endDate}`);
        if (!driverResponse.ok)
            throw new Error("🚨 Failed to fetch driver availability!");
        const availableDrivers = await driverResponse.json();

        // ✅ Display Availability Results
        document.getElementById("vehicleAvailability").innerText = availableVehicles.length > 0
                ? `✅ ${availableVehicles.length} Vehicles Available`
                : "❌ No Vehicles Available";

        document.getElementById("driverAvailability").innerText = availableDrivers.length > 0
                ? `✅ ${availableDrivers.length} Drivers Available`
                : "❌ No Drivers Available";

        // ✅ Show "Reserve" button only if vehicles are available
        document.getElementById("showSummaryBtn").style.display = availableVehicles.length > 0 ? "block" : "none";

    } catch (error) {
        console.error("🚨 Error checking availability:", error);
        alert("❌ Error checking availability! Please try again.");
    }
}
let selectedDiscountId = null;
let discountPercentage = 0;

/**
 * ✅ Select a Discount and Apply It
 */
function selectDiscount(discId, percentage) {
    selectedDiscountId = discId;
    discountPercentage = percentage;

    // ✅ Remove 'selected' class from all discount options
    document.querySelectorAll(".discount-option").forEach(el => el.classList.remove("selected"));

    // ✅ Add 'selected' class to the clicked discount option
    let selectedElement = event.currentTarget;
    if (selectedElement) {
        selectedElement.classList.add("selected");
    }

    console.log(`📌 Selected Discount ID: ${discId}, Percentage: ${percentage}%`);

    // ✅ Update final price in the summary
    updateFinalPrice();

    // ✅ Update discount selection UI in booking summary
    updateDiscountSummary();
}


/**
 * ✅ Update Final Price after Discount Selection
 */
function updateFinalPrice() {
    let basePrice = parseFloat(document.getElementById("summaryAmount").innerText) || 0;
    let discountedPrice = basePrice - (basePrice * (discountPercentage / 100));

    document.getElementById("summaryAmount").innerText = discountedPrice.toFixed(2);
}
/**
 * ✅ Show Booking Summary before confirming payment
 */
function showBookingSummary() {
    const categoryName = document.querySelector(".category-option.selected p").innerText;
    const bookingDate = document.getElementById("bookingDate").value;
    let startDate = bookingDate;
    let endDate = document.getElementById("endDate").value || bookingDate;
    const perDayPrice = parseFloat(document.getElementById("perDayPrice").value.replace("Rs ", "")) || 0;
    const milePkg1Price = parseFloat(document.getElementById("milePkg1Price").innerText) || 0;
    const milePkg2Price = parseFloat(document.getElementById("milePkg2Price").innerText) || 0;

    let totalAmount = 0;
    let numberOfDays = 1;
    if (document.getElementById("perDayDetails").style.display !== "none") {
        numberOfDays = Math.max(1, (new Date(endDate) - new Date(startDate)) / (1000 * 60 * 60 * 24));
        totalAmount = perDayPrice * numberOfDays;
    } else {
        totalAmount = document.getElementById("package1").classList.contains("selected") ? milePkg1Price : milePkg2Price;
    }

    // ✅ Apply Discount if Selected
    let discountText = "No Discount Applied";
    let discountAmount = 0;
    if (selectedDiscountId && discountPercentage > 0) {
        discountAmount = (totalAmount * discountPercentage) / 100;
        totalAmount = totalAmount - discountAmount;
        discountText = `${discountPercentage}% OFF - Rs ${discountAmount.toFixed(2)} Discount Applied`;
    }

    // ✅ Set Summary Details
    document.getElementById("summaryCategory").innerText = categoryName;
    document.getElementById("summaryStartDate").innerText = startDate;
    document.getElementById("summaryEndDate").innerText = endDate;
    document.getElementById("summaryDays").innerText = numberOfDays;
    document.getElementById("summaryAmount").innerText = totalAmount.toFixed(2);

    // ✅ Update Discount Info in UI
    updateDiscountSummary(discountText);

    // ✅ Disable "Confirm & Pay" until checkbox is checked
    document.getElementById("confirmPayBtn").disabled = true;
    document.getElementById("termsCheckbox").checked = false;

    // ✅ Show Modal
    document.getElementById("bookingSummary").style.display = "flex";
}


/**
 * ✅ Generate & Download Booking Summary as PDF
 */
function downloadBookingSummaryPDF() {
    const { jsPDF } = window.jspdf; // Ensure jsPDF is available

    // Extract details from the summary
    const categoryName = document.getElementById("summaryCategory").innerText;
    const startDate = document.getElementById("summaryStartDate").innerText;
    const endDate = document.getElementById("summaryEndDate").innerText;
    const numberOfDays = document.getElementById("summaryDays").innerText;
    const totalAmount = document.getElementById("summaryAmount").innerText;
    const discountText = document.getElementById("summaryDiscount") ? document.getElementById("summaryDiscount").innerText : "No Discount Applied";

    // ✅ Create a new PDF document
    const doc = new jsPDF();

    // ✅ Add Header
    doc.setFont("helvetica", "bold");
    doc.setFontSize(16);
    doc.text("Mega City Cabs - Booking Summary", 15, 20);
    doc.setFontSize(12);
    doc.text("Thank you for booking with Mega City Cabs!", 15, 28);

    // ✅ Add Booking Details
    doc.setFont("helvetica", "normal");
    doc.setFontSize(12);
    let y = 40; // Initial Y position for details

    const details = [
        { label: "Category", value: categoryName },
        { label: "Start Date", value: startDate },
        { label: "End Date", value: endDate },
        { label: "Number of Days", value: numberOfDays },
        { label: "Total Amount", value: `Rs ${totalAmount}` },
        { label: "Discount", value: discountText },
    ];

    details.forEach(detail => {
        doc.text(`${detail.label}: ${detail.value}`, 15, y);
        y += 8;
    });

    // ✅ Add Terms & Conditions
    doc.setFont("helvetica", "bold");
    doc.text("Terms & Conditions:", 15, y + 10);
    doc.setFont("helvetica", "normal");
    const terms = [
        "• Full amount will be charged upon confirmation.",
        "• Additional kilometers will be charged separately.",
        "• Waiting charges apply if included hours are exceeded.",
        "• No refund for cancellations within 24 hours.",
    ];

    y += 18;
    terms.forEach(term => {
        doc.text(term, 15, y);
        y += 6;
    });

    // ✅ Save & Download PDF
    doc.save(`Booking_Summary_${startDate}.pdf`);
}
/**
 * ✅ Update the Discount Section in Booking Summary
 */
function updateDiscountSummary(discountText = "No Discount Applied") {
    let discountSummaryElement = document.getElementById("summaryDiscount");

    if (!discountSummaryElement) {
        let summaryContainer = document.querySelector(".summary-content");
        let discountElement = document.createElement("p");
        discountElement.innerHTML = `<strong>Discount:</strong> <span id="summaryDiscount">${discountText}</span>`;
        summaryContainer.appendChild(discountElement);
    } else {
        discountSummaryElement.innerText = discountText;
}
}
function togglePayButton() {
    document.getElementById("confirmPayBtn").disabled = !document.getElementById("termsCheckbox").checked;
}



/**
 * ✅ Confirm Payment & Proceed with Booking
 */
function confirmPayment() {
    document.getElementById("bookingSummary").style.display = "none";
    reserveBooking();
}

/**
 * ✅ Close Booking Summary
 */
function closeBookingSummary() {
    document.getElementById("bookingSummary").style.display = "none";
}


/**
 * ✅ Reserve the Booking with Selected Discount
 */
async function reserveBooking() {
    const userId = getSessionUserId();
    if (!userId) {
        alert("❌ Session expired! Please log in again.");
        window.location.href = "login.jsp";
        return;
    }

    const categoryId = document.getElementById("selectedCategoryId").value;
    const startDate = document.getElementById("bookingDate").value;
    let endDate = document.getElementById("endDate").value || startDate;
    const startTime = document.getElementById("bookingTime").value;
    const startLocation = document.getElementById("pickupAddress").value;
    const finalPrice = parseFloat(document.getElementById("summaryAmount").innerText) || 0;

    if (!categoryId || !startDate || !startTime || !startLocation) {
        alert("❌ Please fill in all required details!");
        return;
    }

    const bookingData = {
        userId: userId,
        categoryId: categoryId,
        startDate: startDate,
        endDate: endDate,
        startTime: startTime + ":00",
        startLocation: startLocation,
        dissId: selectedDiscountId, // ✅ Send selected discount ID
        finalPrice: finalPrice // ✅ Send discounted final price
    };

    console.log("🚀 Sending Booking Data:", bookingData);

    try {
        const response = await fetch(`${jointApiUrl}/createReservation`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(bookingData)
        });

        if (response.ok) {
            alert("✅ Booking Reserved Successfully!");
            loadPage("customerDash.jsp");
        } else {
            alert("❌ Failed to reserve booking! Please try again.");
        }
    } catch (error) {
        console.error("🚨 Error reserving booking:", error);
        alert("❌ Error reserving booking! Please try again.");
    }
}


/**
 * ✅ Get Logged-in User ID from SessionStorage
 */
function getSessionUserId() {
    let userId = sessionStorage.getItem("userId"); // Retrieve user ID from session storage
    if (!userId) {
        console.warn("⚠️ User ID not found in sessionStorage! User may be logged out.");
        return null;
    }
    return parseInt(userId, 10); // Convert to integer for safety
}
// ==========================================
// 🔹 FETCH NEXT TRIP FOR CUSTOMER DASHBOARD
// ==========================================

/**
 * ✅ Fetch & Display Next Trip for the Customer
 */
async function loadTripDetails(userId = null) {
    if (!userId) {
        userId = getSessionUserId();
        if (!userId) {
            console.warn("⚠️ User ID not found in session. Skipping trip details load.");
            return;
        }
    }

    try {
        console.log(`📌 Fetching reservations for user ID: ${userId}`);
        const reservationResponse = await fetch(`${reservationApiUrl}${userId}`);

        if (reservationResponse.status === 404) {
            showNoUpcomingTrips();
            return;
        }

        if (!reservationResponse.ok) {
            throw new Error(`🚨 Failed to fetch reservations. Server responded with status: ${reservationResponse.status}`);
        }

        const reservations = await reservationResponse.json();
        console.log("✅ Fetched Reservations:", reservations);

        if (!Array.isArray(reservations) || reservations.length === 0) {
            console.warn("⚠️ No upcoming trips found.");
            showNoUpcomingTrips();
            return;
        }

        const today = new Date().toISOString().split("T")[0];
        const upcomingTrips = reservations
                .filter(trip => new Date(trip.stDate) >= new Date(today) && trip.stat !== "Cancelled")
                .sort((a, b) => new Date(a.stDate) - new Date(b.stDate));

        if (upcomingTrips.length === 0) {
            console.log("❌️ No upcoming trips available.");
            showNoUpcomingTrips();
            return;
        }

        const nextTrip = upcomingTrips[0];
        console.log("✅ Next Trip:", nextTrip);

        // 🔹 Get DOM elements
        const tripDetailsContainer = document.getElementById("tripDetailsContainer");
        const noTripsMessage = document.getElementById("noUpcomingTrips");

        if (tripDetailsContainer)
            tripDetailsContainer.style.display = "block";
        if (noTripsMessage)
            noTripsMessage.style.display = "none";

        const elements = {
            tripId: document.getElementById("tripId"),
            tripStartDate: document.getElementById("tripStartDate"),
            tripEndDate: document.getElementById("tripEndDate"),
            tripStartTime: document.getElementById("tripStartTime"),
            tripLocation: document.getElementById("tripLocation"),
            tripVehicle: document.getElementById("tripVehicle"),
            tripDriver: document.getElementById("tripDriver"),
            tripStatus: document.getElementById("tripStatus"),
            tripDiscount: document.getElementById("tripDiscount"),
            tripFinalPrice: document.getElementById("tripFinalPrice")
        };

        if (elements.tripId)
            elements.tripId.value = nextTrip.id || "";
        if (elements.tripStartDate)
            elements.tripStartDate.value = nextTrip.stDate || "";
        if (elements.tripEndDate)
            elements.tripEndDate.value = nextTrip.endDate || "";
        if (elements.tripStartTime)
            elements.tripStartTime.value = nextTrip.stTime || "N/A";
        if (elements.tripLocation)
            elements.tripLocation.value = nextTrip.stLocation || "";
        if (elements.tripVehicle)
            elements.tripVehicle.value = nextTrip.vehicleId || "Not Assigned";
        if (elements.tripDriver)
            elements.tripDriver.value = nextTrip.driverId || "Not Assigned";
        if (elements.tripStatus)
            elements.tripStatus.value = nextTrip.stat || "";

        if (elements.tripDiscount) {
            elements.tripDiscount.value = nextTrip.dissId ? `${nextTrip.dissId}%` : "No Discount";
        }

        if (elements.tripFinalPrice) {
            elements.tripFinalPrice.value = nextTrip.finalPrice ? `Rs ${parseFloat(nextTrip.finalPrice).toFixed(2)}` : "Not Finalized";
        }

    } catch (error) {
        console.error("🚨 Error loading trip details:", error);
        showNoUpcomingTrips();
}
}

/**
 * ✅ Remove "Your Next Trip" Form & Show "No Upcoming Trips"
 */
function showNoUpcomingTrips() {
    const tripDetailsContainer = document.getElementById("tripDetailsContainer");
    const noTripsMessage = document.getElementById("noUpcomingTrips");

    if (tripDetailsContainer)
        tripDetailsContainer.style.display = "none"; // Hide trip form
    if (noTripsMessage)
        noTripsMessage.style.display = "block"; // Show "No Trips" message
}
/**
 * ✅ Display "No Upcoming Trips" Message in UI
 */
function displayNoUpcomingTripsMessage() {
    const tripDetailsContainer = document.getElementById("tripDetailsContainer");
    if (tripDetailsContainer) {
        tripDetailsContainer.innerHTML = `
            <div class="alert alert-warning text-center">
                <i class="bi bi-exclamation-circle"></i> No upcoming trips found.
            </div>
        `;
    }
}

async function cancelTrip(tripId) {
    if (!confirm("Are you sure you want to cancel this trip?"))
        return;

    try {
        console.log(`🔍 Fetching details for trip ID: ${tripId}`);

        // ✅ Step 1: Fetch Trip Details by ID
        const tripResponse = await fetch(`${reservationApiUrl}reservation/${tripId}`);
        console.log("🔍 Raw API Response:", tripResponse);

        const tripData = await tripResponse.json();
        console.log("✅ Parsed Trip Data:", tripData);

        if (!tripData || tripData === "null") {
            console.warn("⚠️ No trip data found.");
            alert("⚠️ No trip data found. Please try again.");
            return;
        }

        const {userId, vehicleId, driverId, dissId, stDate} = tripData;

        const dateObj = new Date(stDate);
        const formattedDate = dateObj.getFullYear() + '-' +
                String(dateObj.getMonth() + 1).padStart(2, '0') + '-' +
                String(dateObj.getDate()).padStart(2, '0'); // "YYYY-MM-DD"

        // ✅ Step 2: Update Reservation Status
        console.log(`❌ Updating reservation status for trip ID: ${tripId}`);
        const cancelResponse = await fetch(`${reservationApiUrl}updateStatus/${tripId}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({stat: "Cancelled"}),
        });

        if (!cancelResponse.ok)
            throw new Error("🚨 Failed to update reservation status.");

        // ✅ Step 3: Free Up Resources
        if (vehicleId) {
            console.log(`❌ Removing vehicle availability for vehicle ID: ${vehicleId} on ${formattedDate}`);
            await fetch(`${vehicleAvailabilityApiUrl}delete/${vehicleId}/${formattedDate}`, {
                method: "DELETE",
            });
        }

        if (driverId) {
            console.log(`❌ Removing driver availability for driver ID: ${driverId} on ${formattedDate}`);
            await fetch(`${driverAvailabilityApiUrl}delete/${driverId}/${formattedDate}`, {
                method: "DELETE",
            });
        }

        if (dissId) {
            console.log(`❌ Removing discount usage for user ID: ${userId}, discount ID: ${dissId}`);
            await fetch(`${discountAvailabilityApiUrl}delete/${userId}/${dissId}`, {
                method: "DELETE",
            });
        }

        alert("✅ Trip Cancelled Successfully!");
        loadBookingHistory(); // Refresh UI

    } catch (error) {
        console.error("🚨 Error Cancelling Trip:", error);
        alert("❌ Failed to cancel trip. Please try again.");
    }
}

/**
 * ✅ Load Booking History and Populate Table
 */
async function loadBookingHistory() {
    console.log("📌 Fetching Booking History...");
    const userId = getSessionUserId();
    if (!userId)
        return;

    const bookingHistoryTable = document.getElementById("bookingHistoryTable");
    bookingHistoryTable.innerHTML = `
        <tr><td colspan="10" class="no-data-message">Loading booking history...</td></tr>
    `;

    try {
        // ✅ Fetch reservations
        const reservationsResponse = await fetch(`${reservationApiUrl}${userId}`);
        if (!reservationsResponse.ok)
            throw new Error("🚨 Failed to fetch reservations");
        const reservations = await reservationsResponse.json();
        if (reservations.length === 0) {
            bookingHistoryTable.innerHTML = `
                <tr><td colspan="10" class="no-data-message">No booking history found.</td></tr>
            `;
            return;
        }

        // ✅ Fetch Vehicles, Drivers, Discounts, and Categories for Details
        const [vehiclesData, driversData, discountsData, categoriesData] = await Promise.all([
            fetchData(vehicleApiUrl), // Vehicles (Contains catId)
            fetchData(driverApiUrl), // Drivers
            fetchData(discountApiUrl), // Discounts
            fetchData(categoryApiUrl) // Categories (Contains catName)
        ]);

        bookingHistoryTable.innerHTML = ""; // Clear previous content

        reservations.forEach(reservation => {
            // ✅ Get Vehicle Category Name
            const vehicle = vehiclesData.find(v => v.id === reservation.vehicleId);
            const category = categoriesData.find(c => c.id === (vehicle ? vehicle.catId : null));
            const categoryName = category ? category.catName : "Unknown";

            // ✅ Get Driver Name
            const driver = driversData.find(d => d.id === reservation.driverId);
            const driverName = driver ? driver.dName : "Not Assigned";

            // ✅ Get Discount Percentage
            const discount = discountsData.find(d => d.id === reservation.dissId);
            const discountPercentage = discount ? `${discount.percentage}%` : "No Discount";

            // ✅ Show Cancel button only if status = "Approved"
            const actionButton = reservation.stat === "Approved"
                    ? `<button class="btn btn-danger btn-sm" onclick="cancelTrip(${reservation.id})">
                    Cancel
                   </button>`
                    : `<span class="text-muted">N/A</span>`;

            // ✅ Create Row with Status Column
            const row = `
                <tr>
                    <td>${reservation.id}</td>
                    <td>${categoryName}</td>
                    <td>${driverName}</td>
                    <td>${reservation.stDate}</td>
                    <td>${formatTime(reservation.stTime)}</td>
                    <td>${reservation.stLocation}</td>
                    <td>${reservation.endDate || "N/A"}</td>
                    <td>${discountPercentage}</td>
                    <td><span class="status-badge ${reservation.stat.toLowerCase()}">${reservation.stat}</span></td>
                    <td>${actionButton}</td>
                </tr>
            `;
            bookingHistoryTable.innerHTML += row;
        });

    } catch (error) {
        console.error("🚨 Error Fetching Booking History:", error);
        bookingHistoryTable.innerHTML = `
            <tr><td colspan="10" class="no-data-message">Error loading data. Try again.</td></tr>
        `;
    }
}

/**
 * ✅ Utility Function: Fetch API Data
 */
async function fetchData(apiUrl) {
    try {
        const response = await fetch(apiUrl);
        return response.ok ? await response.json() : [];
    } catch (error) {
        console.error(`🚨 Error fetching data from ${apiUrl}:`, error);
        return [];
    }
}

/**
 * ✅ Format Time (24h to 12h)
 */
function formatTime(timeString) {
    if (!timeString)
        return "N/A";
    const [hours, minutes] = timeString.split(":");
    const hour = parseInt(hours);
    const ampm = hour >= 12 ? "PM" : "AM";
    return `${hour % 12 || 12}:${minutes} ${ampm}`;
}



/**
 * ✅ Load User Profile
 */
async function loadUserProfile() {
    console.log("📌 Loading User Profile...");

    const userId = getSessionUserId();
    if (!userId) {
        console.error("🚨 No user ID found in session!");
        return;
    }

    try {
        const response = await fetch(`${userApiUrl}/${userId}`); // ✅ Fixed URL
        if (!response.ok) throw new Error("🚨 Failed to fetch user data!");

        const user = await response.json();
        console.log("✅ User Data:", user);

        // ✅ Check if elements exist before setting their values
        const userIdField = document.getElementById("userId");
        const emailField = document.getElementById("email");
        const phoneField = document.getElementById("phone");
        const addressField = document.getElementById("address");

        if (!userIdField || !emailField || !phoneField || !addressField) {
            console.error("🚨 One or more profile form elements are missing!");
            return;
        }

        userIdField.value = user.id;
        emailField.value = user.email;
        phoneField.value = user.phone;
        addressField.value = user.address;

    } catch (error) {
        console.error("🚨 Error loading user profile:", error);
    }
}

/**
 * ✅ Update Contact Information
 */
/**
 * ✅ Update Contact Information (Email, Phone, Address)
 */
async function updateProfile(event) {
    event.preventDefault();

    const userId = document.getElementById("userId").value;
    const updatedUser = {
        email: document.getElementById("email").value,
        phone: document.getElementById("phone").value,
        address: document.getElementById("address").value
    };

    console.log("📌 Sending Profile Update:", updatedUser);

    try {
        const response = await fetch(`${userApiUrl}/${userId}/updateContact`, { // ✅ Corrected API Endpoint
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedUser)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || "🚨 Failed to update profile!");
        }

        alert("✅ Profile updated successfully!");
        loadUserProfile(); // Reload profile to show updated details

    } catch (error) {
        console.error("🚨 Error updating profile:", error);
        alert(error.message);
    }
}

/**
 * ✅ Change Password
 */
async function changePassword(event) {
    event.preventDefault();

    const userId = document.getElementById("userId").value;
    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (newPassword !== confirmPassword) {
        alert("❌ Passwords do not match!");
        return;
    }

    const passwordData = { currentPassword, newPassword };

    console.log("📌 Sending Password Change Request:", passwordData);

    try {
        const response = await fetch(`${userApiUrl}/${userId}/changePassword`, { // ✅ Corrected API Endpoint
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(passwordData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || "🚨 Failed to change password!");
        }

        alert("✅ Password changed successfully!");
        document.getElementById("passwordForm").reset();

    } catch (error) {
        console.error("🚨 Error changing password:", error);
        alert(error.message);
    }
}

/**
 * ✅ Load Pending Payments from reservation_finalize (Simplified)
 */
async function loadPendingPayments() {
    console.log("📌 Fetching pending payments...");
    
    const tableBody = document.getElementById("pendingPaymentsTable");
    const pendingPaymentsSection = document.querySelector(".pending-payments-section"); // Get the section

    if (!tableBody || !pendingPaymentsSection) {
        console.error("🚨 'pendingPaymentsTable' or 'pending-payments-section' not found!");
        return;
    }

    tableBody.innerHTML = `<tr><td colspan="3" class="text-center">Loading...</td></tr>`;

    try {
        const response = await fetch(`${reservationFinalizeApiUrl}/all`);
        if (!response.ok) throw new Error("Failed to fetch payments.");

        let pendingPayments = await response.json();
        pendingPayments = pendingPayments.filter(payment => payment.stat === "Pending");

        if (pendingPayments.length === 0) {
            pendingPaymentsSection.style.display = "none"; // ✅ Hide section if no pending payments
            return;
        } else {
            pendingPaymentsSection.style.display = "block"; // ✅ Show section if payments exist
        }

        tableBody.innerHTML = "";

        pendingPayments.forEach(payment => {
            const row = `
                <tr>
                    <td>${payment.resId}</td>
                    <td>Rs. ${payment.price.toFixed(2)}</td>
                    <td>
                        <button class="btn btn-info btn-sm" onclick="viewPendingPayment(${payment.id}, ${payment.resId})">
                            <i class="fas fa-eye"></i> View
                        </button>
                    </td>
                </tr>`;
            tableBody.innerHTML += row;
        });

    } catch (error) {
        console.error("🚨 Error fetching pending payments:", error);
        tableBody.innerHTML = `<tr><td colspan="3" class="text-danger text-center">Failed to load payments.</td></tr>`;
    }
}

/**
 * ✅ View Pending Payment Details in Modal
 */
async function viewPendingPayment(finalizeId, resId) {
    console.log(`📌 Viewing details for Finalization ID: ${finalizeId}`);

    try {
        // Fetch reservation finalization details
        const finalizeResponse = await fetch(`${reservationFinalizeApiUrl}/${finalizeId}`);
        if (!finalizeResponse.ok) throw new Error("Failed to fetch finalize details.");
        const finalizeData = await finalizeResponse.json();

        // Fetch reservation details
        const reservationResponse = await fetch(`${reservationApiUrl}reservation/${resId}`);
        if (!reservationResponse.ok) throw new Error("Failed to fetch reservation details.");
        const reservationData = await reservationResponse.json();

        // Fill modal data
        document.getElementById("modalResId").innerText = finalizeData.resId;
        document.getElementById("modalStartDate").innerText = reservationData.stDate;
        document.getElementById("modalEndDate").innerText = reservationData.endDate;
        document.getElementById("modalFinalPrice").innerText = reservationData.finalPrice.toFixed(2);
        document.getElementById("modalExtraKm").innerText = finalizeData.extraKm;
        document.getElementById("modalExtraHr").innerText = finalizeData.extraHr;
        document.getElementById("modalPrice").innerText = finalizeData.price.toFixed(2);

        // Attach payment function to "Pay Now" button
        document.getElementById("payNowBtn").setAttribute("onclick", `payPendingPayment(${finalizeId}, ${resId})`);

        // Show modal
        new bootstrap.Modal(document.getElementById("paymentModal")).show();

    } catch (error) {
        console.error("🚨 Error fetching payment details:", error);
        alert("🚨 Failed to load payment details.");
    }
}

/**
 * ✅ Mark Payment as Completed
 */
async function payPendingPayment(finalizeId, resId) {
    if (!confirm("Are you sure you want to pay for this trip?")) return;

    try {
        // 🔹 Update reservation_finalize to Paid
        const finalizeResponse = await fetch(`${reservationFinalizeApiUrl}/updateStatus/${finalizeId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ stat: "Paid" })
        });

        if (!finalizeResponse.ok) throw new Error("Failed to update finalize status!");

        // 🔹 Update reservation status to Finalized
        const reservationResponse = await fetch(`${reservationApiUrl}updateStatus/${resId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ stat: "Finalized" })
        });

        if (!reservationResponse.ok) throw new Error("Failed to update reservation status!");

        alert("✅ Payment successful! Your trip has been finalized.");
        loadPendingPayments(); // Refresh the table

        // Close modal
        bootstrap.Modal.getInstance(document.getElementById("paymentModal")).hide();

    } catch (error) {
        console.error("🚨 Error processing payment:", error);
        alert("🚨 Payment failed! Try again.");
    }
}

