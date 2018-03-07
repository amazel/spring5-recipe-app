package com.hlezama.demo.services;

import com.hlezama.demo.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUOM();
}
