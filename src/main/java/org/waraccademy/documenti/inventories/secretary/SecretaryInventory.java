package org.waraccademy.documenti.inventories.secretary;

import de.tr7zw.nbtapi.NBTItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.waraccademy.documenti.Documents;
import org.waraccademy.documenti.utils.Utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.waraccademy.documenti.utils.Utils.color;

public class SecretaryInventory implements InventoryProvider {
    private final YamlConfiguration config = Documents.getInstance().getConfig();


    @Override
    public void init(Player player, InventoryContents contents) {
        ConfigurationSection section = config.getConfigurationSection("inventory.secretary.items");

        Utils.setupGlass(contents, section);

        ConfigurationSection identity = section.getConfigurationSection("identity");

        SlotPos identitySlot = SlotPos.of(identity.getInt("slot.row"),identity.getInt("slot.column"));
        ItemStack identityItem = Utils.getItem(identity,Collections.emptyMap());
        
        contents.set(identitySlot, ClickableItem.of(identityItem, chooseClick(player,CardType.IDENTITY)));

        ConfigurationSection health = section.getConfigurationSection("health");

        SlotPos healthSlot = SlotPos.of(health.getInt("slot.row"),health.getInt("slot.column"));
        ItemStack healthItem = Utils.getItem(health,Collections.emptyMap());

        contents.set(healthSlot, ClickableItem.of(healthItem, chooseClick(player,CardType.HEALTH)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static SmartInventory getInventory(){
        return SmartInventory.builder()
                .provider(new SecretaryInventory())
                .title(color(Documents.getInstance().getConfig().getString("inventory.secretary.title")))
                .size(3,9)
                .build();
    }

    public Consumer<InventoryClickEvent> chooseClick(Player p,CardType type){
        return (click) -> {
            Set<Player> nearby = p.getNearbyEntities(10,10,10)
                    .stream()
                    .filter(entity -> entity instanceof Player)
                    .map(entity -> (Player)entity)
                    .collect(Collectors.toSet());

            CompletableFuture.supplyAsync(() -> nearby.stream()
                            .map(player -> ClickableItem.of(Utils.getHead(player),clickConsumer(player,type)))
                            .collect(Collectors.toList()))
                    .thenAccept((result) -> {
                        Bukkit.getScheduler().runTask(Documents.getInstance(), () -> SelectInventory.getInventory(type,result).open(p));
                    });

        };
    }

    private Consumer<InventoryClickEvent> clickConsumer(Player p,CardType type) {
        return (click) -> {
            Player sender = (Player) click.getWhoClicked();
            ConfigurationSection section = config.getConfigurationSection(type.getPath());

            LocalDateTime time = LocalDateTime.now();

            time = time.plusDays(config.getInt("expires-in-days"));

            String expires = time.format(DateTimeFormatter.ISO_LOCAL_DATE);

            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("%player%", p.getName());
            placeholders.put("%expiration%", expires);

            ItemStack item = Utils.getItem(section, placeholders);

            NBTItem nbtItem = new NBTItem(item);

            nbtItem.setString("cardOwner", p.getUniqueId().toString());
            nbtItem.setString("cardType", type.name());

            sender.getInventory().addItem(nbtItem.getItem());

            sender.sendMessage(color(config.getString("messages.card-printed")));
        };
    }


    protected enum CardType {
        IDENTITY("items.identity"),
        HEALTH("items.health");

        private final String path;

        CardType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

}
