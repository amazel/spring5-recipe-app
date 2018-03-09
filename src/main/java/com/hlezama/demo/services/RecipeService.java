package com.hlezama.demo.services;

import com.hlezama.demo.commands.RecipeCommand;
import com.hlezama.demo.domain.Recipe;
import com.hlezama.demo.exceptions.NotFoundException;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id) ;
    RecipeCommand findCommandById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    void deleteById(Long id);
}
