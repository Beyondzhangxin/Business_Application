package com.aiidc.controller;

import com.aiidc.dao.UserDao;
import com.aiidc.entity.ActIdUser;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Zhangx on 2017/5/10 at 9:55.
 */
@Controller
public class LoginController
{
    @Resource
    private UserDao userDao;

    ActIdUser actIdUser = new ActIdUser();

    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest sr)
    {
        String name = sr.getParameter("username");
        String pwd = sr.getParameter("password");
        List<ActIdUser> list = userDao.findByCondition("first",name,ActIdUser.class);
        if (list.size() > 0)
        {
            ActIdUser actIdUser = list.get(0);
            if (pwd.equals(actIdUser.getPwd()) )
            {
                sr.getSession().setAttribute("loginUser", actIdUser);
                return new JSONObject().put("status", 0).toString();
            } else
            {
                return new JSONObject().put("status", 1).toString();
            }
        } else
        {
            return new JSONObject().put("status", 2). toString();
        }
    }

    @RequestMapping("logout")
    public void logout(HttpServletRequest servletRequest)
    {
        servletRequest.getSession().setAttribute("loginUser", null);
    }

    @RequestMapping("logon")
    public String logon()
    {
        return "main";
    }

}
