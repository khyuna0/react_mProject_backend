package com.khyuna0.mProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
   
	@RequestMapping(value = {"/{path:^(?!api$|static|index\\.html$).*$}/**" })
    public String forward() {
        return "forward:/index.html";
    }

}