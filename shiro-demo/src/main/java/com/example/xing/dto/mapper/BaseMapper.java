package com.example.xing.dto.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author xiexingxing
 * @Created by 2019-01-23 1:34 AM.
 */
public interface BaseMapper<SOURCE, TARGET> {

    @Mappings({})
    @InheritConfiguration
    TARGET to(SOURCE source);

    @InheritConfiguration
    List<TARGET> to(List<SOURCE> sources);

    @InheritInverseConfiguration
    SOURCE from(TARGET target);

    @InheritInverseConfiguration
    List<SOURCE> from(List<TARGET> targets);
}
