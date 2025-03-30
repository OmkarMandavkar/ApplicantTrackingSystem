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
            justify-content: center;
        }
        .wrapper {
    display: flex;
}


    </style>
</head>
<body>
    <div class="wrapper">
        <div class="sidebar">
            <div class="d-flex justify-content-center">
                <img src="${companyLogo}" alt="Company Logo" class="rounded-circle" style="width: 50px; height: 50px"/>
                <h4><strong>${companyName}</strong></h4>
            </div>
	    <hr style="border:1px solid #D3D3D3; width:75%" />
		<nav class="nav flex-column mt-4">
		    <a href="<%=request.getContextPath()%>/dashboard/company" class="nav-link active"> <i class="fas fa-tachometer-alt mr-1"></i> Dashboard </a>
			<a href="<%=request.getContextPath()%>/company/viewDetails" class="nav-link"> <i class="fas fa-user-edit mr-1"></i> View/Edit Profile </a>
		    <a href="<%=request.getContextPath()%>/company/createJobDescription" class="nav-link"> <i class="fas fa-plus-circle mr-1"></i> Create Job Profile </a>
		    <a href="<%=request.getContextPath()%>/company/viewAllJobDescription" class="nav-link"> <i class="fas fa-briefcase mr-1"></i> View All Jobs </a>
		    <a href="<%=request.getContextPath()%>/company/manageRecruiter" class="nav-link"> <i class="fas fa-users-cog mr-1"></i> Manage Recruiter </a>
		    <a href="<%=request.getContextPath()%>/company/manageCandidate" class="nav-link"> <i class="fas fa-user-check mr-1"></i> Manage Candidate </a>
		    <a href="<%=request.getContextPath()%>/company/viewHiringAnalytics" class="nav-link"> <i class="fas fa-chart-line mr-1"></i> View Hiring Analytics </a>
		</nav>
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">Logout</button>
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
		                    <h2 style="color: #287e71">${jobData.title}</h2>
		                     </div>   
	                        <h5 class="text-muted">${jobData.company.companyName}</h5>
		                </div>
		                <div class="card-body">
							
							<c:if test="${not empty jobData.aboutJob}">
								<p><strong>About Job:</strong> ${jobData.aboutJob}</p>
							</c:if>
														
							<c:if test="${not empty jobData.domain}">
								<p><strong>Domain:</strong> ${jobData.domain}</p>
							</c:if>							
	
							<c:if test="${not empty jobData.experience}">
								<p><strong>Experience Required:</strong> ${jobData.experience}</p>
							</c:if>
							
							<c:if test="${not empty jobData.qualification}">
								<p><strong>Qualification:</strong> ${jobData.qualification}</p>
							</c:if>
							
							<c:if test="${not empty jobData.jobLocation}">
								<p><strong>Location:</strong> ${jobData.jobLocation}</p>
							</c:if>
							
							<c:if test="${not empty jobData.noticePeriod}">
								<p><strong>Notice Period:</strong> ${jobData.noticePeriod}</p>
							</c:if>
	
							<c:if test="${not empty jobData.jobType}">
			                    <p><strong>Job Type:</strong> ${jobData.jobType}</p>
							</c:if>
							
							<c:if test="${not empty jobData.roleAndResp}">
								<p><strong>Role And Responsibilities:</strong> ${jobData.roleAndResp}</p>
							</c:if>							
							
							<c:if test="${not empty jobData.skillsRequired}">
								<p><strong>Skills Required:</strong> ${jobData.skillsRequired}</p>
							</c:if>							
													
							<c:if test="${not empty roundData}">
								<p><strong>Interview Rounds:</strong></p>
							    <ul>
						        	<c:forEach var="data" items="${roundData}">
							          <li>Round ${data.roundNumber}: ${data.roundType}</li>
						          	</c:forEach>
						        </ul>
							</c:if>
		                
		                <div class="border border-secondary rounded p-2">
		                	<h5> About Company </h5>
		                	
		                	<div class="d-flex flex-row p-2 mt-2">
		                		<img alt="" src="${jobData.company.logoUrl}" class="rounded-circle border border-secondary" style="width: 50px; height: 50px">
		                		
		                		<div class="d-flex flex-column align-items-start justify-content-between pl-4">
				                	<h6>${jobData.company.companyName}</h6>
				                	<p>${jobData.company.natureOfBusiness}  |  ${jobData.company.employeeCount} employees </p>                		
		                		</div>
		                	</div>
							
							<p class="m-1">${jobData.company.description}</p>
							
		                </div>
							
		                </div>		                
		            </div>
		        </div>
		    </div>
		</div>
		</div>
	</div>
        
    <jsp:include page="/WEB-INF/views/utility/model.jsp" />
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
       
        $('#confirmLogoutModal').on('show.bs.modal', function (event) {
            var modal = $(this);
            var logoutUrl = "<%=request.getContextPath()%>/auth/logout";
            modal.find('#confirmLogoutBtn').attr("href", logoutUrl);
        });
    </script>
</body>
</html>