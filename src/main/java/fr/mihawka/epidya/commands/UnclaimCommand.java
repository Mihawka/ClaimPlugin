package fr.mihawka.epidya.commands;

import fr.mihawka.epidya.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnclaimCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (args.length != 0) return false;
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        Main.CLAIMS.unclaimHere(p);

        return true;
    }
}
