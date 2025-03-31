# 🚀 Applicant Tracking System

## 🌟 Overview

The **Applicant Tracking System (ATS)** is a web-based application built using **Spring MVC, JSP, and Spring Data JPA** to streamline the recruitment process. It enables companies to manage job postings, recruiters, candidates, and interview processes efficiently. The system provides different functionalities based on user roles: **Company Admin, Candidate, Recruiter, and Super Admin**.

---

## 🎯 Features

### 🔹 1. Candidate Registration & Company Admin Registration

✅ Users can register as a **Candidate** or **Company Admin**.  
✅ Secure authentication and role-based access control.

---

### 🏢 2. Company Admin Functionalities

📊 **Dashboard**: Displays job statistics, active jobs, interview status, and application counts (selected/rejected/in-progress/hired).  
📝 **Profile Management**: View and edit admin details.  
🛠 **Job Management**: Create and manage job postings (ACTIVE/INACTIVE status).  
👥 **Recruiter Management**: Register recruiters using email and mobile, generate temporary credentials, and send via email.  
🎯 **Candidate Management**: Track job applications and monitor candidate progress.  
📈 **Hiring Analytics**: View hiring trends and recruiter performance.

---

### 👩‍💼 3. Candidate Functionalities

📌 **Dashboard**:  
   - 📊 Displays application statistics (applied/rejected/selected/in-progress).  
   - 📉 Graph representing application trends over the last six months.  
   - 📅 Table showing upcoming interviews with schedule and round details.
👤 **Profile Management**: View and edit personal details, add professional background (projects, education, work experience).  
📃 **Job Listings**: View all active jobs.  
📌 **Application Tracking**: Monitor applied job statuses.  
⭐ **Saved Jobs**: View saved jobs with quick access to job details.  
📜 **Job Logs**: Track application history.  
📂 **Document Upload**: Upload necessary documents for job applications.  
📢 **Job Alerts**: Receive notifications for relevant job openings.

---

### 🤝 4. Recruiter Functionalities

👤 **Profile Management**: View and edit recruiter details.  
📅 **Interview Management**:  
   - 🔍 View candidate and job details.  
   - ✉️ Schedule interviews via email.  
   - ✅ Evaluate candidates and update interview status.  
📋 **Interview Logs**: View history of conducted interviews.
📜 **Onboarding Process**:  
   - 📩 Request candidate documents via email.  
   - 📄 Send offer and joining letters.  
   - 🔄 Update hiring status (on-hold/rejected).

---

### 👑 5. Super Admin Functionalities

🛠 **Profile Management**: View and edit super admin details.  
📊 **Global Analytics** (Upcoming Feature):  
   - 📈 View job application trends.  
   - 🎤 Monitor interviews conducted.  
   - 📉 Track hiring trends.  
   - ⭐ Evaluate recruiter performance.

---

## 🛠 Technologies Used

- **Backend**: Spring MVC, Spring Data JPA
- **Frontend**: JSP, CSS, JavaScript  
- **Database**: MySQL  
- **Email Notifications**: JavaMail API  
- **Version Control**: Git, GitHub  

---

## ⚙️ Installation & Setup

1. **Clone the repository**:
   ```sh
   git clone https://github.com/OmkarMandavkar/ApplicantTrackingSystem.git
   ```
2. **Import the project** into Spring Tool Suite (STS).
3. **Configure the database**:
   - 🛢 Create a MySQL database.  
   - ⚙️ Update database credentials in `application.properties`.
   - 📩 Configure and update the Email configuration in `application.properties`.
4. **Run the application**:
   ```sh
   mvn spring-boot:run
   ```
5. **Access the application** via:
   ```sh
   http://localhost:8080
   ```

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

### 📬 Contact

For any queries or contributions, feel free to reach out:

- **GitHub**: OmkarMandavkar (https://github.com/OmkarMandavkar)  
- **Email**: omkarmandavkar000@gmail.com 📩

