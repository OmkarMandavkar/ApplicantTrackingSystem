package com.dev.ATSapp.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Entity.RoundDetails;
import com.dev.ATSapp.Repository.RoundDetailsRepository;

@Service
public class RoundDetailsService {

	private RoundDetailsRepository roundDetailsRepository;

	public RoundDetailsService(RoundDetailsRepository roundDetailsRepository) {
		this.roundDetailsRepository = roundDetailsRepository;
	}

	public List<RoundDetails> getRoundDetailByJobId(Integer jobId) {
		return roundDetailsRepository.findByJobId(jobId);
	}
	
	
}
