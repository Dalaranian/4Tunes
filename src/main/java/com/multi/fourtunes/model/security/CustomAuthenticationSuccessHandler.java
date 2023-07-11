package com.multi.fourtunes.model.security;

import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.UserEntity;
import com.multi.fourtunes.model.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

//        // log.debug(userDetails);

        UserEntity user = userRepository.findByUserId(userDetails.getUsername());

        UserDto dto = new UserDto(user.getUserNo(), user.getUserId(), user.getUserName(), "", user.getUserGrade(), user.getUserSuggestCount());

//        // log.debug(dto);

        HttpSession session = request.getSession();
        session.setAttribute("login", dto);

        response.sendRedirect("/"); // 로그인 성공 후 이동할 경로

    }
}
