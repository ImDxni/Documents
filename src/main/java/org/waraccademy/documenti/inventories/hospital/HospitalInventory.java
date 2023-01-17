package org.waraccademy.documenti.inventories.hospital;

import de.tr7zw.nbtapi.NBTItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.waraccademy.documenti.Documents;
import org.waraccademy.documenti.utils.Utils;

import java.util.Collections;

import static org.waraccademy.documenti.utils.Utils.color;

public class HospitalInventory implements InventoryProvider {
    private final YamlConfiguration config = Documents.getInstance().getConfig();

    @Override
    public void init(Player player, InventoryContents contents) {
        ConfigurationSection section = config.getConfigurationSection("inventory.hospital.items");

        Utils.setupGlass(contents, section);

        ConfigurationSection register = section.getConfigurationSection("register");

        SlotPos  registerSlot = SlotPos.of(register.getInt("slot.row"),register.getInt("slot.column"));
        ItemStack registerItem = Utils.getItem(register,Collections.emptyMap());

        contents.set(registerSlot, ClickableItem.of(registerItem, (click) -> {
            ItemStack item = player.getInventory().getItemInMainHand();

            NBTItem nbtItem = new NBTItem(item);

            if(nbtItem.hasKey("cardType") && nbtItem.getString("cardType").equals("HEALTH")){
                nbtItem.setBoolean("cardRegistered",true);
                player.getInventory().setItemInMainHand(nbtItem.getItem());
                player.sendMessage(color(config.getString("messages.card-registered-successfully")));
            } else {
                player.sendMessage(color(config.getString("messages.isnt-health-card")));
            }
        }));

        ConfigurationSection check = section.getConfigurationSection("check");

        SlotPos checkSlot = SlotPos.of(check.getInt("slot.row"),check.getInt("slot.column"));
        ItemStack checkItem = Utils.getItem(check,Collections.emptyMap());

        contents.set(checkSlot, ClickableItem.of(checkItem, (click) -> {
            ItemStack item = player.getInventory().getItemInMainHand();

            if (item == null || item.getType() == Material.AIR)
                return;

            NBTItem nbtItem = new NBTItem(item);

            if(nbtItem.hasKey("cardType") && nbtItem.getString("cardType").equals("HEALTH")){
                String path = nbtItem.hasKey("cardRegistered") ? "messages.card-registered" : "messages.card-not-registered";
                player.sendMessage(color(config.getString(path)));
            } else {
                player.sendMessage(color(config.getString("messages.isnt-health-card")));
            }
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static SmartInventory INVENTORY = SmartInventory.builder()
            .provider(new HospitalInventory())
            .title(color(Documents.getInstance().getConfig().getString("inventory.hospital.title")))
            .size(3,9)
            .build();
}
