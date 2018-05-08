package com.main.app.dto.basic;

import lombok.Data;

@Data
public class RoomDTO {

    private long id;
    private long hostId;
    private boolean isStarted;
    private int teamsCount;

}
