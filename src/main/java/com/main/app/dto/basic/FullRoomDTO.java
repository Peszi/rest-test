package com.main.app.dto.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FullRoomDTO extends RoomDTO {

    private List<TeamDTO> teamsList;

}
