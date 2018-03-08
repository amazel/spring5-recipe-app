package com.hlezama.demo.services;

import com.hlezama.demo.commands.IngredientCommand;
import com.hlezama.demo.converters.IngredientCommandToIngredient;
import com.hlezama.demo.converters.IngredientToIngredientCommand;
import com.hlezama.demo.domain.Ingredient;
import com.hlezama.demo.domain.Recipe;
import com.hlezama.demo.repositories.RecipeRepository;
import com.hlezama.demo.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            //todo implement error handling
            log.error("recipe not found: " + recipeId);
            return null;
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            //todo implement error handling
            log.error("ingredient not found: " + ingredientId);
            return null;
        }
        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveRecipeIngredient(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()) {
            return new IngredientCommand();
        }
        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient found = ingredientOptional.get();
            found.setDescription(ingredientCommand.getDescription());
            found.setAmount(ingredientCommand.getAmount());
            found.setUnitOfMeasure(uomRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));

        } else {
            Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            recipe.addIngredient(ingredient);
        }
        Recipe savedRecipe = recipeRepository.save(recipe);


        Optional<Ingredient> savedIngredientOpt = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (!savedIngredientOpt.isPresent()) {
            savedIngredientOpt = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                    .findFirst()
            ;
        }


        //todo check for fail
        return ingredientToIngredientCommand.convert(savedIngredientOpt.get());
    }

    @Override
    public void deleteRecipeIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient>  ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientToDelete);
                recipeRepository.save(recipe);
            }
        } else {
            //todo handle error
        }
    }
}
