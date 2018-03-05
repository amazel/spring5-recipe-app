package com.hlezama.demo.repositories;

import com.hlezama.demo.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
