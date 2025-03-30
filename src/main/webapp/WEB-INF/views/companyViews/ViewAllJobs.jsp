<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.dev.ATSapp.Entity.Company"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Company Dashboard</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

<style>
    .card {
        border-radius: 10px;
        transition: transform 0.3s;
        height: 100%;
        display: flex;
        flex-direction: column;
    }

    .card-body {
        flex-grow: 1;
    }

    .card-footer {
        text-align: center;
        padding: 10px;
        background-color: #fff;
        border-top: none;
    }

    .job-card {
        margin-bottom: 20px;
    }

    .btn-secondary {
        background-color: #3498db;
        border-color: #3498db;
    }

    .btn-secondary:hover {
        background-color: #2980b9;
        border-color: #2980b9;
    }

    .status-filter-container {
        margin-bottom: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 10px;
    }

    .status-filter-container label {
        font-weight: bold;
    }

    .status-filter-container select, .status-filter-container input {
        width: 200px;
        padding: 12px;
        padding-bottom: 2px;
        border-radius: 5px;
        border: 1px solid #ccc;
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

        <c:if test="${empty jobList}">
            <p class="text-center">No Job data available</p>
        </c:if>

<div class="status-filter-container d-flex flex-wrap justify-content-between align-items-center p-3 rounded">
    
    <div class="d-flex align-items-center ">
        <label for="statusFilter" class="mb-0 mr-2 font-weight-bold text-secondary">Filter by Status:</label>
        <select id="statusFilter" class="form-control custom-select w-auto p-2">
            <option value="ALL">All</option>
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
        </select>
    </div>

    <div class="d-flex align-items-center">
        <label for="searchInput" class="mb-0 mr-2 font-weight-bold text-secondary">Search:</label>
        <input type="text" id="searchInput" class="form-control w-auto p-2" placeholder="Search jobs..." />
    </div>

</div>

        <table class="table table-bordered table-hover" id="jobTable">
            <thead class="thead">
                <tr class="text-center">
                    <th style="width: 18%">Job Title</th>
                    <th style="width: 18%">Domain</th>
                    <th style="width: 6%">Date Posted</th>
                    <th style="width: 6%">Closing Date</th>
                    <th style="width: 6%">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="job" items="${jobList}">
                    <tr>
                        <td style="text-align: left !important;">${job.title}</td>
                        <td style="text-align: left !important;">${job.domain}</td>
                        <td>${job.datePosted}</td>
                        <td>${job.expiryDate}</td>
                        <td><a href="<%=request.getContextPath()%>/company/viewJobDetailByJobId?jobId=${job.jobId}" class="btn btn-secondary">View Details</a></td>
                        <td style="display:none" data-status="${job.status}">${job.status}</td>
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

        $('#confirmLogoutModal').on('show.bs.modal', function (event) {
            var modal = $(this);
            var logoutUrl = "<%=request.getContextPath()%>/auth/logout";
            modal.find('#confirmLogoutBtn').attr("href", logoutUrl);
        });

        $("#statusFilter, #searchInput").on("input", function () {
            var searchText = $("#searchInput").val().toLowerCase();
            var selectedStatus = $("#statusFilter").val();
            var hasVisibleRows = false;

            $("#jobTable tbody tr").each(function () {
                var jobTitle = $(this).find("td:eq(0)").text().toLowerCase();
                var jobDomain = $(this).find("td:eq(1)").text().toLowerCase();
                var jobDatePosted = $(this).find("td:eq(2)").text().toLowerCase();
                var jobClosingDate = $(this).find("td:eq(3)").text().toLowerCase();
                var jobStatus = $(this).find("td[data-status]").data("status");

                var matchesSearch = jobTitle.includes(searchText) || jobDomain.includes(searchText) || jobDatePosted.includes(searchText) || jobClosingDate.includes(searchText);
                var matchesStatus = (selectedStatus === "ALL" || jobStatus === selectedStatus);

                if (matchesSearch && matchesStatus) {
                    $(this).show();
                    hasVisibleRows = true;
                } else {
                    $(this).hide();
                }
            });

            $("#noDataRow").remove();
            if (!hasVisibleRows) {
                $("#jobTable tbody").append(
                    `<tr id="noDataRow">
                        <td colspan="5" class="text-center">No Job data available</td>
                    </tr>`
                );
            }
        });

        var rowsPerPage = 6;
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
