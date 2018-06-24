package com.infy.portfolio.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.infy.portfolio.entity.ContactsEO;
import com.infy.portfolio.entity.PortfolioEO;

public interface PortfolioRepository extends CrudRepository<PortfolioEO, Long> {

	Optional<PortfolioEO> findByProjectId(Long projectId);	
	void deleteByProjectId(Long projectId);
}
