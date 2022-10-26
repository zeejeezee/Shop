package kh.study.shop.cart.service;

import java.util.List;

import kh.study.shop.cart.vo.CartVO;

public interface CartService {
	//장바구니 상품 추가
	void regCart(CartVO cartVO);
	
	//장바구니 목록 조회
	List<CartVO> selectCartList(String memberId);
	
	//장바구니 수량 수정
	void updateAmount(CartVO cartVO);
	
	//장바구니 상품 삭제
	void deleteCart(CartVO cartVO);

	//장바구니 상품 구매 정보 조회
	void buyInfo(CartVO cartVO);
}
