<%@ page import="java.util.List" %>
<%@ page import="com.dev.ATSapp.Entity.Candidate" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/utility/model.jsp" />

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Candidate Dashboard</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />

    <style>
      table {
        background-color: white;
        margin: 0 auto;
        width: 90%;
        border-collapse: collapse;
      }
    </style>
  </head>
  <body>
    <div class="wrapper">
      <div class="sidebar">
        <div class="d-flex justify-content-center">
          <img src="${candidateProfile}" alt="Candidate Profile" class="rounded-circle"
            style="width: 50px; height: 50px"/>
          <h4><strong>${candidateName}</strong></h4>
        </div>
                
        <hr class="border-light" />
        <nav class="nav flex-column">
            <a href="<%=request.getContextPath()%>/dashboard/candidate" class="nav-link">Dashboard</a>
            <a href="<%=request.getContextPath()%>/candidate/viewCandidateDetails" class="nav-link">View/Edit Profile</a>
            <a href="<%=request.getContextPath()%>/candidate/viewAllActiveJobs" class="nav-link">View All Jobs</a>
            <a href="<%=request.getContextPath()%>/candidate/myJobs" class="nav-link">My Jobs</a>
            <a href="<%=request.getContextPath()%>/candidate/viewStatistics" class="nav-link">View Statistics</a>
            <a href="<%=request.getContextPath()%>/candidate/uploadDocuments" class="nav-link">Upload Documents</a>
            <a href="<%=request.getContextPath()%>/candidate/jobAlerts" class="nav-link">Job Alerts</a>
        </nav>
        <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal" >
         	Logout
        </button>
      </div>

      <div class="main-content">
        <h1 class="text-center mb-3">Candidate Dashboard</h1>

        <p>Applied Count: ${appliedCount}</p>

        <!-- Check if candidateList is empty -->
        <c:choose>
            <c:when test="${empty candidateList}">
                <p class="text-center">No candidates found.</p>
            </c:when>
            <c:otherwise>
                <table class="table table-bordered table-hover">
                  <thead class="thead-dark">
                    <tr>
                      <th>Name</th>
                      <th>Email</th>
                      <th>Phone</th>
                      <th>Company</th>
                      <th>Title</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="candidate" items="${candidateList}">
                      <tr>
                        <td>${candidate.firstname} ${candidate.lastname}</td>
                        <td>${candidate.email}</td>
                        <td>${candidate.phone}</td>
                        <td>${candidate.company.cname}</td>
                        <td>${candidate.jobDescription.title}</td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
            </c:otherwise>
        </c:choose>
      </div>
    </div>

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
