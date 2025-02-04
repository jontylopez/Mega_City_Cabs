<%-- 
    Document   : adminHome
    Created on : Feb 4, 2025, 10:55:23 PM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Admin!</h1>
        <script>
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
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(requestData)
                });

                let responseData = await response.json();

                if (!response.ok) {
                    throw new Error(responseData.message || "❌ Invalid email or password!");
                }

                if (responseData.uRole === "adm") {
                    alert("✅ Login Successful! Redirecting to Admin Dashboard...");
                    window.location.href = "adminHome.jsp";
                } else if (responseData.uRole === "cus") {
                    alert("✅ Login Successful! Redirecting to Customer Dashboard...");
                    window.location.href = "customerHome.jsp";
                } else {
                    showMessage("❌ Unauthorized role detected!", "danger");
                }

            } catch (error) {
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
</script>
    </body>
</html>
