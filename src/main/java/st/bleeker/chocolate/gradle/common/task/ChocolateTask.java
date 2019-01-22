package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.DefaultTask;

import java.io.File;

public abstract class ChocolateTask extends DefaultTask {

    public ChocolateTask() {
        this.setGroup("chocolate");
    }

    public File getChocolateCache() {
        File chocolateDir = getProject().getBuildDir().toPath().resolve("chocolate").toFile();
        if (!chocolateDir.exists()) {
            chocolateDir.mkdir();
        }
        return chocolateDir;
    }

    public File getMinecraftCache() {
        File mcDir = getChocolateCache().toPath().resolve("minecraft").toFile();
        if (!mcDir.exists()) {
            mcDir.mkdir();
        }
        return mcDir;
    }

}
