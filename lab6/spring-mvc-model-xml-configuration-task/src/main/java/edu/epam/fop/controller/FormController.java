package edu.epam.fop.controller;

import edu.epam.fop.model.FormData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {
    @GetMapping("/form")
    public String get(FormData formData){
        return "formTemplate";
    }
    @PostMapping("/processForm")
    public String post(FormData formData){
        return "resultTemplate";
    }
}
