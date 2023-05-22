package dev.xf3d3.celestyteams.api;

import dev.xf3d3.celestyteams.models.Team;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClanHomeCreateEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player createdBy;
    private final Team team;
    private final Location homeLocation;

    public ClanHomeCreateEvent(Player createdBy, Team team, Location homeLocation) {
        this.createdBy = createdBy;
        this.team = team;
        this.homeLocation = homeLocation;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getCreatedBy() {
        return createdBy;
    }

    public Team getClan() {
        return team;
    }

    public Location getHomeLocation() {
        return homeLocation;
    }
}
