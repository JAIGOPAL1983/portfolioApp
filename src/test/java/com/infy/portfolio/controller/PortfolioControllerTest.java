package com.infy.portfolio.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.infy.portfolio.model.Portfolio;
import com.infy.portfolio.model.ProjectDetails;
import com.infy.portfolio.service.PortfolioService;

@RunWith(SpringRunner.class)
@WebMvcTest(PortfolioController.class)
public class PortfolioControllerTest {

	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private PortfolioService service;
    
    @Before
	public void setUp() {
    	long projectId = 1;
    	Portfolio pf = new Portfolio();
		pf.setProjectId(projectId);
		ProjectDetails pDetails = new ProjectDetails();
		pDetails.setName("Test Project-1");
		pf.setProjectDetails(pDetails);
		
    	Mockito.when(service.findPortfolioByProjectId(projectId))
	      .thenReturn(pf);
    	
    	List<Portfolio> portfolioLst = new ArrayList<Portfolio>();
    	portfolioLst.add(pf);
    	Mockito.when(service.findAllPortfolio())
	      .thenReturn(portfolioLst);
    }
    
    @Test
    public void givenProjectId_whenfindProject_thenReturnJsonArray()
      throws Exception {    	
    	mvc.perform(get("/api/v1/portfolio/project/1")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())          
          .andExpect(jsonPath("projectDetails.name").value("Test Project-1"));
    }
    
    @Test
    public void givenProjectId_whenfindfindAllProjects_thenReturnJsonArray()
      throws Exception {    	
    	mvc.perform(get("/api/v1/portfolio/project")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())          
          .andExpect(jsonPath("$[0].projectDetails.name").value("Test Project-1"));
    }
}
