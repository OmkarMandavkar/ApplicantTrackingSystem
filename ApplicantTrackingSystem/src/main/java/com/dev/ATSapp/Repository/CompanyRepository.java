package com.dev.ATSapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.ATSapp.Entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Optional<Company> findByCompanyAdminEmail(String companyAdminEmail);

}
