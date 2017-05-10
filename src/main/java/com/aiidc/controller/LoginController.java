package com.aiidc.controller;

import com.aiidc.entity.ActIdUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Zhangx on 2017/5/10 at 9:55.
 */
@Controller
public class LoginController
{
    ActIdUser actIdUser = new ActIdUser();

    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest sr)
    {
        actIdUser.setFirst(sr.getParameter("username"));
        actIdUser.setPwd(sr.getParameter("password"));
        sr.getSession().setAttribute("loginUser", actIdUser);
        return "成功";
    }

    @RequestMapping("logout")
    public void logout(HttpServletRequest servletRequest)
    {
        servletRequest.getSession().setAttribute("loginUser", null);
    }

    @RequestMapping("logon")
    public String logon(){
      return "main";
    }

}
