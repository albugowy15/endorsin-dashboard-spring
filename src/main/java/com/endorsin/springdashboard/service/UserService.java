package com.endorsin.springdashboard.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.endorsin.springdashboard.model.User;
import com.endorsin.springdashboard.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
