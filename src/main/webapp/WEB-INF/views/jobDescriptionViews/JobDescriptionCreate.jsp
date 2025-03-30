<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Job Description</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        .main-div {
            width: 100%;
            margin: 5px auto;
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        textarea{
        	resize: none;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <div class="sidebar">
            <div class="d-flex justify-content-center">
                <img src="${companyLogo}" alt="Company Logo" class="rounded-circle" style="width: 50px; height: 50px"/>
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
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">Logout</button>
        </div>

        <div class="main-content">
            <div class="container main-div bg-white">
                <form action="/company/createJobDescription" method="post">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <input type="text" name="title" class="form-control" placeholder="Title" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <input type="text" name="domain" class="form-control" placeholder="Domain" required>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-4 mb-3">
                            <select name="employementType" class="form-control" required>
                                <option value="" selected disabled>Employment Type</option>
                                <option value="fulltime">Full-time</option>
                                <option value="parttime">Part-Time</option>
                                <option value="contract">Contract</option>
                                <option value="internship">Internship</option>
                            </select>
                        </div>
                        <div class="col-md-4 mb-3">
                            <select name="jobType" class="form-control" required>
                                <option value="" selected disabled>Job Type</option>
                                <option value="onsite">Onsite</option>
                                <option value="remote">Remote</option>
                                <option value="hybrid">Hybrid</option>
                            </select>
                        </div>
						<div class="col-md-4 mb-3">
						    <select name="department" class="form-control" required>
						        <option value="" selected disabled>Select Department</option>
						        <option value="HUMAN_RESOURCES">Human Resources</option>
						        <option value="FINANCE_AND_ACCOUNTING">Finance and Accounting</option>
						        <option value="SALES_AND_BUSINESS_DEVELOPMENT">Sales and Business Development</option>
						        <option value="MARKETING_ADVERTISING">Marketing & Advertising</option>
						        <option value="CUSTOMER_SUPPORT_AND_SERVICE">Customer Support & Service</option>
						        <option value="INFORMATION_TECHNOLOGY">Information Technology</option>
						        <option value="ENGINEERING_AND_PRODUCT_DEVELOPMENT">Engineering & Product Development</option>
						        <option value="OPERATIONS_AND_LOGISTICS">Operations & Logistics</option>
						        <option value="RESEARCH_AND_DEVELOPMENT">Research & Development</option>
						        <option value="LEGAL_AND_COMPLIANCE">Legal & Compliance</option>
						        <option value="PROCUREMENT_AND_SUPPLY_CHAIN">Procurement & Supply Chain</option>
						        <option value="QUALITY_ASSURANCE">Quality Assurance</option>
						        <option value="PUBLIC_RELATIONS_AND_COMMUNICATIONS">Public Relations & Communications</option>
						        <option value="EXECUTIVE_MANAGEMENT">Executive Management</option>
						        <option value="STRATEGY_AND_PLANNING">Strategy & Planning</option>
						    </select>
						</div>

                    </div>

                    <div class="row">
                        <div class="col-md-4 mb-3">
                            <input type="text" name="qualification" class="form-control" placeholder="Qualification" required>
                        </div>
                        <div class="col-md-4 mb-3">
                            <input type="text" name="experience" class="form-control" placeholder="Experience (e.g. 1+ years)" required>
                        </div>
                        <div class="col-md-4 mb-3">
                            <input type="text" name="salary" class="form-control" placeholder="Salary in LPA" required>
                        </div>
                    </div>

					<div class="row">
					    <div class="col-md-12 mb-3">
					        <label>Interview Rounds:</label>
					        <div class="row">
					            <div class="col-md-4">
					                <input type="checkbox" name="interviewRounds" value="APTITUDE"> Aptitude <br>
					                <input type="checkbox" name="interviewRounds" value="TECHNICAL_ROUND1"> Technical Round 1 <br>
					                <input type="checkbox" name="interviewRounds" value="TECHNICAL_ROUND2"> Technical Round 2 <br>
					                <input type="checkbox" name="interviewRounds" value="TECHNICAL_ROUND3"> Technical Round 3 <br>
					                <input type="checkbox" name="interviewRounds" value="TECHNICAL_ROUND4"> Technical Round 4 <br>
					                <input type="checkbox" name="interviewRounds" value="TECHNICAL_ROUND5"> Technical Round 5 <br>
					            </div>
					            <div class="col-md-4">
					                <input type="checkbox" name="interviewRounds" value="GROUP_DISCUSSION"> Group Discussion <br>
					                <input type="checkbox" name="interviewRounds" value="SYSTEM_DESIGN_ROUND"> System Design Round <br>
					                <input type="checkbox" name="interviewRounds" value="CODING_CHALLENGE"> Coding Challenge <br>
					                <input type="checkbox" name="interviewRounds" value="PAIR_PROGRAMMING"> Pair Programming <br>
					                <input type="checkbox" name="interviewRounds" value="MANAGERIAL_ROUND1"> Managerial Round 1 <br>
					                <input type="checkbox" name="interviewRounds" value="MANAGERIAL_ROUND2"> Managerial Round 2 <br>
					            </div>
					            <div class="col-md-4">
					                <input type="checkbox" name="interviewRounds" value="LEADERSHIP_DISCUSSION"> Leadership Discussion <br>
					                <input type="checkbox" name="interviewRounds" value="HR1"> HR 1 <br>
					                <input type="checkbox" name="interviewRounds" value="HR2"> HR 2 <br>
					                <input type="checkbox" name="interviewRounds" value="FINAL_DISCUSSION"> Final Discussion <br>
					            </div>
					        </div>
					    </div>
					</div>
                    
                    <div class="form-group">
                        <textarea name="aboutJob" class="form-control" placeholder="About Job" rows="4" required></textarea>
                    </div>
                    <div class="form-group">
                        <textarea name="roleAndResp" class="form-control" placeholder="Roles and Responsibilities" rows="4" required></textarea>
                    </div>
                    <div class="form-group">
                        <textarea name="skillsRequired" class="form-control" placeholder="Skills Required" rows="2" required></textarea>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <input type="text" name="jobLocation" class="form-control" placeholder="Job Location" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <input type="text" name="vacancy" class="form-control" placeholder="Vacancy" required>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <select name="noticePeriod" class="form-control" required>
                                <option value="" selected disabled>Notice Period</option>
                                <option value="Immediate">Immediate</option>
                                <option value="1 Month">1 Month</option>
                                <option value="2 Month">2 Month</option>
                                <option value="3 Month">3 Month</option>
                            </select>
                        </div>
						<div class="col-md-6 mb-3 d-flex align-items-center">
						    <p class="mb-0 mr-2 flex-fill text-nowrap">Closing Date:</p>
						    <input type="date" id="expiryDate" name="expiryDate" class="form-control flex-fill" required>
						</div>
                    </div>

                    <button type="submit" class="btn btn-primary btn-block mt-3" style="margin:0 auto; width: 20%">Submit</button>
                </form>
            </div>
        </div>
    </div>

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
     </script>

</body>
</html>
