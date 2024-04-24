package com.ddangme.dmadmin.converter;

import com.ddangme.dmadmin.model.constants.AdminRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class RoleTypesConverter implements AttributeConverter<Set<AdminRole>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<AdminRole> attribute) {
        return attribute.stream().map(AdminRole::name).sorted().collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<AdminRole> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER)).map(AdminRole::valueOf).collect(Collectors.toSet());
    }

}