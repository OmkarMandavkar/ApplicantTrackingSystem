<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Recruiter Profile</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .card-body-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 10px;
        }
        
		.fas{
			margin-right: 5px;
		}
    </style>
</head>
<body>
    <div class="wrapper">
        <div class="sidebar">
            <div class="d-flex justify-content-center">
                <img src="${recruiterImage}" alt="Recruiter Logo" class="rounded-circle" style="width: 50px; height: 50px"/>
                <h4><strong>${recruiterName}</strong></h4>
            </div>
            <hr class="border-light" />
            <nav class="nav flex-column">
				<a href="<%=request.getContextPath()%>/recruiter/viewEditDetails" class="nav-link active"> <i class="fas fa-user-edit"></i> View/Edit Profile </a>
				<a href="<%=request.getContextPath()%>/recruiter/scheduleInterview" class="nav-link"> <i class="fas fa-calendar-check"></i> Manage Interview </a>
				<a href="<%=request.getContextPath()%>/recruiter/interviewLogs" class="nav-link"> <i class="fas fa-file-alt"></i> Interview Logs </a>	
				<a href="<%=request.getContextPath()%>/recruiter/onboarding" class="nav-link"> <i class="fas fa-user-plus"></i> Onboarding </a>
            </nav>
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">Logout</button>
        </div>

        <div class="main-content">
            <div class="card p-3 mb-3">
                <div class="d-flex justify-content-between align-items-center">
                    <h2>Recruiter Profile</h2>
                </div>
                <hr />

                <form action="${pageContext.request.contextPath}/recruiter/updateRecruiterData" method="post">
                    <div class="card-body-grid">
                        <div>
                            <label for="recruiterFullname" class="font-weight-bold">Name</label>
                            <input type="text" class="form-control" name="recruiterFullname" value="${recruiterData.recruiterFullname}" required>
                        </div>
                        <div>
                            <label for="recruiterMobile" class="font-weight-bold">Mobile</label>
                            <input type="text" class="form-control" name="recruiterMobile" value="${recruiterData.recruiterMobile}" required>
                        </div>
                        <div>
                            <label for="recruiterEmail" class="font-weight-bold">Email</label>
                            <input type="email" class="form-control" name="recruiterEmail" value="${recruiterData.recruiterEmail}" readonly>
                        </div>
                        <div>
                            <label for="recruiterDesignation" class="font-weight-bold">Designation</label>
                            <input type="text" class="form-control" name="recruiterDesignation" value="${recruiterData.recruiterDesignation}" required>
                        </div>                
                        <div>
                            <label for="recruiterPassword" class="font-weight-bold">Password</label>
                            <input type="text" class="form-control" name="recruiterPassword" value="${recruiterData.recruiterPassword}" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block mt-3">Update</button>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/utility/model.jsp" />

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
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
