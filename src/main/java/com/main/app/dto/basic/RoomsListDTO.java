package com.main.app.dto.basic;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RoomsListDTO {
    private boolean hasRoom;
    private Iterable<RoomDTO> roomsList;
}
