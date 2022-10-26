package kh.study.shop.cart.vo;

import java.util.List;

import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartVO {	
	private String cartCode;
	private String itemCode;
	private String memberId;
	private int cartAmount;
	private String regDate;
	private int totalPrice;
	private List<ImgVO> imgList;
	private ItemVO itemVO;
	private List<String> cartCodeList;
}
