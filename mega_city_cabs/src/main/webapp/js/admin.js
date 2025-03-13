document.addEventListener("DOMContentLoaded", function () {
    console.log("📌 Admin Dashboard Loaded!");

    // Load default admin dashboard
    loadPage("adminDash.jsp");

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
                setupLinks(); // Ensure links are clickable

                console.log("✅ Page Loaded: ", url);
                
                if (url.includes("adminDash.jsp")) {
                 console.log("📌 Admin Dashboard Loaded! Fetching pending reservations...");
                setTimeout(loadPendingReservations, 500);
            }
            
            if (url.includes("bookingManager.jsp")) {
                 setTimeout(loadAllReservations, 500);
                
            }

                // ✅ Handle Category Manager
                if (url.includes("categoryManager.jsp")) {
                    console.log("📌 Category Manager Loaded! Fetching categories...");
                    updateCategoryTable();

                    // ✅ Ensure Category Form works
                    setupForm("categoryForm", createCategory);
                }

                // ✅ Handle Vehicle Manager
                if (url.includes("vehicleManager.jsp")) {
                    console.log("📌 Vehicle Manager Loaded! Fetching vehicles...");
                    updateVehicleTable();

                    // ✅ Populate category dropdowns
                    updateCategoryDropdowns();

                    // ✅ Ensure Vehicle Form works
                    setupForm("vehicleForm", createVehicle);
                }

                // ✅ Handle Driver Manager
                if (url.includes("driverManager.jsp")) {
                    console.log("📌 Driver Manager Loaded! Fetching drivers...");
                    updateDriverTable();

                    // ✅ Ensure Driver Form works
                    setupForm("driverForm", createDriver);
                }
                if (url.includes("discountManager.jsp")) {
                    console.log("📌 Discount Manager Loaded! Fetching Discounts...");
                    loadDiscounts();
                    setTimeout(loadDiscounts, 500); // ✅ Add delay to ensure table is loaded
                }
                if (url.includes("userManager.jsp")) {
                    console.log("📌 User Manager Loaded! Fetching Users...");
                    loadUsers();

                }
                if (url.includes("adminProfile.jsp")) {
                    console.log("📌 Profile Page Loaded! Fetching user details...");
                    setTimeout(loadUserProfile, 500);  // Ensure it runs after content is loaded
                }
            })
            .catch(error => console.error("❌ Error loading page:", error));
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
/**
 * ✅ Utility Function: Attach Event Listener to Forms
 * @param {string} formId - The ID of the form
 * @param {function} submitHandler - The function to call on form submission
 */
function setupForm(formId, submitHandler) {
    setTimeout(() => {
        const form = document.getElementById(formId);
        if (form) {
            form.addEventListener("submit", function (event) {
                event.preventDefault();
                submitHandler();
            });
            console.log(`✅ Form ${formId} initialized.`);
        } else {
            console.warn(`⚠️ Form ${formId} not found.`);
        }
    }, 300);
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

const categoryApiUrl = "http://localhost:8080/restAPIMCCabs/api/categories/";
const vehicleApiUrl = "http://localhost:8080/restAPIMCCabs/api/vehicles/";
const driverApiUrl = "http://localhost:8080/restAPIMCCabs/api/drivers/";
const discountApiUrl = "http://localhost:8080/restAPIMCCabs/api/discounts/";
const userApiUrl = "http://localhost:8080/restAPIMCCabs/api/users/";
const ratingApiUrl = "http://localhost:8080/restAPIMCCabs/api/ratings/";
const reservationApiUrl = "http://localhost:8080/restAPIMCCabs/api/reservations/";
const reservationFinalizeApiUrl = "http://localhost:8080/restAPIMCCabs/api/reservation_finalize/";  

function isValidFutureDate(inputDate) {
    const formattedDate = formatDate(inputDate.value);
    const today = new Date().toISOString().split("T")[0];

    if (!formattedDate || formattedDate <= today) {
        alert("❌ Selected date must be a future date!");
        return false;
    }
    return true;
}

// ==========================================
// 🔹 CATEGORY MANAGEMENT FUNCTIONS
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
        return categories; // ✅ Return data for other functions to use
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
        return []; // ✅ Return empty array if fetch fails
    }
}

// 🔹 Update Category Table with Data
async function updateCategoryTable() {
    const categories = await getCategories(); // Fetch categories

    const tableBody = document.getElementById("categoryTableBody");
    if (!tableBody) {
        console.error("❌ categoryTableBody not found!");
        return;
    }

    tableBody.innerHTML = ""; // ✅ Clear table

    categories.forEach(category => {
        const row = `
        <tr>
            <td>${category.id}</td>
            <td>${category.catName}</td>
            <td>${category.maxPsngr}</td>
            <td>${category.perDayValue}</td>
            <td>${category.maxKmPerDay}</td>
            <td>${category.milePkg1}</td>
            <td>${category.pkg1Hrs}</td> <!-- ✅ New Column -->
            <td>${category.milePkg2}</td>
            <td>${category.pkg2Hrs}</td> <!-- ✅ New Column -->
            <td>${category.waitingPerHr}</td>
            <td>${category.extraKm}</td>
            <td><span class="badge bg-${category.active === "Active" ? "success" : "danger"}">${category.active}</span></td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editCategory(${category.id}, '${category.catName}', ${category.maxPsngr}, ${category.perDayValue}, ${category.maxKmPerDay}, ${category.milePkg1}, ${category.pkg1Hrs}, ${category.milePkg2}, ${category.pkg2Hrs}, ${category.waitingPerHr}, ${category.extraKm}, '${category.active}')"><i class="bi bi-pencil"></i></button>
                <button class="btn btn-danger btn-sm" onclick="deleteCategory(${category.id})"><i class="bi bi-trash"></i></button>
            </td>
        </tr>`;
        tableBody.innerHTML += row;
    });

    console.log("✅ Category Table Updated!");
}

// 🔹 Create a New Category
async function createCategory() {
    const category = {
        catName: document.getElementById("catName").value,
        maxPsngr: parseInt(document.getElementById("maxPsngr").value),
        perDayValue: parseFloat(document.getElementById("perDayValue").value),
        maxKmPerDay: parseInt(document.getElementById("maxKmPerDay").value),
        milePkg1: parseFloat(document.getElementById("milePkg1").value),
        pkg1Hrs: parseInt(document.getElementById("pkg1Hrs").value), // ✅ New Field
        milePkg2: parseFloat(document.getElementById("milePkg2").value),
        pkg2Hrs: parseInt(document.getElementById("pkg2Hrs").value), // ✅ New Field
        waitingPerHr: parseFloat(document.getElementById("waitingPerHr").value),
        extraKm: parseFloat(document.getElementById("extraKm").value),
        active: document.getElementById("active").value
    };

    console.log("📌 Sending data:", category);

    try {
        const response = await fetch(categoryApiUrl + "create", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(category)
        });

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);

        if (response.ok) {
            alert("✅ Category Created Successfully!");
            updateCategoryTable(); // Refresh list
            document.getElementById("categoryForm").reset();
        } else {
            console.error("❌ Insert Failed:", responseData);
        }
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}

// 🔹 Edit a Category
function editCategory(id, catName, maxPsngr, perDayValue, maxKmPerDay, milePkg1, pkg1Hrs, milePkg2, pkg2Hrs, waitingPerHr, extraKm, active) {
    document.getElementById("updateCategoryId").value = id;
    document.getElementById("updateCatName").value = catName;
    document.getElementById("updateMaxPsngr").value = maxPsngr;
    document.getElementById("updatePerDayValue").value = perDayValue;
    document.getElementById("updateMaxKmPerDay").value = maxKmPerDay;
    document.getElementById("updateMilePkg1").value = milePkg1;
    document.getElementById("updatePkg1Hrs").value = pkg1Hrs; // ✅ New Field
    document.getElementById("updateMilePkg2").value = milePkg2;
    document.getElementById("updatePkg2Hrs").value = pkg2Hrs; // ✅ New Field
    document.getElementById("updateWaitingPerHr").value = waitingPerHr;
    document.getElementById("updateExtraKm").value = extraKm;
    document.getElementById("updateActive").value = active;

    document.getElementById("updateForm").style.display = "block";
}

// 🔹 Update a Category
async function submitUpdate(event = null) {
    if (event)
        event.preventDefault();

    const id = document.getElementById("updateCategoryId").value;
    const updatedCategory = {
        id: parseInt(id),
        catName: document.getElementById("updateCatName").value,
        maxPsngr: parseInt(document.getElementById("updateMaxPsngr").value),
        perDayValue: parseFloat(document.getElementById("updatePerDayValue").value),
        maxKmPerDay: parseInt(document.getElementById("updateMaxKmPerDay").value),
        milePkg1: parseFloat(document.getElementById("updateMilePkg1").value),
        pkg1Hrs: parseInt(document.getElementById("updatePkg1Hrs").value), // ✅ New Field
        milePkg2: parseFloat(document.getElementById("updateMilePkg2").value),
        pkg2Hrs: parseInt(document.getElementById("updatePkg2Hrs").value), // ✅ New Field
        waitingPerHr: parseFloat(document.getElementById("updateWaitingPerHr").value),
        extraKm: parseFloat(document.getElementById("updateExtraKm").value),
        active: document.getElementById("updateActive").value
    };

    console.log("📌 Sending Update Request:", updatedCategory);

    try {
        const response = await fetch(`${categoryApiUrl}${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(updatedCategory)
        });

        if (response.ok) {
            alert("✅ Category Updated Successfully!");
            updateCategoryTable();
            cancelUpdate();
        } else {
            console.error("❌ Update Failed");
        }
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
}
}

// 🔹 Cancel Update
function cancelUpdate() {
    document.getElementById("updateForm").style.display = "none";
}

// 🔹 Delete a Category
async function deleteCategory(id) {
    if (confirm("Are you sure you want to delete this category?")) {
        try {
            const response = await fetch(`${categoryApiUrl}${id}`, {method: "DELETE"});

            if (response.ok) {
                alert("✅ Category Deleted Successfully!");
                updateCategoryTable();
            } else {
                console.error("❌ Delete Failed");
            }
        } catch (error) {
            console.error("🚨 Fetch Error:", error);
        }
    }
}

// ==========================================
// 🔹 VEHICLE MANAGEMENT FUNCTIONS
// ==========================================


/**
 * ✅ Utility Function: Format date to YYYY-MM-DD
 */
function formatDate(dateString) {
    if (!dateString)
        return null;
    const date = new Date(dateString);
    if (isNaN(date.getTime()))
        return null;
    return date.toISOString().split("T")[0];
}

// 🔹 Fetch Vehicle Data
async function getVehicles() {
    console.log("📌 Fetching vehicles...");

    try {
        const response = await fetch(vehicleApiUrl);
        if (!response.ok)
            throw new Error(`❌ HTTP Error: ${response.status}`);

        const vehicles = await response.json();
        console.log("✅ API Response Data:", vehicles);
        return vehicles; // ✅ Return the fetched data
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
        return []; // ✅ Return empty array if fetching fails
    }
}

// 🔹 Update Vehicle Table with Category Names
async function updateVehicleTable() {
    const vehicles = await getVehicles(); // Fetch vehicle data
    const categories = await getCategories(); // Fetch category data

    const tableBody = document.getElementById("vehicleTableBody");
    if (!tableBody) {
        console.error("❌ vehicleTableBody not found!");
        return;
    }

    tableBody.innerHTML = ""; // ✅ Clear table before inserting new data

    vehicles.forEach(vehicle => {
        // 🔹 Find the matching category name
        const category = categories.find(cat => cat.id === vehicle.catId);
        const categoryName = category ? category.catName : "Unknown"; // Default to "Unknown" if not found

        const row = `
        <tr>
            <td>${vehicle.id}</td>
            <td>${categoryName}</td> <!-- ✅ Display category name instead of ID -->
            <td>${vehicle.vehicleNo}</td>
            <td>${vehicle.regExpDate}</td>
            <td><span class="badge bg-${vehicle.stat === "Active" ? "success" : "danger"}">${vehicle.stat}</span></td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editVehicle(${vehicle.id}, ${vehicle.catId}, '${vehicle.vehicleNo}', '${vehicle.regExpDate}', '${vehicle.stat}')"><i class="bi bi-pencil"></i></button>
                <button class="btn btn-danger btn-sm" onclick="deleteVehicle(${vehicle.id})"><i class="bi bi-trash"></i></button>
            </td>
        </tr>`;
        tableBody.innerHTML += row;
    });

    console.log("✅ Vehicle Table Updated with Category Names!");
}

// 🔹 Update Category Dropdowns
async function updateCategoryDropdowns() {
    const categories = await getCategories(); // Fetch categories

    populateDropdown("vehicleCategory", categories);
    populateDropdown("updateVehicleCategory", categories);

    console.log("✅ Category Dropdowns Updated!");
}

// 🔹 Helper Function to Populate Dropdowns
function populateDropdown(dropdownId, categories) {
    const dropdown = document.getElementById(dropdownId);
    if (!dropdown)
        return;

    dropdown.innerHTML = `<option value="" disabled selected>Select a Category</option>`;

    categories.forEach(category => {
        let option = `<option value="${category.id}">${category.catName}</option>`;
        dropdown.innerHTML += option;
    });
}


async function createVehicle(event) {
    if (event)
        event.preventDefault();

    const categorySelect = document.getElementById("vehicleCategory");
    const catId = parseInt(categorySelect.value);
    const regExpDateInput = document.getElementById("regExpDate");

    // 🚨 Validate Registration Expiry Date
    if (!isValidFutureDate(regExpDateInput)) return;

    const vehicle = {
        catId: catId,
        vehicleNo: document.getElementById("vehicleNo").value.trim(),
        regExpDate: formatDate(regExpDateInput.value),
        stat: "Active"
    };

    console.log("🚀 Sending Vehicle Data:", JSON.stringify(vehicle, null, 2));

    try {
        const response = await fetch(vehicleApiUrl + "create", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(vehicle)
        });

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);

        if (!response.ok) {
            alert(responseData.message || "❌ Error creating vehicle.");
            return;
        }

        alert("✅ Vehicle created successfully!");
        updateVehicleTable(); // Refresh list
        document.getElementById("vehicleForm").reset();
    } catch (error) {
        console.error("🚨 Error adding vehicle:", error);
        alert("🚨 Server error! Please try again.");
    }
}



/**
 * ✅ Edit a Vehicle (Pre-fill update form)
 */
function editVehicle(id, catId, vehicleNo, regExpDate, stat) {
    if (!document.getElementById("updateVehicleId")) {
        console.error("❌ Update form not found!");
        return;
    }

    document.getElementById("updateVehicleId").value = id;
    document.getElementById("updateVehicleNo").value = vehicleNo;
    document.getElementById("updateRegExpDate").value = formatDate(regExpDate);
    document.getElementById("updateStat").value = stat;

    // ✅ Set the correct category as selected in the dropdown
    const updateCategoryDropdown = document.getElementById("updateVehicleCategory");
    if (updateCategoryDropdown) {
        updateCategoryDropdown.value = catId; // ✅ Set the selected category
    }

    document.getElementById("updateVehicleForm").style.display = "block";
}
/**
 * ✅ Update a Vehicle
 */
async function submitVehicleUpdate(event) {
    if (event)
        event.preventDefault();

    const id = document.getElementById("updateVehicleId").value;
    const updatedVehicle = {
        id: parseInt(id),
        catId: parseInt(document.getElementById("updateVehicleCategory").value),
        vehicleNo: document.getElementById("updateVehicleNo").value,
        regExpDate: formatDate(document.getElementById("updateRegExpDate").value),
        stat: document.getElementById("updateStat").value
    };

    console.log("📌 Sending Update Request:", updatedVehicle);

    try {
        const response = await fetch(`${vehicleApiUrl}${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(updatedVehicle)
        });

        if (!response.ok)
            throw new Error(`❌ HTTP Error: ${response.status}`);

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);
        alert("✅ Updated");
        updateVehicleTable();
        cancelVehicleUpdate();
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}

/**
 * ✅ Cancel Update
 */
function cancelVehicleUpdate() {
    document.getElementById("updateVehicleForm").style.display = "none";
}

/**
 * ✅ Delete a Vehicle
 */
async function deleteVehicle(id) {
    if (confirm("Are you sure you want to delete this vehicle?")) {
        try {
            const response = await fetch(`${vehicleApiUrl}${id}`, {
                method: "DELETE"
            });

            if (!response.ok)
                throw new Error(`❌ HTTP Error: ${response.status}`);
            alert("✅ Deleted");
            updateVehicleTable(); // Refresh vehicle list
        } catch (error) {
            console.error("🚨 Fetch Error:", error);
        }
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const vehicleForm = document.getElementById("vehicleForm");
    if (vehicleForm) {
        vehicleForm.addEventListener("submit", function (event) {
            event.preventDefault(); // ✅ Prevent form refresh
            createVehicle(event);
        });
    }
});

// ==========================================
// 🔹 DRIVER MANAGEMENT FUNCTIONS
// ==========================================


/**
 * ✅ Fetch Drivers Data
 */
async function getDrivers() {
    console.log("📌 Fetching drivers...");

    try {
        const response = await fetch(driverApiUrl);
        if (!response.ok)
            throw new Error(`❌ HTTP Error: ${response.status}`);

        const drivers = await response.json();
        console.log("✅ API Response Data:", drivers);

        return drivers; // Return fetched data instead of updating UI
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
        return []; // Return empty array in case of error
    }
}

/**
 * ✅ Update Driver Table with Data
 */
async function updateDriverTable() {
    const drivers = await getDrivers(); // Fetch data first

    const tableBody = document.getElementById("driverTableBody");
    if (!tableBody) {
        console.error("❌ driverTableBody not found!");
        return;
    }

    tableBody.innerHTML = ""; // ✅ Clear table before inserting new data

    drivers.forEach(driver => {
        const row = `
        <tr>
            <td>${driver.id}</td>
            <td>${driver.dName}</td>
            <td>${driver.dAddress}</td>
            <td>${driver.dTel}</td>
            <td>${driver.dLNum}</td>
            <td>${driver.dLExpDate}</td>
            <td><span class="badge bg-${driver.stat === "Active" ? "success" : "danger"}">${driver.stat}</span></td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editDriver(${driver.id}, '${driver.dName}', '${driver.dAddress}', '${driver.dTel}', '${driver.dLNum}', '${driver.dLExpDate}', '${driver.stat}')"><i class="bi bi-pencil"></i></button>
                <button class="btn btn-danger btn-sm" onclick="deleteDriver(${driver.id})"><i class="bi bi-trash"></i></button>
            </td>
        </tr>`;
        tableBody.innerHTML += row;
    });

    console.log("✅ Driver Table Updated!");
}

/**
 * ✅ Create a New Driver
 */
async function createDriver() {
    const dLExpDateInput = document.getElementById("dLExpDate");

    // 🚨 Validate Driver License Expiry Date
    if (!isValidFutureDate(dLExpDateInput)) return;

    const driver = {
        dName: document.getElementById("dName").value.trim(),
        dAddress: document.getElementById("dAddress").value.trim(),
        dTel: document.getElementById("dTel").value.trim(),
        dLNum: document.getElementById("dLNum").value.trim(),
        dLExpDate: formatDate(dLExpDateInput.value),
        stat: "Active"
    };

    console.log("📌 Sending Driver Data:", JSON.stringify(driver, null, 2));

    try {
        const response = await fetch(driverApiUrl + "create", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(driver)
        });

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);

        if (!response.ok) {
            alert(responseData.message || "❌ Error creating driver.");
            return;
        }

        alert("✅ Driver created successfully!");
        updateDriverTable(); // Refresh driver list
        document.getElementById("driverForm").reset();
    } catch (error) {
        console.error("🚨 Error adding driver:", error);
        alert("🚨 Server error! Please try again.");
    }
}


/**
 * ✅ Edit a Driver (Pre-fill update form)
 */
function editDriver(id, dName, dAddress, dTel, dLNum, dLExpDate, stat) {
    if (!document.getElementById("updateDriverId")) {
        console.error("❌ Update form not found!");
        return;
    }

    document.getElementById("updateDriverId").value = id;
    document.getElementById("updateDName").value = dName;
    document.getElementById("updateDAddress").value = dAddress;
    document.getElementById("updateDTel").value = dTel;
    document.getElementById("updateDLNum").value = dLNum;
    document.getElementById("updateDLExpDate").value = dLExpDate;
    document.getElementById("updateStat").value = stat;

    document.getElementById("updateDriverForm").style.display = "block";
}

/**
 * ✅ Update a Driver
 */
async function submitDriverUpdate() {
    const id = document.getElementById("updateDriverId").value;
    const updatedDriver = {
        id: parseInt(id),
        dName: document.getElementById("updateDName").value.trim(),
        dAddress: document.getElementById("updateDAddress").value.trim(),
        dTel: document.getElementById("updateDTel").value.trim(),
        dLNum: document.getElementById("updateDLNum").value.trim(),
        dLExpDate: document.getElementById("updateDLExpDate").value,
        stat: document.getElementById("updateStat").value
    };

    console.log("📌 Sending Update Request:", updatedDriver);

    try {
        const response = await fetch(`${driverApiUrl}${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(updatedDriver)
        });

        if (!response.ok)
            throw new Error(`❌ HTTP Error: ${response.status}`);

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);
        alert("✅ Updated");
        updateDriverTable();
        cancelDriverUpdate();
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}

/**
 * ✅ Cancel Update
 */
function cancelDriverUpdate() {
    document.getElementById("updateDriverForm").style.display = "none";
}

/**
 * ✅ Delete a Driver
 */
async function deleteDriver(id) {
    if (confirm("Are you sure you want to delete this driver?")) {
        try {
            const response = await fetch(`${driverApiUrl}${id}`, {
                method: "DELETE"
            });

            if (!response.ok)
                throw new Error(`❌ HTTP Error: ${response.status}`);
            alert("✅ Deleted");
            updateDriverTable(); // Refresh driver list
        } catch (error) {
            console.error("🚨 Fetch Error:", error);
        }
    }
}

// ==========================================
// 🔹 DISCOUNT MANAGEMENT FUNCTIONS
// ==========================================

// 🔹 Fetch and Load Discounts (Filtering Active Discounts on Frontend)
async function loadDiscounts() {
    console.log("📌 Fetching All Discounts...");

    try {
        const response = await fetch(`${discountApiUrl}`); // Fetch ALL discounts
        if (!response.ok)
            throw new Error(`❌ HTTP Error: ${response.status}`);

        let discounts = await response.json();
        console.log("✅ Full API Response Data:", discounts);

        // 🔹 Filter Active Discounts
        const activeDiscounts = discounts.filter(discount => discount.dStatus === "Active");
        console.log(`📌 After Filtering: ${activeDiscounts.length} Active Discounts`);

        const tableBody = document.getElementById("discountTableBody");

        if (!tableBody) {
            console.error("❌ Element 'discountTableBody' not found! Make sure the ID is correct in the HTML.");
            return;
        }

        tableBody.innerHTML = ""; // ✅ Clear existing data

        activeDiscounts.forEach(discount => {
            const row = `
            <tr>
                <td>${discount.id}</td>
                <td>${discount.diskId}</td>
                <td>${discount.percentage}%</td>
                <td>${discount.startDate}</td>
                <td>${discount.endDate}</td>
                <td><span class="badge bg-${discount.dStatus === "Active" ? "success" : "danger"}">${discount.dStatus}</span></td>
                <td>
                    <button class="btn btn-warning btn-sm" onclick="editDiscount(${discount.id}, '${discount.diskId}', ${discount.percentage}, '${discount.startDate}', '${discount.endDate}', '${discount.dStatus}')">
                        <i class="bi bi-pencil"></i> Edit
                    </button>
                    <button class="btn btn-danger btn-sm" onclick="deleteDiscount(${discount.id})">
                        <i class="bi bi-trash"></i> Delete
                    </button>
                </td>
            </tr>`;
            tableBody.innerHTML += row;
        });

        console.log("✅ Discounts Table Updated!");

    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }

    // 🔹 Load Expired Discounts Separately
    loadExpiredDiscounts();
}

// 🔹 Fetch and Load Expired Discounts
async function loadExpiredDiscounts() {
    console.log("📌 Fetching Expired Discounts...");

    try {
        const response = await fetch(`${discountApiUrl}expired`);
        if (!response.ok)
            throw new Error(`❌ HTTP Error: ${response.status}`);

        const expiredDiscounts = await response.json();
        console.log("✅ Expired Discounts Data:", expiredDiscounts);

        const expiredTableBody = document.getElementById("expiredDiscountTable");

        if (!expiredTableBody) {
            console.error("❌ Element 'expiredDiscountTable' not found! Ensure ID matches in HTML.");
            return;
        }

        expiredTableBody.innerHTML = ""; // ✅ Clear previous data

        expiredDiscounts.forEach(discount => {
            const row = `
            <tr>
                <td>${discount.id}</td>
                <td>${discount.diskId}</td>
                <td>${discount.percentage}%</td>
                <td>${discount.startDate}</td>
                <td>${discount.endDate}</td>
                <td><span class="badge bg-danger">Inactive</span></td>
            </tr>`;
            expiredTableBody.innerHTML += row;
        });

        console.log("✅ Expired Discounts Table Updated!");
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}
async function addDiscount() {
    const percentage = document.getElementById("discountPercentage").value;
    const startDateInput = document.getElementById("startDate");
    const endDateInput = document.getElementById("endDate");
    const dStatus = document.getElementById("discountStatus").value;

    // 🚨 Validate Start and End Dates
    if (!isValidFutureDate(startDateInput) || !isValidFutureDate(endDateInput)) return;

    const discountData = {
        percentage,
        startDate: formatDate(startDateInput.value),
        endDate: formatDate(endDateInput.value),
        dStatus
    };

    try {
        const response = await fetch(discountApiUrl + "create", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(discountData),
        });

        if (!response.ok)
            throw new Error("❌ Failed to create discount!");

        alert("✅ Discount Added Successfully!");

        // ✅ Reset form fields
        document.getElementById("discountPercentage").value = "";
        document.getElementById("startDate").value = "";
        document.getElementById("endDate").value = "";
        document.getElementById("discountStatus").value = "Active";

        // ✅ Reload discount tables
        loadDiscounts();

    } catch (error) {
        console.error("🚨 Error Adding Discount:", error);
    }
}
// 🔹 Edit Discount
// ✅ Edit Discount (Show Update Form)
function editDiscount(id, diskId, percentage, startDate, endDate, dStatus) {
    document.getElementById("updateDiscountId").value = id;
    document.getElementById("updateDiscountPercentage").value = percentage;
    document.getElementById("updateStartDate").value = startDate;
    document.getElementById("updateEndDate").value = endDate;

    // ✅ Show the Update Discount Form and Scroll to It
    document.getElementById("updateDiscountForm").style.display = "block";
    document.getElementById("updateDiscountForm").scrollIntoView({behavior: "smooth"});
}
// ✅ Submit Discount Update
async function submitDiscountUpdate() {
    const id = document.getElementById("updateDiscountId").value;
    const percentage = document.getElementById("updateDiscountPercentage").value;
    const startDate = document.getElementById("updateStartDate").value;
    const endDate = document.getElementById("updateEndDate").value;
    const dStatus = "Active"; // ✅ Always set status to Active

    if (!percentage || !startDate || !endDate) {
        alert("❌ Please fill in all fields!");
        return;
    }

    const discountData = {percentage, startDate, endDate, dStatus}; // ✅ Include dStatus

    try {
        const response = await fetch(`${discountApiUrl}${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(discountData),
        });

        if (!response.ok)
            throw new Error("❌ Failed to update discount!");

        alert("✅ Discount Updated Successfully!");

        // ✅ Hide and Reset Update Form
        cancelDiscountUpdate();

        // ✅ Refresh Discount Table
        loadDiscounts();
    } catch (error) {
        console.error("🚨 Error Updating Discount:", error);
    }
}

// ✅ Cancel Discount Update
function cancelDiscountUpdate() {
    document.getElementById("updateDiscountForm").style.display = "none";

    // ✅ Reset fields
    document.getElementById("updateDiscountId").value = "";
    document.getElementById("updateDiscountPercentage").value = "";
    document.getElementById("updateStartDate").value = "";
    document.getElementById("updateEndDate").value = "";
}

// 🔹 Delete Discount
async function deleteDiscount(id) {
    if (!confirm("❌ Are you sure you want to delete this discount?"))
        return;

    try {
        const response = await fetch(`${discountApiUrl}${id}`, {
            method: "DELETE",
        });

        if (!response.ok)
            throw new Error("❌ Failed to delete discount!");

        alert("✅ Discount Deleted Successfully!");
        loadDiscounts(); // Refresh the table

    } catch (error) {
        console.error("🚨 Error Deleting Discount:", error);
    }
}


// ==========================================
// 🔹 USER MANAGEMENT FUNCTIONS
// ==========================================
// ✅ Fetch and Display Users
async function loadUsers() {
    console.log("📌 Fetching users...");

    try {
        const response = await fetch(userApiUrl);
        if (!response.ok) throw new Error(`❌ HTTP Error: ${response.status}`);

        const users = await response.json();
        console.log("✅ Retrieved Users:", users);

        const tableBody = document.getElementById("userTableBody");
        tableBody.innerHTML = "";

        users.forEach(user => {
            const row = `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.fullName}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td><span class="badge bg-${user.uRole === 'adm' ? 'primary' : 'success'}">${user.uRole === 'adm' ? 'Admin' : 'Customer'}</span></td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editUser(${user.id}, '${user.fullName}', '${user.email}', '${user.uRole}')">
                            <i class="bi bi-pencil"></i> Edit
                        </button>
                        ${user.uRole === 'adm' ? '' : `<button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">
                            <i class="bi bi-trash"></i> Delete
                        </button>`}
                    </td>
                </tr>`;
            tableBody.innerHTML += row;
        });

    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}

// ✅ Open Edit User Modal
function editUser(id, fullName, email, uRole) {
    document.getElementById("editUserId").value = id;
    document.getElementById("editFullName").value = fullName;
    document.getElementById("editEmail").value = email;
    document.getElementById("editUserRole").value = uRole;

    const modal = new bootstrap.Modal(document.getElementById("editUserModal"));
    modal.show();
}

// ✅ Update User Role (New Optimized API)
async function updateUserRole() {
    const id = document.getElementById("editUserId").value;
    const newRole = document.getElementById("editUserRole").value;

    try {
        const response = await fetch(`${userApiUrl}${id}/role`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ uRole: newRole }),
        });

        if (!response.ok) throw new Error("❌ Failed to update user role!");

        alert("✅ User role updated successfully!");
        loadUsers(); // Refresh user list
        const modal = bootstrap.Modal.getInstance(document.getElementById("editUserModal"));
        modal.hide(); // Close modal

    } catch (error) {
        console.error("🚨 Error Updating Role:", error);
    }
}

// ✅ Delete User
async function deleteUser(id) {
    if (!confirm("❌ Are you sure you want to delete this user?")) return;

    try {
        const response = await fetch(`${userApiUrl}${id}`, { method: "DELETE" });

        if (!response.ok) throw new Error("❌ Failed to delete user!");

        alert("✅ User Deleted Successfully!");
        loadUsers();

    } catch (error) {
        console.error("🚨 Error Deleting User:", error);
    }
}



// ==========================================
// 🔹 BOOKING MANAGEMENT FUNCTIONS
// ==========================================

/**
 * ✅ Load All Reservations (Now Includes Rating Column)
 */
async function loadAllReservations() {
    console.log("📌 Fetching all reservations...");
    const tableBody = document.getElementById("reservationTable");

    if (!tableBody) {
        console.error("🚨 'reservationTable' element not found!");
        return;
    }

    tableBody.innerHTML = `<tr><td colspan="9" class="text-center">Loading...</td></tr>`;

    try {
        const response = await fetch(reservationApiUrl + "all");
        if (!response.ok) throw new Error("🚨 Failed to fetch reservations!");

        let reservations = await response.json();
        console.log("✅ All Reservations:", reservations);

        if (reservations.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="9" class="text-center">No reservations found.</td></tr>`;
            return;
        }

        tableBody.innerHTML = ""; // ✅ Clear previous data

        for (const reservation of reservations) {
            // ✅ Fetch User & Vehicle first
            const userPromise = fetch(userApiUrl + reservation.userId).then(res => res.ok ? res.json() : { fullName: "Unknown User" });
            const vehiclePromise = fetch(vehicleApiUrl + reservation.vehicleId).then(res => res.ok ? res.json() : { vehicleNo: "Unknown Vehicle", catId: null });

            const [user, vehicle] = await Promise.all([userPromise, vehiclePromise]);

            // ✅ Fetch Category & Driver only after vehicle is fetched
            const categoryPromise = vehicle.catId ? fetch(categoryApiUrl + vehicle.catId).then(res => res.ok ? res.json() : { catName: "Unknown Category" }) : { catName: "Unknown Category" };
            const driverPromise = reservation.driverId ? fetch(driverApiUrl + reservation.driverId).then(res => res.ok ? res.json() : { dName: "Not Assigned" }) : { dName: "Not Assigned" };
            const ratingPromise = reservation.ratId ? fetch(`${ratingApiUrl}${reservation.ratId}`).then(res => res.ok ? res.json() : null) : null;

            const [category, driver, rating] = await Promise.all([categoryPromise, driverPromise, ratingPromise]);

            // ✅ Determine Rating Column Value
            let ratingColumn = "N/A";
            if (rating) {
                const overallRating = ((rating.tripRating + rating.vehicleRating + rating.driverRating) / 3).toFixed(1);
                ratingColumn = `<a href="#" onclick="openRatingModal(${reservation.ratId})">${overallRating} ⭐</a>`;
            }

            // ✅ Add row to table
            const row = `
                <tr>
                    <td>${reservation.id}</td>
                    <td>${user.fullName}</td>
                    <td>${category.catName}</td>
                    <td>${driver.dName}</td>
                    <td>${reservation.stDate}</td>
                    <td>${reservation.endDate}</td>
                    <td>Rs. ${reservation.finalPrice ? reservation.finalPrice.toFixed(2) : "N/A"}</td>
                    <td><span class="status-badge ${reservation.stat.toLowerCase()}">${reservation.stat}</span></td>
                    <td>${ratingColumn}</td>
                </tr>`;

            tableBody.innerHTML += row;
        }

    } catch (error) {
        console.error("🚨 Error fetching reservations:", error);
        tableBody.innerHTML = `<tr><td colspan="9" class="text-center text-danger">Failed to load reservations.</td></tr>`;
    }
}

function openRatingModal(ratingId) {
    fetch(`${ratingApiUrl}${ratingId}`)
        .then(response => response.json())
        .then(rating => {
            document.getElementById("modalTripRating").innerText = rating.tripRating + " ⭐";
            document.getElementById("modalVehicleRating").innerText = rating.vehicleRating + " ⭐";
            document.getElementById("modalDriverRating").innerText = rating.driverRating + " ⭐";
            document.getElementById("modalRatingComment").value = rating.comment || "No comments provided.";
            
            // Show modal
            document.getElementById("ratingModal").style.display = "flex";
            
            // Force reflow and prevent scroll on body
            document.body.style.overflow = "hidden";
        })
        .catch(error => {
            console.error("🚨 Error fetching rating details:", error);
            alert("❌ Error loading rating details.");
        });
}

function closeRatingModal() {
    document.getElementById("ratingModal").style.display = "none";
    
    // Re-enable scrolling
    document.body.style.overflow = "";
}


/**
 * ✅ Filters
 */
function filterTable(tableId) {
    const input = document.getElementById("searchInput").value.toLowerCase();
    const rows = document.querySelectorAll(`#${tableId} tr`);

    rows.forEach(row => {
        const text = row.innerText.toLowerCase();
        row.style.display = text.includes(input) ? "" : "none";
    });
}
function filterReservations() {
    filterTable("reservationTable");
}

function filterCategories() {
    filterTable("categoryTable");
}

function filterVehicles() {
    filterTable("vehicleTable");
}

function filterDrivers() {
    filterTable("driverTable");
}

function filterDiscounts() {
    filterTable("discountTable");
}
function filterUsers() {
    filterTable("userTable");
}

async function loadPendingReservations() {
    console.log("📌 Fetching all reservations...");
    const tableBody = document.getElementById("reservationTable");

    if (!tableBody) {
        console.error("🚨 'reservationTable' element not found!");
        return;
    }

    tableBody.innerHTML = `<tr><td colspan="5" class="text-center">Loading...</td></tr>`;

    try {
        const response = await fetch(reservationApiUrl + "all");
        if (!response.ok) throw new Error("🚨 Failed to fetch reservations!");

        let allReservations = await response.json();
        console.log("✅ All Reservations:", allReservations);

        let pendingReservations = allReservations.filter(reservation => {
            let endDate = new Date(reservation.endDate);
            let currentDate = new Date();
            return endDate < currentDate && reservation.stat === "Approved";
        });

        if (pendingReservations.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="5" class="text-center">No pending finalizations.</td></tr>`;
            return;
        }

        tableBody.innerHTML = "";

        for (const reservation of pendingReservations) {
            const user = await fetch(userApiUrl + reservation.userId)
                .then(res => res.ok ? res.json() : { fullName: "Unknown User" });

            const row = `
                <tr>
                    <td>${reservation.id}</td>
                    <td>${user.fullName}</td>
                    <td>Rs. ${reservation.finalPrice ? reservation.finalPrice.toFixed(2) : "N/A"}</td>
                    <td><span class="status-badge pending">${reservation.stat}</span></td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="openFinalizeModal(${reservation.id}, ${reservation.vehicleId}, '${reservation.stDate}', '${reservation.endDate}')">
                            <i class="bi bi-clipboard-check"></i> Finalize
                        </button>
                    </td>
                </tr>`;

            tableBody.innerHTML += row;
        }
    } catch (error) {
        console.error("🚨 Error fetching reservations:", error);
        tableBody.innerHTML = `<tr><td colspan="5" class="text-center text-danger">Failed to load reservations.</td></tr>`;
    }
}


/**
 * ✅ Open Finalize Modal & Prefill Data
 */
async function openFinalizeModal(id, vehicleId, stDate, endDate) {
    document.getElementById("finalizeReservationId").value = id;
    document.getElementById("modalStartDate").innerText = stDate;
    document.getElementById("modalEndDate").innerText = endDate;
    document.getElementById("extraKm").value = "";
    document.getElementById("extraHrs").value = "";
    document.getElementById("calculatedCharges").innerText = "Rs. 0.00";

    document.getElementById("finalizeModal").style.display = "flex";

    // ✅ Ensure category details are fetched before calculation
    try {
        const vehicleResponse = await fetch(`${vehicleApiUrl}${vehicleId}`);
        if (!vehicleResponse.ok) throw new Error("Failed to fetch vehicle details");
        const vehicle = await vehicleResponse.json();

        const categoryResponse = await fetch(`${categoryApiUrl}${vehicle.catId}`);
        if (!categoryResponse.ok) throw new Error("Failed to fetch category details");
        const category = await categoryResponse.json();

        console.log("🚗 Vehicle Details:", vehicle);
        console.log("🏷️ Category Details:", category);

        // Attach input events for calculation
        document.getElementById("extraKm").addEventListener("input", () => calculateAdditionalCharges(category));
        document.getElementById("extraHrs").addEventListener("input", () => calculateAdditionalCharges(category));
    } catch (error) {
        console.error("🚨 Error fetching vehicle or category details:", error);
        document.getElementById("calculatedCharges").innerText = "Error fetching rates!";
    }
}

/**
 * ✅ Calculate Additional Charges Based on Category Pricing
 */
function calculateAdditionalCharges(category) {
    const extraKm = parseFloat(document.getElementById("extraKm").value) || 0;
    const extraHrs = parseFloat(document.getElementById("extraHrs").value) || 0;

    if (!category) {
        console.error("🚨 Category details are missing!");
        document.getElementById("calculatedCharges").innerText = "Error in calculation!";
        return;
    }

    const extraKmCharge = extraKm * category.extraKm;
    const extraHrCharge = extraHrs * category.waitingPerHr;
    const totalExtraCharge = extraKmCharge + extraHrCharge;

    console.log(`📌 Calculated: Extra KM: ${extraKmCharge}, Extra Hrs: ${extraHrCharge}, Total: ${totalExtraCharge}`);

    document.getElementById("calculatedCharges").innerText = `Rs. ${totalExtraCharge.toFixed(2)}`;
}

/**
 * ✅ Finalize Reservation
 */
async function finalizeTrip() {
    const reservationId = document.getElementById("finalizeReservationId").value;
    const extraKm = parseFloat(document.getElementById("extraKm").value) || 0;
    const extraHrs = parseFloat(document.getElementById("extraHrs").value) || 0;
    const calculatedCharges = parseFloat(document.getElementById("calculatedCharges").innerText.replace("Rs. ", "")) || 0;

    if (!reservationId) {
        alert("🚨 Invalid reservation ID!");
        return;
    }

    try {
        if (extraKm === 0 && extraHrs === 0) {
            // ✅ No extra charges → Update Reservation Status to "Finalized"
            console.log(`✅ Finalizing reservation ${reservationId} without extra charges.`);
            const statusResponse = await fetch(`${reservationApiUrl}updateStatus/${reservationId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ stat: "Finalized" })
            });

            if (statusResponse.ok) {
                alert("🎉 Reservation finalized successfully!");
            } else {
                throw new Error("Failed to finalize reservation!");
            }
        } else {
            // ✅ Extra charges exist → Insert into `reservation_finalize` and Update Reservation Status to "PendingPayment"
            console.log(`📌 Adding reservation_finalize entry for reservation ${reservationId}.`);
            const finalizeResponse = await fetch(`${reservationFinalizeApiUrl}create`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    resId: reservationId,
                    extraKm: extraKm,
                    extraHr: extraHrs,
                    price: calculatedCharges,
                    stat: "Pending"
                })
            });

            if (!finalizeResponse.ok) throw new Error("Failed to add finalize record!");

            console.log(`✅ Changing reservation ${reservationId} status to 'PendingPayment'.`);
            const statusResponse = await fetch(`${reservationApiUrl}updateStatus/${reservationId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ stat: "PendingPayment" })
            });

            if (!statusResponse.ok) throw new Error("Failed to update reservation status!");

            alert("🎉 Reservation finalized with additional charges!");
        }
    } catch (error) {
        console.error("🚨 Error finalizing trip:", error);
        alert("🚨 An error occurred while finalizing the reservation.");
    }

    closeFinalizeModal();
}
/**
 * ✅ Close Modal
 */
function closeFinalizeModal() {
    document.getElementById("finalizeModal").style.display = "none";
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
        const response = await fetch(`${userApiUrl}${userId}`); // ✅ Fixed URL
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
