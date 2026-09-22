package edu.epam.fop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionController {
    @GetMapping("/numberFormatException")
    public void nfe(){
        System.out.println("nfe");
        throw new NumberFormatException("nfe");
    }
    @GetMapping("/nullPointerException")
    public void npe(){
        throw new NullPointerException("npe");
    }
}