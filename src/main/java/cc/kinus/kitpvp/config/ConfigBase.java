package cc.kinus.kitpvp.config;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.utils.YamlUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @Author RE
 * <p>
 * 配置文件基础类
 * 读取每个配置文件需要继承此类
 */
public class ConfigBase {
    private File file;
    private YamlConfiguration configuration;
    private String name;

    public ConfigBase(String name) {
        this.name = name;
        this.file = new File(KitPVP.getInstance().getDataFolder(), name);
        if (!this.file.exists()) {
            KitPVP.getInstance().saveResource(name, false);
        }
        this.configuration = YamlUtil.load(this.file);
        KitPVP.log("Loaded configuration: " + this.name);
    }

    public void save() {
        try {
            this.configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.configuration = YamlUtil.load(this.file);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(YamlConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigBase that = (ConfigBase) o;

        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (configuration != null ? !configuration.equals(that.configuration) : that.configuration != null)
            return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = file != null ? file.hashCode() : 0;
        result = 31 * result + (configuration != null ? configuration.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConfigBase{" +
                "file=" + file +
                ", configuration=" + configuration +
                ", name='" + name + '\'' +
                '}';
    }
}
