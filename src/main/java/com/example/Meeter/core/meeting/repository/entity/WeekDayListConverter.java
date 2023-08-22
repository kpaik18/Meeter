package com.example.Meeter.core.meeting.repository.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class WeekDayListConverter implements AttributeConverter<Set<WeekDay>, String> {
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<WeekDay> weekDays) {
        return weekDays.stream()
                .map(WeekDay::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<WeekDay> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(DELIMITER))
                .map(WeekDay::valueOf)
                .collect(Collectors.toSet());
    }
}
