package kh.study.shop.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import kh.study.shop.item.vo.ImgVO;

public class UploadFileUtil {
	//파일이 첨부될 경로
	//변수 앞에 final을 붙이면 상수처럼 바뀌어서 변경 불가능
	//상수는 웬만하면 대문자로
	private static final String UPLOAD_PATH = "/Users/yz/git/Shop/src/main/resources/static/images/";
	//D:\\workspaceSTS\\Shop\\src\\main\\resources\\static\\images\\
	
	//파일 첨부
	public static ImgVO uploadFile(MultipartFile mainImg) {
		String originFileName = null;
		String fileName = null;
		
		//실제 첨부 파일이 있을 때만 첨부 기능 실행
		if(!mainImg.isEmpty()) { //파일명이 비어 있지 않다면
			//첨부하려는 원본 파일명
			originFileName = mainImg.getOriginalFilename();

			//파일명 중복을 막기 위해 랜덤한 파일명을 문자열로 생성
			String uuid = UUID.randomUUID().toString();
			
			//확장자 추출
			String extension = originFileName.substring(originFileName.lastIndexOf("."));
			
			//첨부될 파일명 생성
			fileName = uuid + extension;
			
			try {
				//파일 객체 생성  File file = new File("파일경로 + 파일명");
				File file = new File(UPLOAD_PATH + fileName);

				mainImg.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//리턴해야 하는 데이터를 저장하기 위한 객체
		ImgVO imgVO = new ImgVO();
		imgVO.setAttachedName(fileName);
		imgVO.setOriginName(originFileName);
		imgVO.setIsMain("Y");
		
		return imgVO;
		
	}
	
	
	//다중 파일 첨부
	public static List<ImgVO> multiUploadFile(List<MultipartFile> subImgs) {
		List<ImgVO> list = new ArrayList<>();
		
		//첨부된 파일의 개수만큼 첨부를 시작
		for(MultipartFile subImg : subImgs) {
			ImgVO vo = uploadFile(subImg);
			vo.setIsMain("N");
			list.add(vo);
		}
		
		return list;
	}
	
	
}
