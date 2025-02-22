<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Candidate Dashboard</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">


<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">


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
            <a href="<%=request.getContextPath()%>/candidate/viewAllActiveJobs" class="nav-link">View All Jobs</a>
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

        <c:if test="${empty activeJobData}">
            <p class="text-center">No Job data available</p>
        </c:if>

        <div class="row" id="jobContainer">
            <c:forEach var="job" items="${activeJobData}">


	    <div class="col-md-3 mb-2">
	      <div class="card shadow">
	        <div class="card-body">
	          <div class="d-flex justify-content-between align-items-start mb-3">
	            <img src="${job.company.imageLink}" alt="" class="rounded-circle" style="width: 30px; height: 30px" />
	            <p class="card-text fs-6"><i class="fa-solid fa-clock mr-2" style="color:grey"></i>${job.expiryDate}</p>
	          </div>
	
	          <div class="d-flex flex-column align-items-start">
	            <h5 class="card-text jobTitle">${job.title}</h5>
	            <p class="card-text fs-3">${job.cname}</p>
	            <p class="card-text fs-5"><i class="fa-solid fa-location-dot mr-2" style="color:dark-grey"></i>${job.jobLocation} </p>
	          </div>
	
	          <div class="text-center mt-4">
	            <a href="<%=request.getContextPath()%>/candidate/viewJobDetailsByJobId?jobId=${job.jobId}" class="btn btn-primary"> View More Details </a>
	          </div>
	        </div>	
	      </div>
	    </div>


<!--                  <div class="col-md-4 mb-4 job-card" data-status="${job.status}">
                    <div class="card shadow">
                        <div class="card-body">
                            <h5 class="card-title"><strong>${job.title}</strong></h5>
                            <hr>
                            <p class="card-text"><strong>Domain:</strong> ${job.domain}</p>
                            <p class="card-text"><strong>Experience:</strong> ${job.experience}</p>
                            <p class="card-text"><strong>Skills:</strong> ${job.skillsRequired}</p>
                            <p class="card-text"><strong>Date Posted:</strong> ${job.datePosted}</p>
                            <p class="card-text status-text" style="display:none">${job.status}</p>
                        </div>
                        <div class="card-footer mb-2">
                            <a href="<%=request.getContextPath()%>/company/viewJobDetailByJobId?jobId=${job.jobId}" class="btn btn-secondary">View More Details</a>
                        </div>
                    </div>
                </div>-->
                
            </c:forEach> 
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
		document.addEventListener("DOMContentLoaded", function () {
		    let jobTitles = document.querySelectorAll(".jobTitle"); 
		    let maxLength = 20 ; 

		    jobTitles.forEach(title => {
		        if (title.innerText.length > maxLength) {
		            title.innerText = title.innerText.substring(0, maxLength) + "...";
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
