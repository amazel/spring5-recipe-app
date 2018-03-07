package com.hlezama.demo.services;

import com.hlezama.demo.commands.UnitOfMeasureCommand;
import com.hlezama.demo.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.hlezama.demo.domain.UnitOfMeasure;
import com.hlezama.demo.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService service;
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Mock
    UnitOfMeasureRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter =  new UnitOfMeasureToUnitOfMeasureCommand();
        service = new UnitOfMeasureServiceImpl(repository, converter);
    }

    @Test
    public void testListAllUOMs() {
        //given
        Set<UnitOfMeasure> retSet = new HashSet<>();
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);

        retSet.add(uom);
        retSet.add(uom2);

        when(repository.findAll()).thenReturn(retSet);

        //when
        Set<UnitOfMeasureCommand> commands = service.listAllUOM();

        //then
        assertEquals(2,commands.size());
        verify(repository,times(1)).findAll();
    }
}