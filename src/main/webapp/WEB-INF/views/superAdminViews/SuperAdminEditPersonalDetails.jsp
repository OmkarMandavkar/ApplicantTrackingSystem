<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>
<link
  href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
  rel="stylesheet"
/>
</head>
<body>
    <div class="container main-div" style="width: 30%; margin-top: 50px; padding: 30px; border: 1px solid #ddd; background-color: #f9f9f9; border-radius: 10px; " >
      
    <form action="${pageContext.request.contextPath}/superAdmin/updateCompanyAdminData" method="post">
        <div class="form-group">
          <label for="firstname">First Name</label>
          <input type="text" class="form-control" id="firstname" name="firstname" value="${superAdminData.firstname}">
        </div>
        <div class="form-group">
          <label for="lastname">Last Name</label>
          <input type="text" class="form-control" id="lastname" name="lastname" value="${superAdminData.lastname}">
        </div>
        <div class="form-group">
          <label for="lastname">Date of Birth</label>
          <input type="date" class="form-control" id="lastname" name="dob" value="${superAdminData.dob}">
        </div>
        <div class="form-group">
          <label for="mobile">Mobile</label>
          <input type="text" class="form-control" id="mobile" name="mobile" value="${superAdminData.mobile}">
        </div>
        <div class="form-group">
          <label for="country">Country</label>
          <input type="text" class="form-control" name="country" value="${superAdminData.country}" />
        </div>
        <div class="form-group">
          <label for="city">City</label>
          <input type="text" class="form-control" name="city" value="${superAdminData.city}" />
        </div>
        <div class="form-group">
          <label for="pincode">Pincode</label>
          <input type="text" class="form-control" name="pincode" value="${superAdminData.pincode}" />
        </div>
        
        <button type="submit" class="btn btn-primary btn-block">Update</button>
    </form>
    </div>
</body>
</html>
