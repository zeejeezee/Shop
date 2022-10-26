package kh.study.shop.cart.Controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.shop.cart.service.CartService;
import kh.study.shop.cart.vo.CartVO;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Resource(name="cartService")
	private CartService cartService;
	
	//모든 메소드가 실행되기 전에 무조건 실행되는 메소드
	@ModelAttribute
	public void test(@RequestParam(defaultValue = "1") String menu, Model model) {
		model.addAttribute("menu", menu);
	}
	
	//장바구니 등록
	//리턴할 필요가 없어서 String을 void로 바꾸고 리턴 구문 삭제
	@ResponseBody
	@PostMapping("/regCart")
	public void regCart(CartVO cartVO, Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		cartVO.setMemberId(user.getUsername());
		
		cartService.regCart(cartVO);
	}
	
	//장바구니 목록 페이지로 이동
	@GetMapping("/cartList")
	public String cartList(Model model, CartVO cartVO, Authentication authentication) {
		User user = (User)authentication.getPrincipal();	

		List<CartVO> list = cartService.selectCartList(user.getUsername());
		model.addAttribute("cartList", list);
		
		for(CartVO e : list) {
			System.out.println(e);
		}
		
		//전체 총 가격 데이터
		int finalPrice = 0;
		for(CartVO cart : list) {
			finalPrice = finalPrice + cart.getTotalPrice();
		}
		
		model.addAttribute("finalPrice", finalPrice);
		
		return "content/cart/cart_list";
	}
	

	//장바구니 수량 변경
	@ResponseBody
	@PostMapping("/updateAmount")
	public void updateAmount(CartVO cartVO) {
		cartService.updateAmount(cartVO);
	}
	
	
	//장바구니 삭제
	@PostMapping("/deleteCart")
	public String deleteCart(String cartCodes) { //"CART_001,CART_002,.."
		String[] cartCodeArr = cartCodes.split(",");
		//배열을 리스트로 만듦
		List<String> cartCodeList = Arrays.asList(cartCodeArr);
		
		CartVO cartVO = new CartVO();
		cartVO.setCartCodeList(cartCodeList);
		
		cartService.deleteCart(cartVO);

		return "redirect:/cart/cartList";
	}
	
	

	
	
	//dataset 연습 메소드
	@GetMapping("/test")
	public String datasetTest() {
		return "content/cart/test";
	}
	
}
