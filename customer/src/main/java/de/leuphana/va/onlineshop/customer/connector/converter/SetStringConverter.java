package de.leuphana.va.onlineshop.customer.connector.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class SetStringConverter implements AttributeConverter<Set<Integer>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Integer> set) {
        if (set == null) return "";
        return String.join(",", set.stream().map(i -> i + "").toList());
    }

    @Override
    public Set<Integer> convertToEntityAttribute(String joined) {
        if (joined == null) return new HashSet<>();
        return Arrays.stream(joined.split(",")).map(Integer::getInteger).collect(Collectors.toSet());
    }
}
