package com.example.mobilelele.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrandController {

    @GetMapping("/brands/all")
    public String getAllBrands() {
        return "brands";
    }
}
