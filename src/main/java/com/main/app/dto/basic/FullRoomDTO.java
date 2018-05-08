package com.main.app.dto.basic;

import lombok.Data;

import java.util.List;

@Data
public class FullRoomDTO {

    private long id;
    private long hostId;
    private boolean isStarted;
    private int teamsCount;

    private List<TeamDTO> teamsList;

}
