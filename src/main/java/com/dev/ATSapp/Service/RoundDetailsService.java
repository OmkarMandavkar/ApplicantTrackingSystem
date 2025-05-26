package com.dev.ATSapp.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Entity.RoundDetails;
import com.dev.ATSapp.Repository.RoundDetailsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoundDetailsService {

	private final RoundDetailsRepository roundDetailsRepository;

	public List<RoundDetails> getRoundDetailByJobId(Integer jobId) {
		return roundDetailsRepository.findByJobId(jobId);
	}
}