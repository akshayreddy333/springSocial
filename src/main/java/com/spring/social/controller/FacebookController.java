package com.spring.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.spring.social.model.UserInfo;
import com.spring.social.service.FacebookService;

@Controller
public class FacebookController {
	
	@Autowired private FacebookService fbService;
	
	@GetMapping(value="/facebooklogin")
	public RedirectView facebookLogin() {
		RedirectView redirectView = new RedirectView();
		String url = fbService.facebookLogin();
		System.out.println(url);
		redirectView.setUrl(url);
		return redirectView;
		
	}
	
	@GetMapping(value="/facebook")
	public String facebook(@RequestParam("code") String code) {
		String accessToken = fbService.getFacebookAccessToken(code);
		return "redirect:/facebookprofiledata/"+accessToken;
		
	}
	
	@GetMapping(value="/facebookprofiledata/{accessToken:.+}")
	public String facebookprofiledata(@PathVariable String accessToken, Model model) {
		User user = fbService.getFacebookUserProfile(accessToken);
		UserInfo userInfo = new UserInfo(user.getFirstName(), user.getLastName(), user.getLastName());
		model.addAttribute("user", userInfo);
		return "view/userprofile";
		
	}

}
