<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dev.ATSapp.Entity.Company" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Super Admin Profile</title>

    <link
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <style>
      body {
        background-color: #f8f9fa;
      }
      .card-body-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 10px;
      }
      .card {
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      }
    </style>
  </head>

  <body>
    <div class="container mt-4">
	  <div class="card-head d-flex justify-content-between align-items-center m-2">
        <h2 class="text-start mb-3" style="font-size: 25px"> Super-Admin Profile </h2>
      	<a href="<%=request.getContextPath()%>/superAdmin/updateCompanyAdminData" class="btn btn-warning btn-sm">Update</a>
      </div>

        <div class="card p-3 mb-3">
          <div class="card-head d-flex justify-content-between align-items-center">
            <h5> Personal Information </h5>
          </div>
          <hr />

          <div class="card-body-grid">
            <div>
              <p class="font-weight-bold">First Name</p>
              <p>${superAdminData.getFirstname()}</p>
            </div>
            <div>
              <p class="font-weight-bold">Last Name</p>
              <p>${superAdminData.getLastname()}</p>
            </div>
            <div>
              <p class="font-weight-bold">Date of Birth</p>
              <p>${superAdminData.getDob()}</p>
            </div>
            <div>
              <p class="font-weight-bold">Email</p>
              <p>${superAdminData.getEmail()}</p>
            </div>
            <div>
              <p class="font-weight-bold">Mobile</p>
              <p>${superAdminData.getMobile()}</p>
            </div>
            <div>
              <p class="font-weight-bold">Role</p>
              <p>${superAdminData.getRole()}</p>
            </div>
          </div>
        </div>


        <div class="card p-3 mb-3">
          <div class="card-head d-flex justify-content-between align-items-center">
            <h5> Address Information </h5>
          </div>
          <hr />

          <div class="card-body-grid">
            <div>
              <p class="font-weight-bold">Country</p>
              <p>${superAdminData.getCountry()}</p>
            </div>
            <div>
              <p class="font-weight-bold">City</p>
              <p>${superAdminData.getCity()}</p>
            </div>
            <div>
              <p class="font-weight-bold">Pincode</p>
              <p>${superAdminData.getPincode()}</p>
            </div>
          </div>
        </div>

      <div class="text-center mt-4">
        <a href="<%=request.getContextPath()%>/dashboard/superAdmin" class="btn btn-secondary">Back to Dashboard</a>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  </body>
</html>
