<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Job Description</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="/css/sidebar.css" rel="stylesheet" />
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
      .fas{
      	margin-right: 5px;
      }
    </style>
  </head>
  <body style="background-color: #edebe9">
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
            <div class="container main-div bg-white">
            	<div class="card-head d-flex justify-content-between align-items-center">
					<h5>Add Educational and Professional Details</h5>
			    </div>
			    <hr class="mb-4	"/>            
    
                <form action="/candidate/addAdditionalCandidateDetails" method="post">
                    <div class="row">                  
                    
	                  <div class="col-md-6 mb-3">
                          <input type="text" name="currentDesignation" class="form-control" placeholder="Current Designation" >
                      </div>
                      <div class="col-md-6 mb-3">
                          <input type="text" name="salaryExpectations" class="form-control" placeholder="Salary Expectations (Per Annum)" >
                      </div>
                    </div>
                    
                    <div class="form-group">
                        <textarea name="qualification" class="form-control" placeholder="Qualification" rows="2" ></textarea>
                    </div>

                    <div class="form-group">
                        <textarea name="skills" class="form-control" placeholder="Skills" rows="2" ></textarea>
                    </div>
                    <div class="form-group">
                        <textarea name="workExperience" class="form-control" placeholder="Work Experience" rows="3" ></textarea>
                    </div>

                    <div class="form-group">
                        <textarea name="project1" class="form-control" placeholder="Project 1 (Title & Short Description)" rows="3" ></textarea>
                    </div>
                    <div class="form-group">
                        <textarea name="project2" class="form-control" placeholder="Project 2 (Title & Short Description)" rows="3" ></textarea>
                    </div>
                    <div class="form-group">
                        <textarea name="project3" class="form-control" placeholder="Project 3 (Title & Short Description)" rows="3" ></textarea>
                    </div>
                    <div class="form-group">
                        <textarea name="project4" class="form-control" placeholder="Project 4 (Title & Short Description)" rows="3" ></textarea>
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
