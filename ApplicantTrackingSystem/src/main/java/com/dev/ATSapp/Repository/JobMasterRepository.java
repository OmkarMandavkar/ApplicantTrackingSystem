package com.dev.ATSapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.ATSapp.Entity.JobMaster;

public interface JobMasterRepository extends JpaRepository<JobMaster, Integer> {

    List<JobMaster> findByRecruiterEmail(String recruiterEmail);

}
