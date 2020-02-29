package com.keanntech.provider.admin.service;

import javax.servlet.http.HttpServletRequest;

public interface IOauthService {

   boolean hasPermission(HttpServletRequest authRequest);

}
