package dev.xf3d3.ultimateteams.hooks;

import dev.xf3d3.ultimateteams.UltimateTeams;
import dev.xf3d3.ultimateteams.models.Team;
import dev.xf3d3.ultimateteams.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class PapiExpansion extends PlaceholderExpansion {

    private final UltimateTeams plugin;

    public PapiExpansion(@NotNull UltimateTeams plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "UltimateTeams";
    }

    @Override
    public @NotNull String getAuthor() {
        return UltimateTeams.getPlugin().getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return UltimateTeams.getPlugin().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        final Team team = getTeam(player);

        switch (params.toLowerCase(Locale.ENGLISH)) {
            case "teamname" -> {
                return getTeamName(team);
            }
            case "teamprefix" -> {
                return getTeamPrefix(team);
            }
            case "friendlyfire" -> {
                return getFriendlyFire(team);
            }
            case "teamhomeset" -> {
                return getTeamHomeSet(team);
            }
            case "teammemberssize" -> {
                return getTeamMemberSize(team);
            }
            case "teamallysize" -> {
                return getTeamAllySize(team);
            }
            case "teamenemysize" -> {
                return getTeamEnemySize(team);
            }
            case "isinteam" -> {
                return getIsInTeam(team);
            }
        }

        final String[] parts = params.split(plugin.getSettings().getSeperator(), 2);
        final String action = parts[0].toLowerCase(Locale.ENGLISH);

        final OfflinePlayer otherPlayer = plugin.getServer().getPlayer(parts[1]);
        final Team otherTeam = getTeam(otherPlayer);

        switch (action) {
            case "ismyteam" -> {
                return getIsMyTeam(team, otherTeam);
            }
            case "ismyenemy" -> {
                return getIsMyEnemy(team, otherTeam);
            }
            case "ismyally" -> {
                return getIsMyAlly(team, otherTeam);
            }
            case "isneutral" -> {
                return getIsNeutral(team, otherTeam);
            }
        }

        return null;
    }

    private Team getTeam(OfflinePlayer player) {
        Team team = plugin.getTeamStorageUtil().findTeamByOfflineOwner(player);
        if (team == null) {
            team = plugin.getTeamStorageUtil().findTeamByOfflinePlayer(player);
        }
        return team;
    }

    private static @NotNull String getTeamName(Team team) {
        if (team != null) {
            return Utils.Color(team.getTeamFinalName() + "&r ");
        }

        return "";
    }

    private @NotNull String getTeamPrefix(Team team) {
        String openBracket = plugin.getSettings().getPrefixBracketsOpening();
        String closeBracket = plugin.getSettings().getPrefixBracketsClosing();

        if (team != null) {
            StringBuilder tmp = new StringBuilder();
            if (plugin.getSettings().addPrefixBrackets()) {
                tmp.append(openBracket);
            }

            tmp.append(team.getTeamPrefix());

            if (plugin.getSettings().addPrefixBrackets()) {
                tmp.append(closeBracket);
            }
            tmp.append("&r");
            if (plugin.getSettings().addSpaceAfterPrefix()) {
                tmp.append(' ');
            }

            return tmp.toString();
        }

        return "";
    }

    private static @NotNull String getFriendlyFire(Team team) {
        if (team != null) {
            return String.valueOf(team.isFriendlyFireAllowed());
        }

        return "";
    }

    private @NotNull String getTeamHomeSet(Team team) {
        if (team != null) {
            return String.valueOf(plugin.getTeamStorageUtil().isHomeSet(team));
        }

        return "";
    }

    private static @NotNull String getTeamMemberSize(Team team) {
        if (team != null) {
            return String.valueOf(team.getTeamMembers().size());
        }

        return "";
    }

    private static @NotNull String getTeamAllySize(Team team) {
        if (team != null) {
            return String.valueOf(team.getTeamAllies().size());
        }

        return "";
    }

    private static @NotNull String getTeamEnemySize(Team team) {
        if (team != null) {
            return String.valueOf(team.getTeamEnemies().size());
        }

        return "";
    }

    private static @NotNull String getIsInTeam(Team team) {
        return String.valueOf(team != null);
    }

    private @NotNull String getIsMyTeam(@Nullable Team myTeam, @Nullable Team otherTeam) {
        if (myTeam == null || otherTeam == null) {
            return String.valueOf(false);
        }

        return String.valueOf(myTeam.isMyTeam(otherTeam));
    }

    private @NotNull String getIsMyEnemy(@Nullable Team myTeam, @Nullable Team otherTeam) {
        if (myTeam == null || otherTeam == null) {
            return String.valueOf(false);
        }

        return String.valueOf(myTeam.isMyEnemy(otherTeam));
    }

    private @NotNull String getIsMyAlly(@Nullable Team myTeam, @Nullable Team otherTeam) {
        if (myTeam == null || otherTeam == null) {
            return String.valueOf(false);
        }

        return String.valueOf(myTeam.isMyAlly(otherTeam));
    }

    private @NotNull String getIsNeutral(@Nullable Team myTeam, @Nullable Team otherTeam) {
        if (myTeam == null || otherTeam == null) {
            return String.valueOf(true);
        }

        return String.valueOf(myTeam.isNeutral(otherTeam));
    }

}
