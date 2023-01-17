package org.waraccademy.documenti.inventories.secretary;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.waraccademy.documenti.Documents;
import java.util.*;

import static org.waraccademy.documenti.utils.Utils.color;

public class SelectInventory implements InventoryProvider {
    private final SecretaryInventory.CardType type;
    private final List<ClickableItem> items;

    public SelectInventory(SecretaryInventory.CardType type,List<ClickableItem> items) {
        this.type = type;
        this.items = items;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        pagination.setItems(items.toArray(new ClickableItem[0]));
        pagination.setItemsPerPage(7);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));

        contents.set(2, 3, ClickableItem.of(new ItemStack(Material.ARROW),
                e -> getInventory(type,items).open(player, pagination.previous().getPage())));
        contents.set(2, 5, ClickableItem.of(new ItemStack(Material.ARROW),
                e -> getInventory(type,items).open(player, pagination.next().getPage())));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static SmartInventory getInventory(SecretaryInventory.CardType type,List<ClickableItem> items){
        return SmartInventory.builder()
                .provider(new SelectInventory(type,items))
                .title(color(Documents.getInstance().getConfig().getString("inventory.select.title")))
                .size(3,9)
                .build();
    }
}
