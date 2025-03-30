<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            overflow-x: hidden; /* Prevent horizontal scrolling */
            background-color: #fafcfe;
        }

        .sidebar {
            width: 250px;
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            background-color: white;
            padding-top: 20px;
            display: flex;
            flex-direction: column;
        }

        .sidebar a {
            color: #36454F;
            display: block;
            padding: 10px 15px;
            text-decoration: none;
            transition: 0.3s;
        }

        .sidebar a:hover, 
        .sidebar a.active {
			color: white;
            background-color: #287e71;
        }

        .logout-btn {
            margin-top: auto; 
            margin-bottom: 20px; /* Raises button above the bottom */
            width: 90%;
            align-self: center;
        }

        .main-content {
            margin-left: 250px;
            padding: 20px;
            width: calc(100% - 250px);
        }
		td {
		    max-width: 150px; 
		    overflow-x: auto;
		    white-space: nowrap;
		    scrollbar-width: none;
		}
		td::-webkit-scrollbar {
		    display: none; 
		}
        
    </style>
</head>
<body>
    <div class="sidebar">
    	<div class="d-flex flex-column align-items-center">
           	<h4 class="text-center">
   				<a href="<%=request.getContextPath()%>/dashboard/superAdmin">${userFullName}</a>
   			</h4>
	        <button class="btn btn-danger btn-sm m-2" style="height: 30px;" data-toggle="modal" data-target="#confirmLogoutModal">Logout</button>
    	</div>
        <hr>
        <a href="<%=request.getContextPath()%>/superAdmin/viewDetails">View/Edit Profile</a>
        <a href="<%=request.getContextPath()%>/superAdmin/manageCompanies">Manage Companies</a>
        <a href="<%=request.getContextPath()%>/superAdmin/manageUsers">Manage Users</a>
        <a href="<%=request.getContextPath()%>/superAdmin/viewCompanyStatistics">View Company Statistics</a>
        <a href="<%=request.getContextPath()%>/superAdmin/viewJobStatistics">View Job Statistics</a>
        <a href="<%=request.getContextPath()%>/superAdmin/viewApplicationStatistics">View Applications Statistics</a>
    </div>

    <div class="main-content container mt-4">
        <div class="d-flex justify-content-around align-items-center mb-3">
            <h1 class="text-center mb-4">Super-Admin Dashboard</h1>
        </div>

		<div class="row text-center mb-4">
		    <div class="col-md-3">
		        <div class="card shadow">
		            <div class="card-body">
		                <h5 class="card-title">Companies</h5> <hr>
		                <p class="card-text display-4">${companyCount}</p>
		            </div>
		        </div>
		    </div>
		    <div class="col-md-3">
		        <div class="card shadow">
		            <div class="card-body">
		                <h5 class="card-title">Active Jobs</h5> <hr>
		                <p class="card-text display-4">${activeJobsCount}</p>
		            </div>
		        </div>
		    </div>
		    <div class="col-md-3">
		        <div class="card shadow">
		            <div class="card-body">
		                <h5 class="card-title">Candidates</h5> <hr>
		                <p class="card-text display-4">${candidateCount}</p>
		            </div>
		        </div>
		    </div>
		    <div class="col-md-3">
		        <div class="card shadow">
		            <div class="card-body">
		                <h5 class="card-title">Placed</h5> <hr>
		                <p class="card-text display-4">${placedCount}</p>
		            </div>
		        </div>
		    </div>
		</div>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Company Name</th>
                    <th>Admin Name</th>
                    <th>Admin Email</th>
                    <th>Admin Mobile</th>
                    <th style="width:8%">Update</th>
                    <th style="width:8%">Delete</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty companyList}">
                    <tr>
                        <td colspan="9" class="text-center">No Company data available</td>
                    </tr>
                </c:if>

                <c:forEach var="feed" items="${companyList}">
                    <tr>
                        <td>${feed.companyName}</td>
                        <td>${feed.companyAdminFirstName} ${feed.companyAdminLastName}</td>
                        <td>${feed.companyAdminEmail}</td>
                        <td>${feed.companyAdminMobile}</td>
                        <td><a href="<%=request.getContextPath()%>/updateFeedByAdmin?fid=${feed.companyId}" class="btn btn-warning btn-sm">Update</a></td>
                        <td>
                            <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#confirmDeleteModal" data-fid="${feed.companyId}">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Modal for Logout Confirmation -->
    <div class="modal fade" id="confirmLogoutModal" tabindex="-1" role="dialog" aria-labelledby="confirmLogoutModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmLogoutModalLabel">Confirm Logout</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you sure you want to logout?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <a id="confirmLogoutBtn" href="<%=request.getContextPath()%>/auth/logout" class="btn btn-danger">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for Feedback Deletion Confirmation -->
    <div class="modal fade" id="confirmDeleteModal" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmDeleteModalLabel">Confirm Deletion</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete this feedback?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <a id="confirmDeleteBtn" href="#" class="btn btn-danger">Delete</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- Custom Script for handling modals -->
    <script>
        // Handle the Feedback Deletion Modal
        $('#confirmDeleteModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);  // Button that triggered the modal
            var fid = button.data('fid');  // Extract the feedback ID from the data-* attributes
            var deleteUrl = "<%=request.getContextPath()%>/deleteFeedByAdmin?fid=" + fid;
            var modal = $(this);
            modal.find('#confirmDeleteBtn').attr("href", deleteUrl);  // Set the delete link with the feedback ID
        });

        // Handle the Logout Confirmation Modal
        $('#confirmLogoutModal').on('show.bs.modal', function (event) {
            var modal = $(this);
            var logoutUrl = "<%=request.getContextPath()%>/auth/logout";
            modal.find('#confirmLogoutBtn').attr("href", logoutUrl);  // Set the logout link
        });
    </script>
</body>
</html>
