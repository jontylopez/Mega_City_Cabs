<%-- 
    Document   : adminDash
    Created on : Feb 6, 2025, 11:39:13 AM
    Author     : Janith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../sessionCheck.jsp" %>

        <h1 class="mb-4">Dashboard</h1>
        <div class="row">
            <div class="col-md-4">
                <div class="dashboard-card">
                    <h4><i class="bi bi-person-fill"></i> Total Users</h4>
                    <p class="fs-3">245</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dashboard-card">
                    <h4><i class="bi bi-car-front-fill"></i> Active Rides</h4>
                    <p class="fs-3">87</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dashboard-card">
                    <h4><i class="bi bi-cash-stack"></i> Total Earnings</h4>
                    <p class="fs-3">$12,430</p>
                </div>
            </div>
        </div>

