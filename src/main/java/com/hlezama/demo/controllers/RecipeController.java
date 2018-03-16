package com.hlezama.demo.controllers;

import com.hlezama.demo.commands.RecipeCommand;
import com.hlezama.demo.exceptions.NotFoundException;
import com.hlezama.demo.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public static final String RECIPE_FORM_URL = "recipe/recipeform";
    public static final String RECIPE_SHOW_URL = "recipe/show";

    @GetMapping("/recipe/{id}/show")
    public String getRecipe(@PathVariable Long id, Model model) {
        log.debug("RecipeController.getRecipe " + id);
        model.addAttribute("recipe", recipeService.findById(id));
        return RECIPE_SHOW_URL;
    }



    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return RECIPE_FORM_URL;
    }


    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return RECIPE_FORM_URL;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/"+savedCommand.getId()+"/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe (@PathVariable Long id,Model model){
        model.addAttribute("recipe",recipeService.findCommandById(id));
        return RECIPE_FORM_URL;
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable Long id, Model model){
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e){
        log.error("Handling NOT FOUND EXCEPTION");
log.error(e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception",e);
        mav.setViewName("404error");
        return mav;
    }


}