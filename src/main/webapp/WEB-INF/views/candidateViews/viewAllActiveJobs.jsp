<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Active Jobs</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">


<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<style>
      .fas{
      	margin-right: 5px;
      }
      .fa-solid{
      	margin-right: 4px;
      }
    </style>
  </head>
  <body style="background-color: #edebe9">
    <div class="wrapper">
      <div class="sidebar">
		<div class="d-flex justify-content-center">
		    <img src="${sessionScope.candidateProfile}" alt="Candidate Logo" class="rounded-circle" style="width: 50px; height: 50px"/>
		    <h4><strong>${sessionScope.candidateName}</strong></h4>
		</div>        
		
		    <hr style="border:1px solid #D3D3D3; width:75%" />
            <nav class="nav flex-column mt-4">
                <a href="<%=request.getContextPath()%>/dashboard/candidate" class="nav-link"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a href="<%=request.getContextPath()%>/candidate/viewCandidateDetails" class="nav-link"><i class="fas fa-user-edit"></i> View/Edit Profile</a>
                <a href="<%=request.getContextPath()%>/candidate/viewAllActiveJobs" class="nav-link"><i class="fas fa-briefcase"></i> View All Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/appliedJobs" class="nav-link"><i class="fas fa-check-circle"></i> Applied Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/savedJobs" class="nav-link"><i class="fas fa-bookmark"></i> Saved Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/jobLogs" class="nav-link"><i class="fas fa-history"></i> Job Logs</a>
                <a href="<%=request.getContextPath()%>/candidate/uploadDocuments" class="nav-link"><i class="fas fa-file-upload"></i> Upload Documents</a>
                <a href="<%=request.getContextPath()%>/candidate/jobAlerts" class="nav-link"><i class="fas fa-bell"></i> Job Alerts</a>
            </nav>

        <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal" >
         	Logout
        </button>
      </div>

    <div class="main-content">
		<c:choose>
		    <c:when test="${empty activeJobData}">
	            <p class="text-center mt-4">No jobs found at the moment. Subscribe to job alerts to get notified about new opportunities.</p>
				<a href="<%=request.getContextPath()%>/candidate/jobAlerts" class="btn btn-success d-block mx-auto" style="width: fit-content;">Subscribe to Job Alerts</a>
		    </c:when>    
		    <c:otherwise>
		        <div class="row " id="jobContainer">
		            <c:forEach var="job" items="${activeJobData}">
					<div class="col-md-4 mb-2">
                      <div class="card shadow mb-2">
                        <div class="card-body">
                          <div class="d-flex flex-row">
                            <div style="flex-basis: 85%;">
                              <h5 class="card-text jobTitle">${job.title}</h5>
                              <p class="card-text fs-3">${job.company.companyName}</p>
    
                              <div class="d-flex flex-row align-items-center justify-content-between" style="width: 100%; font-size: 13px;">
                                <p><i class="fa-solid fa-briefcase"></i>${job.experience}</p>
                                <p class="ml-1"> | </p>
                                <p class="ml-1 mr-1"> <i class="fa-solid fa-indian-rupee-sign"></i> ${job.salary} LPA</p>
                                <p class="mr-1"> | </p>
                                <p><i class="fa-solid fa-location-dot"></i>${job.jobLocation}</p>
                              </div>
    
                              <p class="skills"><i class="fa-solid fa-laptop-code"></i>${job.skillsRequired} </p>
                              <p style="font-size: 14px;">Posted On: ${job.datePosted}</p>
                            </div>
    
                            <div class="d-flex align-items-start justify-content-center" style="flex-basis: 15%;">
                              <img src="${job.company.logoUrl }" alt="" class="border border-light rounded" style="width: 50px; height: 50px" />
                            </div>
                          </div>
       
                          <div class="text-center">
                            <a href="<%=request.getContextPath()%>/candidate/viewJobDetailsByJobId?jobId=${job.jobId}" class="btn btn-primary"> View Details </a>
                          </div>
                        </div>
                      </div>
					    </div>
		            </c:forEach> 
		        </div>				
		    </c:otherwise>
		</c:choose>
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
		    let skills = document.querySelectorAll(".skills");
		    let maxLength = 20; 

		    jobTitles.forEach(title => {
		        if (title.innerText.length > maxLength) {
		            title.innerText = title.innerText.substring(0, maxLength) + "...";
		        }
		    });
		    
		    skills.forEach(skillsRequired => {
		        let skillTextNode = skillsRequired.childNodes[1]; 
		        if (skillTextNode && skillTextNode.nodeType === 3) {
		            let trimmedText = skillTextNode.nodeValue.trim();
		            if (trimmedText.length > 30) {
		                skillTextNode.nodeValue = trimmedText.substring(0, 28) + "...";
		            }
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
