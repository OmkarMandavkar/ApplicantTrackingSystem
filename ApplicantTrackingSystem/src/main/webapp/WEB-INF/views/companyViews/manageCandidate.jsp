<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Manage Candidate</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
      table {
        background-color: white;
        margin: 0 auto;
        width: 90%;
        border-collapse: collapse;
      }
    </style>
  </head>
  <body style="background-color: #edebe9;">
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
        <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal" >
         	Logout
        </button>
      </div>


      <div class="main-content">
        <table class="table table-bordered table-hover" id="jobTable">
          <thead class="thead">
            <tr>
              <th style="width: 11%">Candidate Name</th>
              <th style="width: 18%">Job Title</th>
			  <th style="width: 10%">Company</th>
              <th style="width: 8%">Applied Date</th>
              <th style="width: 8%">Application Status</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="feed" items="${jobData}">
              <tr>
                <td class="text-left">${feed.candidate.user.firstName} ${feed.candidate.user.lastName}</td>
                <td class="text-left">${feed.jobDescription.title}</td>
                <td class="text-left">${feed.company.companyName}</td>
                <td>${feed.appliedDate}</td>
                <td class="text-left">${feed.candidateStatus}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
			    <nav>
			        <ul class="pagination justify-content-center" id="pagination"></ul>
			    </nav>
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
        
        //PAGINATION
    	$(document).ready(function () {
            var rowsPerPage = 9;
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
