package com.fia.base.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;



import jakarta.validation.Valid;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio() {
        return "Index";
    }
    
    
	

}
