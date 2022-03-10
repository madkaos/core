package com.madkaos.core.player.entities;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class PlayerData extends Entity {
    @ID
    public String id;

    @Prop
    public String uuid;

    @Prop
    public String username;

    @Prop
    public String displayName;
}
