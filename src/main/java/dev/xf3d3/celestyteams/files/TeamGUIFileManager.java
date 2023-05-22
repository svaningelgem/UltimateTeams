package dev.xf3d3.celestyteams.files;

import dev.xf3d3.celestyteams.CelestyTeams;
import dev.xf3d3.celestyteams.utils.ColorUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class TeamGUIFileManager {

    private CelestyTeams plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    Logger logger = CelestyTeams.getPlugin().getLogger();

    public TeamGUIFileManager(CelestyTeams plugin){
        this.plugin = plugin;
        saveDefaultClanGUIConfig();
    }

    public void reloadClanGUIConfig(){

        if (this.configFile == null){
            this.configFile = new File(plugin.getDataFolder(), "teamgui.yml");
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.plugin.getResource("teamgui.yml");
        if (defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getClanGUIConfig(){
        if (this.dataConfig == null){
            this.reloadClanGUIConfig();
        }
        return this.dataConfig;
    }

    public void saveClanGUIConfig() {
        if (this.dataConfig == null||this.configFile == null){
            return;
        }
        try {
            this.getClanGUIConfig().save(this.configFile);
        }catch (IOException e){
            logger.severe(ColorUtils.translateColorCodes("&6CelestyTeams: &4Could not save teamgui.yml"));
            logger.severe(ColorUtils.translateColorCodes("&6CelestyTeams: &4Check the below message for the reasons!"));
            e.printStackTrace();
        }
    }

    public void saveDefaultClanGUIConfig(){
        if (this.configFile == null){
            this.configFile = new File(plugin.getDataFolder(), "teamgui.yml");
        }
        if (!this.configFile.exists()){
            this.plugin.saveResource("teamgui.yml", false);
        }
    }
}
