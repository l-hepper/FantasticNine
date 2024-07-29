package com.sparta.doom.fantasticninewebandapi.models;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Document
public class Schedule {
    @Id
    private Integer id;
    //should have theatre
    //should have movies
    //should have Start Times, End times will be calculated based on duration of movie + start time

}
