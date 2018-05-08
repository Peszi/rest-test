package com.main.app.dto.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {
    private Long id;
    private String alias;
    private Set<BasicUserDTO> usersList;
}
