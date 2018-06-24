package com.infy.portfolio.validations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.infy.portfolio.exceptions.EstimateValueNotCorrectException;
import com.infy.portfolio.exceptions.ProjectTypeNotValidException;
import com.infy.portfolio.model.Portfolio;

@Component
public class PortfolioValidationHandler {

	private static final Integer maxFibonnaciNum = 9;
	private static final String DOCS_MGMT = "Docs Management";
	private static final String SECU_COLL = "Securities & Collateral";
	
	
	public void validatePortfolioDetails(Portfolio pf) {
		Integer estimateValue = pf.getEstimates();
		validateEstimate(estimateValue);
		validateProjectType(pf.getProjectType());
		
	}
	
	public void validateEstimate(Integer estimateValue) throws EstimateValueNotCorrectException{
		if(!isFibonaaciNumber(estimateValue)) {
			String msg = "Estimate Value "+ estimateValue+ " not correct.";
			throw new EstimateValueNotCorrectException(msg);
		}
	}
	
	public void validateProjectType(String projectType) {
		if(!(projectType!=null && !"".equals(projectType) && (DOCS_MGMT.equals(projectType) || SECU_COLL.equals(projectType)))) {
			throw new ProjectTypeNotValidException("Project Type is not valid");
		}
		
	}
	
	public boolean isFibonaaciNumber(Integer estimateValue) {
		boolean isFibonaaci = false;		
		if(estimateValue < 0 && estimateValue > 21)
			return isFibonaaci;
		List<Integer> fibList = generateFibonaaciNumber(maxFibonnaciNum);
		if(fibList.contains(estimateValue))
			return true;		
		return isFibonaaci;
	}
	
	public static List<Integer> generateFibonaaciNumber(int numMax) {
		List<Integer> fibList = new ArrayList<Integer>();
		int n1 = 0;
		int n2 = 1;
		int n3 = 0;		
		fibList.add(n1);
		fibList.add(n2);
		for(int i=2; i<numMax; i++) {			
			n3=n1+n2;		
			fibList.add(n3);
			n1=n2;    
			n2=n3; 			
		}
		//System.out.println(fibList);
		return fibList;
	}
}
