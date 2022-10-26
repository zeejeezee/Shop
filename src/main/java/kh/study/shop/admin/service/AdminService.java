package kh.study.shop.admin.service;

import java.util.List;
import java.util.Map;

import kh.study.shop.item.vo.CategoryVO;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.vo.MemberVO;

public interface AdminService {
	List<CategoryVO> selectCategoryListAll();
	List<CategoryVO> selectCategoryListInUse();
	void insertCategory(CategoryVO categoryVO);
	
	//카테고리 사용 여부 수정
	void updateStatus(CategoryVO categoryVO);
	
	//상품, 상품 이미지 등록
	void regItem(ItemVO itemVO);
	//회원 목록 조회
	List<MemberVO> selectMemberList();
	
	//회원 정보 상세 조회
	MemberVO selectMemberDetail(String memberId);
	
	//상품 목록 조회
	List<ItemVO> getItemListForAdmin(Map<String, String> map);
	
	//상품 재고 수정
	void updateStock(ItemVO itemVO);
	
	//상품 판매 여부 수정
	void updateItemStatus(ItemVO itemVO);
	
	String getNextItemCode();
	

	


}
