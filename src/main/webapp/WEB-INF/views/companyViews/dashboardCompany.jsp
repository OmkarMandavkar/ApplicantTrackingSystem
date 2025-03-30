<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Company Dashboard</title>
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
		    padding: 6px;
		    border-radius: 10px; /* Slightly curved corners */
		    background: rgba(255, 255, 255, 0.2);
		    display: flex;
		    align-items: center;
		    justify-content: center;
		}
		
		.card-icon i {
		    font-size: 25px;
		    color: #287e71 !important; /* Ensuring color is applied */
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
	    <div class="row">
	        <div class="col-md-3">
	            <div class="dashboard-card">
	                <div >
	                    <h6>Total Job Postings</h6>
	                    <p class="fs-3 fw-bold">${totalJobCount}</p>
	                </div>
	                <div class="card-icon"><i class="fas fa-briefcase"></i></div>
	            </div>
	        </div>
	        <div class="col-md-3">
	            <div class="dashboard-card">
	                <div>
	                    <h6>Active Job Postings</h6>
	                    <p class="fs-3 fw-bold">${activeJobCount}</p>
	                </div>
	                <div class="card-icon"><i class="fas fa-check-circle"></i></div>
	            </div>
	        </div>
	        <div class="col-md-3">
	            <div class="dashboard-card purple">
	                <div>
	                    <h6>Interview Conducted</h6>
	                    <p class="fs-3 fw-bold">${interviewCount}</p>
	                </div>
	                <div class="card-icon"><i class="fas fa-calendar-alt"></i></div>
	            </div>
	        </div>
	        <div class="col-md-3">
	            <div class="dashboard-card blue">
	                <div>
	                    <h6>Total Applications</h6>
	                    <p class="fs-3 fw-bold">${allApplicationCount}</p>
	                </div>
	                <div class="card-icon"><i class="fas fa-paper-plane"></i></div>
	            </div>
	        </div>
	    </div>
	
	    <div class="row mt-3">
	        <div class="col-md-3">
	            <div class="dashboard-card green">
	                <div>
	                    <h6>Selected Count</h6>
	                    <p class="fs-3 fw-bold">${selectedCount}</p>
	                </div>
	                <div class="card-icon"><i class="fas fa-user-check"></i></div>
	            </div>
	        </div>
	
	        <div class="col-md-3">
	            <div class="dashboard-card yellow">
	                <div>
	                    <h6>Rejected Count</h6>
	                    <p class="fs-3 fw-bold">${rejectedCount}</p>
	                </div>
	                <div class="card-icon"><i class="fas fa-user-times"></i></div>
	            </div>
	        </div>
	        <div class="col-md-3">
	            <div class="dashboard-card dark">
	                <div>
	                    <h6>In-Progress Count</h6>
	                    <p class="fs-3 fw-bold">${inProgressCount}</p>
	                </div>
	                <div class="card-icon"><i class="fas fa-spinner"></i></div>
	            </div>
	        </div>
	        <div class="col-md-3">
	            <div class="dashboard-card red">
	                <div>
	                    <h6>Hired Count</h6>
	                    <p class="fs-3 fw-bold">${hiredCount}</p>
	                </div>
	                <div class="card-icon"><i class="fas fa-user-tie"></i></div>
	            </div>
	        </div>        
	    </div>

        <table class="table table-bordered table-hover mt-4" id="jobTable">
          <thead class="thead text-center">
            <tr>
              <th style="width: 22%">Job Title</th>
              <th style="width: 18%">Domain</th>
              <th style="width: 3%">Vacancy</th>
              <th style="width: 3%">Applications</th>
              <th style="width: 4%">Status</th>
              <th style="width: 3%">Action</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="feed" items="${jobsDataList}">
              <tr>
                <td style="text-align: left !important;">${feed.title}</td>
                <td style="text-align: left !important;">${feed.domain}</td>
                <td>${feed.vacancy}</td>
                <td>${jobCounts[feed.jobId] != null ? jobCounts[feed.jobId] : 0}</td>                           
                <td>
                  <span class="badge badge-${feed.status == 'ACTIVE' ? 'success' : 'warning'}" >
                    ${feed.status}
                  </span>
                </td>
                <td>                                
                	<div class="dropdown">
                    	<button class="btn btn-light dropdown-toggle" type="button" data-toggle="dropdown">
                        	<i class="fas fa-cog"></i>
                       	</button>
                        <div class="dropdown-menu">
                        	<a class="dropdown-item" href="<%=request.getContextPath()%>/company/viewJobDetail?jobId=${feed.jobId}"><i class="fa-solid fa-circle-info"></i> View Job Details </a>
						    <a class="dropdown-item" href="<%=request.getContextPath()%>/company/updateJobByJobId?jobId=${feed.jobId}"><i class="fas fa-edit"></i> Edit Job Details  </a>
						    <a class="dropdown-item" href="<%=request.getContextPath()%>/company/sendOfferLetter?applicationId=${data.jobMaster.applicationId}"> <i class="fas fa-trash-alt"></i> Remove Job </a>
						    <a class="dropdown-item" href="<%=request.getContextPath()%>/company/repostJobByJobId?jobId=${feed.jobId}">  <i class="fas fa-redo"></i> Repost Job </a>
                        </div>
	                </div>
                </td> 
              </tr>
            </c:forEach>
          </tbody>
        </table>
         <nav>
		 	<ul class="pagination justify-content-start" id="pagination"></ul>
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
        
        //PAGINATION
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
