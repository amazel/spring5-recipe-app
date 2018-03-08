package com.hlezama.demo.services;

import com.hlezama.demo.domain.Recipe;
import com.hlezama.demo.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceImplTest {

	ImageService imageService;

	@Mock
	RecipeRepository  recipeRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	public void saveImageFile() throws Exception{
		Long recipeId = 1L;
		MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plan",
				"Spring Framework Guru".getBytes());

		Recipe recipe = new Recipe();
		recipe.setId(recipeId);

		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

		ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

		imageService.saveImageFile(recipeId, mockMultipartFile);

		verify(recipeRepository, times(1)).save(recipeArgumentCaptor.capture());
		Recipe savedRecipe = recipeArgumentCaptor.getValue();
		assertEquals(mockMultipartFile.getBytes().length,savedRecipe.getImage().length);
	}
}