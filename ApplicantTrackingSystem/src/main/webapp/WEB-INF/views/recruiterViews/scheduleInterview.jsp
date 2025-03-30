<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Schedule Interview</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
		.fas{
			margin-right: 5px;
		}
        .dropdown {
            position: relative;
        }
        .dropdown-menu {
            position: absolute;
            left: auto;
            right: 0;
            min-width: 150px;
            z-index: 1000;
        }
        .dropdown-toggle::after {
            margin-left: 0.5em;
        }
        .dropdown:hover .dropdown-menu {
            display: block;
            margin-top: 0;
        }
        .dropdown-menu a:hover {
            background-color: #f8f9fa;
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
				<a href="<%=request.getContextPath()%>/recruiter/scheduleInterview" class="nav-link"> <i class="fas fa-calendar-check"></i> Manage Interview </a>
				<a href="<%=request.getContextPath()%>/recruiter/interviewLogs" class="nav-link"> <i class="fas fa-file-alt"></i> Interview Logs </a>	
				<a href="<%=request.getContextPath()%>/recruiter/onboarding" class="nav-link"> <i class="fas fa-user-plus"></i> Onboarding </a>
            </nav>
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">
                Logout
            </button>
        </div>

        <div class="main-content">
            <c:if test="${empty viewJobListings}">
                <p class="text-center">No Job data available</p>
            </c:if>
            <div class="row col-md-12 mt-2" id="jobContainer" style="margin: 0 auto;">
                <table class="table table-bordered table-hover">
                    <thead class="thead text-center">
                        <tr>
                        	<th class="d-none">Round Id</th>
                            <th style="width: 2%">Round</th>
                            <th style="width: 18%">Job Title</th>
                            <th style="width: 10%">Company</th>
                            <th style="width: 12%">Candidate Name</th>
                            <th style="width: 9%">Round Date</th>
                            <th style="width: 10%">Round Time</th>
                            <th style="width: 5%">Status</th>
                            <th style="width: 2%">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="job" items="${viewJobListings}">
                            <tr>
                            	<td class="d-none">${job.roundId}</td>
                            	<td>${job.roundNumber}</td>
                                <td class="text-left">${job.jobMaster.jobDescription.title}</td>
                                <td class="text-left">${job.jobMaster.company.companyName}</td>
                                <td class="text-left">${job.jobMaster.candidate.user.firstName} ${job.jobMaster.candidate.user.lastName}</td>
								<c:choose>
								    <c:when test="${empty job.roundDate and empty job.roundTime}">
								        <td colspan="2" class="text-center">Not Scheduled</td>
								    </c:when>
								    <c:otherwise>
								        <td>${job.roundDate}</td>
								        <td>${job.roundTime}</td>
								    </c:otherwise>
								</c:choose>

                                <td class="text-left">${job.roundStatus}</td>
                                <td>                                
                                    <div class="dropdown">
                                        <button class="btn btn-light dropdown-toggle" type="button" data-toggle="dropdown">
                                            <i class="fas fa-cog"></i>
                                        </button>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="<%=request.getContextPath()%>/recruiter/viewJobDetailsByJobId?jobId=${job.jobMaster.jobDescription.jobId}"><i class="fa-solid fa-circle-info"></i> View Details </a>
                                            <a class="dropdown-item" href="<%=request.getContextPath()%>/recruiter/sendEmailInvitation?roundId=${job.roundId}"><i class="fas fa-envelope"></i> Send Invitation </a>
                                            <a class="dropdown-item" href="<%=request.getContextPath()%>/recruiter/attendInterview?applicationId=${job.jobMaster.applicationId}&recruiterId=${job.recruiter.recruiterId}"><i class="fas fa-calendar-check"></i> Attend Interview </a>
                                        </div>
                                    </div>
                                </td>
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

        $('#confirmLogoutModal').on('show.bs.modal', function (event) {
            var modal = $(this);
            var logoutUrl = "<%=request.getContextPath()%>/auth/logout";
            modal.find('#confirmLogoutBtn').attr("href", logoutUrl);
        });
        

        $(document).ready(function () {
            $('.dropdown-toggle').dropdown();
        });
	</script>
</body>
</html>
