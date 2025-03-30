<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Send Email Invitation</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <style>
      table {
        background-color: white;
        margin: 0 auto;
        width: 90%;
        border-collapse: collapse;
      }
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
            <div class="container mt-2">
                <form action="${pageContext.request.contextPath}/recruiter/sendEmailInvitation" method="post">    
            
                <div class="card p-2">
                    <div class="card-header bg-white border-bottom d-flex flex-row justify-content-between align-items-center">
                        <h2>Candidate Profile</h2>
                    </div>
    
                    <div class="p-3 mb-3">
                        <div class="card-body-grid">
                            <div class="mb-4">
								<input type="hidden" class="form-control" name="roundId" value="${emailData.roundId}">
                            </div>            
                            <div class="mb-4">
                                <p class="font-weight-bold mb-1">Candidate Name</p>
								<input type="text" class="form-control" name="" value="${emailData.jobMaster.candidate.user.firstName} ${emailData.jobMaster.candidate.user.lastName}" readonly>
                            </div>            
                            <div class="mb-4">
                                <p class="font-weight-bold mb-1">Candidate Email</p>
								<input type="text" class="form-control" name="email" value="${emailData.jobMaster.candidate.email}" readonly>
                            </div>
		                    <div class="row">
	                            <div class="col-md-3 mb-4">
	                                <p class="font-weight-bold mb-1">Round Number</p>
									<input type="text" class="form-control" name="roundNumber" value="${emailData.roundNumber}" readonly>
	                            </div>
	                            <div class="col-md-9 mb-4">
	                                <p class="font-weight-bold mb-1">Round Type</p>
									<select name="roundType" class="form-control">
									    <c:forEach var="data" items="${roundDetail}">
									        <c:if test="${data.roundNumber == emailData.roundNumber}">
									            <option value="${data.roundType}">Round ${data.roundNumber}: ${data.roundType}</option>
									        </c:if>
									    </c:forEach>
									</select>
	                            </div>
		                    </div>                                                        
		                    <div class="row">
		                        <div class="col-md-6 mb-4">
								    <label for="roundDate" class="font-weight-bold">Interview Date</label>
								    <input type="date" class="form-control" id="roundDate" name="roundDate" required>
		                        </div>
		                        <div class="col-md-6 mb-3">
								    <label for="roundTime" class="font-weight-bold">Interview Time</label>
								    <input type="time" class="form-control" id="roundTime" name="roundTime" required>
		                        </div>
		                    </div>

		                                                
                        </div>
                    </div>
			        <button type="submit" class="btn btn-primary btn-block mb-2" style="width: 30%; margin: 0px auto">Send Invitation</button>

                </div>
				</form>
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
