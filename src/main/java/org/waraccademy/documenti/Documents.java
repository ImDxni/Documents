package org.waraccademy.documenti;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.waraccademy.documenti.commands.ComputerCommand;
import org.waraccademy.documenti.configuration.FileManager;
import org.waraccademy.documenti.listener.BlockListener;
import org.waraccademy.documenti.listener.CardListener;
import org.waraccademy.documenti.listener.HeadDatabaseListener;
import org.waraccademy.documenti.service.ComputerService;
import org.waraccademy.documenti.service.object.AbstractComputer;
import org.waraccademy.documenti.utils.Triple;

import java.io.File;
import java.io.IOException;

public final class Documents extends JavaPlugin {
    private static Documents instance;

    private YamlConfiguration config;
    private YamlConfiguration data;
    private File dataFile;

    private HeadDatabaseAPI head;

    private ComputerService service;

    private Chat chat = null;


    @Override
    public void onEnable() {
        instance = this;

        setupChat();

        FileManager configManager = new FileManager("config",this);

        configManager.saveDefault();

        config = configManager.getConfig();

        FileManager dataManager = new FileManager("data"+ File.separator + "data",this);
        dataManager.saveDefault();

        data = dataManager.getConfig();
        dataFile = dataManager.getFile();

        service = new ComputerService();

        Bukkit.getPluginManager().registerEvents(new HeadDatabaseListener(),this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(),this);
        Bukkit.getPluginManager().registerEvents(service,this);
        Bukkit.getPluginManager().registerEvents(new CardListener(),this);


        getCommand("computer").setExecutor(new ComputerCommand());

    }

    @Override
    public void onDisable() {
        data.createSection("secretary");
        data.createSection("hospital");

        int i = 0;
        for (AbstractComputer computer : service.getComputers()) {
            ConfigurationSection section = data.getConfigurationSection(computer.getSection());

            Triple<Integer> loc = computer.getLocation();
            section.set(i + ".x",loc.getFirst());
            section.set(i + ".y",loc.getSecond());
            section.set(i + ".z",loc.getThird());

            i++;
        }

        try {
            data.save(dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
    }

    @Override
    public @NotNull YamlConfiguration getConfig() {
        return config;
    }

    public YamlConfiguration getData() {
        return data;
    }

    public static Documents getInstance() {
        return instance;
    }

    public ComputerService getService() {
        return service;
    }

    public HeadDatabaseAPI getHead() {
        return head;
    }

    public Chat getChat() {
        return chat;
    }

    public void setHead(HeadDatabaseAPI head) {
        this.head = head;
    }
}
