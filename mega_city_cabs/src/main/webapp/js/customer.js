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
            }

            // ✅ Load Booking Page & Categories
            if (url.includes("bookRide.jsp")) {
                console.log("📌 Booking Page Loaded! Fetching Categories...");
                loadCategoriesForBooking();
            }

            // ✅ Load Booking History Page
            if (url.includes("bookingHistory.jsp")) {
                console.log("📌 Booking History Page Loaded! Fetching Trips...");
                loadBookingHistory();
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


// ==========================================
// 🔹 CATEGORY MANAGEMENT FOR BOOKING PAGE
// ==========================================
const categoryApiUrl = "http://localhost:8080/restAPIMCCabs/api/categories";

// 🔹 Fetch Categories Data
async function getCategories() {
    console.log("📌 Fetching categories...");

    try {
        const response = await fetch(categoryApiUrl);
        if (!response.ok) throw new Error(`❌ HTTP Error: ${response.status}`);

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

const jointApiUrl = "http://localhost:8080/restAPIMCCabs/api/joint";

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
        if (!vehicleResponse.ok) throw new Error("🚨 Failed to fetch vehicle availability!");
        const availableVehicles = await vehicleResponse.json();

        // ✅ Check Available Drivers for the Date Range
        const driverResponse = await fetch(`${jointApiUrl}/availableDrivers/${startDate}/${endDate}`);
        if (!driverResponse.ok) throw new Error("🚨 Failed to fetch driver availability!");
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

/**
 * ✅ Show Booking Summary before confirming payment
 */
function showBookingSummary() {
    const categoryName = document.querySelector(".category-option.selected p").innerText; // Get selected category name
    const bookingDate = document.getElementById("bookingDate").value;
    let startDate = bookingDate; 
    let endDate = document.getElementById("endDate").value || bookingDate; // Default to booking date if not set
    const perDayPrice = parseFloat(document.getElementById("perDayPrice").value.replace("Rs ", "")) || 0;
    const milePkg1Price = parseFloat(document.getElementById("milePkg1Price").innerText) || 0;
    const milePkg2Price = parseFloat(document.getElementById("milePkg2Price").innerText) || 0;

    let totalAmount = 0;
    let numberOfDays = 1; // Default to 1 day if no endDate
    if (document.getElementById("perDayDetails").style.display !== "none") {
        numberOfDays = Math.max(1, (new Date(endDate) - new Date(startDate)) / (1000 * 60 * 60 * 24));
        totalAmount = perDayPrice * numberOfDays;
    } else {
        totalAmount = document.getElementById("package1").classList.contains("selected") ? milePkg1Price : milePkg2Price;
    }

    // ✅ Set Summary Details
    document.getElementById("summaryCategory").innerText = categoryName;
    document.getElementById("summaryStartDate").innerText = startDate;
    document.getElementById("summaryEndDate").innerText = endDate;
    document.getElementById("summaryDays").innerText = numberOfDays; // Display number of days
    document.getElementById("summaryAmount").innerText = totalAmount.toFixed(2);

    // ✅ Disable "Confirm & Pay" until checkbox is checked
    document.getElementById("confirmPayBtn").disabled = true;
    document.getElementById("termsCheckbox").checked = false;

    // ✅ Show Modal
    document.getElementById("bookingSummary").style.display = "flex";
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
 * ✅ Reserve the Booking
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
        startLocation: startLocation
    };

    console.log("🚀 Sending Booking Data:", bookingData);

    try {
        const response = await fetch(`${jointApiUrl}/createReservation`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bookingData)
        });

        if (response.ok) {
            alert("✅ Booking Reserved Successfully!");
            loadPage("bookingHistory.jsp");
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
const reservationApiUrl = "http://localhost:8080/restAPIMCCabs/api/reservations/";
const driverApiUrl = "http://localhost:8080/restAPIMCCabs/api/drivers/";

/**
 * ✅ Fetch & Display Next Trip for the Customer
 */
async function loadTripDetails(userId = null) {
    // If userId is not provided, try to get it from session
    if (!userId) {
        userId = getSessionUserId();
        if (!userId) {
            console.error("🚨 User ID not found!");
            return;
        }
    }

    try {
        console.log(`📌 Fetching reservations for user ID: ${userId}`);
        const reservationResponse = await fetch(`${reservationApiUrl}${userId}`);
        if (!reservationResponse.ok) throw new Error("🚨 Failed to fetch reservations");

        const reservations = await reservationResponse.json();
        console.log("✅ Fetched Reservations:", reservations);

        if (reservations.length === 0) {
            console.warn("⚠️ No reservations found!");
            return;
        }

        // ✅ Get the upcoming trip
        const today = new Date().toISOString().split("T")[0];
        const upcomingTrips = reservations
            .filter(trip => new Date(trip.stDate) >= new Date(today) && trip.stat !== "Cancelled")
            .sort((a, b) => new Date(a.stDate) - new Date(b.stDate));

        if (upcomingTrips.length === 0) {
            console.warn("⚠️ No upcoming trips available!");
            return;
        }

        const nextTrip = upcomingTrips[0]; // ✅ Get the closest trip
        console.log("✅ Next Trip:", nextTrip);

        // ✅ Fetch category and driver details
        const [categoryResponse, driverResponse] = await Promise.all([
            fetch(`${categoryApiUrl}/${nextTrip.id}`), 
            nextTrip.driverId ? fetch(`${driverApiUrl}${nextTrip.driverId}`) : null
        ]);

        const categoryData = categoryResponse.ok ? await categoryResponse.json() : { catName: "Unknown Category" };
        const driverData = driverResponse && driverResponse.ok ? await driverResponse.json() : { dName: "Not Assigned" };

        console.log("✅ Category:", categoryData);
        console.log("✅ Driver:", driverData);

        // ✅ Populate the form with null checks for all elements
        const elements = {
            tripId: document.getElementById("tripId"),
            tripStartDate: document.getElementById("tripStartDate"),
            tripEndDate: document.getElementById("tripEndDate"),
            tripStartTime: document.getElementById("tripStartTime"),
            tripLocation: document.getElementById("tripLocation"),
            tripVehicle: document.getElementById("tripVehicle"),
            tripDriver: document.getElementById("tripDriver"),
            tripStatus: document.getElementById("tripStatus")
        };

        if (elements.tripId) elements.tripId.value = nextTrip.id;
        if (elements.tripStartDate) elements.tripStartDate.value = nextTrip.stDate;
        if (elements.tripEndDate) elements.tripEndDate.value = nextTrip.endDate;
        if (elements.tripStartTime) elements.tripStartTime.value = nextTrip.stTime || "N/A";
        if (elements.tripLocation) elements.tripLocation.value = nextTrip.stLocation;
        if (elements.tripVehicle) elements.tripVehicle.value = categoryData.catName;
        if (elements.tripDriver) elements.tripDriver.value = driverData.dName;
        if (elements.tripStatus) elements.tripStatus.value = nextTrip.stat;

    } catch (error) {
        console.error("🚨 Error loading trip details:", error);
    }
}

/**
 * ✅ Cancel an Upcoming Trip
 */
async function cancelTrip(tripId) {
    if (!confirm("Are you sure you want to cancel this trip?")) return;

    try {
        const response = await fetch(`${reservationApiUrl}/cancel/${tripId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ stat: "Cancelled" }),
        });

        if (!response.ok) throw new Error("Failed to cancel the trip.");

        alert("✅ Trip Cancelled Successfully!");
        loadTripDetails(); // Fixed: Calling the correct function name
    } catch (error) {
        console.error("🚨 Error Cancelling Trip:", error);
        alert("❌ Failed to cancel trip. Please try again.");
    }
}

/**
 * ✅ Load Booking History
 */
async function loadBookingHistory() {
    console.log("📌 Fetching Booking History...");

    const userId = getSessionUserId();
    if (!userId) return;

    try {
        const response = await fetch(`${reservationApiUrl}${userId}`);
        if (!response.ok) throw new Error(`❌ Server Error: ${response.status}`);

        let reservations = await response.json();
        console.log("✅ Fetched Booking History:", reservations);

        // ✅ Update UI with booking history
        const bookingHistoryTable = document.getElementById("bookingHistoryTable");
        if (!bookingHistoryTable) {
            console.error("❌ Booking history table not found!");
            return;
        }
        
        // Clear previous content
        bookingHistoryTable.innerHTML = "";
        
        // Check if we have reservations
        if (reservations.length === 0) {
            bookingHistoryTable.innerHTML = `
                <tr>
                    <td colspan="8" class="text-center">No booking history found.</td>
                </tr>`;
            return;
        }
        
        // Sort by start date, newest first
        reservations.sort((a, b) => new Date(b.stDate) - new Date(a.stDate));
        
        // Create rows for each reservation
        reservations.forEach(reservation => {
            const row = document.createElement("tr");
            
            // Format date
            const formattedStartDate = new Date(reservation.stDate).toLocaleDateString();
            
            row.innerHTML = `
                <td>${reservation.id}</td>
                <td>${formattedStartDate}</td>
                <td>${reservation.stTime || "N/A"}</td>
                <td>${reservation.stLocation || "N/A"}</td>
                <td>${reservation.vehicleId || "Not Assigned"}</td>
                <td>${reservation.driverId || "Not Assigned"}</td>
                <td><span class="status-badge ${reservation.stat.toLowerCase()}">${reservation.stat}</span></td>
                <td>
                    ${reservation.stat !== "Cancelled" && reservation.stat !== "Completed" ? 
                        `<button class="btn btn-sm btn-danger" onclick="cancelTrip(${reservation.id})">Cancel</button>` : 
                        ""}
                </td>
            `;
            
            bookingHistoryTable.appendChild(row);
        });

    } catch (error) {
        console.error("🚨 Error Fetching Booking History:", error);
        const bookingHistoryTable = document.getElementById("bookingHistoryTable");
        if (bookingHistoryTable) {
            bookingHistoryTable.innerHTML = `
                <tr>
                    <td colspan="8" class="text-center">Error loading booking history. Please try again later.</td>
                </tr>`;
        }
    }
}