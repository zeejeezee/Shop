package kh.study.shop.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.shop.admin.service.AdminService;
import kh.study.shop.item.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Resource(name = "adminService")
	private AdminService adminService;
	
	@Resource(name="itemService")
	private ItemService itemService;
	
	//상품 목록 페이지
	//boolean loginFail : 로그인 실패할 때만 데이터를 받아온다
	@GetMapping("/list")
	public String itemList(boolean isLoginFail, Model model) {
		//로그인 성공, 실패 여부를 html에 전달
		model.addAttribute("isLoginFail", isLoginFail);
		
		//List<ItemVO> list = itemService.selectItemList();
		
		//상품 목록 조회
		model.addAttribute("itemList", itemService.selectItemList());
		
		//for() {
		//	System.out.println(e);
		//}
		
		

		return "content/item/item_list";
	}
	
	//상품 상세 보기 페이지
	@GetMapping("/itemDetail")
	public String itemDetail(String itemCode, Model model) {
		
		model.addAttribute("item", itemService.selectItemDetail(itemCode));
		
		return "content/item/item_detail";
	}
	
	//상품 상세 보기 페이지에서 수량 변경시 Ajax 실행
	@ResponseBody
	@PostMapping("/itemDetailAjax")
	public void changeTotalPrice(int itemPrice, int cartAmount) {
				
	}

}
