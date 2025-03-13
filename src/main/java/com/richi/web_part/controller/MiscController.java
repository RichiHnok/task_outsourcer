package com.richi.web_part.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Misc - Miscellaneous - разное

@Controller
public class MiscController {
    
    @GetMapping("/")
    public String welcomePage(){
        return "index";
    }

    @GetMapping("/editor")
    public String editorChoosingPage(){
        return "editor/editor";
    }

    @GetMapping("/placeholder")
    public String placeholderPage(){
        return "placeholder";
    }
}
