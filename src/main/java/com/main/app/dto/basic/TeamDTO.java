package com.main.app.dto.basic;

import lombok.Data;

import java.util.Set;

@Data
public class TeamDTO {
    private Long id;
    private String alias;
    private Set<BasicUserDTO> usersList;
}
