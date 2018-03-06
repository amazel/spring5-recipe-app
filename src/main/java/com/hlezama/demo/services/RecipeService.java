package com.hlezama.demo.services;

import com.hlezama.demo.commands.RecipeCommand;
import com.hlezama.demo.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand findCommandById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
