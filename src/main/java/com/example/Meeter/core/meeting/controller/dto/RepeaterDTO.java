package com.example.Meeter.core.meeting.controller.dto;

import com.example.Meeter.core.meeting.repository.entity.WeekDay;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Set;

public record RepeaterDTO(Long id,
                          @NotNull String name,
                          @NotNull Set<WeekDay> weekDayList,
                          @NotNull Time startTime,
                          @NotNull Time endTime) {
    @AssertTrue
    @JsonIgnore
    public boolean isRepeaterValid() {
        if (weekDayList.size() == 0) {
            return false;
        }
        if (startTime.after(endTime) || startTime.equals(endTime)) {
            return false;
        }
        return true;
    }
}
