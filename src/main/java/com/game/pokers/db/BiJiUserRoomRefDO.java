package com.game.pokers.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("biji_user_room_ref")
public class BiJiUserRoomRefDO {
    @Id
    private Long id;
    private Long userId;
    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public BiJiUserRoomRefDO() {

    }

    public BiJiUserRoomRefDO(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
