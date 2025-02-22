<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>
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
  <body style="background-color: #edebe9">
    <div class="wrapper">
      <div class="sidebar">
        <div class="d-flex justify-content-center">
          <img src="${candidateImage}" alt="Candidate Logo" class="rounded-circle"
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
        <h1 class="text-center mb-3">Company Dashboard</h1>

        <table class="table table-bordered table-hover">
          <thead class="thead">
            <tr>
              <th>Job Title</th>
              <th>Domain</th>
              <th>Skills Required</th>
              <th>Experience Required</th>
              <th>Job Location</th>
              <th>Vacancy</th>
              <th>Status</th>
              <th style="width: 8%">Update</th>
              <th style="width: 8%">Delete</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="feed" items="${jobsDataList}">
              <tr>
                <td>${feed.title}</td>
                <td>${feed.domain}</td>
                <td>${feed.skillsRequired}</td>
                <td>${feed.experience}</td>
                <td>${feed.jobLocation}</td>
                <td>${feed.vacancy}</td>
                <td>
                  <span class="badge badge-${feed.status == 'ACTIVE' ? 'success' : 'warning'}" >
                    ${feed.status}
                  </span>
                </td>
                <td>
                  <a href="<%=request.getContextPath()%>/company/updateJobByJobId?jobId=${feed.jobId}" class="btn btn-warning btn-sm">Update</a>
                </td>
                <td>
                  <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#confirmDeleteModal" data-fid="${feed.jobId}" >
                    Delete
                  </button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
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
