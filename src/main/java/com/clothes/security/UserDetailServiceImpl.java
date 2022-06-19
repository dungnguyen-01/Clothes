package com.clothes.security;

import com.clothes.repository.Account;
import com.clothes.repository.AccountDAO;
import com.clothes.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    AccountDAO dao;
    @Autowired
    RoleDAO roleDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Account account = dao.getByEmail(email);
            UserDetails userDetails = new UserDetailImpl(account);
            return userDetails;
        } catch (Exception e) {
            throw new UsernameNotFoundException(email+" User not found");
        }  
    }

    public void authenticate(OAuth2AuthenticationToken oauth2) {
        String email = oauth2.getPrincipal().getAttribute("email");
        String fullname = oauth2.getPrincipal().getAttribute("name");

        if (!dao.existsByEmail(email)) {
            Account account = new Account(null,fullname," ",email," ",true,true,new Date(),new Date(),roleDAO.getById(4));
            dao.save(account);
        }

        UserDetails user = this.loadUserByUsername(email);
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
