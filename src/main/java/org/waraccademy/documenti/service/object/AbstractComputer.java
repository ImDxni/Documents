package org.waraccademy.documenti.service.object;

import org.bukkit.entity.Player;
import org.waraccademy.documenti.utils.Triple;


public abstract class AbstractComputer {
    private final Triple<Integer> location;

    public AbstractComputer(Triple<Integer> location) {
        this.location = location;
    }

    public abstract void interact(Player p);

    public Triple<Integer> getLocation() {
        return location;
    }


    public abstract String getSection();
}
