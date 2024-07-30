package com.sparta.doom.fantasticninewebandapi.models;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@Setter
public class Theatre {
    @Id
    private String id;
}
