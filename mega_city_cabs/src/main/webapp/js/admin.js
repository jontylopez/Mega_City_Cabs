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

            // ✅ Fetch Categories when Category Manager or Vehicle Manager is loaded
            if (url.includes("categoryManager.jsp") || url.includes("vehicleManager.jsp")) {
                console.log("📌 Fetching categories...");
                getCategories();
            }

            // ✅ Fetch Vehicles when Vehicle Manager is loaded
            if (url.includes("vehicleManager.jsp")) {
                console.log("📌 Vehicle Manager Loaded! Fetching vehicles...");
                getVehicles();

                // ✅ Attach Event Listener for the Form AFTER loading the page
                const vehicleForm = document.getElementById("vehicleForm");
                if (vehicleForm) {
                    vehicleForm.addEventListener("submit", function (event) {
                        event.preventDefault(); // ✅ Prevent page refresh
                        createVehicle(event);
                    });
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
// 🔹 CATEGORY MANAGEMENT FUNCTIONS
// ==========================================
const categoryApiUrl = "http://localhost:8080/restAPIMCCabs/api/categories";

// 🔹 Fetch and Display Categories
async function getCategories() {
    console.log("📌 Fetching categories...");
    try {
        const response = await fetch(categoryApiUrl);
        if (!response.ok) throw new Error("HTTP Error " + response.status);

        const categories = await response.json();
        console.log("✅ API Response Data:", categories);

        // ✅ Update category table if available
        const tableBody = document.getElementById("categoryTableBody");
        if (tableBody) {
            tableBody.innerHTML = ""; // Clear table before inserting new data
            categories.forEach(category => {
                const row = `
                <tr>
                    <td>${category.id}</td>
                    <td>${category.catName}</td>
                    <td>${category.maxPsngr}</td>
                    <td>${category.perDayValue}</td>
                    <td>${category.maxKmPerDay}</td>
                    <td>${category.milePkg1}</td>
                    <td>${category.milePkg2}</td>
                    <td>${category.waitingPerHr}</td>
                    <td>${category.extraKm}</td>
                    <td>${category.active}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editCategory(${category.id}, '${category.catName}', ${category.maxPsngr}, ${category.perDayValue}, ${category.maxKmPerDay}, ${category.milePkg1}, ${category.milePkg2}, ${category.waitingPerHr}, ${category.extraKm}, '${category.active}')"><i class="bi bi-pencil"></i></button>
                        <button class="btn btn-danger btn-sm" onclick="deleteCategory(${category.id})"><i class="bi bi-trash"></i></button>
                    </td>
                </tr>`;
                tableBody.innerHTML += row;
            });
            console.log("✅ Category Table Updated!");
        }

        // ✅ Update category dropdowns if available
        const categoryDropdown = document.getElementById("vehicleCategory");
        const updateCategoryDropdown = document.getElementById("updateVehicleCategory");

        if (categoryDropdown || updateCategoryDropdown) {
            console.log("📌 Populating category dropdowns...");
            const options = categories.map(cat => `<option value="${cat.id}">${cat.catName}</option>`).join("");

            if (categoryDropdown) categoryDropdown.innerHTML = options;
            if (updateCategoryDropdown) updateCategoryDropdown.innerHTML = options;

            console.log("✅ Category Dropdowns Updated!");
        }

    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}

// 🔹 Create a New Category
async function createCategory(event) {
    event.preventDefault();

    const category = {
        catName: document.getElementById("catName").value,
        maxPsngr: parseInt(document.getElementById("maxPsngr").value),
        perDayValue: parseFloat(document.getElementById("perDayValue").value),
        maxKmPerDay: parseInt(document.getElementById("maxKmPerDay").value),
        milePkg1: parseFloat(document.getElementById("milePkg1").value),
        milePkg2: parseFloat(document.getElementById("milePkg2").value),
        waitingPerHr: parseFloat(document.getElementById("waitingPerHr").value),
        extraKm: parseFloat(document.getElementById("extraKm").value),
        active: document.getElementById("active").value
    };

    console.log("📌 Sending data:", category);

    try {
        const response = await fetch(categoryApiUrl + "/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(category)
        });

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);

        if (response.ok) {
            getCategories(); // Refresh list
            document.getElementById("categoryForm").reset();
        } else {
            console.error("❌ Insert Failed:", responseData);
        }
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}

// 🔹 Edit a Category
function editCategory(id, catName, maxPsngr, perDayValue, maxKmPerDay, milePkg1, milePkg2, waitingPerHr, extraKm, active) {
    // ✅ Ensure the update form exists before accessing its elements
    if (!document.getElementById("updateCategoryId")) {
        console.error("❌ Update form not found! Make sure categoryManager.jsp is properly loaded.");
        return;
    }

    // ✅ Assign values if elements exist
    document.getElementById("updateCategoryId").value = id;
    document.getElementById("updateCatName").value = catName;
    document.getElementById("updateMaxPsngr").value = maxPsngr;
    document.getElementById("updatePerDayValue").value = perDayValue;
    document.getElementById("updateMaxKmPerDay").value = maxKmPerDay;
    document.getElementById("updateMilePkg1").value = milePkg1;
    document.getElementById("updateMilePkg2").value = milePkg2;
    document.getElementById("updateWaitingPerHr").value = waitingPerHr;
    document.getElementById("updateExtraKm").value = extraKm;
    document.getElementById("updateActive").value = active;

    // ✅ Make sure the update form is visible
    document.getElementById("updateForm").style.display = "block";
}


// 🔹 Update a Category
async function submitUpdate(event = null) {  
    if (event) event.preventDefault();  

    const id = document.getElementById("updateCategoryId").value;
    const updatedCategory = {
        id: parseInt(id),
        catName: document.getElementById("updateCatName").value,
        maxPsngr: parseInt(document.getElementById("updateMaxPsngr").value),
        perDayValue: parseFloat(document.getElementById("updatePerDayValue").value),
        maxKmPerDay: parseInt(document.getElementById("updateMaxKmPerDay").value),
        milePkg1: parseFloat(document.getElementById("updateMilePkg1").value),  // ✅ Added MilePkg1
        milePkg2: parseFloat(document.getElementById("updateMilePkg2").value),  // ✅ Added MilePkg2
        waitingPerHr: parseFloat(document.getElementById("updateWaitingPerHr").value),  // ✅ Added WaitingPerHr
        extraKm: parseFloat(document.getElementById("updateExtraKm").value),
        active: document.getElementById("updateActive").value
    };

    console.log("📌 Sending Update Request:", updatedCategory); 

    try {
        const response = await fetch(`${categoryApiUrl}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedCategory)
        });

        const responseData = await response.json();
        console.log("📌 API Response:", responseData);

        if (response.ok) {
            getCategories();
            cancelUpdate();
        } else {
            console.error("❌ Update Failed:", responseData);
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
            const response = await fetch(`${categoryApiUrl}/${id}`, {
                method: "DELETE"
            });

            if (response.ok) {
                getCategories(); // ✅ Refresh the list dynamically
            } else {
                console.error("❌ Delete Failed");
            }
        } catch (error) {
            console.error("🚨 Fetch Error:", error);
        }
    }
}

// Ensure categories are loaded when the page is dynamically inserted
document.addEventListener("DOMContentLoaded", function () {
    if (window.location.href.includes("categoryManager.jsp")) {
        getCategories();
    }
});

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
// 🔹 VEHICLE MANAGEMENT FUNCTIONS
// ==========================================
const vehicleApiUrl = "http://localhost:8080/restAPIMCCabs/api/vehicles";

/**
 * ✅ Utility Function: Format date to YYYY-MM-DD
 */
function formatDate(dateString) {
    if (!dateString) return null;
    const date = new Date(dateString);
    if (isNaN(date.getTime())) return null;
    return date.toISOString().split("T")[0]; 
}

/**
 * ✅ Fetch and Display Vehicles
 */
async function getVehicles() {
    console.log("📌 Fetching vehicles...");

    try {
        const response = await fetch(vehicleApiUrl);
        if (!response.ok) throw new Error(`❌ HTTP Error: ${response.status}`);

        const vehicles = await response.json();
        console.log("✅ API Response Data:", vehicles);

        const tableBody = document.getElementById("vehicleTableBody");
        if (!tableBody) {
            console.error("❌ vehicleTableBody not found!");
            return;
        }

        tableBody.innerHTML = ""; // Clear table before inserting new data
        vehicles.forEach(vehicle => {
            const row = `
            <tr>
                <td>${vehicle.id}</td>
                <td>${vehicle.catId}</td>
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

        console.log("✅ Vehicle Table Updated!");
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}

/**
 * ✅ Create a New Vehicle
 */
// 🔹 Fetch and Display Categories (for Add & Update Vehicle Forms)
async function getCategories() {
    console.log("📌 Fetching categories...");
    try {
        const response = await fetch(categoryApiUrl);
        if (!response.ok) throw new Error("HTTP Error " + response.status);

        const categories = await response.json();
        console.log("✅ API Response Data:", categories);

        // ✅ Populate both Add & Update dropdowns
        populateDropdown("vehicleCategory", categories);
        populateDropdown("updateVehicleCategory", categories);

        console.log("✅ Category Dropdowns Updated!");
    } catch (error) {
        console.error("🚨 Fetch Error:", error);
    }
}

// 🔹 Helper Function to Populate Dropdowns
function populateDropdown(dropdownId, categories) {
    const dropdown = document.getElementById(dropdownId);
    if (!dropdown) return; // ✅ Exit if dropdown not found

    dropdown.innerHTML = ""; // ✅ Clear previous options
    dropdown.innerHTML += `<option value="" disabled selected>Select a Category</option>`; // Default Option

    categories.forEach(category => {
        let option = `<option value="${category.id}">${category.catName}</option>`;
        dropdown.innerHTML += option;
    });
}


async function createVehicle(event) {
    if (event) event.preventDefault();

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
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(vehicle)
        });

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);

        if (!response.ok) {
            alert(responseData.message || "❌ Error creating vehicle.");
            return;
        }

        alert("✅ Vehicle created successfully!");
        getVehicles(); // Refresh list
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
    if (event) event.preventDefault();

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
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedVehicle)
        });

        if (!response.ok) throw new Error(`❌ HTTP Error: ${response.status}`);

        const responseData = await response.json();
        console.log("✅ API Response:", responseData);

        getVehicles();
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

            if (!response.ok) throw new Error(`❌ HTTP Error: ${response.status}`);

            getVehicles(); // Refresh vehicle list
        } catch (error) {
            console.error("🚨 Fetch Error:", error);
        }
    }
}

// ✅ Ensure vehicles load on page load
document.addEventListener("DOMContentLoaded", function () {
    if (window.location.href.includes("vehicleManager.jsp")) {
        getVehicles();
    }
});
