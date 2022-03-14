package com.madkaos.core.player.entities;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class PlayerPunishment extends Entity {
    @ID
    public String id;

    @Prop
    public String emisorId;

    @Prop
    public String playerId;

    @Prop
    public long createdOn;

    @Prop
    public long expiresOn = -1;

    @Prop
    public boolean ofIP = false;

    @Prop
    public int type;

    @Prop
    public String reason;
}
