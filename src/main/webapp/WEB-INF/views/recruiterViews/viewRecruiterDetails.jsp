<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recruiter Job Listing</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"> 
    <style>
        .card-body-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 10px;
        }
		.fas{
			margin-right: 5px;
		}
    </style>
</head>
<body>
    <div class="wrapper">
        <div class="sidebar">
            <div class="d-flex justify-content-center">
                <img src="${recruiterImage}" alt="Recruiter Logo" class="rounded-circle" style="width: 50px; height: 50px"/>
                <h4><strong>${recruiterName}</strong></h4>
            </div>

            <hr class="border-light" />
            <nav class="nav flex-column">
				<a href="<%=request.getContextPath()%>/recruiter/viewEditDetails" class="nav-link"> <i class="fas fa-user-edit"></i> View/Edit Profile </a>
				<a href="<%=request.getContextPath()%>/recruiter/scheduleInterview" class="nav-link"> <i class="fas fa-calendar-check"></i> Manage Interview </a>
				<a href="<%=request.getContextPath()%>/recruiter/interviewLogs" class="nav-link"> <i class="fas fa-file-alt"></i> Interview Logs </a>	
				<a href="<%=request.getContextPath()%>/recruiter/onboarding" class="nav-link"> <i class="fas fa-user-plus"></i> Onboarding </a>
            </nav>
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">
                Logout
            </button>
        </div>

    <div class="main-content">
        <div class="card p-3 mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <h2>Recruiter Profile</h2>
                <a href="<%=request.getContextPath()%>/recruiter/updateRecruiterData" class="btn btn-warning btn-sm">Update</a>
            </div>
            <hr />
            <div class="card-body-grid">
                <div><p class="font-weight-bold">Name</p><p>${recruiterData.recruiterFullname}</p></div>
                <div><p class="font-weight-bold">Mobile</p><p>${recruiterData.recruiterMobile}</p></div>
                <div><p class="font-weight-bold">Email</p><p>${recruiterData.recruiterEmail}</p></div>
                <div><p class="font-weight-bold">Designation</p><p>${recruiterData.recruiterDesignation}</p></div>
                <div><p class="font-weight-bold">Password</p><p id="passwordField"></p></div>
            </div>
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
        
        var password = "${recruiterData.recruiterPassword}";
        document.getElementById("passwordField").innerText = "*".repeat(password.length);
        
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