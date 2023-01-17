package org.waraccademy.documenti.service.object.impl;

import org.bukkit.entity.Player;
import org.waraccademy.documenti.inventories.secretary.SecretaryInventory;
import org.waraccademy.documenti.service.object.AbstractComputer;
import org.waraccademy.documenti.utils.Triple;
public class SecretaryComputer extends AbstractComputer {
    public SecretaryComputer(Triple<Integer> location) {
        super(location);
    }

    @Override
    public void interact(Player p) {
        if(p.hasPermission("metropolis.segreteria.computer")){
            SecretaryInventory.getInventory().open(p);
        }
    }

    @Override
    public String getSection() {
        return "secretary";
    }

}
