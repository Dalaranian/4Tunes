package com.multi.fourtunes.model.security;

import com.multi.fourtunes.model.dao.UserDao;
import com.multi.fourtunes.model.jpa.entity.UserEntity;
import com.multi.fourtunes.model.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */

    private final UserRepository userRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

//        System.out.println("LoadUserByName 진입");

        UserEntity user = userRepository.findByUserId(userId);

        String userRole = userDao.getUserRoleByUserNo(user.getUserNo());

//        System.out.println(user.toString() + "\n" + userRole);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getUserPw(), true, true, true, true, getAuthorities(userRole));
    }

    // String 으로 된 권한을 GrandAuthority 로 변환
    private Collection<? extends GrantedAuthority> getAuthorities(String roleString) {
        String[] roleArray = {roleString};
        return Arrays.stream(roleArray).map(role -> new SimpleGrantedAuthority(role.trim())).collect(Collectors.toList());
    }
}
