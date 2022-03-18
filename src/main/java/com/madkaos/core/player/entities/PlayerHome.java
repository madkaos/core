package com.madkaos.core.player.entities;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;

public class PlayerHome extends Entity {
    @ID
    public String id;

    @Prop
    public String uuid;

    @Prop
    public String name;

    @Prop
    public double x;
    
    @Prop
    public double y;

    @Prop
    public double z;

    @Prop
    public float pitch;

    @Prop
    public float yaw;

    @Prop
    public String world;
}
