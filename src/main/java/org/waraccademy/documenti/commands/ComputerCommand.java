package org.waraccademy.documenti.commands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.waraccademy.documenti.Documents;
import org.waraccademy.documenti.service.ComputerService;
import org.waraccademy.documenti.service.object.AbstractComputer;
import org.waraccademy.documenti.service.object.impl.HospitalComputer;
import org.waraccademy.documenti.service.object.impl.SecretaryComputer;
import org.waraccademy.documenti.utils.Triple;

import java.util.Locale;

import static org.waraccademy.documenti.utils.Utils.color;

public class ComputerCommand implements CommandExecutor {
    private final ComputerService service = Documents.getInstance().getService();
    private final YamlConfiguration config = Documents.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return true;

        if(args.length != 1)
            return true;

        Player p = (Player) sender;

        if(!p.hasPermission("metropolis.documenti.computer"))
            return true;

        Block block = p.getTargetBlock(null,5);

        if(!block.getType().isBlock())
            return true;

        Location loc = block.getLocation();

        String type = args[0].toLowerCase(Locale.ROOT);

        AbstractComputer computer;
        Triple<Integer> triple = Triple.of(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ());

        switch(type){
            case "ospedale":
                computer = new HospitalComputer(triple);
                break;
            case "segreteria":
                computer = new SecretaryComputer(triple);
                break;
            default:
                p.sendMessage(color(config.getString("messages.wrong-type")));
                return true;
        }

        service.addComputer(computer);

        p.sendMessage(color(config.getString("messages.computer-added")));
        return true;
    }
}
