<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recruiter Job Listing</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
	
	<style type="text/css">
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
            <h2 class="text-center mb-4">Interview Evaluation</h2>
            <form action="${pageContext.request.contextPath}/recruiter/attendInterview" method="post" class="shadow p-4 bg-white rounded">
                <input type="hidden" name="applicationId" value="${interviewData.jobMaster.applicationId}">
                <input type="hidden" name="recruiterId" value="${interviewData.recruiter.recruiterId}">
                
                <div class="form-group">
                    <label for="roundNumber">Round Number</label>
                    <input type="text" class="form-control" name="roundNumber" value="${interviewData.roundNumber}" readonly>
                </div>
                <div class="form-group">
                    <label for="score">Score</label>
                    <input type="text" class="form-control" name="score" value="${interviewData.score}">
                </div>
                <div class="form-group">
                    <label for="feedback">Feedback</label>
                    <input type="text" class="form-control" name="feedback" value="${interviewData.feedback}">
                </div>
                <div class="form-group">
                    <label for="roundStatus">Status</label>
                    <select name="roundStatus" class="form-control">
                        <option selected disabled>Select Status</option>
                        <option value="SELECTED">SELECTED</option>
                        <option value="REJECTED">REJECTED</option>
                        <option value="COMPLETED">COMPLETED</option>
                        <option value="PENDING">PENDING</option>
                    </select>
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
