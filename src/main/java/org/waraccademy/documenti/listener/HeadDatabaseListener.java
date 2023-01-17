package org.waraccademy.documenti.listener;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.waraccademy.documenti.Documents;

public class HeadDatabaseListener implements Listener {


    @EventHandler
    public void onLoad(DatabaseLoadEvent e){
        System.out.println("Caricato aaaaaaaaaaaaaa");
        Documents.getInstance().setHead(new HeadDatabaseAPI());

        System.out.println(Documents.getInstance().getHead());
    }
}
