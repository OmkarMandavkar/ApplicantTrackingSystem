<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Company Admin Profile</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    
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

	    <hr style="border:1px solid #D3D3D3; width:75%" />
		<nav class="nav flex-column mt-4">
		    <a href="<%=request.getContextPath()%>/dashboard/company" class="nav-link"> <i class="fas fa-tachometer-alt mr-1"></i> Dashboard </a>
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
            <form action="${pageContext.request.contextPath}/company/repostJobByJobId" method="post" class="shadow p-4 bg-white rounded">
                <input type="hidden" name="jobId" value="${jobData.jobId}">
                <input type="hidden" name="companyId" value="${jobData.company.companyId}">
	  	                
                <div class="form-group">
                    <label for="title">Job Title</label>
                    <input type="text" class="form-control" value="${jobData.title}" readonly>
                </div>
				<div class="row">
		            <div class="form-group col-md-6">	
	                    <label for="company">Company</label>
	                    <input type="text" class="form-control" value="${jobData.company.companyName}" readonly>
	                </div>
	                <div class="form-group col-md-6">
	                    <label for="expiryDate">Application Deadline</label>
	                    <input type="date" class="form-control" name="expiryDate">
	                </div>				
				</div>			


                <button type="submit" class="btn btn-primary btn-block">Submit</button>
            </form>
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

        $('#confirmLogoutModal').on('show.bs.modal', function (event) {
            var modal = $(this);
            var logoutUrl = "<%=request.getContextPath()%>/auth/logout";
            modal.find('#confirmLogoutBtn').attr("href", logoutUrl);
        });
    </script>
</body>
</html>
