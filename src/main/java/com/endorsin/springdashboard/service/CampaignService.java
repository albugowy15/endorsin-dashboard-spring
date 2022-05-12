package com.endorsin.springdashboard.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.endorsin.springdashboard.model.Campaign;

public interface CampaignService {
	List<Campaign> getAllCampaigns();
	void saveCampaign(Campaign campaign);
	Campaign getCampaignById(long id);
	void deleteCampaignById(long id);
	Page<Campaign> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
