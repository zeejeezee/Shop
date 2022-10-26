package kh.study.shop.buy.vo;

import kh.study.shop.item.vo.ItemVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyDetailVO {
	private String buyDetailCode;
	private String itemCode;
	private String buyCode;
	private int buyAmount;
	private ItemVO itemVO;
	private int buyPrice;
	

// 쌤 ResultMap 사용할 거면 이거 써야 함
//	private String buyDetailCode;
//	private String itemCode;
//	private String buyCode;
//	private int buyAmount;
//	private String cateName;
//	private int itemPrice;
//	private int buyPrice;
//	private String attachedName;
//	private String itemName;
}
