<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Statistics</title>
    <link href="/css/sidebar.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">


    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .chart-container {
            width: 80%;
            margin: auto;
        }
    </style>
</head>
<body>


<h3>Candidate Count for Each Job</h3>
<canvas id="jobChart" width="400" height="400"></canvas>

<canvas id="jobPieChart" width="400" height="400"></canvas>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        let jobData = [];
        <c:forEach var="job" items="${jobCounts}">
            jobData.push({ title: "${job.title}", candidateCount: ${job.candidateCount} });
        </c:forEach>;

        if (jobData.length === 0) {
            document.write("<h3>No data available for this company.</h3>");
            return;
        }

        let jobTitles = jobData.map(item => item.title);
        let candidateCounts = jobData.map(item => item.candidateCount);

        let ctx = document.getElementById("jobChart").getContext("2d");
        new Chart(ctx, {
            type: "bar",
            data: {
                labels: jobTitles,
                datasets: [{
                    label: "Number of Candidates",
                    data: candidateCounts,
                    backgroundColor: "rgba(54, 162, 235, 0.6)"
                }]
            },
            options: {
                responsive: false,  // Disable responsiveness to keep fixed size
                maintainAspectRatio: false, // Prevent aspect ratio enforcement
                scales: {
                    x: { title: { display: true, text: "Job Titles" } },
                    y: { beginAtZero: true, title: { display: true, text: "Candidate Count" } }
                }
            }
        });
    });
    
    
    
    
    
    
    
    document.addEventListener("DOMContentLoaded", function () {
        let jobData = [];
        let totalCandidates = 0;

        <c:forEach var="job" items="${jobCounts}">
            jobData.push({ jobTitle: "${job.title}", candidateCount: ${job.candidateCount} });
            totalCandidates += ${job.candidateCount};
        </c:forEach>;

        if (jobData.length === 0) {
            document.write("<h3>No data available for this company.</h3>");
            return;
        }

        let jobTitles = jobData.map(item => item.jobTitle);
        let candidateCounts = jobData.map(item => item.candidateCount);

        let ctx = document.getElementById("jobPieChart").getContext("2d");
        new Chart(ctx, {
            type: "pie",
            data: {
                labels: jobTitles,
                datasets: [{
                    label: "Candidates ",
                    data: candidateCounts,
                    backgroundColor: [
                        "#ff6384", "#36a2eb", "#ffce56", "#4bc0c0", "#9966ff", "#ff9f40", "#8d6e63"
                    ]
                }]
            },
            options: {
                responsive: false,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: "Total Candidates: " + totalCandidates
                    }
                }
            }
        });
    });
    
    
</script>



</body>
</html>
