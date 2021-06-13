package fr.mihawka.epidya.utils;

import com.google.gson.Gson;
import fr.mihawka.epidya.Main;
import fr.mihawka.epidya.storage.ClaimStorageModule;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class ConfigManager {
    File fileConfig;

    //No need to save
    File fileMessage;

    //Keep in memory messages content | READ ONLY !
    final YamlConfiguration message;
    final String prefix;

    public ConfigManager(Main instance) {
        fileConfig = new File(instance.getDataFolder().getAbsolutePath());
        fileConfig.mkdir();

        fileConfig = new File(String.format("%s/config.json", instance.getDataFolder().getAbsolutePath()));
        if (!fileConfig.exists()) {
            try {
                fileConfig.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileMessage = new File(String.format("%s/message.yml", instance.getDataFolder().getAbsolutePath()));
        if (!fileMessage.exists()) {
            try {
                fileMessage.createNewFile();
                BufferedWriter writer = Files.newBufferedWriter(fileMessage.toPath(), StandardCharsets.UTF_8);
                writer.write("prefix: \"&a[Claim Plugin] &r\"\n" +
                        "\n" +
                        "already_claim: \"Ce chunk est déjà claim !\"\n" +
                        "already_member: \"Le joueur %player% est déjà membre !\"\n" +
                        "\n" +
                        "add_member: \"Le joueur %player% à été ajouté au claim !\"\n" +
                        "remove_member: \"Le joueur %player% à été retiré du claim !\"\n" +
                        "\n" +
                        "success_claim: \"Ce chunk à été claim !\"\n" +
                        "success_unclaim: \"Le chunk à bien été unclaim !\"\n" +
                        "\n" +
                        "not_claim: \"Ce chunk n'est pas claim !\"\n" +
                        "not_owner: \"Vous n'êtes pas le propriétaire de ce chunk !\"\n" +
                        "not_member: \"Le joueur %player% n'est pas membre !\"\n" +
                        "player_not_found: \"Le joueur %player% n'existe pas ou n'est pas en ligne !\"\n" +
                        "\n" +
                        "prevent_block_place: \"Vous n'êtes pas autorisé à placer des blocks ici !\"\n" +
                        "prevent_block_break: \"Vous n'êtes pas autorisé à casser des blocks ici !\"\n" +
                        "prevent_block_explode: \"Vous n'êtes pas autorisé à exploser des blocks ici !\"\n" +
                        "\n" +
                        "list_members:\n" +
                        "    - \"&a===================================================\"\n" +
                        "    - \"&bPropriétaire: %owner%\"\n" +
                        "    - \"&bListe des membres: %members%\"\n" +
                        "    - \"&a===================================================\"");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        message = YamlConfiguration.loadConfiguration(fileMessage);
        prefix = message.getString("prefix").replace('&', '§');
    }

    public ClaimStorageModule getStorage() {
        if (fileConfig.length() == 0) return new ClaimStorageModule();
        else {
            try {
                return new Gson().fromJson(String.join("\n", Files.readAllLines(fileConfig.toPath())), ClaimStorageModule.class);
            } catch (IOException e) {
                Main.LOG.info("Echec de la récupération de la config");
                return new ClaimStorageModule();
            }
        }
    }
    public String getMessage(String tag) {
        //Color
        return prefix + message.getString(tag).replace('&', '§');
    }
    public List<String> getMessages(String tag) {
        return message.getStringList(tag);
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(fileConfig);
            writer.write(new Gson().toJson(Main.CLAIMS));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
