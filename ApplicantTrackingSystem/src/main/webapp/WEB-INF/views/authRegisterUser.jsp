<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .main-div {
            width: 35%;
            margin-top: 50px;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        #visible {
            display: none;
            color: red;
        }
    </style>
</head>

<body>
    <div class="container main-div">
        <h2 class="text-center mb-4">Register</h2>
        
        <!-- Radio Buttons to Switch Pages -->
        <div class="form-group text-center">
            <label>
            	<input type="radio" name="registerType" value="user" checked> Register as User</label>
            <label class="ml-3">
            	<input type="radio" name="registerType" value="company"> Register as Company</label>
        </div>

        <form action="/auth/register/user" method="post">
            <div class="form-group">
                <input type="text" name="fullname" class="form-control" placeholder="Enter Fullname" required>
            </div>

            <div class="form-group">
                <input type="email" name="email" class="form-control" placeholder="Enter Email" required>
            </div>

            <div class="form-group">
                <input type="text" name="mobile" class="form-control" placeholder="Enter mobile" required>
            </div>

            <div class="form-group">
                <input type="date" name="dob" class="form-control">
            </div>

            <div class="form-group">
                <input type="password" name="password" id="pwd" class="form-control" placeholder="Enter Password" required>
            </div>

            <div class="form-group">
                <input type="password" name="confirmpassword" id="cnfpwd" class="form-control" placeholder="Confirm Password" required>
            </div>

            <p id="visible">Passwords do not match</p>

            <div class="form-group">
                <select name="role" class="form-control" required>
                    <option value="" selected disabled>Select Role</option>
                    <option value="CANDIDATE">Candidate</option>
                    <option value="SUPER_ADMIN">Super-Admin</option>
                    <option value="RECRUITER">Recruiter</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary btn-block">Register</button>
        </form>
        <p class="text-center mt-3">Already Registered? <a href="/auth/login">Login here</a></p>
        
         <p class="text-center text-danger">${message}</p>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function() {
            const password = document.querySelector("#pwd");
            const cnfpassword = document.querySelector("#cnfpwd");
            const visibleMessage = document.querySelector("#visible");
            const registerTypeRadios = document.querySelectorAll("input[name='registerType']");

            // Password matching validation
            cnfpassword.addEventListener("input", function() {
                if (password.value.trim() !== cnfpassword.value.trim()) {
                    visibleMessage.style.display = "block";
                } else {
                    visibleMessage.style.display = "none";
                }
            });

            // Page switching based on radio button
            registerTypeRadios.forEach(radio => {
                radio.addEventListener("change", function() {
                    if (this.value === "company") {
                        window.location.href = "/auth/register/company";
                    }
                });
            });
        });
    </script>
</body>
</html>
