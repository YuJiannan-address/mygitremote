package com.lagou.edu.controller;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.dto.LoginDTO;
import com.lagou.edu.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 默认请求，跳转到登录页面
     */
    @RequestMapping("/")
    public ModelAndView toLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/doLogin")
    public ModelAndView doLogin(HttpServletRequest request,
                                HttpServletResponse response,
                                LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        ModelAndView modelAndView = new ModelAndView();
        if (!"admin" .equals(username) || !"admin" .equals(password)) {
            modelAndView.setViewName("index");
            modelAndView.addObject("message", "用户名/密码错误！");
            return modelAndView;
        }
        // 登录成功
        // session.setAttribute("username", "admin");

        // 将session存入Redis，sessionId随机值作为key，有效期 1800秒
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        Jedis jedis = jedisPool.getResource();
        jedis.hset(uuid, map);
        jedis.expire(uuid, 1800);
        jedis.close();

        // 将uuid 写入cookie
        addCookie(response, "REDIS_SESSION_ID", uuid, 1800);

        modelAndView.setViewName("redirect:list");
        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        List<Resume> allResume = resumeDao.findAll();
        modelAndView.addObject("list", allResume);
        modelAndView.setViewName("list");
        return modelAndView;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Resume> resume = resumeDao.findById(id);
        modelAndView.addObject("resume", resume.get());
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(Resume resume) {
        ModelAndView modelAndView = new ModelAndView();
        resumeDao.save(resume);
        modelAndView.setViewName("redirect:list");
        return modelAndView;
    }

    @RequestMapping("/toAdd")
    public ModelAndView toAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView add(Resume resume) {
        ModelAndView modelAndView = new ModelAndView();
        resume.setId(null);
        resumeDao.save(resume);
        modelAndView.setViewName("redirect:list");
        return modelAndView;
    }

    @RequestMapping("/delete")
    public ModelAndView delete(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        resumeDao.deleteById(id);
        modelAndView.setViewName("redirect:list");
        return modelAndView;
    }


    public void addCookie(HttpServletResponse response,
                          String sessionId,
                          String value,
                          int age) {
        // 添加
        Cookie cookie = new Cookie(sessionId, value);
        cookie.setMaxAge(age); // 有效期为1800秒
        response.addCookie(cookie);
    }
}
