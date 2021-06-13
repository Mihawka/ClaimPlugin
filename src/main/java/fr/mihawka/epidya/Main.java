package fr.mihawka.epidya;

import fr.mihawka.epidya.commands.ClaimCommand;
import fr.mihawka.epidya.commands.UnclaimCommand;
import fr.mihawka.epidya.events.OnBreakBlock;
import fr.mihawka.epidya.events.OnExplosion;
import fr.mihawka.epidya.events.OnPlaceBlock;
import fr.mihawka.epidya.storage.ClaimStorageModule;
import fr.mihawka.epidya.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static Logger LOG;
    public static ClaimStorageModule CLAIMS;
    public static ConfigManager CONFIG;
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        LOG = Bukkit.getLogger();
        LOG.info("[ClaimPlugin] Init...");
        //===================================================
        // Utility
        //===================================================
        INSTANCE = this;
        CONFIG = new ConfigManager(this);
        CLAIMS = CONFIG.getStorage();
        //===================================================
        // COMMAND
        //===================================================
        Objects.requireNonNull(getCommand("claim")).setExecutor(new ClaimCommand());
        Objects.requireNonNull(getCommand("unclaim")).setExecutor(new UnclaimCommand());
        //===================================================
        // EVENTS
        //===================================================
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new OnBreakBlock(), this);
        manager.registerEvents(new OnPlaceBlock(), this);
        manager.registerEvents(new OnExplosion(), this);
        //===================================================
        LOG.info("[ClaimPlugin] Init !");
    }

    @Override
    public void onDisable() {
        LOG.info("[ClaimPlugin] Shutdown...");
        CONFIG.save();
        LOG.info("[ClaimPlugin] Shutdown !");
    }
}
