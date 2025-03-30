<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Application Status</title>
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
				<a href="<%=request.getContextPath()%>/recruiter/scheduleInterview" class="nav-link"> <i class="fas fa-calendar-check"></i> Manage Interview </a>
				<a href="<%=request.getContextPath()%>/recruiter/interviewLogs" class="nav-link"> <i class="fas fa-file-alt"></i> Interview Logs </a>	
				<a href="<%=request.getContextPath()%>/recruiter/onboarding" class="nav-link active"> <i class="fas fa-user-plus"></i> Onboarding </a>
            </nav>
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">
                Logout
            </button>
        </div>

<div class="container vh-100 d-flex justify-content-center">
    <div class="main-content col-md-10">
        <form action="${pageContext.request.contextPath}/recruiter/changeApplicationStatus" method="post" class="shadow p-4 bg-white rounded">
            <input type="hidden" name="applicationId" value="${applicantData.applicationId}">

            <div class="row m-2">
                <div class="form-group col-md-6">
                    <label for="">Candidate Name</label>
                    <input type="text" class="form-control" value="${applicantData.candidate.user.firstName} ${applicantData.candidate.user.lastName}">
                </div>
                <div class="form-group col-md-6">
                    <label for="">Designation</label>
                    <input type="text" class="form-control" value="${applicantData.jobDescription.title}">
                </div>
            </div>    

            <div class="row m-2">
                <div class="form-group col-md-6">
                    <label for="">Current Status</label>
                    <input type="text" class="form-control" value="${applicantData.candidateStatus}">
                </div>                
                <div class="form-group col-md-6">
                    <label for="candidateStatus">Candidate Status</label>
                    <select name="candidateStatus" class="form-control">
                        <option selected disabled>Select Status</option>
                        <option value="HIRED">HIRED</option>
                        <option value="REJECTED">REJECTED</option>
                        <option value="ON_HOLD">ON_HOLD</option>
                    </select>
                </div>                
            </div>

            <button type="submit" class="btn btn-primary btn-block mt-2">Submit</button>
        </form>
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
    </script>
</body>
</html>
