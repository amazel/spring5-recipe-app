package com.hlezama.demo.controllers;

import com.hlezama.demo.services.ImageService;
import com.hlezama.demo.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    ImageService imageService;
    RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String getImage(@PathVariable Long recipeId, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(recipeId));
        return "/recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(recipeId,file);
        return "redirect:/recipe/"+recipeId+"/show";
    }
}
