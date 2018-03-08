package com.hlezama.demo.services;

import com.hlezama.demo.domain.Recipe;
import com.hlezama.demo.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	@Transactional
	public void saveImageFile(Long recipeId, MultipartFile file) {
		try {
			Byte[] bytesObject = new Byte[file.getBytes().length];
			int i=0;
			for (byte byteInFile:file.getBytes()) {
				bytesObject[i++] = Byte.valueOf(byteInFile);
			}

			Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
			if (recipeOpt.isPresent()) {
				Recipe recipe1 = recipeOpt.get();
				recipe1.setImage(bytesObject);
				recipeRepository.save(recipe1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
