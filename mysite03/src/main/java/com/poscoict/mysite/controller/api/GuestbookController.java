package com.poscoict.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@RestController("/guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	//service auto wired
	@Autowired
	private GuestbookService guestbookService;
	
	
	//get, put, delete ...
	
	@PostMapping("")
	public ResponseEntity<JsonResult> addMessage(@RequestBody GuestbookVo vo){
		guestbookService.addMessage(vo);
		vo.setNo(1L);
		vo.setPassword("");
		return new ResponseEntity<JsonResult>(JsonResult.success(vo), HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<JsonResult> listMessage(@RequestParam(value="sn", required=true, defaultValue="-1") Long no){
		System.out.println(no);
		List<GuestbookVo> list = guestbookService.getMessageList(no);
		
		return new ResponseEntity<JsonResult>(JsonResult.success(list), HttpStatus.OK);
	}
	
	@DeleteMapping("/{no}")
	public ResponseEntity<JsonResult> deleteMessage(
			@PathVariable("no") Long no,
			@RequestParam(value="password", required=true, defaultValue = "") String password){
		Long data = guestbookService.deleteMessage(no, password) ? no : -1L;
		return new ResponseEntity<JsonResult>(JsonResult.success(data), HttpStatus.OK);
	}
}
