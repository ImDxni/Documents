package org.waraccademy.documenti.listener;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.waraccademy.documenti.Documents;
import org.waraccademy.documenti.service.ComputerService;
import org.waraccademy.documenti.service.object.AbstractComputer;

import java.util.Optional;

import static org.waraccademy.documenti.utils.Utils.color;

public class BlockListener implements Listener {
    private final ComputerService service = Documents.getInstance().getService();
    private final YamlConfiguration config = Documents.getInstance().getConfig();

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();

        Location loc = e.getBlock().getLocation();

        Optional<AbstractComputer> optional = service.getComputer(loc);

        if(optional.isPresent()){
            if(!p.isSneaking()){
                e.setCancelled(true);
                p.sendMessage(color(config.getString("messages.cant-break")));
                return;
            }

            AbstractComputer computer = optional.get();

            service.removeComputer(computer);

            p.sendMessage(color(config.getString("messages.computer-removed")));
        }
    }
}
