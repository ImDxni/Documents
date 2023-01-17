package org.waraccademy.documenti.utils;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.waraccademy.documenti.Documents;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&',s);
    }

    public static ItemStack getItem(ConfigurationSection section, Map<String,String> placeholders){
        Material material = Material.valueOf(section.getString("material"));

        ItemStack item;
        if(material == Material.PLAYER_HEAD)
            item = Documents.getInstance().getHead().getItemHead(section.getString("data"));
        else
            item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(processPlaceholders(section.getString("name"),placeholders));

        meta.setLore(section.getStringList("lore")
                .stream()
                .map(line -> processPlaceholders(line,placeholders))
                .collect(Collectors.toList()));

        item.setItemMeta(meta);

        return item;
    }

    public static String processPlaceholders(String line, Map<String,String> placeholders){
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            line = line.replace(entry.getKey(),entry.getValue());
        }

        return color(line);
    }

    public static void setupGlass(InventoryContents contents, ConfigurationSection section) {
        for (String key : section.getConfigurationSection("empty").getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection("empty."+key);
            SlotPos slot = SlotPos.of(itemSection.getInt("slot.row"), itemSection.getInt("slot.column"));
            ItemStack item = getItem(itemSection, Collections.emptyMap());

            contents.set(slot, ClickableItem.empty(item));
        }
    }

    public static ItemStack getHead(Player p) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(p);
        skull.setItemMeta(meta);

        return skull;
    }

}
