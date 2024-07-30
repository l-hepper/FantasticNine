package com.sparta.doom.fantasticninewebandapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@Setter
@AllArgsConstructor
public class Movie {
    @Id
    private String id;
}
