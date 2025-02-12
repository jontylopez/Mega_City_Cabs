document.addEventListener("DOMContentLoaded", function () {
    console.log("📌 Admin Dashboard Loaded!");
    
    // Load default admin dashboard
    loadPage("adminDash.jsp");

    // Attach event listeners
    setupLinks();
});

// 🔹 Load Pages Dynamically Inside `adminHome.jsp`
function loadPage(url) {
    fetch(url)
        .then(response => response.text())
        .then(html => {
            document.querySelector(".main-content").innerHTML = html;
            setupLinks(); // Ensure links are clickable

            // ✅ Run `getCategories()` when Category Manager is loaded
            if (url.includes("categoryManager.jsp")) {
                console.log("📌 Category Manager Loaded! Fetching categories...");
                getCategories(); // 🔥 Ensure categories load when the page is inserted
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

        const tableBody = document.getElementById("categoryTableBody");
        if (!tableBody) return;

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

        console.log("✅ Table updated successfully!");
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

