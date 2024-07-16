package dev.xf3d3.ultimateteams.hooks;

import dev.xf3d3.ultimateteams.UltimateTeams;
import dev.xf3d3.ultimateteams.models.Team;
import dev.xf3d3.ultimateteams.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

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
        Team team = plugin.getTeamStorageUtil().findTeamByOfflineOwner(player);
        if (team == null) {
            team = plugin.getTeamStorageUtil().findTeamByOfflinePlayer(player);
        }

        if (params.equalsIgnoreCase("teamName")) {
            if (team != null) {
                return Utils.Color(team.getTeamFinalName() + "&r ");
            }

            return "";
        }

        if (params.equalsIgnoreCase("teamPrefix")) {
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

        if (params.equalsIgnoreCase("friendlyFire")) {
            if (team != null) {
                return String.valueOf(team.isFriendlyFireAllowed());
            }

            return "";
        }

        if (params.equalsIgnoreCase("teamHomeSet")) {
            if (team != null) {
                return String.valueOf(plugin.getTeamStorageUtil().isHomeSet(team));
            }

            return "";

        }

        if (params.equalsIgnoreCase("teamMembersSize")) {
            if (team != null) {
                return String.valueOf(team.getTeamMembers().size());
            }

            return "";

        }

        if (params.equalsIgnoreCase("teamAllySize")) {
            if (team != null) {
                return String.valueOf(team.getTeamAllies().size());
            }

            return "";
        }

        if (params.equalsIgnoreCase("teamEnemySize")) {
            if (team != null) {
                return String.valueOf(team.getTeamEnemies().size());
            }

            return "";
        }

        if (params.equalsIgnoreCase("isInTeam")) {
            return String.valueOf(team != null);
        }

        return null;
    }
}
