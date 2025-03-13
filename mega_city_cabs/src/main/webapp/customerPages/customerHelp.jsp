<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Help Guide | Mega City Cabs</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/customerHelp.css">
</head>
<body>
    <div class="help-container">
        <div class="help-card">
            <div class="card-header">
                <i class="bi bi-info-circle-fill header-icon"></i>
                <h1 class="header-title">How to Use Mega City Cabs</h1>
            </div>
            <div class="card-body">
                <div id="helpContent" class="help-content">
                    <!-- Help Image -->
                    <div class="help-img-container">
                        <img id="helpImage" src="../images/img1.jpg" class="help-img" alt="Help Step">
                    </div>
                    
                    <div class="help-text-container">
                        <!-- Step indicator dots -->
                        <div class="step-indicator" id="stepIndicator">
                            <!-- Will be populated by JavaScript -->
                        </div>
                        
                        <!-- Help Text -->
                        <p id="helpText" class="help-text animate-fade">
                            <!-- Instructions will be inserted dynamically -->
                        </p>
                    </div>
                    
                    <!-- Navigation Buttons -->
                    <div class="nav-buttons">
                        <button class="nav-btn prev-btn" onclick="prevStep()" id="prevBtn" disabled>
                            <i class="bi bi-arrow-left"></i> Previous
                        </button>
                        <button class="nav-btn next-btn" onclick="nextStep()" id="nextBtn">
                            Next <i class="bi bi-arrow-right"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="../js/customer.js"></script>
</body>
</html>