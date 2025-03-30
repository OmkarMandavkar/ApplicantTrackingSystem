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
                <a href="<%=request.getContextPath()%>/candidate/viewCandidateDetails" class="nav-link"><i class="fas fa-user-edit"></i> View/Edit Profile</a>
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
                <div class="card p-2">
                    <div class="card-header bg-white border-bottom d-flex flex-row justify-content-between align-items-center">
                        <h2 style="color: #287e71">Candidate Profile</h2>
                        <div class="d-flex">
                            <a href="<%=request.getContextPath()%>/candidate/addAdditionalCandidateDetails" class="btn btn-warning btn-sm">Add Professional Background</a>
                            <a href="<%=request.getContextPath()%>/candidate/viewEditCandidateDetails" class="btn btn-warning btn-sm ml-3">Update</a>
                        </div>
                    </div>
    
                    <div class="p-3 mb-3">
                        <div class="card-head d-flex justify-content-between align-items-center">
                            <h5>Personal Information</h5>
                        </div>
                        <hr />
                        <div class="card-body-grid">
                            <div>
                                <p class="font-weight-bold">First Name</p>
                                <p>${userData.firstName}</p>
                            </div>
                            <div>
                                <p class="font-weight-bold">Last Name</p>
                                <p>${userData.lastName}</p>
                            </div>
                            <div>
                                <p class="font-weight-bold">Date of Birth</p>
                                <p>${userData.dob}</p>
                            </div>
                            <div>
                                <p class="font-weight-bold">Email</p>
                                <p>${userData.email}</p>
                            </div>
                            <div>
                                <p class="font-weight-bold">Mobile</p>
                                <p>${userData.mobile}</p>
                            </div>
                            <div>
                                <p class="font-weight-bold">Role</p>
                                <p>${userData.role}</p>
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
                                <p>${userData.country}</p>
                            </div>
                            <div>
                                <p class="font-weight-bold">City</p>
                                <p>${userData.city}</p>
                            </div>
                            <div>
                                <p class="font-weight-bold">Pincode</p>
                                <p>${userData.pincode}</p>
                            </div>
                        </div>
                    </div>
                    
					<c:if test="${not empty candidateData.qualification or not empty candidateData.skills 
					    or not empty candidateData.project1 or not empty candidateData.project2 or not empty candidateData.project3 or not empty candidateData.project4}">
					    <div class="p-3 mb-3">
					        <div class="card-head d-flex justify-content-between align-items-center">
					            <h5>Educational Information</h5>
					        </div>
					        <hr />
					        <div class="card-body-grid" style="grid-template-columns: repeat(2, 1fr);">
					            <c:if test="${not empty candidateData.qualification}">
					                <div>
					                    <p class="font-weight-bold">Qualification</p>
					                    <p>${candidateData.qualification}</p>
					                </div>
					            </c:if>
					            <c:if test="${not empty candidateData.skills}">
					                <div>
					                    <p class="font-weight-bold">Skills</p>
					                    <p>${candidateData.skills}</p>
					                </div>
					            </c:if>
					            <c:if test="${not empty candidateData.project1}">
					                <div>
					                    <p class="font-weight-bold">Project 1</p>
					                    <p>${candidateData.project1}</p>
					                </div>
					            </c:if>
					            <c:if test="${not empty candidateData.project2}">
					                <div>
					                    <p class="font-weight-bold">Project 2</p>
					                    <p>${candidateData.project2}</p>
					                </div>
					            </c:if>
					            <c:if test="${not empty candidateData.project3}">
					                <div>
					                    <p class="font-weight-bold">Project 3</p>
					                    <p>${candidateData.project3}</p>
					                </div>
					            </c:if>
					            <c:if test="${not empty candidateData.project4}">
					                <div>
					                    <p class="font-weight-bold">Project 4</p>
					                    <p>${candidateData.project4}</p>
					                </div>
					            </c:if>
					        </div>
					    </div>
					</c:if>
					
					<c:if test="${not empty candidateData.currentDesignation or not empty candidateData.salaryExpectations 
					    or not empty candidateData.workExperience}">
					    <div class="p-3 mb-3">
					        <div class="card-head d-flex justify-content-between align-items-center">
					            <h5>Professional Information</h5>
					        </div>
					        <hr />
					        <div class="card-body-grid" style="grid-template-columns: repeat(2, 1fr);">
					            <c:if test="${not empty candidateData.currentDesignation}">
					                <div>
					                    <p class="font-weight-bold">Current Designation</p>
					                    <p>${candidateData.currentDesignation}</p>
					                </div>
					            </c:if>
					            <c:if test="${not empty candidateData.salaryExpectations}">
					                <div>
					                    <p class="font-weight-bold">Salary Expectations</p>
					                    <p>${candidateData.salaryExpectations}</p>
					                </div>
					            </c:if>
					        </div>
					        <c:if test="${not empty candidateData.workExperience}">
					            <div>
					                <p class="font-weight-bold">Work Experience</p>
					                <p>${candidateData.workExperience}</p>
					            </div>
					        </c:if>
					    </div>
					</c:if>
					</div>    
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