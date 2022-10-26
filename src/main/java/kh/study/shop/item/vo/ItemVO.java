package kh.study.shop.item.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemVO {
	private String itemCode;
	private String itemName;
	private int itemPrice;
	private String itemComment;
	private String regDate;
	private int itemStock;
	private String cateCode;
	private String itemStatus;
	private String cateName;
	private List<ImgVO> imgList; //상품은 이미지를 여러개 가질 수 있다.
	private CategoryVO categoryVO; //상품은 카테고리 정보를 하나씩만 가지고 있어서 목록 X
}
