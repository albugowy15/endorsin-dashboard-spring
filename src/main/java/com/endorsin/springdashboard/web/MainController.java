package com.endorsin.springdashboard.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.endorsin.springdashboard.model.Campaign;
import com.endorsin.springdashboard.service.CampaignService;

@Controller
public class MainController {
	
	@Autowired
	private CampaignService campaignService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "title", "asc", model);
	}

	
	@GetMapping("/showNewCampaignForm")
	public String showNewCampaignForm(Model model) {
		// create model attribute to bind form data
		Campaign campaign = new Campaign();
		model.addAttribute("campaign", campaign);
		return "new_campaign";
	}
	
	@PostMapping("/saveCampaign")
	public String saveCampaign(@ModelAttribute("campaign") Campaign campaign) {
		// save campaign to database
		campaignService.saveCampaign(campaign);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get campaign from the service
		Campaign campaign = campaignService.getCampaignById(id);
		
		// set campaign as a model attribute to pre-populate the form
		model.addAttribute("campaign", campaign);
		return "update_campaign";
	}
	
	@GetMapping("/deleteCampaign/{id}")
	public String deleteCampaign(@PathVariable (value = "id") long id) {
		
		// call delete campaign method 
		this.campaignService.deleteCampaignById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Campaign> page = campaignService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Campaign> listCampaigns = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listCampaigns", listCampaigns);
		return "index";
	}
}
