package org.waraccademy.documenti.listener;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.waraccademy.documenti.Documents;
import org.waraccademy.playtime.PlayTime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.waraccademy.documenti.utils.Utils.processPlaceholders;
import static org.waraccademy.playtime.utils.Utils.convertMillis;

public class CardListener implements Listener {
    private final YamlConfiguration config = Documents.getInstance().getConfig();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getAction().name().startsWith("RIGHT"))
            return;

        Player p = e.getPlayer();
        ItemStack hand = p.getInventory().getItemInMainHand();

        if (hand == null || hand.getType() == Material.AIR)
            return;


        NBTItem nbtItem = new NBTItem(hand);

        if(!nbtItem.hasKey("cardType"))
            return;

        e.setCancelled(true);

        if (nbtItem.getString("cardType").equals("IDENTITY")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(nbtItem.getString("cardOwner")));

            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);

            BookMeta meta = (BookMeta) item.getItemMeta();

            Map<String, String> placeholders = new HashMap<>();

            String prefix = Documents.getInstance().getChat().getPlayerPrefix(p.getWorld().getName(),target);

            placeholders.put("%job%", prefix);
            PlayTime.getInstance().getManager().getPlayer(target.getName()).whenComplete((player, error) -> {
                if (error != null) {
                    error.printStackTrace();
                    return;
                }

                long time = PlayTime.getInstance().getService().getTime(p.getName());

                placeholders.put("%time%", convertMillis(time + player.getWeek()));

                StringBuilder builder = new StringBuilder();

                ConfigurationSection section = config.getConfigurationSection("identity-book");

                for (String key : section.getKeys(false)) {
                    for(String line : section.getStringList(key)){
                        builder.append(processPlaceholders(line,placeholders))
                                .append("\n");
                    }

                    meta.addPage(builder.toString());
                    builder.setLength(0);
                }
                meta.setAuthor("Segreteria");
                meta.setTitle("Carta");
                item.setItemMeta(meta);

                p.openBook(item);
            });

        }
    }
}
