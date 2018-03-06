package com.hlezama.demo.controllers;

import com.hlezama.demo.commands.RecipeCommand;
import com.hlezama.demo.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String getRecipe(@PathVariable Long id, Model model) {
        log.debug("RecipeController.getRecipe " + id);
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }


    @GetMapping
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/"+savedCommand.getId()+"/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe (@PathVariable Long id,Model model){
        model.addAttribute("recipe",recipeService.findCommandById(id));
        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id, Model model){
        recipeService.deleteById(id);
        return "redirect:/";
    }

}