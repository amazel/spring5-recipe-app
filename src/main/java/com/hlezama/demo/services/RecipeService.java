package com.hlezama.demo.services;

import com.hlezama.demo.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
