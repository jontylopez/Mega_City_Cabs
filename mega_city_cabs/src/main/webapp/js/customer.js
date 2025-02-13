document.addEventListener("DOMContentLoaded", function () {
    console.log("📌 Customer Dashboard Loaded!");

    // ✅ Load Customer Pages Dynamically
    loadPage("customerDash.jsp");

    // Attach event listeners
    setupLinks();
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
            document.querySelector(".main-content").innerHTML = html;
            setupLinks(); // ✅ Reattach event listeners
            console.log("✅ Page Loaded: ", url);

            // ✅ Attach event listeners for booking buttons
            if (url.includes("bookRide.jsp")) {
                console.log("📌 Booking Page Loaded! Fetching categories...");
                loadCategoriesForBooking();

                // Attach event listeners for booking buttons
                const perDayButton = document.getElementById("perDayButton");
                const perMileageButton = document.getElementById("perMileageButton");

                if (perDayButton) {
                    perDayButton.addEventListener("click", showPerDayBooking);
                }
                if (perMileageButton) {
                    perMileageButton.addEventListener("click", showPerMileageBooking);
                }
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
        // ✅ Construct Image Path (Appending .svg to catName)
        const imagePath = `../images/${category.catName}.svg`;

        // ✅ Create Category Button with Image and Passenger Info
        const card = `
        <div class="category-option" 
             onclick="selectCategory(${category.id}, '${category.catName}', ${category.perDayValue}, ${category.maxKmPerDay}, ${category.extraKm}, ${category.milePkg1}, ${category.milePkg2}, ${category.waitingPerHr})">
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
function selectCategory(id, name, perDayValue, maxKmPerDay, extraKm, milePkg1, milePkg2, waitingPerHr) {
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

    // ✅ Populate Package Pricing and Additional Charges
    document.getElementById("milePkg1Price").innerText = milePkg1;
    document.getElementById("milePkg2Price").innerText = milePkg2;

    // ✅ Populate Waiting and Extra Km Charges for Packages
    document.getElementById("waitingCharge1").innerText = waitingPerHr;
    document.getElementById("waitingCharge2").innerText = waitingPerHr;
    document.getElementById("extraKmCharge1").innerText = extraKm;
    document.getElementById("extraKmCharge2").innerText = extraKm;
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

// Function to select a package for mileage booking
function selectPackage(packageType) {
    // Remove 'selected' class from all package options
    document.querySelectorAll(".package-option").forEach(el => el.classList.remove("selected"));

    // Add 'selected' class to the clicked package
    document.getElementById(`package${packageType}`).classList.add("selected");

    console.log(`📌 Selected Package: ${packageType}`);
}


const jointApiUrl = "http://localhost:8080/restAPIMCCabs/api/joint";

/**
 * ✅ Check Vehicle & Driver Availability using API
 */
async function checkAvailability() {
    const categoryId = document.getElementById("selectedCategoryId").value;
    const startDate = document.getElementById("bookingDate").value;
    let endDate = document.getElementById("endDate").value || startDate; // Use bookingDate if endDate is empty

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
        document.getElementById("reserveBookingBtn").style.display = availableVehicles.length > 0 ? "block" : "none";

    } catch (error) {
        console.error("🚨 Error checking availability:", error);
        alert("❌ Error checking availability! Please try again.");
    }
}

/**
 * ✅ Reserve the Booking by Sending Data to the Backend
 */
async function reserveBooking() {
    const userId = getSessionUserId();
    if (!userId) {
        alert("❌ Session expired! Please log in again.");
        window.location.href = "login.jsp"; // Redirect to login page
        return;
    }

    const categoryId = document.getElementById("selectedCategoryId").value;
    const startDate = document.getElementById("bookingDate").value;
    let endDate = document.getElementById("endDate").value || startDate; // Use startDate if endDate is empty
    const startTime = document.getElementById("bookingTime").value;
    const startLocation = document.getElementById("pickupAddress").value;

    if (!categoryId || !startDate || !startTime || !startLocation) {
        alert("❌ Please fill in all required details!");
        return;
    }

    const bookingData = {
        userId: userId, // ✅ Now correctly retrieved from sessionStorage
        categoryId: categoryId,
        startDate: startDate,
        endDate: endDate,
        startTime: startTime + ":00", // Append seconds for SQL Time format
        startLocation: startLocation
    };

    console.log("🚀 Sending Booking Data:", bookingData);

    try {
        const response = await fetch(`${jointApiUrl}/createReservation`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bookingData)
        });

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);

        if (response.ok) {
            alert("✅ Booking Reserved Successfully! Our team will contact you soon.");
            loadPage("bookingHistory.jsp"); // ✅ Load inside main-content dynamically
        } else {
            alert(responseData.message || "❌ Failed to reserve booking! Please try again.");
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
