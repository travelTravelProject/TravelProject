package com.travel.project.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/404")
    public String error404() {
        return "error/error404";
    }

    @GetMapping("/error/500")
    public String error500() {
        return "error/error500";
    }

    @GetMapping("/access-deny")
    public String accessDeny(String message, Model model) {
        model.addAttribute("msg", message);
        return "error/access-deny";
    }

}
