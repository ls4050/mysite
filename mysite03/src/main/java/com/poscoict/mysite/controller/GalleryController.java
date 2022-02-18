package com.poscoict.mysite.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.service.GalleryService;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	

	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		galleryService.getImages(model);
		return "gallery/index";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		galleryService.removeImage(no);
		return "redirect:/gallery";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(
			@RequestParam("file") MultipartFile multipartFile, 
			@RequestParam(value="comments", required=true, defaultValue="") String comments) {
		galleryService.saveImage(multipartFile, comments);
		System.out.println("comments:"+comments);
		return "redirect:/gallery";
	}
	
}
