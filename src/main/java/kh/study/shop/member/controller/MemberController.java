package kh.study.shop.member.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.config.MemberRole;
import kh.study.shop.config.MemberStatus;
import kh.study.shop.member.service.MemberService;
import kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Resource(name="memberService")
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	//모든 메소드가 실행되기 전에 무조건 실행되는 메소드
	@ModelAttribute
	public void test(@RequestParam(defaultValue = "1") String menu, Model model) {
		model.addAttribute("menu", menu);
	}
	
	//회원가입
	@PostMapping("/join")
	public String join(MemberVO memberVO) {
		System.out.println(memberVO);
		
		memberVO.setMemberStatus(MemberStatus.ACTIVE.toString()); //enum 객체 사용 -> ACTIVE 문자열이 들어감
		memberVO.setMemberRole(MemberRole.MEMBER.toString());
		
		//비밀번호 암호화
		String pw = encoder.encode(memberVO.getMemberPw()); //memberVO 안에서 input으로 입력받은 비밀번호를 가지고 와서 암호화 후 이름을 pw로 지정
		memberVO.setMemberPw(pw); //암호화한 값 pw를 memberVO의 비밀번호로 세팅
		
		memberService.join(memberVO);
		
//		return "redirect:/item/list"; //html이 아닌 controller에 가기 위해 앞에 redirect:를 붙임
		return "content/member/join_result"; //alert을 띄우기 위해 join_result로 보냄
	}
	
	
	//로그인
//	@PostMapping("/login")
//	public String login(HttpSession session, MemberVO memberVO) {
//		MemberVO loginInfo = memberService.login(memberVO); //로그인한 데이터를 loginInfo가 가지게 됨
		
//		if(loginInfo != null) { //로그인 실패하면 null
//			session.setAttribute("loginInfo", loginInfo); //로그인 성공하면 세션에 데이터를 넣겠다
//		}
	
		//로그인 실패하면 세션에 데이터가 들어가지 않는다
		//성공하면 세션에 들어간 데이터를 빼서 쓰면 된다
//		return "content/member/login_result";
//	}
	
	//로그인 시도하면 login_result.html로 보내 준다
	@GetMapping("/loginResult")
	public String loginResult() {		
		return "content/member/login_result";
	}
	
	//Ajax 로그인
	@ResponseBody //return 값이 페이지(html)를 찾아가는 게 아니라고 알려 줌
	@PostMapping("/ajaxLogin")
	public boolean ajaxLogin(HttpSession session, MemberVO memberVO) {
	//return 값이 true or false라서 자료형이 boolean
	//클래스 안에 있는 변수명과 넘어오는 데이터의 변수명이 일치하면 데이터를 받아온다
		MemberVO loginInfo = memberService.login(memberVO);
		
		if(loginInfo != null) {
			session.setAttribute("loginInfo", loginInfo);
		}
		
		//Ajax는 내가 가지고 가야 하는 데이터를 return할 수 있다
		//로그인 성공 여부를 alert으로 띄우고 싶다
		return loginInfo == null ? false : true; //loginInfo가 null이면 false, 아니면 true
	}
	
	
	
	//구매 내역 조회 페이지 이동
	@GetMapping("/buyList")
	public String buyList(Model model, Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		List<BuyVO> buyList = memberService.getBuyList(user.getUsername());
		model.addAttribute("buyList", buyList);
		
		//들어있는 데이터 확인
		for(BuyVO e : buyList) {
			System.out.println(e);
		}

		return "content/member/buy_list";
	}			
	
	
	
	
	
	
	
}
