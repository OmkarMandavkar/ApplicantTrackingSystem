

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Super Admin Profile</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/sidebar.css" rel="stylesheet" />
    <style>
      body {
        background-color: #edebe9;
      }
      .card {
        border-radius: 10px;
        transition: transform 0.3s;
      }
      .card-body-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 10px;
      }
    </style>
  </head>
  <body>
    <div class="wrapper">
      <div class="sidebar">
        <div class="d-flex justify-content-center">
          <img src="${candidateImage}" alt="Admin Logo" class="rounded-circle" style="width: 50px; height: 50px"/>
          <h4><strong>${candidateName}</strong></h4>
        </div>
        <hr class="border-light" />
        <nav class="nav flex-column">
            <a href="<%=request.getContextPath()%>/dashboard/candidate" class="nav-link">Dashboard</a>
            <a href="<%=request.getContextPath()%>/candidate/viewCandidateDetails" class="nav-link">View/Edit Profile</a>
            <a href="<%=request.getContextPath()%>/candidate/viewAllActiveJobs" class="nav-link">View All Jobs</a>
            <a href="<%=request.getContextPath()%>/candidate/myJobs" class="nav-link">My Jobs</a>
            <a href="<%=request.getContextPath()%>/candidate/viewStatistics" class="nav-link">View Statistics</a>
            <a href="<%=request.getContextPath()%>/candidate/uploadDocuments" class="nav-link">Upload Documents</a>
            <a href="<%=request.getContextPath()%>/candidate/jobAlerts" class="nav-link">Job Alerts</a>
        </nav>

        <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">Logout</button>
      </div>
      
      <div class="main-content">
        <div class="container mt-2">
          <div class="card p-2">
            <div class="card-header bg-white border-bottom d-flex flex-row justify-content-between align-items-center">
              <h2 class="text-primary">Super Admin Profile</h2>
              <a href="<%=request.getContextPath()%>/superadmin/viewEditAdminDetails" class="btn btn-warning btn-sm">Update</a>
            </div>
              <div class="p-3 mb-3">
          <div class="card-head d-flex justify-content-between align-items-center">
            <h5> Personal Information </h5>
          </div>
          <hr />

          <div class="card-body-grid">
            <div>
              <p class="font-weight-bold">First Name</p>
              <p>${candidateData.firstname}</p>
            </div>
            <div>
              <p class="font-weight-bold">Last Name</p>
              <p>${candidateData.lastname}</p>
            </div> 
            <div>
              <p class="font-weight-bold">Date of Birth</p>
              <p>${candidateData.dob}</p>
            </div>
            <div>
              <p class="font-weight-bold">Email</p>
              <p>${candidateData.email}</p>
            </div>
            <div>
              <p class="font-weight-bold">Mobile</p>
              <p>${candidateData.mobile}</p>
            </div>
            <div>
              <p class="font-weight-bold">Role</p>
              <p>${candidateData.role}</p>
            </div>
          </div>
        </div>
            
       <div class="p-3 mb-3">
          <div class="card-head d-flex justify-content-between align-items-center">
            <h5> Address Information </h5>
          </div>
          <hr />

          <div class="card-body-grid">
            <div>
              <p class="font-weight-bold">Country</p>
              <p>${candidateData.country}</p>
            </div>
            <div>
              <p class="font-weight-bold">City</p>
              <p>${candidateData.city}</p>
            </div>
            <div>
              <p class="font-weight-bold">Pincode</p>
              <p>${candidateData.pincode}</p>
            </div>
          </div>
        </div>
            
          </div>
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
      $(document).ready(function () {
        var currentUrl = window.location.pathname;
        $('.sidebar a').each(function () {
          if (this.href.indexOf(currentUrl) !== -1) {
            $(this).addClass('active');
          }
        });
      });

      $('#confirmLogoutModal').on('show.bs.modal', function (event) {
        var modal = $(this);
        var logoutUrl = "<%=request.getContextPath()%>/auth/logout";
        modal.find('#confirmLogoutBtn').attr("href", logoutUrl);
      });
    </script>
  </body>
</html>
