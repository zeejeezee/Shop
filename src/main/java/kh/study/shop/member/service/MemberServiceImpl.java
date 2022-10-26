package kh.study.shop.member.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
	@Autowired
	private SqlSessionTemplate sqlsession;

	@Override
	public void join(MemberVO memberVO) {
		sqlsession.insert("memberMapper.join", memberVO);
	}

	@Override
	public MemberVO login(MemberVO memberVO) {
		return sqlsession.selectOne("memberMapper.login", memberVO);
	}

	@Override
	public MemberVO selectMemberInfo(String memberId) {
		return sqlsession.selectOne("memberMapper.selectMemberInfo", memberId);
	}
	
	@Override
	public List<BuyVO> getBuyList(String memberId) {
		return sqlsession.selectList("memberMapper.getBuyList", memberId);
	}

}
