package com.main.app.dto.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {

    private long id;
    private long hostId;
    private String hostName;
    private boolean isStarted;
    private int teamsCount;

}
