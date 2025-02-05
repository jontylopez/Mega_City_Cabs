<%-- 
    Document   : sessionCheck
    Created on : Feb 6, 2025, 3:01:33 AM
    Author     : Janith
--%>

<%-- sessionCheck.jsp - Session Validation Logic --%>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        let userId = sessionStorage.getItem("userId");
        let uRole = sessionStorage.getItem("uRole");

        console.log("Checking session...");
        console.log("userId:", userId);
        console.log("uRole:", uRole);

        //  Redirect immediately if user is not logged in
        if (!userId || !uRole) {
            alert("Please log in first!");
            window.location.replace("login.jsp");
        }
    });

    // Prevent unauthorized "Back" navigation
    window.addEventListener("pageshow", function (event) {
        if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
            location.reload();
        }
    });

    // Update user role in navbar
    document.addEventListener("DOMContentLoaded", function () {
        let uRole = sessionStorage.getItem("uRole");
        let userText = document.getElementById("userText");

        if (userText && uRole) {
            userText.textContent = (uRole === "adm") ? "Admin" : "Customer";
        }
    });

    function logout() {
        sessionStorage.clear();
        alert("Logged out successfully!");
        window.location.replace("login.jsp");
    }
</script>
