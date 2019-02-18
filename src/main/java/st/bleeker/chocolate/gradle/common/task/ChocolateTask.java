package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.DefaultTask;

import java.io.File;

public abstract class ChocolateTask extends DefaultTask {

    public ChocolateTask() {
        this.setGroup("chocolate");
    }

    public File getChocolateCache() {
        File chocolateDir = new File(getProject().getBuildDir(), "chocolate");
        if (!chocolateDir.exists()) {
            chocolateDir.mkdir();
        }
        return chocolateDir;
    }

    public File getMinecraftCache() {
        File mcDir = new File(getChocolateCache(), "minecraft");
        if (!mcDir.exists()) {
            mcDir.mkdir();
        }
        return mcDir;
    }

    public File getMinecraftVersionCache(String mcVersionID) {
        File vDir = new File(getMinecraftCache(), mcVersionID);
        if (!vDir.exists()) {
            vDir.mkdir();
        }
        return vDir;
    }

    public File getMinecraftLibraryCache() {
        File libDir = new File(getMinecraftCache(), "libraries");
        if (!libDir.exists()) {
            libDir.mkdir();
        }
        return libDir;
    }

}
