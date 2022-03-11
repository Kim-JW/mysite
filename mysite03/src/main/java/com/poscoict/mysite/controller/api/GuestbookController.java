package com.poscoict.mysite.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poscoict.mysite.dto.JsonResult;
import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;

@RestController("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@Autowired
	GuestbookService guestbookService;
	
	@GetMapping("/list")
	public Object read() {
		List<GuestbookVo> list = guestbookService.getMessageList();
		
		return JsonResult.success(list);
	}
	
	@GetMapping("list/{sn}")
	public Object getList(@PathVariable(value = "sn") Long sn) {
		List<GuestbookVo> list = guestbookService.getMessageList(sn);
		
		System.out.println(sn);
		
		return JsonResult.success(list);
	}
	
	@DeleteMapping("/delete")
	public Object delete(@RequestBody GuestbookVo vo) {
		System.out.println(vo);
		boolean result = guestbookService.deleteMessage(vo.getNo(), vo.getPassword());
		
		Long data = result ? vo.getNo() : -1L;
		
		return JsonResult.success(data);
	}
	
	@PostMapping("/add")
	public Object add(@RequestBody GuestbookVo vo) {
		boolean result = guestbookService.addMessage(vo);
		
		Long data = result ? vo.getNo() : -1L;
		
		return JsonResult.success(vo);
		
	}
}
