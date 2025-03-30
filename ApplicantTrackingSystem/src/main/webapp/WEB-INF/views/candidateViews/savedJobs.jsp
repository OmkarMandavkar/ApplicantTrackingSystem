<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Candidate Dashboard</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .fas {
            margin-right: 5px;
        }
        #jobTable td {
            vertical-align: middle !important;
        }
    </style>
</head>

<body style="background-color: #edebe9">
    <div class="wrapper">
        <div class="sidebar">
            <div class="d-flex justify-content-center">
                <img src="${sessionScope.candidateProfile}" alt="Candidate Logo" class="rounded-circle" style="width: 50px; height: 50px"/>
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
            <button class="btn btn-danger logout-btn" data-toggle="modal" data-target="#confirmLogoutModal">Logout</button>
        </div>
    
        <div class="main-content">
            <c:if test="${empty SavedJobs}">
                <p class="text-center">No Job data available</p>
            </c:if>
            <div class="row col-md-12 mt-2" id="jobContainer" style="margin: 0 auto;">
                <table class="table table-bordered table-hover" id="jobTable">
                    <thead class="thead">
                        <tr>
                            <th style="width: 18%">Job Title</th>
                            <th style="width: 8%">Company</th>
                            <th style="width: 9%">Job Location</th>
                            <th style="width: 4%">Experience</th>
                            <th style="width: 8%">Apply Link</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="data" items="${SavedJobs}">
                            <tr>
                                <td style="text-align: left !important">${data.jobDescription.title}</td>
                                <td style="text-align: left !important">${data.jobDescription.company.companyName}</td>
                                <td style="text-align: left !important">${data.jobDescription.jobLocation}</td>
                                <td>${data.jobDescription.experience}</td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/candidate/viewJobDetailsByJobId?jobId=${data.jobDescription.jobId}" class="btn btn-primary"> Apply Here </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <nav>
                    <ul class="pagination justify-content-center" id="pagination"></ul>
                </nav>
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
        
        // PAGINATION
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
