package kh.study.shop.buy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.study.shop.buy.service.BuyService;
import kh.study.shop.buy.vo.BuyDetailVO;
import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.cart.service.CartService;
import kh.study.shop.cart.vo.CartVO;
import kh.study.shop.item.service.ItemService;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.service.MemberService;

@Controller
@RequestMapping("/buy")
public class BuyController {
	@Resource(name="buyService") //객체 생성
	private BuyService buyService;
	
	@Resource(name="memberService")
	private MemberService memberService;

	@Resource(name="cartService")
	private CartService cartService;

	@Resource(name="itemService")
	private ItemService itemService;
	


	
	

	//상품 상세 보기에서 구매 버튼 클릭 시
	@PostMapping("/buyInfoDirect")
	public String buyInfoDirect(String itemCode, int buyAmount, Model model, Authentication authentication) {
		ItemVO itemVO = itemService.selectItemDetail(itemCode);
		model.addAttribute("item", itemVO);
		model.addAttribute("buyAmount", buyAmount);
		
		//로그인한 회원의 정보 추출
		User user = (User)authentication.getPrincipal();
		
		//회원 정보 조회
		model.addAttribute("memberInfo", memberService.selectMemberInfo(user.getUsername()));
		
		return "content/buy/buy_info";
	}
	
	
	//장바구니에서 선택구매 버튼 클릭 시
	@PostMapping("/buyInfo")
	public String buyInfo(String cartCodes
						, CartVO cartVO
						, Model model
						, Authentication authentication) {
		String[] cartCodeArr = cartCodes.split(",");
		List<String> cartCodeList = Arrays.asList(cartCodeArr);
		cartVO.setCartCodeList(cartCodeList);
		
		//구매 예정 목록 조회
		model.addAttribute("buyingList", buyService.selectBuyingList(cartVO));
		
		//로그인한 회원의 정보 추출
		User user = (User)authentication.getPrincipal();
		
		//회원 정보 조회
		model.addAttribute("memberInfo", memberService.selectMemberInfo(user.getUsername()));
		
		return "content/buy/buy_info";
	}
	
	
	@PostMapping("/insertDirect")
	public String insertDirect(String itemCode
							, int buyAmount
							, int itemPrice
							, Authentication authentication) {
		
		String buyCode = buyService.selectNextBuyCode();
		User user = (User)authentication.getPrincipal();
		
		BuyVO buyVO = new BuyVO();
		buyVO.setBuyCode(buyCode);
		buyVO.setMemberId(user.getUsername());
		buyVO.setTotalPrice(itemPrice * buyAmount);
		
		List<BuyDetailVO> buyDetailList = new ArrayList<>();
		BuyDetailVO buyDetailVO = new BuyDetailVO(); //buyDetailList에 들어감
		buyDetailVO.setBuyCode(buyCode);
		buyDetailVO.setItemCode(itemCode);
		buyDetailVO.setBuyAmount(buyAmount);
		buyDetailList.add(buyDetailVO);
		
		buyVO.setBuyDetailList(buyDetailList);
		
		buyService.insertBuy(buyVO, null);
		
		return "redirect:/item/list";
	}
	
	
	//totalPrice, itemCode들, cartAmount들이 필요한데 이것들을 가지고 오려면 cartCode들만 있으면 됨
	@PostMapping("/insert")
	public String insertBuy(String[] cartCodes, CartVO cartVO, Authentication authentication) {
		
		//insert 돼야 하는 buyCode 조회
		String buyCode = buyService.selectNextBuyCode();
		
		//totalPrice, itemCode, cartAmount들
		//cartVO 안에 cartCode들이 있어야 한다
		
		//배열을 리스트 타입으로 변환해서 넣어 줘야 함
		List<String> cartCodeList = Arrays.asList(cartCodes);
		cartVO.setCartCodeList(cartCodeList);
		
		List<CartVO> cartList = buyService.getCartInfoForBuy(cartVO);
		List<BuyDetailVO> buyDetailList = new ArrayList<>();
		
		int totalPrice = 0;
		
		//조회한 데이터만큼 반복
		//for문으로 만든 vo를 buyDetailList에 넣어줌
		for(CartVO e : cartList) {
			totalPrice = totalPrice + e.getTotalPrice();
			
			BuyDetailVO vo = new BuyDetailVO();
			vo.setItemCode(e.getItemCode());
			vo.setBuyAmount(e.getCartAmount());
			buyDetailList.add(vo);
		}
		
		//memberId
		User user = (User)authentication.getPrincipal();
		
		//매개변수로 BuyVO buyVO 넣어도 됨
		BuyVO buyVO = new BuyVO();
		
		buyVO.setBuyCode(buyCode);
		buyVO.setMemberId(user.getUsername());
		buyVO.setTotalPrice(totalPrice);
		buyVO.setBuyDetailList(buyDetailList); //buyDetailVO -> itemCode, cartAmount
		
		buyService.insertBuy(buyVO, cartVO);
		
		return "redirect:/item/list";
	}
	
	

}
