package kh.study.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Controller, Service
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	//메소드의 리턴 타입에 대한 객체를 생성하는 어노테이션, 주로 메소드 위에서 정의
	// -> SecurityFilterChain 객체가 생성
	@Bean
	//이 안에 있는 메소드는 예외 처리를 꼭 해 줘야 함 -> throws 사용
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		security.csrf().disable()
				.authorizeRequests()
				.antMatchers("/item/list"
							, "/item/itemDetail"
							, "/member/join"
							, "/member/login").permitAll() //회원가입, 로그인, 게시글 목록, 게시글 상세보기
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
				.formLogin() //인증이 되어 있지 않다면 로그인 페이지로 보내라
				.loginPage("/member/login")
				.defaultSuccessUrl("/member/loginResult") //로그인 성공하면 게시글 목록 페이지로 이동
				.failureUrl("/member/loginResult") //로그인 실패하면 다시 로그인 페이지로 이동
				.loginProcessingUrl("/member/login") //실제 로그인을 진행할 요청 정보 /member/login이라는 요청이 들어오면 진행하겠다
				.usernameParameter("memberId") //여기에 입력한 이름으로 input의 아이디와 이름을 설정할 수 있음
				.passwordParameter("memberPw")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/member/accessDenied") //인증은 했지만 권한이 없어서 못 들어가는 페이지로 이동했을 때
			.and()
				.logout()
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/item/list");
				
				
		return security.build();
	}
	
	@Bean //비밀번호 암호화 자동으로 해 주는 객체 생성
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//security로 접근 권한을 설정하면 .js, .css 등 정적인 리소스에 접근도 권한을 체크
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
	}
	
	
	
	
	
	
	
	
	
}
