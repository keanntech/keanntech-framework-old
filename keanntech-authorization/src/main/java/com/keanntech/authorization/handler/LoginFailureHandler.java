package com.keanntech.authorization.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keanntech.common.base.reponse.Result;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        Result resData = null;
        if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
            resData = Result.error().message("账户名或者密码输入错误!");
        } else if (e instanceof LockedException) {
            resData = Result.error().message("账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            resData = Result.error().message("密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            resData = Result.error().message("账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            resData = Result.error().message("账户被禁用，请联系管理员!");
        } else {
            resData = Result.error().message("登录失败!");
        }
        httpServletResponse.setStatus(401);
        resData.setCode(401);
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = httpServletResponse.getWriter();
        out.write(om.writeValueAsString(resData));
        out.flush();
        out.close();

    }
}
