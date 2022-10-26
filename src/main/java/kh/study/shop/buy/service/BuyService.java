package kh.study.shop.buy.service;

import java.util.List;

import kh.study.shop.buy.vo.BuyVO;
import kh.study.shop.cart.vo.CartVO;

public interface BuyService {
	List<CartVO> selectBuyingList(CartVO cartVO);
	String selectNextBuyCode();
	void insertBuy(BuyVO buyVO, CartVO cartVO);
	List<CartVO> getCartInfoForBuy(CartVO cartVO);
}
