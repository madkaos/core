package com.madkaos.core.player.entities;

import java.util.ArrayList;
import java.util.List;

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
    public String address;

    @Prop
    public String pron = "o";

    @Prop
    public List<String> friendRequests = new ArrayList<>();

    @Prop
    public List<String> friends = new ArrayList<>();
}
