package kh.study.shop.buy.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.cart.vo.CartVO;

@Service("buyService")
public class BuyServiceImpl implements BuyService {
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<CartVO> selectBuyingList(CartVO cartVO) {
		return sqlSession.selectList("buyMapper.selectBuyingList", cartVO);
	}

	@Override
	public String selectNextBuyCode() {
		return sqlSession.selectOne("buyMapper.selectNextBuyCode");
	}
	
	@Transactional(rollbackFor = Exception.class) //오류가 무엇이든 롤백시키겠다
	@Override
	public void insertBuy(BuyVO buyVO, CartVO cartVO) {
		sqlSession.insert("buyMapper.insertBuy", buyVO);
		sqlSession.insert("buyMapper.insertBuyDetail", buyVO);
		
		if(cartVO != null) {
			sqlSession.delete("cartMapper.deleteCart", cartVO);
		}
		
	}

	@Override
	public List<CartVO> getCartInfoForBuy(CartVO cartVO) {
		return sqlSession.selectList("buyMapper.getCartInfoForBuy", cartVO);
	}





}
