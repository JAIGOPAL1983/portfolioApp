package com.infy.portfolio.dao;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.infy.portfolio.entity.PortfolioEO;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PortfolioRepositoryTest {

	 @Autowired
	 private TestEntityManager entityManager;
	 
	 @Autowired
	 private PortfolioRepository portfolioRepository;
	 
	 @Test
	 public void whenFindByProjectId_thenReturnPortfolio() {
	     // when
		 long projectId = 1;
		 Optional<PortfolioEO> ptfolio = portfolioRepository.findByProjectId(projectId);
	     // then
		 assertEquals(ptfolio.get().getProjectName(),"Test Project-1");
	 }
}
