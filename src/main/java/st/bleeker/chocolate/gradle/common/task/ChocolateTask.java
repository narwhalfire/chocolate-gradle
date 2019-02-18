package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.DefaultTask;

import java.io.File;

public abstract class ChocolateTask extends DefaultTask {

    public ChocolateTask() {
        this.setGroup("chocolate");
    }

    public File getChocolateCache() {
        return ensureChild(getProject().getBuildDir(), "chocolate");
    }

    public File getMinecraftCache() {
        return ensureChild(getChocolateCache(), "minecraft");
    }

    public File getMinecraftVersionCache(String mcVersionID) {
        return ensureChild(getMinecraftCache(), mcVersionID);
    }

    public File getMinecraftLibraryCache() {
        return ensureChild(getMinecraftCache(), "libraries");
    }

    public File getMinecraftAssetCahce() {
        return ensureChild(getMinecraftCache(), "assets");
    }

    private File ensureChild(File parent, String child) {
        File dir = new File(parent, child);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

}
