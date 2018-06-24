package com.infy.portfolio.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.infy.portfolio.model.KeyContacts;
import com.infy.portfolio.model.Portfolio;
import com.infy.portfolio.model.ProjectDetails;
import com.infy.portfolio.validations.PortfolioValidationHandler;

@RunWith(SpringRunner.class)
public class PortfolioServiceTest {
	
	@TestConfiguration
    static class PortfolioServiceImplTestContextConfiguration {  
        @Bean
        public PortfolioService pfolioService() {
            return new PortfolioService();
        }
        @Bean
        public PortfolioValidationHandler valHandler() {
            return new PortfolioValidationHandler();
        }
    }

	@Autowired
	private PortfolioService pfolioService;
	@Autowired
	PortfolioValidationHandler valHandler;
	
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
	   
	   PortfolioEO pfEO = getMockPortfolioEO();
	   Mockito.when(repository.save(pfEO)).thenReturn(pfEO);
	}
	
	@Test
	public void whenValidProjectId_thenPortfolioShouldBeFound() {
		long projectId = 1;
		String projectName = "Test Project-1";
	    Portfolio ptFolio = pfolioService.findPortfolioByProjectId(projectId);
	  
	     assertEquals(ptFolio.getProjectDetails().getName(), projectName);
	 }
	
	@Test 
	public void givenMockPortfolio_thenSaveProject() {
		Portfolio pf = getMockPortfolioObject();		
		Portfolio ptFolio = pfolioService.savePortfolioDetails(pf);		
		assertEquals(pf.getProjectDetails().getName(), ptFolio.getProjectDetails().getName());		
	}
	
	@Test
	public void givenMockPortfolio_thenUpdateProject() {
		Portfolio pf = getMockPortfolioObject();
		long projectId = 1;
		try {
			pfolioService.updatePortfolioDetails(pf, projectId);
		} catch(Exception ex) {
			fail("No Exception should be thrown");
		}		
	}
	
	@Test
	public void givenValidProjectId_thenDeleteProject() {
		long projectId = 1;		
		Mockito.doNothing().when(repository).deleteByProjectId(projectId);
	    try {
	    	pfolioService.deletePortfolioByProjectId(projectId);
	    } catch(Exception ex) {
	    	fail("No Exception should be thrown");
	    }
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
	
	public Portfolio getMockPortfolioObject() {
    	Portfolio pf = new Portfolio();
    	
    	ProjectDetails pDtls = new ProjectDetails();
    	pDtls.setName("Project-1");
    	pDtls.setDescription("Project-1-Description");
    	pDtls.setSummary("Project-1-Summary");
    	pf.setProjectDetails(pDtls);
    	
    	pf.setDateRequested(Calendar.getInstance().getTime());
    	pf.setDateRequired(Calendar.getInstance().getTime());
    	
    	List<KeyContacts> keyList = new ArrayList<KeyContacts>();
    	KeyContacts key = new KeyContacts();
    	key.setFirstName("Jaigopal");
    	key.setLastName("Senapati");
    	Long phoneNumber = Long.valueOf("987654321");
    	key.setPhone(phoneNumber);
    	key.setEmail("jaigopal.senapati@gmail.com");
    	key.setDepartment("Trade");
    	key.setRole("Full Stack Java Developer");
    	keyList.add(key);
    	pf.setContacts(keyList);
    	    	
    	pf.setEstimates(0);
    	pf.setProjectType("Securities & Collateral");
    	pf.setCritical(true);    	
    	
    	return pf;
    }
	
	public PortfolioEO getMockPortfolioEO() {
		PortfolioEO pfEO = new PortfolioEO();
		long projectId = 1;
		pfEO.setProjectId(projectId);
		pfEO.setProjectName("Project-1");
		pfEO.setProjectDescription("Project-1-Description");
		
		Set<ContactsEO> keyContactDetails = new HashSet<ContactsEO>();
		ContactsEO ctcs = new ContactsEO();
		ctcs.setFirstName("Jaigopal");
		ctcs.setLastName("Senapati");
    	Long phoneNumber = Long.valueOf("987654321");
    	ctcs.setPhoneNumber(phoneNumber);
    	ctcs.setEmailId("jaigopal.senapati@gmail.com");
    	ctcs.setDepartment("Trade");
    	ctcs.setRole("Full Stack Java Developer");
    	keyContactDetails.add(ctcs);
		pfEO.setKeyContactDetails(keyContactDetails);
		
		pfEO.setEstimates(0);
		pfEO.setProjectType("Securities & Collateral");
		pfEO.setCritical("Y");
		
		return pfEO;
	}
	
}
