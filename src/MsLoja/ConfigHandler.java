package MsLoja;


import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


public class ConfigHandler extends YamlConfiguration {

    private File file = null;

    public ConfigHandler(Plugin pl, String name) throws IOException, InvalidConfigurationException {
        file = new File(pl.getDataFolder(), name);
        if (!this.file.exists()) {
            if (pl.getResource(name) != null) {
                pl.saveResource(name, false);
                pl.getServer().getConsoleSender().sendMessage("[" + pl.getName() + "] arquivo " + name + " foi criado com exito no diretorio " + pl.getDataFolder().getAbsolutePath() + " !");
            }
        } else {
            this.file.mkdirs();
            this.file.createNewFile();
        }
        load(this.file);
    }

    public void save() throws IOException {
        save(this.file);
    }

    public void reload() {
        reload();
    }

    public boolean trySave() {
        try {
            save();
            return true;
        } catch (IOException ex) {
        }
        return false;
    }

    public String getStringCorrect(String path) {
        return this.getString(path).replaceAll("&", "§");
    }

    public String getStringReplaced(String path, String regex, String after) {
        return this.getString(path).replaceAll("&", "§").replaceAll(regex, after);
    }

    public String getStringReplaced(String path, String regex, String after, String regex2, String after2) {
        return this.getString(path).replaceAll("&", "§").replaceAll(regex, after).replaceAll(regex2, after2);
    }

    public String getStringReplaced(String path, String regex, String after, String regex2, String after2,
                                    String regex3, String after3) {
        return this.getString(path).replaceAll("&", "§").replaceAll(regex, after).replaceAll(regex2, after2).replaceAll(regex3,
                after3);
    }
}