<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Company Dashboard</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

<style>
body {
    display: flex;
}

.container {
    max-width: 100%;
}

.card {
    border-radius: 10px;
    transition: transform 0.3s;
    height: 100%;
    display: flex;
    flex-direction: column;
}

.card-body {
    flex-grow: 1;
}

.card-footer {
    text-align: center;
    padding: 10px;
    background-color: #fff;
    border-top: none;
}
		.fas{
			margin-right: 5px;
		}

</style>
</head>
<body style="background-color: #edebe9">
    <div class="wrapper">
        <div class="sidebar">
            <div class="d-flex justify-content-center">
                <img src="${recruiterImage}" alt="Recruiter Logo" class="rounded-circle" style="width: 50px; height: 50px"/>
                <h4><strong>${recruiterName}</strong></h4>
            </div>
            <hr class="border-light" />
            <nav class="nav flex-column">
				<a href="<%=request.getContextPath()%>/recruiter/viewEditDetails" class="nav-link"> <i class="fas fa-user-edit"></i> View/Edit Profile </a>
				<a href="<%=request.getContextPath()%>/recruiter/scheduleInterview" class="nav-link active"> <i class="fas fa-calendar-check"></i> Manage Interview </a>
				<a href="<%=request.getContextPath()%>/recruiter/interviewLogs" class="nav-link"> <i class="fas fa-file-alt"></i> Interview Logs </a>	
				<a href="<%=request.getContextPath()%>/recruiter/onboarding" class="nav-link"> <i class="fas fa-user-plus"></i> Onboarding </a>
            </nav>
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">
                Logout
            </button>
        </div>


    <div class="main-content">
        <c:if test="${empty jobData}">
            <p class="text-center">No Job data available</p>
        </c:if>

		<div class="container mt-4">
		    <div class="row justify-content-center">
		        <div class="col-md-11">
		            <div class="card shadow-lg p-4">
		                <div class="card-header bg-white border-bottom d-flex justify-content-between">
		                    <div>
		                        <h2 >${jobData.title}</h2>
		                        <h5 class="text-muted">${jobData.company.companyName}</h5>
		                    </div>
		                </div>
		                <div class="card-body">
		                    <p><strong>About Job:</strong> ${jobData.aboutJob}</p>
		                    <p><strong>Job Type:</strong> ${jobData.jobType}</p>
		                    <p><strong>Vacancy:</strong> ${jobData.vacancy}</p>
		                    <p><strong>Experience Required:</strong> ${jobData.experience}</p>
		                    <p><strong>Qualification:</strong> ${jobData.qualification}</p>
		                    <p><strong>Location:</strong> ${jobData.jobLocation}</p>
		                    <p><strong>Notice Period:</strong> ${jobData.noticePeriod}</p>
		                    <p><strong>Domain:</strong> ${jobData.domain}</p>
		                    <p><strong>Role & Responsibilities:</strong> ${jobData.roleAndResp}</p>
		                    <p><strong>Skills Required:</strong> ${jobData.skillsRequired}</p>
		                    <p><strong>Date Posted:</strong> ${jobData.datePosted}</p>
		                    <p><strong>Expiry Date:</strong> ${jobData.expiryDate}</p>
		                    <p><strong>Status:</strong> <span class="badge badge-${jobData.status == 'ACTIVE' ? 'success' : 'danger'}">${jobData.status}</span></p>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
</div>
</div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
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


