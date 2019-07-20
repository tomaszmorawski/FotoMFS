package com.example.fotomfs.Handlers;

import com.example.fotomfs.Model.User;
import com.example.fotomfs.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final
    UserRepository repository;

    @Autowired
    public CustomLoginSuccessHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println(authentication.getName());
        HttpSession session = httpServletRequest.getSession();
        User user = repository.findByLogin(authentication.getName());
        session.setAttribute("user", user);
        httpServletResponse.sendRedirect("/");
    }
}