package kh.study.shop.intercepter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

	//어노테이션에 의해 만들어진 카테고리 객체를 매개변수로 전달
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getCategoryIntercepter())
				.addPathPatterns("/**/**")
//				.excludePathPatterns("/admin/changeStatus"
//									, "/admin/selectCategoryListInUseAjax"
//									, "/admin/updateStock");
				.excludePathPatterns("/**/**Ajax");
	}
	
	
	@Bean
	public CategoryIntercepter getCategoryIntercepter() {
		return new CategoryIntercepter();
	}

}
