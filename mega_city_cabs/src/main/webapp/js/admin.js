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
            })
            .catch(error => console.error("❌ Error loading page:", error));
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

// ==========================================
// 🔹 CATEGORY MANAGEMENT FUNCTIONS
// ==========================================
const categoryApiUrl = "http://localhost:8080/restAPIMCCabs/api/categories";

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
        const response = await fetch(categoryApiUrl + "/create", {
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
        const response = await fetch(`${categoryApiUrl}/${id}`, {
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
            const response = await fetch(`${categoryApiUrl}/${id}`, {method: "DELETE"});

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
const vehicleApiUrl = "http://localhost:8080/restAPIMCCabs/api/vehicles";

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

    const vehicle = {
        catId: catId,
        vehicleNo: document.getElementById("vehicleNo").value.trim(),
        regExpDate: formatDate(document.getElementById("regExpDate").value),
        stat: "Active"
    };

    console.log("🚀 Sending Vehicle Data:", JSON.stringify(vehicle, null, 2));

    try {
        const response = await fetch(vehicleApiUrl + "/create", {
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
        const response = await fetch(`${vehicleApiUrl}/${id}`, {
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
            const response = await fetch(`${vehicleApiUrl}/${id}`, {
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
const driverApiUrl = "http://localhost:8080/restAPIMCCabs/api/drivers";

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
    const driver = {
        dName: document.getElementById("dName").value.trim(),
        dAddress: document.getElementById("dAddress").value.trim(),
        dTel: document.getElementById("dTel").value.trim(),
        dLNum: document.getElementById("dLNum").value.trim(),
        dLExpDate: document.getElementById("dLExpDate").value,
        stat: "Active"
    };

    console.log("📌 Sending Driver Data:", JSON.stringify(driver, null, 2));

    try {
        const response = await fetch(driverApiUrl + "/create", {
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
        const response = await fetch(`${driverApiUrl}/${id}`, {
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
            const response = await fetch(`${driverApiUrl}/${id}`, {
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

// 🔹 Fetch and Load Discounts (Both Active & Expired)
async function loadDiscounts() {
    console.log("📌 Fetching Discounts...");

    try {
        const response = await fetch("http://localhost:8080/restAPIMCCabs/api/discounts/active");
        if (!response.ok)
            throw new Error(`❌ HTTP Error: ${response.status}`);

        const discounts = await response.json();
        console.log("✅ API Response Data:", discounts);

        const tableBody = document.getElementById("discountTableBody");

        // 🔹 Check if the element exists before updating
        if (!tableBody) {
            console.error("❌ Element 'discountTableBody' not found! Make sure the ID is correct in the HTML.");
            return;
        }

        tableBody.innerHTML = ""; // ✅ Clear existing data

        discounts.forEach(discount => {
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
        const response = await fetch("http://localhost:8080/restAPIMCCabs/api/discounts/expired");
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
// 🔹 Add New Discount
async function addDiscount() {
    const percentage = document.getElementById("discountPercentage").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const dStatus = document.getElementById("discountStatus").value;

    if (!percentage || !startDate || !endDate) {
        alert("❌ Please fill in all fields!");
        return;
    }

    const discountData = {percentage, startDate, endDate, dStatus};

    try {
        const response = await fetch("http://localhost:8080/restAPIMCCabs/api/discounts/create", {
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
        const response = await fetch(`http://localhost:8080/restAPIMCCabs/api/discounts/${id}`, {
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
        const response = await fetch(`http://localhost:8080/restAPIMCCabs/api/discounts/${id}`, {
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
        const response = await fetch("http://localhost:8080/restAPIMCCabs/api/users");
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
        const response = await fetch(`http://localhost:8080/restAPIMCCabs/api/users/${id}/role`, {
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
        const response = await fetch(`http://localhost:8080/restAPIMCCabs/api/users/${id}`, { method: "DELETE" });

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