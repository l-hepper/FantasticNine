package com.sparta.doom.fantasticninewebandapi.models;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Document(collection = "schedules")
public class ScheduleDoc {
    @Id
    private String id;

    @NotNull
    @DBRef
    private TheaterModel theater;

    @NotNull
    @DBRef
    private MoviesModel movie;

    @NotNull
    @Future
    private LocalDateTime startTime;

}
