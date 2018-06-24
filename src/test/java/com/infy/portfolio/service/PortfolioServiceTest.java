package com.infy.portfolio.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.infy.portfolio.dao.PortfolioRepository;
import com.infy.portfolio.entity.ContactsEO;
import com.infy.portfolio.entity.PortfolioEO;
import com.infy.portfolio.model.Portfolio;

@RunWith(SpringRunner.class)
public class PortfolioServiceTest {
	
	@TestConfiguration
    static class PortfolioServiceImplTestContextConfiguration {  
        @Bean
        public PortfolioService pfolioService() {
            return new PortfolioService();
        }
    }

	@Autowired
	private PortfolioService pfolioService;
	
	@MockBean
	private PortfolioRepository repository;
	
	@Before
	public void setUp() {
	   PortfolioEO ptFolio1 = new PortfolioEO();
	   long projectId1 = 1;
	   ptFolio1.setProjectId(projectId1);
	   ptFolio1.setProjectName("Test Project-1");
	   
	   ptFolio1.setKeyContactDetails(setupContactDetails());
	   
	   Optional<PortfolioEO> optPfEO = Optional.of(ptFolio1);
	   Mockito.when(repository.findByProjectId(projectId1))
	      .thenReturn(optPfEO);
	   
	   PortfolioEO ptFolio2 = new PortfolioEO();
	   long projectId2 = 2;
	   ptFolio2.setProjectId(projectId2);
	   ptFolio2.setProjectName("Test Project-2");
	   
	   List<PortfolioEO> ptFolioList= new ArrayList<PortfolioEO>();
	   ptFolioList.add(ptFolio1);
	   ptFolioList.add(ptFolio2);
	   Mockito.when(repository.findAll())
	      .thenReturn(ptFolioList);
	   
	   
	}
	
	@Test
	public void whenValidProjectId_thenPortfolioShouldBeFound() {
		long projectId = 1;
		String projectName = "Test Project-1";
	    Portfolio ptFolio = pfolioService.findPortfolioByProjectId(projectId);
	  
	     assertEquals(ptFolio.getProjectDetails().getName(), projectName);
	 }
	
	@Test
	public void whenFinalAllProjects_thenAllPortfoliosShouldBeFound() {
		List<Portfolio> ptFolioList = pfolioService.findAllPortfolio();
		assertEquals(ptFolioList.size(),2);
	}
	
	public Set<ContactsEO> setupContactDetails() {
		Set<ContactsEO> contactsEOSet = new HashSet<ContactsEO>();
		ContactsEO contact1 = new ContactsEO();
		long contactId1 = 1;
		contact1.setContactId(contactId1);
		contact1.setFirstName("Jaigopal");
		contact1.setLastName("Senapati");
		contact1.setEmailId("jaigopal.senapati@gmail.com");
		contactsEOSet.add(contact1);
		return contactsEOSet;
	}
	
}
