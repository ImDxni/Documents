package org.waraccademy.documenti.service.object.impl;

import org.bukkit.entity.Player;
import org.waraccademy.documenti.inventories.hospital.HospitalInventory;
import org.waraccademy.documenti.service.object.AbstractComputer;
import org.waraccademy.documenti.utils.Triple;

public class HospitalComputer extends AbstractComputer {

    public HospitalComputer(Triple<Integer> location) {
        super(location);
    }

    @Override
    public void interact(Player p) {
        if(p.hasPermission("metropolis.ospedale.computer")){
            HospitalInventory.INVENTORY.open(p);
        }
    }

    @Override
    public String getSection() {
        return "hospital";
    }
}
