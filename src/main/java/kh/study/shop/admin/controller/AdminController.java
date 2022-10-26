package kh.study.shop.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.study.shop.admin.service.AdminService;
import kh.study.shop.config.ShopDateUtil;
import kh.study.shop.config.UploadFileUtil;
import kh.study.shop.item.vo.CategoryVO;
import kh.study.shop.item.vo.ImgVO;
import kh.study.shop.item.vo.ItemVO;
import kh.study.shop.member.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Resource(name = "adminService")
	private AdminService adminService;
	
	//모든 메소드가 실행되기 전에 무조건 실행되는 메소드
	@ModelAttribute
	public void test(@RequestParam(defaultValue = "1") String menu, Model model) {
		model.addAttribute("menu", menu);

		//사용 중인 카테고리 목록 조회
		//model.addAttribute("categoryListInUse", adminService.selectCategoryListInUse());
	}
	
	//상품 등록 페이지
	@GetMapping("/regItem")
	public String regItem(Model model, ItemVO itemVO) {
		//전체 카테고리 목록 조회
		model.addAttribute("categoryListAll", adminService.selectCategoryListAll());
		
		return "content/admin/reg_item";
	}

	//상품 등록
	//일반적인 데이터는 커맨드 객체로 전달받는다.
	//첨부파일 데이터는 MultipartFile 객체를 통해 전달받아야 한다.
	@PostMapping("/regItem")
	public String regItemProcess(ItemVO itemVO
								, ImgVO imgVO
								, MultipartFile mainImg
								, List<MultipartFile> subImgs){
		
		//단일 이미지 파일 첨부 - 메인 이미지
		ImgVO uploadInfo = UploadFileUtil.uploadFile(mainImg);

		//다중 이미지 파일 첨부 - 서브 이미지
		List<ImgVO> uploadList = UploadFileUtil.multiUploadFile(subImgs);
		
		uploadList.add(uploadInfo);
		//uploadList는 ImgVO가 여러개인 List라서 자료형이 ImgVO인 uploadInfo도 추가할 수 있다.
		
		//상품 정보 insert
		//다음에 들어갈 ITEM_CODE를 조회하고,
		String nextItemCode = adminService.getNextItemCode();
		itemVO.setItemCode(nextItemCode);
		
		//이미지 정보를 insert하기 위한 데이터를 가진 uploadList에
		//조회한 ITEM_CODE 값도 넣어 준다.
		for(ImgVO vo : uploadList) {
			vo.setItemCode(nextItemCode);
		}
		
		itemVO.setImgList(uploadList);
		
		//상품 정보 + 이미지 정보 insert
		//itemVO가 uploadList를 가지고 있게 됐기 때문에 매개변수로 uploadList를 넣어 줄 필요가 없어짐
		adminService.regItem(itemVO);
		
		//상품의 이미지 정보 insert
		//IMG_CODE, 원본 파일명, 첨부한 파일명, IS_MAIN, ITEM_CODE

		return "redirect:/admin/regItem";
	}
	
	//카테고리 등록
	@PostMapping("/regCategory")
	public String regCategory(CategoryVO categoryVO) {
		adminService.insertCategory(categoryVO);
		
		return "redirect:/admin/regItem";
	}
	
	//사용, 미사용 라디오 버튼 클릭 시 진행
	@ResponseBody //페이지 이동 안 하려고 씀
	//리턴할 게 없어서 String을 void로 변경
	//리턴으로 페이지 이동 X, AJAX의 success 구문으로 감
	@PostMapping("/changeStatusAjax")
	public void changeStatus(CategoryVO categoryVO) {
		adminService.updateStatus(categoryVO);
	}
	
	@ResponseBody
	@PostMapping("/selectCategoryListInUseAjax")
	public List<CategoryVO> selectCategoryListInUseAjax() {
		List<CategoryVO> cateList = adminService.selectCategoryListInUse();
		
		return cateList;
	}
	
	
	//회원 권한 설정 페이지
	@GetMapping("/memberManage")
	public String memberManage(Model model) {
		model.addAttribute("memberList", adminService.selectMemberList());
		
		return "content/admin/member_manage";
	}
	
	//회원 정보 상세 조회
	@ResponseBody
	@PostMapping("/selectMemberDetail")
	public MemberVO selectMemberDetail(String memberId) {
		
		return adminService.selectMemberDetail(memberId);
	}
	
	//상품 관리 페이지
	@RequestMapping("/itemManage")
	public String itemManage(@RequestParam Map<String, String> paramMap, Model model) {
		System.out.println("상품명 : " + paramMap.get("itemName"));
		System.out.println("카테고리 코드 : " + paramMap.get("cateCode"));
		System.out.println("재고 : " + paramMap.get("itemStock"));
		System.out.println("fromDate : " + paramMap.get("fromDate"));
		System.out.println("toDate : " + paramMap.get("toDate"));
		System.out.println("상태 : " + paramMap.get("itemStatus"));
		
		model.addAttribute("itemList", adminService.getItemListForAdmin(paramMap));
		model.addAttribute("categoryList", adminService.selectCategoryListAll());
		
		//현재 날짜
		//메소드에 static이 있으면 객체를 생성할 필요 없이 호출할 수 있다
		String nowDate = ShopDateUtil.getNowDateToString("-");
		//한 달 전 날짜
		String beforeDate = ShopDateUtil.getBeforeMonthDateToString();

		//넘어오는 fromDate가 없다면 한 달 전 날짜로 세팅
		if(paramMap.get("fromDate") == null) {
			paramMap.put("fromDate", beforeDate);
		}
		
		if(paramMap.get("toDate") == null) {
			paramMap.put("toDate", nowDate);
		}

		model.addAttribute("paramMap", paramMap);
		
		return "content/admin/item_manage";
	}
	
	//상품 재고 변경
	@ResponseBody
	@PostMapping("/updateStockAjax")
	public void updateStock(ItemVO itemVO) {
		
		adminService.updateStock(itemVO);
	}
	
	//판매중, 매진 라디오 버튼 클릭 시 진행
	@ResponseBody
	@PostMapping("/changeItemStatus")
	public void changeItemStatus(ItemVO itemVO) {
		adminService.updateItemStatus(itemVO);
	}
	
	//메뉴 관리 페이지
	@GetMapping("/menuManage")
	public String menuManage() {
		
		return "content/admin/menu_manage";
	}
}
