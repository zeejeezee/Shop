package kh.study.shop.member.service;

import java.util.List;

import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.member.vo.MemberVO;

public interface MemberService {
	//회원가입
	void join(MemberVO memberVO);
	//로그인
	MemberVO login(MemberVO memberVO);
	//구매자 정보 조회
	MemberVO selectMemberInfo(String memberId);
	//구매 내역 조회
	List<BuyVO> getBuyList(String memberId);
	
}
