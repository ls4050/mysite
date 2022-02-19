package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.repository.GalleryRepository;
import com.poscoict.mysite.vo.GalleryVo;


@Service
public class GalleryService {

	@Autowired
	private GalleryRepository galleryRepository;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	public Boolean getImages(Model model) {
		List<GalleryVo> list = galleryRepository.findAll();
		model.addAttribute("list", list);
		return true;
	}

	public Boolean removeImage(Long no) {
		return galleryRepository.delete(no);
	}

	public Boolean saveImage(MultipartFile multipartFile, String comments) {
		GalleryVo galleryVo = new GalleryVo();
		String url = fileUploadService.restore(multipartFile);
		if(url == null) {
			return false;
		}
		galleryVo.setComments(comments);
		galleryVo.setUrl(url);
		return galleryRepository.insert(galleryVo);
		
	}

}
