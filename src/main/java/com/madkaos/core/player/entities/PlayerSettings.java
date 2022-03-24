package com.madkaos.core.player.entities;

import com.dotphin.milkshakeorm.entity.Entity;
import com.dotphin.milkshakeorm.entity.ID;
import com.dotphin.milkshakeorm.entity.Prop;
import com.madkaos.core.player.PlayerFilter;

public class PlayerSettings extends Entity {
    @ID
    public String id;

    @Prop
    public String uuid;

    // Staff settings
    @Prop
    public boolean vanished = false;

    // Player settings
    @Prop
    public boolean fly = false;

    @Prop
    public int playerVisibilityFilter = PlayerFilter.ANYBODY;

    // Chat settings
    @Prop
    public String chatColor = "7";

    @Prop
    public int chatVisibilityFilter = PlayerFilter.ANYBODY;

    @Prop
    public int messageRequestsFilter = PlayerFilter.ANYBODY;

    // Social settings
    @Prop
    public int friendRequestsFilter = PlayerFilter.ANYBODY;

    @Prop
    public int partyRequestsFilter = PlayerFilter.ANYBODY;
}
