package com.multi.fourtunes.model.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.fourtunes.model.biz.KeywordBiz;
import com.multi.fourtunes.model.biz.KeywordChartBiz;
import com.multi.fourtunes.model.biz.LoginBiz;
import com.multi.fourtunes.model.biz.PlaylistBiz;
import com.multi.fourtunes.model.dto.KeywordChartDto;
import com.multi.fourtunes.model.dto.UserDto;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginBiz loginBiz;

	@Autowired
	private KeywordBiz keywordBiz;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PlaylistBiz playlistBiz;
	
	@Autowired
	private KeywordChartBiz keywordChartBiz;

	@GetMapping("/sociallogin")
	public String socialLogin(@RequestParam("email") String email, @RequestParam("name") String name, Model model,
							  HttpSession session) {
		// // log.debug(email + " " + name);
		boolean isUserExist = loginBiz.checkUserExist(email, name);
		if (isUserExist) {
			// DB에 있는 이메일이면 로그인 진행
			// 로그인 처리 로직 구현

			UserDto loginUser = new UserDto();
			loginUser.setUser_id(email);
			loginUser.setUser_name(name);

			UserDto res = loginBiz.socialLogin(loginUser);
			session.setAttribute("login", res);

			// 권한 리스트 생성
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("USER"));

			Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);

			return "index"; // 로그인 후 이동할 페이지
		} else {
			// DB에 없는 이메일이면 ID 입력칸과 이름 입력칸으로 이동
			// 회원 가입 페이지로 이동
			model.addAttribute("email", email);
			model.addAttribute("name", name);

			String[] keywordList = loginBiz.getKeyword();
			model.addAttribute("keywordlist", keywordList);
			List<KeywordChartDto> keywordCounts = keywordChartBiz.getKeywordChart();
		    model.addAttribute("keywordCounts", keywordCounts);
		    
			return "login_socialjoin";
		}
	}

	/**
	 * 회원가입 페이지에, MODEL 에 키워드 배열을 담아서 전달
	 *
	 * @param model
	 * @return 회원가입페이지로 전환
	 */
	@GetMapping("/join")
	public String join(Model model) {
		String[] keywordList = loginBiz.getKeyword();
		model.addAttribute("keywordlist", keywordList);
		List<KeywordChartDto> keywordCounts = keywordChartBiz.getKeywordChart();
	    model.addAttribute("keywordCounts", keywordCounts);
		return "login_join";
	}

	@GetMapping("/callback")
	public String callback() {
		return "callback";
	}

	@PostMapping("/login")
	public String login(HttpSession session, UserDto dto) {
//		// log.debug("LoginController 진입 \n" + dto.toString());
		UserDto res = loginBiz.login(dto);
//		// log.debug("리턴받은 dto : " + res.toString());
		if (res != null && passwordEncoder.matches(dto.getUser_pw(), res.getUser_pw())) {
			res.setUser_no(res.getUser_no());
			session.setAttribute("login", res);
			return "index";
		} else {
			return "login_login";
		}
	}

	/**
	 * 회원가입을 담당하는 컨트롤러
	 * @param email
	 * @param password
	 * @param name
	 * @param selectedKeywords
	 * @return 상술을 위한 멤버쉽 뷰 보여주기
	 */
	@GetMapping("/insertuser")
	public String insertUser(@RequestParam("join-email") String email, @RequestParam("join-pw") String password,
							 @RequestParam("join-name") String name, @RequestParam("selected_keyword") List<String> selectedKeywords) {
		//// log.debug(email + " " + password + " " + name + " " + selectedKeywords.toString());

		UserDto insert = new UserDto();
		insert.setUser_id(email);
		insert.setUser_pw(passwordEncoder.encode(password));
		insert.setUser_name(name);


		// 회원정보(아이디, 비밀번호, 이름) 먼저 Insert하고 성공하면
		if(loginBiz.insertUser(insert) > 0) {
			// 선택한 Keyword Insert 처리 (회원번호 저장해야해서?)
			String userId = insert.getUser_id();
			//// log.debug(userId);

			for(Object object : selectedKeywords) {
				String keyword = (String)object;
				//// log.debug(keyword);

				if(keywordBiz.insertKeyword(keyword, userId) > 0 ) {
					//// log.debug("키워드 insert 성공 ㅎㅎ");
				} else {
					//// log.debug("키워드 insert 실패 ㅜㅜ");
				}
			}

//			모두 insert 성공 시 USER 권한 넣기
			loginBiz.insertUserRole(insert.getUser_id());
			// insert 성공 시 플레이리스트 하나 추가

			playlistBiz.allocatePlaylist(insert.getUser_id());

			return "membership_join";
		} else {
			return "login_join";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate(); // 세션 무효화
		}
		return "index";
	}

	@PostMapping("/verifyemailid")
	@ResponseBody
	public String verifyEmail(@RequestParam("joinEmail") String joinEmail) {
		if (loginBiz.isValidEmail(joinEmail)) {
			return "{\"status\": \"success\", \"message\": \"Email verification successful\"}";
		} else {
			return "{\"status\": \"error\", \"message\": \"Invalid email format\"}";
		}
	}
}
