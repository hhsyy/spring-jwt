package com.yiyuclub.springjwt.filter;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.Claim;
import com.yiyuclub.springjwt.config.JJwtUtils;
import com.yiyuclub.springjwt.config.JwtUtils;
import com.yiyuclub.springjwt.utils.ResultData;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("TOKEN");
        System.out.println(httpServletRequest.getServletPath());
        if(httpServletRequest.getServletPath().equals("/login")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //判断token
        if(!StringUtils.isEmpty(token)){
//            boolean isToken = JwtUtils.checkToken(token);

            boolean isToken = JJwtUtils.checkToken(token);
            if(isToken){
                //成功返回信息
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }

        //失败返回信息

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ResultData resultData = new ResultData();

        resultData.setStatus(401);
        resultData.setMsg("权限不足，请登陆获取权限");

        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(resultData));
    }
}
