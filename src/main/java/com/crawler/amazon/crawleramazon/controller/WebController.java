package com.crawler.amazon.crawleramazon.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping()
public class WebController {

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/403")
    public String accessDenied(){
        return "403";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @GetMapping(value = "/product")
    public String product(){
        return "product";
    }

    @GetMapping(value = "/crawl")
    public String crawlData(){
        return "crawl-data";
    }

}
