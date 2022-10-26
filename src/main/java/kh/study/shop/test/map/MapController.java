package kh.study.shop.test.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/map")
public class MapController {
	
	//기본 데이터 html에서 표현하기
	@GetMapping("/test1")
	public String mapTest1(Model model) {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "java");
		map.put(2, "python");
		map.put(3, "c++");
		
		model.addAttribute("mapData", map);
		
		return "test/map/test1";
	}
	
	//map에 객체가 들어가 있을 때 html에서 표현하기
	@GetMapping("/test2")
	public String mapTest2(Model model) {
		Map<String, MemberVO> map = new HashMap<>();
		
		MemberVO m1 = new MemberVO();
		m1.setMemberId("java1");
		m1.setMemberName("자바1");
		map.put("member1", m1);
		
		MemberVO m2 = new MemberVO();
		m2.setMemberId("java2");
		m2.setMemberName("자바2");
		map.put("member2", m2);
		
		MemberVO m3 = new MemberVO();
		m3.setMemberId("java3");
		m3.setMemberName("자바3");
		map.put("member3", m3);
		
		
		model.addAttribute("mapData", map);

		return "test/map/test2";
	}
	
	//map에 List 데이터가 저장되어 있을 때 html 사용 방법
	@GetMapping("/test3")
	public String mapTest3(Model model) {
		List<MemberVO> list1 = new ArrayList<>();
		MemberVO m1 = new MemberVO();
		m1.setMemberId("java1"); m1.setMemberName("자바1");
		MemberVO m2 = new MemberVO();
		m2.setMemberId("java2"); m2.setMemberName("자바2");
		list1.add(m1); list1.add(m2);

		List<MemberVO> list2 = new ArrayList<>();
		MemberVO m3 = new MemberVO();
		m3.setMemberId("java3"); m3.setMemberName("자바3");
		MemberVO m4 = new MemberVO();
		m4.setMemberId("java4"); m4.setMemberName("자바4");
		list2.add(m3); list2.add(m4);
		
		
		Map<String, List<MemberVO>> map = new HashMap<>();
		map.put("자바반", list1);
		map.put("캐드반", list2);
		
		model.addAttribute("mapData", map);
		
		return "test/map/test3";
	}
	
}
