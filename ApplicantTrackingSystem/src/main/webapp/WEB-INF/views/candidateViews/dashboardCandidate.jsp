<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>

<%
    Map<String, Integer> applicationsPerMonth = (Map<String, Integer>) request.getAttribute("applicationsPerMonth");

    String monthsJson = "[";
    String countsJson = "[";
    for (Map.Entry<String, Integer> entry : applicationsPerMonth.entrySet()) {
        monthsJson += "\"" + entry.getKey() + "\", ";
        countsJson += entry.getValue() + ", ";
    }
    monthsJson = monthsJson.replaceAll(", $", "]");
    countsJson = countsJson.replaceAll(", $", "]");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Candidate Dashboard</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
    
    	.dashboard-card {
	        background-color: white; 
	        color: black !important; 
	        border-radius: 10px; 
	        padding: 12px;
	        display: flex;
	        justify-content: space-between;
	        align-items: center;
	        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
	    }
        .dashboard-card:hover {
            transform: scale(1.05);
        }
		.card-icon {
		    width: 45px;
		    height: 45px;
		    border: 1px solid grey; /* Grey border */
		    
		    border-radius: 10px; /* Slightly curved corners */
		    background: rgba(255, 255, 255, 0.2);
		    display: flex;
		    align-items: center;
		    justify-content: center;
		}
		.card-icon i {
		    font-size: 25px;
		    color: #287e71 !important;
		}    
    </style>
</head>
<body style="background-color: #edebe9">
    <div class="wrapper">
        <div class="sidebar">
            <div class="d-flex justify-content-center">
                <img src="${sessionScope.candidateProfile}" alt="Candidate Logo" class="rounded-circle" style="width: 50px; height: 50px" />
                <h4><strong>${sessionScope.candidateName}</strong></h4>
            </div>
            <hr style="border:1px solid #D3D3D3; width:75%" />
            <nav class="nav flex-column mt-4">
                <a href="<%=request.getContextPath()%>/dashboard/candidate" class="nav-link"><i class="fas fa-tachometer-alt mr-1"></i> Dashboard</a>
                <a href="<%=request.getContextPath()%>/candidate/viewCandidateDetails" class="nav-link"><i class="fas fa-user-edit mr-1"></i> View/Edit Profile</a>
                <a href="<%=request.getContextPath()%>/candidate/viewAllActiveJobs" class="nav-link"><i class="fas fa-briefcase mr-1"></i> View All Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/appliedJobs" class="nav-link"><i class="fas fa-check-circle mr-1"></i> Applied Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/savedJobs" class="nav-link"><i class="fas fa-bookmark mr-1"></i> Saved Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/jobLogs" class="nav-link"><i class="fas fa-history mr-1"></i> Job Logs</a>
                <a href="<%=request.getContextPath()%>/candidate/uploadDocuments" class="nav-link"><i class="fas fa-file-upload mr-1"></i> Upload Documents</a>
                <a href="<%=request.getContextPath()%>/candidate/jobAlerts" class="nav-link"><i class="fas fa-bell mr-1"></i> Job Alerts</a>
            </nav>
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">Logout</button>
        </div>
        
        <div class="main-content">
			<div class="d-flex">
			    <div class="container mt-4 w-50">
			    	<div class="row mb-4">
				        <div class="col-md-6">
				            <div class="dashboard-card">
				                <div >
				                    <h6>Applied</h6>
				                    <p class="fs-3 fw-bold">${not empty appliedCount ? appliedCount : 0}</p>
				                </div>
				                <div class="card-icon"><i class="fas fa-briefcase"></i></div>
				            </div>
				        </div>
				        <div class="col-md-6">
				            <div class="dashboard-card">
				                <div>
				                    <h6>In Progress</h6>
				                    <p class="fs-3 fw-bold">${not empty underReviewCount ? underReviewCount : 0}</p>
				                </div>
				                <div class="card-icon"><i class="fas fa-check-circle"></i></div>
				            </div>
				        </div>
				    </div>
			    	<div class="row mt-4">
				        <div class="col-md-6">
				            <div class="dashboard-card">
				                <div >
				                    <h6>Selected</h6>
				                    <p class="fs-3 fw-bold">${not empty selectedCount ? selectedCount : 0}</p>
				                </div>
				                <div class="card-icon"><i class="fas fa-briefcase"></i></div>
				            </div>
				        </div>
				        <div class="col-md-6">
				            <div class="dashboard-card">
				                <div>
				                    <h6>Rejected</h6>
				                    <p class="fs-3 fw-bold">${not empty rejectedCount ? rejectedCount : 0}</p>
				                </div>
				                <div class="card-icon"><i class="fas fa-check-circle"></i></div>
				            </div>
				        </div>
				    </div>		
			    </div>
			    <div class="d-flex align-items-center justify-content-center pl-4" style="flex-basis: 60%;">
			        <canvas id="applicationChart"></canvas>
			    </div>			    
			</div>

			<c:choose>
			    <c:when test="${empty interviewList}">
			        <div class="text-center mt-5">
			            <i class="fas fa-calendar-alt fa-3x text-muted"></i>
			            <h4 class="mt-3">No upcoming interviews scheduled!</h4>
			            <p>Stay tuned! If you've applied for jobs, check back later for updates.</p>
			            <a href="<%=request.getContextPath()%>/candidate/appliedJobs" class="btn btn-primary">
			                <i class="fas fa-briefcase"></i> View Applied Jobs
			            </a>
			        </div>
			    </c:when>
			    <c:otherwise>
			        <div class="row col-md-12 mt-4" id="jobContainer" style="margin: 0 auto;">
			            <table class="table table-bordered table-hover text-center" id="jobTable">
			                <thead class="thead text-center">
			                    <tr>
			                        <th class="text-center" style="width: 2%">Round</th>
			                        <th class="text-center" style="width: 8%">Interview Round</th>
			                        <th class="text-center" style="width: 16%">Job Title</th>
			                        <th class="text-center" style="width: 8%">Company</th>
			                        <th class="text-center" style="width: 6%">Round Date</th>
			                        <th class="text-center" style="width: 6%">Round Time</th>
			                    </tr>
			                </thead>
			                <tbody class="text-left" id="interviewTableBody">
			                    <c:forEach var="data" items="${interviewList}" varStatus="status">
			                        <tr>
			                            <td class="text-center">${data.roundNumber}</td>
   			                            <td class="text-left">${data.roundType}</td>
			                            <td class="text-left">${data.jobMaster.jobDescription.title}</td>
			                            <td class="text-left">${data.jobMaster.company.companyName}</td>
			                            <td>${data.roundDate}</td>
			                        	<td>${data.roundTime}</td> 
			                        </tr>
			                    </c:forEach>
			                </tbody>
			            </table>                
			            <nav>
			                <ul class="pagination justify-content-center" id="pagination"></ul>
			            </nav>
			        </div>
			    </c:otherwise>
			</c:choose>

        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/utility/model.jsp" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
    	//SIDEBAR
        $(document).ready(function () {
            var currentUrl = window.location.pathname;
            $('.sidebar a').each(function () {
                if (this.href.indexOf(currentUrl) !== -1) {
                    $(this).addClass('active');
                }
            });
        });

        //LOGOUT
        $('#confirmLogoutModal').on('show.bs.modal', function (event) {
            var modal = $(this);
            var logoutUrl = "<%=request.getContextPath()%>/auth/logout";
            modal.find('#confirmLogoutBtn').attr("href", logoutUrl);
        });
        
        //CHART
        var ctx = document.getElementById('applicationChart').getContext('2d');
        var applicationChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: <%= monthsJson %>,
                datasets: [{
                    label: 'Applications in Last 6 Months',
                    data: <%= countsJson %>,
                    backgroundColor: 'rgba(40, 126, 113, 0.8)',
                    borderColor: 'rgba(40, 126, 113, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
        
        //PAGINATION CODE        
		$(document).ready(function () {
	        var rowsPerPage = 4;
	        var rows = $('#jobTable tbody tr');
	        var totalRows = rows.length;
	        var totalPages = Math.ceil(totalRows / rowsPerPage);
	        var pagination = $('#pagination');
	
	        function showPage(pageNumber) {
	            var start = (pageNumber - 1) * rowsPerPage;
	            var end = start + rowsPerPage;
	
	            rows.hide().slice(start, end).show();
	            pagination.find('li').removeClass('active');
	            pagination.find('li:eq(' + (pageNumber - 1) + ')').addClass('active');
	        }
	
	        function createPagination() {
	            pagination.empty();
	
	            for (var i = 1; i <= totalPages; i++) {
	                pagination.append('<li class="page-item"><a class="page-link" href="#">' + i + '</a></li>');
	            }
	
	            pagination.find('li:first').addClass('active');	
	            pagination.find('a').click(function (e) {
	                e.preventDefault();
	                showPage($(this).text());
	            });
	        }
	
	        if (totalPages > 1) {
	            createPagination();
	            showPage(1);
	        }
	    });
    </script>
</body>
</html>
