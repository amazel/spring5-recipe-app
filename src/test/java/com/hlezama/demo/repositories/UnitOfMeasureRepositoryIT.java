package com.hlezama.demo.repositories;

import com.hlezama.demo.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {
  @Autowired UnitOfMeasureRepository unitOfMeasureRepository;

  @Before
  public void setUp(){}

  @Test
  public void findByDescription() {
    Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");
    assertTrue(unitOfMeasure.isPresent());
    assertEquals("Teaspoon", unitOfMeasure.get().getDescription());
  }
}
