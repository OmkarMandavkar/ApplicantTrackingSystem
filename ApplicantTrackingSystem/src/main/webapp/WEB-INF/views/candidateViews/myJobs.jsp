<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.List"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Candidate Dashboard</title>
<link href="/css/sidebar.css" rel="stylesheet" />

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">



</head>
<body style="background-color: #edebe9">
	<div class="wrapper">
		<div class="sidebar">
			<div class="d-flex justify-content-center">
				<img src="${candidateImage}" alt="Candidate Logo"
					class="rounded-circle" style="width: 50px; height: 50px" />
				<h4> <strong>${candidateName}</strong> </h4>
			</div>

			<hr class="border-light" />
			<nav class="nav flex-column">
				<a href="<%=request.getContextPath()%>/dashboard/candidate"
					class="nav-link">Dashboard</a> 
				<a href="<%=request.getContextPath()%>/candidate/viewCandidateDetails"
					class="nav-link">View/Edit Profile</a> 
				<a href="<%=request.getContextPath()%>/candidate/viewAllActiveJobs"
					class="nav-link" id="pushActive">View All Jobs</a> 
				<a href="<%=request.getContextPath()%>/candidate/myJobs"
					class="nav-link">My Jobs</a> 
				<a href="<%=request.getContextPath()%>/candidate/viewStatistics"
					class="nav-link">View Statistics</a> 
				<a href="<%=request.getContextPath()%>/candidate/uploadDocuments"
					class="nav-link">Upload Documents</a> 
				<a href="<%=request.getContextPath()%>/candidate/jobAlerts"
					class="nav-link">Job Alerts</a>
			</nav>
			<button class="btn btn-danger logout-btn" data-toggle="modal"
				data-target="#confirmLogoutModal">Logout</button>
		</div>

		<div class="main-content">

			<c:if test="${empty JobData}">
				<p class="text-center">No Job data available</p>
			</c:if>

			<div class="row col-md-12 mt-2" id="jobContainer" style="margin: 0 auto;">
                <table class="table table-bordered table-hover">
                  <thead class="thead-dark text-center">
                    <tr>
                      <th class="text-center">Name</th>
                      <th class="text-center">Email</th>
                      <th class="text-center">Phone</th>
                      <th class="text-center">Company</th>
                      <th class="text-center">Title</th>
                    </tr>
                  </thead>
                  <tbody class="text-left">
                    <c:forEach var="candidate" items="${JobData}">
                      <tr>
                        <td class="text-left">${candidate.firstname} ${candidate.lastname}</td>
                        <td class="text-left">${candidate.email}</td>
                        <td class="text-left">${candidate.phone}</td>
                        <td class="text-left">${candidate.company.cname}</td>
                        <td class="text-left">${candidate.jobDescription.title}</td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
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
