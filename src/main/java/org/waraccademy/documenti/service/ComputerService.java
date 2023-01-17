package org.waraccademy.documenti.service;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.waraccademy.documenti.Documents;
import org.waraccademy.documenti.service.object.AbstractComputer;
import org.waraccademy.documenti.service.object.impl.HospitalComputer;
import org.waraccademy.documenti.service.object.impl.SecretaryComputer;
import org.waraccademy.documenti.utils.Triple;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ComputerService implements Listener {
    private final Set<AbstractComputer> computers = new HashSet<>();

    public void addComputer(AbstractComputer computer){
        computers.add(computer);
    }

    public void removeComputer(AbstractComputer computer){
        computers.remove(computer);
    }

    public ComputerService(){
        ConfigurationSection secretarySection = Documents.getInstance().getData().getConfigurationSection("secretary");

        if(secretarySection == null)
            return;

        for (String key : secretarySection.getKeys(false)) {
            int x = secretarySection.getInt(key+".x");
            int y = secretarySection.getInt(key+".y");
            int z = secretarySection.getInt(key+".z");

            addComputer(new SecretaryComputer(Triple.of(x,y,z)));
        }

        ConfigurationSection hospitalSection = Documents.getInstance().getData().getConfigurationSection("hospital");

        for (String key : hospitalSection.getKeys(false)) {
            int x = hospitalSection.getInt(key+".x");
            int y = hospitalSection.getInt(key+".y");
            int z = hospitalSection.getInt(key+".z");

            addComputer(new HospitalComputer(Triple.of(x,y,z)));
        }
    }

    public Optional<AbstractComputer> getComputer(Location loc){
        return computers.stream().filter(computer -> {
            Triple<Integer> computerLoc = computer.getLocation();
            return computerLoc.getFirst() == loc.getBlockX() && computerLoc.getSecond() == loc.getBlockY() && computerLoc.getThird() == loc.getBlockZ();
        }).findFirst();
    }

    public Set<AbstractComputer> getComputers() {
        return computers;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Location loc = e.getClickedBlock().getLocation();

        getComputer(loc).ifPresent(computer -> computer.interact(e.getPlayer()));
    }
}
