package com.keanntech.common.base.resolver;

import com.keanntech.common.base.constants.SecurityConstants;
import com.keanntech.common.base.oauth.JWTHelper;
import com.keanntech.common.base.utils.OauthUtil;
import com.keanntech.common.model.methodresolver.CurrentUserResolver;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.Objects;

public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader(SecurityConstants.AUTH_HEADER);
        String publicKey = OauthUtil.getPubKey();
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(publicKey)){
            return null;
        }
        if(StringUtils.startsWith(token, SecurityConstants.AUTH_TYPE)){
            token = token.substring(SecurityConstants.AUTH_TYPE.length());
        }
        Claims claims = JWTHelper.parseJWT(token, publicKey);
        if(Objects.isNull(claims)){
            return null;
        }
        CurrentUserResolver currentUser = new CurrentUserResolver();
        currentUser.setId(Long.valueOf(String.valueOf(claims.get("id"))));
        currentUser.setJobNumber(String.valueOf(claims.get("jobNumber")));
        currentUser.setName(String.valueOf(claims.get("name")));
        currentUser.setRoleIds((List)claims.get("roleIds"));
        currentUser.setUserName(String.valueOf(claims.get("userName")));
        return currentUser;
    }
}
