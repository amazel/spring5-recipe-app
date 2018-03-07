package com.hlezama.demo.services;

import com.hlezama.demo.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);
}
