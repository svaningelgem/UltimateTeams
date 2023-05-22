package dev.xf3d3.celestyteams.api;

import dev.xf3d3.celestyteams.models.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClanHomePreTeleportEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player createdBy;
    private final Team team;

    public ClanHomePreTeleportEvent(Player createdBy, Team team) {
        this.createdBy = createdBy;
        this.team = team;
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

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
