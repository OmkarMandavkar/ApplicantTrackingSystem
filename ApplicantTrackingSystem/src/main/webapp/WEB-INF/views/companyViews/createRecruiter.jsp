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
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />

    <style>
        .wrapper {
            height: 95vh; /* Full viewport height */
        }

        form {
            width: 40%;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
  </head>
  <body>
    <div class="wrapper d-flex justify-content-center align-items-center">
      <form action="/company/addRecruiter" method="post" class="bg-white p-4 shadow rounded">
        <h2 class="text-center mb-3">Add Recruiter</h2>

        <div class="form-group">
          <input type="text" name="recruiterFullname" class="form-control" placeholder="Enter Full Name" required>
        </div>

        <div class="form-group">
          <input type="email" name="recruiterEmail" class="form-control" placeholder="Enter Email" required>
        </div>

        <div class="form-group">
          <input type="text" name="recruiterMobile" class="form-control" placeholder="Enter Mobile" required>
        </div>

		<div class="form-group d-flex">
		    <button type="submit" class="btn btn-primary w-50 mr-2">Register</button>
		    <a href="<%=request.getContextPath()%>/company/manageRecruiter" class="btn btn-secondary w-50">Cancel</a>
		</div>

      </form>
    </div>

    <p class="text-center text-danger">${message}</p>

  </body>
</html>
