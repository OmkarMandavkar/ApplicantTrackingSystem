<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Candidate Details</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="/css/sidebar.css" rel="stylesheet" />
    <style>
        body {
            background-color: #edebe9;
        }
        .card {
            border-radius: 10px;
            transition: transform 0.3s;
        }
        .card-body-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 10px;
        }
        .fas {
            margin-right: 5px;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <div class="sidebar">
            <div class="d-flex justify-content-center">
                <img src="${sessionScope.candidateProfile}" alt="Candidate Logo" class="rounded-circle" style="width: 50px; height: 50px" />
                <h4><strong>${sessionScope.candidateName}</strong></h4>
            </div>
            
		    <hr style="border:1px solid #D3D3D3; width:75%" />
            <nav class="nav flex-column mt-4">
                <a href="<%=request.getContextPath()%>/dashboard/candidate" class="nav-link"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a href="<%=request.getContextPath()%>/candidate/viewCandidateDetails" class="nav-link active"><i class="fas fa-user-edit"></i> View/Edit Profile</a>
                <a href="<%=request.getContextPath()%>/candidate/viewAllActiveJobs" class="nav-link"><i class="fas fa-briefcase"></i> View All Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/appliedJobs" class="nav-link"><i class="fas fa-check-circle"></i> Applied Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/savedJobs" class="nav-link"><i class="fas fa-bookmark"></i> Saved Jobs</a>
                <a href="<%=request.getContextPath()%>/candidate/jobLogs" class="nav-link"><i class="fas fa-history"></i> Job Logs</a>
                <a href="<%=request.getContextPath()%>/candidate/uploadDocuments" class="nav-link"><i class="fas fa-file-upload"></i> Upload Documents</a>
                <a href="<%=request.getContextPath()%>/candidate/jobAlerts" class="nav-link"><i class="fas fa-bell"></i> Job Alerts</a>
            </nav>

            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">
                Logout
            </button>
        </div>
        <div class="main-content">
            <div class="container mt-2">
                <form action="${pageContext.request.contextPath}/candidate/viewEditCandidateDetails" method="post">    
            
                <div class="card p-2">
                    <div class="card-header bg-white border-bottom d-flex flex-row justify-content-between align-items-center">
                        <h2 style="color: #287e71">Candidate Profile</h2>
                    </div>
    
                    <div class="p-3 mb-3">
                        <div class="card-head d-flex justify-content-between align-items-center">
                            <h5>Personal Information</h5>
                        </div>
                        <hr />
                        <div class="card-body-grid">
            	          	<div>
                                <p class="font-weight-bold">First Name</p>
								<input type="text" class="form-control" name="firstName" value="${userData.firstName}">
                            </div>
                            <div>
                                <p class="font-weight-bold">Last Name</p>
								<input type="text" class="form-control" name="lastName" value="${userData.lastName}">
                            </div>
                            <div>
                                <p class="font-weight-bold">Date of Birth</p>
								<input type="text" class="form-control" name="dob" value="${userData.dob}">
                            </div>
                            <div>
                                <p class="font-weight-bold">Email</p>
								<input type="text" class="form-control" name="email" value="${userData.email}" readonly>
                            </div>
                            <div>
                                <p class="font-weight-bold">Mobile</p>
								<input type="text" class="form-control" name="mobile" value="${userData.mobile}">
                            </div>
                            <div>
                                <p class="font-weight-bold">Role</p>
                                <input type="text" class="form-control" name="role" value="${userData.role}" readonly>
                            </div>
                        </div>
                    </div>

                    <div class="p-3 mb-3">
                        <div class="card-head d-flex justify-content-between align-items-center">
                            <h5>Address Information</h5>
                        </div>
                        <hr />
                        <div class="card-body-grid">
                            <div>
                                <p class="font-weight-bold">Country</p>
                                <input type="text" class="form-control" name="country" value="${userData.country}">
                            </div>
                            <div>
                                <p class="font-weight-bold">City</p>
                                <input type="text" class="form-control" name="city" value="${userData.city}">                                
                            </div>
                            <div>
                                <p class="font-weight-bold">Pincode</p>
                                <input type="text" class="form-control" name="pincode" value="${userData.pincode}">
                            </div>
                        </div>
                    </div>
                    
					<div class="p-3 mb-3">
					    <div class="card-head d-flex justify-content-between align-items-center">
					        <h5>Educational Information</h5>
					    </div>
					    <hr />
					    <div class="card-body-grid" style="grid-template-columns: repeat(2, 1fr);">
					        <div>
					            <p class="font-weight-bold">Qualification</p>
                                <input type="text" class="form-control" name="qualification" value="${candidateData.qualification}">
					        </div>
					        <div>
					            <p class="font-weight-bold">Skills</p>
                                <input type="text" class="form-control" name="skills" value="${candidateData.skills}">
					        </div>
						    <div>
						        <p class="font-weight-bold">Project 1</p>
						        <input type="text" class="form-control" name="project1" value="${candidateData.project1}">
						    </div>
						    <div>
						        <p class="font-weight-bold">Project 2</p>
						        <input type="text" class="form-control" name="project2" value="${candidateData.project2}">
						    </div>					    
							<div>
							    <p class="font-weight-bold">Project 3</p>
							    <input type="text" class="form-control" name="project3" value="${candidateData.project3}">
							</div>
							<div>
							    <p class="font-weight-bold">Project 4</p>
							    <input type="text" class="form-control" name="project4" value="${candidateData.project4}">
							</div>
					    </div>
					</div>                    

					<div class="p-3 mb-3">
					    <div class="card-head d-flex justify-content-between align-items-center">
					        <h5>Professional Information</h5>
					    </div>
					    <hr />
					    <div class="card-body-grid" style="grid-template-columns: repeat(2, 1fr);">
					        <div>
					            <p class="font-weight-bold">Current Designation</p>
                                <input type="text" class="form-control" name="currentDesignation" value="${candidateData.currentDesignation}">
					        </div>
					        <div>
					            <p class="font-weight-bold">Salary Expectations</p>
                                <input type="text" class="form-control" name="salaryExpectations" value="${candidateData.salaryExpectations}">
					        </div>
					    </div>
					    
					    <div class="card-body-grid mt-2" style="grid-template-columns: repeat(1, 1fr);">
						    <div>
						        <p class="font-weight-bold">Work Experience</p>
                                <input type="text" class="form-control" name="workExperience" value="${candidateData.workExperience}">
						    </div>
						</div>
					</div>    
			        <button type="submit" class="btn btn-primary btn-block mb-2" style="width: 30%; margin: 0px auto">Update</button>
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
    	//SIDEBAR
        $(document).ready(function () {
            var currentUrl = window.location.pathname;
            $('.sidebar a').each(function () {
                if (this.href.indexOf(currentUrl) !== -1) {
                    $(this).addClass('active');
                }
            });
        });
    </script>
    
</body>
</html>
