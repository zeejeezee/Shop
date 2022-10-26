package kh.study.shop.cart.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.study.shop.cart.vo.CartVO;

@Service("cartService")
public class CartServiceImpl implements CartService {
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public void regCart(CartVO cartVO) {
		sqlSession.insert("cartMapper.regCart", cartVO);
	}

	@Override
	public List<CartVO> selectCartList(String memberId) {
		return sqlSession.selectList("cartMapper.selectCartList", memberId);
	}

	@Override
	public void updateAmount(CartVO cartVO) {
		sqlSession.update("cartMapper.updateAmount", cartVO);
	}

	@Override
	public void deleteCart(CartVO cartVO) {
		sqlSession.delete("cartMapper.deleteCart", cartVO);
	}

	@Override
	public void buyInfo(CartVO cartVO) {
		sqlSession.selectOne("cartMapper.selectBuyInfo", cartVO);
	}

	

}
