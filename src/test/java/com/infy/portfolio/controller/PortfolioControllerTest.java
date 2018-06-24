package com.infy.portfolio.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.infy.portfolio.model.KeyContacts;
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
    public void givenProjectId_whenfindAllProjects_thenReturnJsonArray()
      throws Exception {    	
    	mvc.perform(get("/api/v1/portfolio/project")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())          
          .andExpect(jsonPath("$[0].projectDetails.name").value("Test Project-1"));
    }
    
    @Test
    public void givenRequestBody_thenSaveProjectDetails() throws Exception {    	
    	String jsonFilePath= "/src/test/java/com/infy/portfolio/controller/postRequest.json";
    	String sampleJSON = readFileAsString(jsonFilePath);
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/portfolio/project")
				.accept(MediaType.APPLICATION_JSON).content(sampleJSON)
				.contentType(MediaType.APPLICATION_JSON);
    	MvcResult result = mvc.perform(requestBuilder).andReturn();
    	MockHttpServletResponse response = result.getResponse();
    	assertEquals(HttpStatus.CREATED.value(), response.getStatus());    	
    }
    
    @Test
    public void givenJSON_thenUpdateProjectDetails() throws Exception {
    	String jsonFilePath= "/src/test/java/com/infy/portfolio/controller/postRequest.json";
    	String sampleJSON = readFileAsString(jsonFilePath);
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/v1/portfolio/project/1")
				.accept(MediaType.APPLICATION_JSON).content(sampleJSON)
				.contentType(MediaType.APPLICATION_JSON);
    	MvcResult result = mvc.perform(requestBuilder).andReturn();
    	MockHttpServletResponse response = result.getResponse();
    	assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    @Test
    public void givenProjectId_thendeleteProject()
      throws Exception {    	
    	mvc.perform(delete("/api/v1/portfolio/project/1")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());      
    }
    
	public static String readFileAsString(String jsonFilePath) throws Exception {
		String data = "";
		Path currentDir = Paths.get(".");
		String str = currentDir.toAbsolutePath().toString();
		str = str.replace('\\', '/');		 
		String reqJsonPath = str+jsonFilePath;		
		data = new String(Files.readAllBytes(Paths.get(reqJsonPath)));
		return data;
	}
	
	public static void main(String[] args) throws Exception {
		String test = readFileAsString("postRequest.json");
		System.out.println(test);
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
    
    public Portfolio getSaveReturnObject(Portfolio pf) {
    	long projectId = 1;
    	pf.setProjectId(projectId);
    	return pf;
    }
}
