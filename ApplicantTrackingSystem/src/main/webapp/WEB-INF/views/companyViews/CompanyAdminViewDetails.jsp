<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Company Admin Profile</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
	<link href="/css/sidebar.css" rel="stylesheet">
    
    <style>
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
          <img src="${companyLogo}" alt="Company Logo" class="rounded-circle"
            style="width: 50px; height: 50px"/>
          <h4><strong>${companyName}</strong></h4>
        </div>

        <hr class="border-light">
        <nav class="nav flex-column">
            <a href="<%=request.getContextPath()%>/dashboard/company" class="nav-link">Dashboard</a>
            <a href="<%=request.getContextPath()%>/company/viewDetails" class="nav-link">View/Edit Profile</a>
            <a href="<%=request.getContextPath()%>/company/createJobDescription" class="nav-link">Create Job Profile</a>
            <a href="<%=request.getContextPath()%>/company/viewAllJobDescription" class="nav-link">View All Jobs</a>
            <a href="<%=request.getContextPath()%>/company/manageRecruiter" class="nav-link">Manage Recruiter</a>
            <a href="<%=request.getContextPath()%>/company/manageCandidate" class="nav-link">Manage Candidate</a>
            <a href="<%=request.getContextPath()%>/company/viewHiringAnalytics" class="nav-link">View Hiring Analytics</a>
        </nav>
        <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">Logout</button>
    </div>

    <div class="main-content">
        <div class="card p-3 mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <h2>Company-Admin Profile</h2>
                <a href="<%=request.getContextPath()%>/company/updateCompanyAdminData" class="btn btn-warning btn-sm">Update</a>
            </div>
            <hr />
            <div class="card-body-grid">
                <div><p class="font-weight-bold">First Name</p><p>${companyAdminData.firstname}</p></div>
                <div><p class="font-weight-bold">Last Name</p><p>${companyAdminData.lastname}</p></div>
                <div><p class="font-weight-bold">Company</p><p>${companyAdminEmail.cname}</p></div>
                <div><p class="font-weight-bold">Email</p><p>${companyAdminData.email}</p></div>
                <div><p class="font-weight-bold">Mobile</p><p>${companyAdminData.mobile}</p></div>
                <div><p class="font-weight-bold">Role</p><p>${companyAdminData.role}</p></div>
            </div>
        </div>
        <div class="text-center mt-4">
            <a href="<%=request.getContextPath()%>/dashboard/company" class="btn btn-secondary">Back to Dashboard</a>
        </div>
    </div>
</div>

	<jsp:include page="/WEB-INF/views/utility/model.jsp" />
	
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
        
        $('#confirmDeleteModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); 
            var fid = button.data('fid');  
            var deleteUrl = "<%=request.getContextPath()%>/deleteFeedByAdmin?fid=" + fid;
            var modal = $(this);
            modal.find('#confirmDeleteBtn').attr("href", deleteUrl);  
        });

        $('#confirmLogoutModal').on('show.bs.modal', function (event) {
            var modal = $(this);
            var logoutUrl = "<%=request.getContextPath()%>/auth/logout";
            modal.find('#confirmLogoutBtn').attr("href", logoutUrl);  
        });
    </script>
</body>
</html>