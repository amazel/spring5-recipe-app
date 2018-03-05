package com.hlezama.demo.controllers;

import com.hlezama.demo.domain.Category;
import com.hlezama.demo.domain.UnitOfMeasure;
import com.hlezama.demo.repositories.CategoryRepository;
import com.hlezama.demo.repositories.UnitOfMeasureRepository;
import com.hlezama.demo.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndex(Model model){
        log.debug("Getting index page");
        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";
    }
}
