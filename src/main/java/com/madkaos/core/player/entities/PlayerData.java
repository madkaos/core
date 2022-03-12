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

    @Prop
    public String pron = "o";

    @Prop
    public String[] friendRequests = new String[0];

    @Prop
    public String[] friends = new String[0];
}
