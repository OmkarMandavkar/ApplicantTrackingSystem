package com.dev.ATSapp.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Repository.CompanyRepository;

@Service
public class CompanyService {

	private CompanyRepository companyRepository;

	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public List<Company> getAllCompany() {

		List<Company> companies = companyRepository.findAll();
//		System.out.println("Companies: " + companies);
		return companies;
	}

	public long countAllCompany() {
		return companyRepository.count();
	}

	public Optional<Company> findByEmail(String companyAdminEmail) {

		return companyRepository.findByCompanyAdminEmail(companyAdminEmail);
	}

	public Company saveCompanyAdminData(Company company) {
		return companyRepository.save(company);
	}

	public Optional<Company> findByCompanyId(Integer companyId) {
		return companyRepository.findById(companyId);
	}

}
