package com.infy.portfolio.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.portfolio.dao.PortfolioRepository;
import com.infy.portfolio.entity.ContactsEO;
import com.infy.portfolio.entity.PortfolioEO;
import com.infy.portfolio.model.KeyContacts;
import com.infy.portfolio.model.Portfolio;
import com.infy.portfolio.model.ProjectDetails;
import com.infy.portfolio.validations.PortfolioValidationHandler;

@Service
@Transactional
public class PortfolioService {
	

	@Autowired
	PortfolioRepository repository;
	@Autowired
	PortfolioValidationHandler valHandler;
	
	public List<Portfolio> findAllPortfolio() throws EntityNotFoundException {
		Portfolio pf = null;
		List<Portfolio> pfList = new ArrayList<Portfolio>();		
		for(PortfolioEO ptfolio: repository.findAll()) {
			pf = new Portfolio();
			copyPortfolioEO2BO(ptfolio, pf);
			pfList.add(pf);			
		}
		if(pfList.size() == 0)
			throw new EntityNotFoundException("No projects found in portfolio");
		return pfList;
	}
	
	public Portfolio findPortfolioByProjectId(Long projectId) throws EntityNotFoundException {
		Portfolio pf = new Portfolio();
		try {
			Optional<PortfolioEO> ptfolio = repository.findByProjectId(projectId);
			if(!ptfolio.isPresent())
				throw new EntityNotFoundException();
			copyPortfolioEO2BO(ptfolio.get(), pf);			
		} catch(Exception ex) {
			ex.printStackTrace();
			String errorMsg = "Portfolio not found for project Id => "+projectId;
			throw new EntityNotFoundException(errorMsg);
		}		
		return pf;		
	}
	
	public void deletePortfolioByProjectId(Long projectId) {		
		repository.deleteByProjectId(projectId);
	}
	
	public Portfolio savePortfolioDetails(Portfolio pf) {
		valHandler.validatePortfolioDetails(pf);
		PortfolioEO ptfolio = new PortfolioEO();
		copyPortfolioBO2EO(ptfolio, pf);
		ptfolio = repository.save(ptfolio);	
		copyPortfolioEO2BO(ptfolio, pf);
		return pf;
	}
	
	public void updatePortfolioDetails(Portfolio pf, Long projectId) {		
		valHandler.validatePortfolioDetails(pf);
		Optional<PortfolioEO> ptfolio = repository.findByProjectId(projectId);
		try {
			if(!ptfolio.isPresent())
				throw new EntityNotFoundException();			
			updatePortfolioBO2EO(ptfolio.get(), pf, projectId);
			repository.save(ptfolio.get());
		} catch(Exception ex) {
			ex.printStackTrace();
			String errorMsg = "Portfolio not updated for project Id => "+projectId;
			throw new EntityNotFoundException(errorMsg);
		}
		
	}
	
	public void updatePortfolioBO2EO(PortfolioEO ptfolio, Portfolio pf, Long projectId) {
		ptfolio.setProjectName(pf.getProjectDetails().getName());
		ptfolio.setProjectDescription(pf.getProjectDetails().getDescription());
		ptfolio.setProjectSummary(pf.getProjectDetails().getSummary());		
		ptfolio.setDateRequest(pf.getDateRequested());
		ptfolio.setDateRequired(pf.getDateRequired());		
		for(KeyContacts kCtcts: pf.getContacts()) {
			ContactsEO ctcEO = 	findContactsById(ptfolio, kCtcts.getContactId());
			if(ctcEO == null) {
				ctcEO = new ContactsEO();
				ctcEO.setFirstName(kCtcts.getFirstName());
				ctcEO.setLastName(kCtcts.getLastName());
				ctcEO.setEmailId(kCtcts.getEmail());
				ctcEO.setPhoneNumber(kCtcts.getPhone());
				ctcEO.setRole(kCtcts.getRole());
				ctcEO.setDepartment(kCtcts.getDepartment());
				ctcEO.setPortfolioEO(ptfolio);
				ptfolio.getKeyContactDetails().add(ctcEO);
			} else {
				ctcEO.setContactId(kCtcts.getContactId());
				ctcEO.setFirstName(kCtcts.getFirstName());
				ctcEO.setLastName(kCtcts.getLastName());
				ctcEO.setEmailId(kCtcts.getEmail());
				ctcEO.setPhoneNumber(kCtcts.getPhone());
				ctcEO.setRole(kCtcts.getRole());
				ctcEO.setDepartment(kCtcts.getDepartment());
				ctcEO.setPortfolioEO(ptfolio);
			}
		}	
		ptfolio.setEstimates(pf.getEstimates());
		ptfolio.setProjectType(pf.getProjectType());
		ptfolio.setCritical(pf.isCritical()?"Y":"N");
	}
	
	public ContactsEO findContactsById(PortfolioEO ptfolio, Long contactId) {
		Set<ContactsEO> contactsSet = ptfolio.getKeyContactDetails();
		for(ContactsEO ctctsEO: contactsSet) {
			if(ctctsEO.getContactId().equals(contactId))
				return ctctsEO;			
		}
		return null;
	}	
	
	public void copyPortfolioEO2BO(PortfolioEO ptfolio, Portfolio pf) {
		if(ptfolio!=null) {
			ProjectDetails pDtls = new ProjectDetails();
			pf.setProjectId(ptfolio.getProjectId());
			pDtls.setName(ptfolio.getProjectName());
			pDtls.setDescription(ptfolio.getProjectDescription());
			pDtls.setSummary(ptfolio.getProjectSummary());
			pf.setProjectDetails(pDtls);
			pf.setDateRequested(ptfolio.getDateRequest());
			pf.setDateRequired(ptfolio.getDateRequired());
			
			List<KeyContacts> contactList = new ArrayList<KeyContacts>();
			for(ContactsEO contactEO: ptfolio.getKeyContactDetails()) {
				KeyContacts kCtcts = new KeyContacts();
				kCtcts.setContactId(contactEO.getContactId());
				kCtcts.setFirstName(contactEO.getFirstName());
				kCtcts.setLastName(contactEO.getLastName());
				kCtcts.setPhone(contactEO.getPhoneNumber());
				kCtcts.setEmail(contactEO.getEmailId());
				kCtcts.setRole(contactEO.getRole());
				kCtcts.setDepartment(contactEO.getDepartment());			
				contactList.add(kCtcts);
			}		
			pf.setContacts(contactList);		
			pf.setEstimates(ptfolio.getEstimates());
			pf.setProjectType(ptfolio.getProjectType());
			pf.setCritical((ptfolio.getCritical()!=null && ptfolio.getCritical().equals("Y"))?true:false);
		}
	}
	
	public void copyPortfolioBO2EO(PortfolioEO ptfolio, Portfolio pf) {
		ptfolio.setProjectName(pf.getProjectDetails().getName());
		ptfolio.setProjectDescription(pf.getProjectDetails().getDescription());
		ptfolio.setProjectSummary(pf.getProjectDetails().getSummary());
		
		ptfolio.setDateRequest(pf.getDateRequested());
		ptfolio.setDateRequired(pf.getDateRequired());
				
		Set<ContactsEO> contactsSet = new HashSet<ContactsEO>();
		if(pf.getContacts()!=null && pf.getContacts().size()>0) {
			for(KeyContacts kCtcts: pf.getContacts()) {
				ContactsEO ctcEO = new ContactsEO();
				ctcEO.setFirstName(kCtcts.getFirstName());
				ctcEO.setLastName(kCtcts.getLastName());
				ctcEO.setEmailId(kCtcts.getEmail());
				ctcEO.setPhoneNumber(kCtcts.getPhone());
				ctcEO.setRole(kCtcts.getRole());
				ctcEO.setDepartment(kCtcts.getDepartment());
				ctcEO.setPortfolioEO(ptfolio);
				contactsSet.add(ctcEO);
			}
		}
		ptfolio.setKeyContactDetails(contactsSet);
		ptfolio.setEstimates(pf.getEstimates());
		ptfolio.setProjectType(pf.getProjectType());
		ptfolio.setCritical(pf.isCritical()?"Y":"N");		
	}
	
		
}
