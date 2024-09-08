package com.mtks.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data //insted of this annotation, you can generate the getters and setters
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtParseDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("permissions")
    List<String> permissions = new ArrayList<>();
}
