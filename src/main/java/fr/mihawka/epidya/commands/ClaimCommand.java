package fr.mihawka.epidya.commands;

import fr.mihawka.epidya.Main;
import fr.mihawka.epidya.utils.ChunkHash;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        if (args.length == 0) {
            Main.CLAIMS.claimHere(p);
            return true;
        }
        else if (args.length == 1) {
            if (!args[0].equalsIgnoreCase("list")) return false;
            Main.CLAIMS.listMembers(p);
            return true;
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                Main.CLAIMS.addMember(args[1], p);
                return true;
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                Main.CLAIMS.removeMember(args[1], p);
                return true;
            }

        }
        return false;
    }
}
