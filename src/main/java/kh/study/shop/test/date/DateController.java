package kh.study.shop.test.date;

import java.util.Calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/date")
public class DateController {
	
	//현재 날짜
	@GetMapping("/test1")
	public String test1() {
		//Calendar 사용    디자인 패턴 : 싱글톤 패턴
		Calendar cal = Calendar.getInstance();
		
		//년도
		int year = cal.get(Calendar.YEAR);
		
		//월
		int month = cal.get(Calendar.MONTH) + 1;
		String monthStr = month / 10 == 0 ? "0" + month : "" + month;
			
		//일
		int date =cal.get(Calendar.DATE);
		String dateStr = date / 10 == 0 ? "0" + date : "" + date;
		
			String nowDate = year + "-" + monthStr + "-" + dateStr;
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@" + nowDate);
		
		return nowDate;
	}
}
