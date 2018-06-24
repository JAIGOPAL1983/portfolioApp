package com.infy.portfolio.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.portfolio.model.Portfolio;
import com.infy.portfolio.service.PortfolioService;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {

	@Autowired
	PortfolioService pfService;
	
	
	@GetMapping("/project")
	public List<Portfolio> findAllProjects() {
		List<Portfolio> portfolioLst = pfService.findAllPortfolio();
		return portfolioLst;
	}
	
	@GetMapping("/project/{projectId}")
	public Portfolio findProject(@PathVariable("projectId") Long projectId) {
		Portfolio portfolio = pfService.findPortfolioByProjectId(projectId);
		return portfolio;
	}
	
	@PostMapping("/project") 
	public ResponseEntity<Portfolio> saveProjectDetails(@Valid @RequestBody Portfolio ptfolio) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pfService.savePortfolioDetails(ptfolio));
	}
	
	@PutMapping("/project/{projectId}")
	public void updateProjectDetails(@Valid @RequestBody Portfolio ptfolio, @PathVariable("projectId") Long projectId) {
		pfService.updatePortfolioDetails(ptfolio, projectId);
	}
	
	@DeleteMapping("/project/{projectId}")
	public void deleteProject(@PathVariable("projectId") Long projectId) {
		pfService.deletePortfolioByProjectId(projectId);
	}	
}
