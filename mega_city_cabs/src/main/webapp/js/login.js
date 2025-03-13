/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


// Login logic
document.addEventListener("DOMContentLoaded", function () {
    let loginForm = document.querySelector("form");
    if (!loginForm) {
        console.error("❌ ERROR: loginForm not found!");
        return;
    }

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault(); // Prevent default form submission

        let email = document.getElementById("floatingInput").value.trim();
        let password = document.getElementById("floatingPassword").value.trim();
        if (!email || !password) {
            showMessage("⚠️ Please enter both email and password!", "warning");
            return;
        }

        let requestData = {
            email: email,
            pWord: password
        };
        try {
            let response = await fetch("http://localhost:8080/restAPIMCCabs/api/users/login", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(requestData)
            });
            let responseData = await response.json();
            console.log("🔍 API Response: ", responseData); // Debugging line

            if (!response.ok) {
                throw new Error(responseData.message || "❌ Invalid email or password!");
            }

            // ✅ Store userId and uRole in session storage
            sessionStorage.setItem("userId", responseData.userId);
            sessionStorage.setItem("uRole", responseData.uRole);
            console.log("✅ Session storage set:");
            console.log("userId:", responseData.userId);
            console.log("uRole:", responseData.uRole);
            // ✅ Redirect Based on User Role
            if (responseData.uRole === "adm") {
                alert("✅ Login Successful! Redirecting to Admin Dashboard...");
                window.location.href = "./adminPages/adminHome.jsp";
            } else if (responseData.uRole === "cus") {
                alert("✅ Login Successful! Redirecting to Customer Dashboard...");
                window.location.href = "./customerPages/customerHome.jsp";
            } else {
                showMessage("❌ Unauthorized role detected!", "danger");
            }

        } catch (error) {
            console.error("🚨 ERROR:", error.message);
            showMessage(error.message || "❌ Login failed! Please try again.", "danger");
        }
    });
    function showMessage(message, type) {
        let messageBox = document.getElementById("messageBox");
        if (!messageBox) {
            messageBox = document.createElement("div");
            messageBox.id = "messageBox";
            messageBox.className = `alert alert-${type} mt-3`;
            loginForm.prepend(messageBox);
        } else {
            messageBox.className = `alert alert-${type} mt-3`;
        }
        messageBox.textContent = message;
    }
});