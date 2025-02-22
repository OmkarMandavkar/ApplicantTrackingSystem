<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.dev.ATSapp.Entity.Company"%>
<%@ page import="java.util.List"%>
<jsp:include page="/WEB-INF/views/utility/model.jsp" />


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Candidate Dashboard</title>
<link href="/css/sidebar.css" rel="stylesheet" />


<!-- Bootstrap CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">

<style>
.card {
	border-radius: 10px;
	transition: transform 0.3s;
}

.card-body {
	flex-grow: 1;
}

.card-footer {
	text-align: center;
	background-color: #fff;
	border-top: none;
}

</style>

</head>
  <body style="background-color: #edebe9">
    <div class="wrapper">
      <div class="sidebar">
        <div class="d-flex justify-content-center">
          <img src="${candidateImage}" alt="Candidate Logo" class="rounded-circle"
            style="width: 50px; height: 50px"/>
          <h4><strong>${candidateName}</strong></h4>
        </div>
        
        <hr class="border-light" />
        <nav class="nav flex-column">
            <a href="<%=request.getContextPath()%>/dashboard/candidate" class="nav-link">Dashboard</a>
            <a href="<%=request.getContextPath()%>/candidate/viewCandidateDetails" class="nav-link">View/Edit Profile</a>
            <a href="<%=request.getContextPath()%>/candidate/viewAllActiveJobs" class="nav-link" style="background-color: #287e71; color:white">View All Jobs</a>
            <a href="<%=request.getContextPath()%>/candidate/myJobs" class="nav-link">My Jobs</a>
            <a href="<%=request.getContextPath()%>/candidate/viewStatistics" class="nav-link">View Statistics</a>
            <a href="<%=request.getContextPath()%>/candidate/uploadDocuments" class="nav-link">Upload Documents</a>
            <a href="<%=request.getContextPath()%>/candidate/jobAlerts" class="nav-link">Job Alerts</a>
        </nav>
        <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal" >
         	Logout
        </button>
      </div>
		
	  <div class="main-content">

        <c:if test="${empty jobData}">
            <p class="text-center">No Job data available</p>
        </c:if>

		<div class="container mt-2">
		    <div class="row justify-content-center">
		        <div class="col-md-12">
		            <div class="card shadow-lg p-2">
		                <div class="card-header bg-white border-bottom d-flex flex-column justify-content-between">
		                    <div class="d-flex flex-row justify-content-between align-items-center">
		                    <h2 class="text-primary">${jobData.title}</h2>
		                        
		                        <div class="card-footer bg-white d-flex flex-row justify-content-between">
								
									<c:choose>
								        <c:when test="${applied}">
								            <button class="btn  btn-success" disabled>Applied</button>
								        </c:when>
								        <c:otherwise>
								            <a href="<%=request.getContextPath()%>/candidate/applyJob?jobId=${jobData.jobId}&userId=${candidateId}" class="btn btn-warning">Apply</a>
								        </c:otherwise>
								    </c:choose>					    
									    
								    <a href="<%=request.getContextPath()%>/candidate/saveJob?jobId=${sessionScope.jobId}&userId=${sessionScope.userId}" class="btn btn-warning ml-3">Save</a>
								</div>
		                        
		                     </div>   
	                        <h5 class="text-muted">${jobData.cname}</h5>
		                </div>
		                <div class="card-body">
		                    <p><strong>About Job:</strong> ${jobData.aboutJob}</p>
		                    <p><strong>Job Type:</strong> ${jobData.jobType}</p>
		                    <p><strong>Experience Required:</strong> ${jobData.experience}</p>
		                    <p><strong>Qualification:</strong> ${jobData.qualification}</p>
		                    <p><strong>Location:</strong> ${jobData.jobLocation}</p>
		                    <p><strong>Notice Period:</strong> ${jobData.noticePeriod}</p>
		                    <p><strong>Domain:</strong> ${jobData.domain}</p>
		                    <p><strong>Role And Responsibilities:</strong> ${jobData.roleAndResp}</p>
		                    <p><strong>Skills Required:</strong> ${jobData.skillsRequired}</p>
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