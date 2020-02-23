package com.keanntech.authorization.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginOutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

//    @Autowired
//    @Qualifier("redisTokenStore")
//    TokenStore redisTokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET);
        ResponseData resData = ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(),"成功退出！", null);
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = httpServletResponse.getWriter();
        out.write(om.writeValueAsString(resData));
        out.flush();
        out.close();
//        String token = httpServletRequest.getHeader(SecurityConstants.AUTH_HEADER);
//        String tokenValue = token.replace(SecurityConstants.BEARER_TYPE, StrUtil.EMPTY).trim();
//        OAuth2AccessToken accessToken = redisTokenStore.readAccessToken(tokenValue);
//        if (accessToken != null || !StrUtil.isBlank(accessToken.getValue())) {
//            redisTokenStore.removeAccessToken(accessToken);
//            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//            redisTokenStore.removeRefreshToken(refreshToken);
//        }
    }
}
