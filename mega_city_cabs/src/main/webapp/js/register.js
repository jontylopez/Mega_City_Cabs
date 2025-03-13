/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener("DOMContentLoaded", function () {
    let registerForm = document.getElementById("registerForm");
    if (!registerForm) {
        console.error("❌ ERROR: registerForm not found!");
        return;
    }

    registerForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        let fullName = document.getElementById("fullName").value.trim();
        let address = document.getElementById("address").value.trim();
        let phone = document.getElementById("phone").value.trim();
        let email = document.getElementById("email").value.trim();
        let username = document.getElementById("username").value.trim();
        let pWord = document.getElementById("pWord").value.trim();
        let confirmPassword = document.getElementById("confirmPassword").value.trim();

        if (pWord !== confirmPassword) {
            showMessage("❌ Passwords do not match!", "danger");
            return;
        }

        if (!fullName || !address || !phone || !email || !username || !pWord) {
            showMessage("⚠️ Please fill in all required fields!", "warning");
            return;
        }

        let requestData = {
            username: username,
            pWord: pWord,
            uRole: "cus",
            fullName: fullName,
            address: address,
            phone: phone,
            email: email
        };

        try {
            let response = await fetch("http://localhost:8080/restAPIMCCabs/api/users/create", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(requestData)
            });

            let responseData = await response.json();

            if (!response.ok) {
                if (response.status === 409) {
                    showMessage("⚠️ Username or Email is already taken!", "warning");
                } else {
                    throw new Error(responseData.message || "Registration failed");
                }
                return;
            }

            // ✅ Alert for successful registration
            alert("✅ User Registration Successful!");
            showMessage("✅ User registered successfully! Redirecting to login...", "success");

            setTimeout(() => window.location.href = "login.jsp", 2000);

        } catch (error) {
            showMessage(error.message || "❌ Registration failed! Please try again.", "danger");
        }
    });

    function showMessage(message, type) {
        let messageBox = document.getElementById("messageBox");
        if (messageBox) {
            messageBox.className = `alert alert-${type} mt-3`;
            messageBox.textContent = message;
            messageBox.classList.remove("d-none");
        }
    }
});

