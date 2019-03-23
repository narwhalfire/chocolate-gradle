package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import java.io.File;

public abstract class ChocolateTask extends DefaultTask {

    protected Project project;
    protected MinecraftExtension minecraftExtension;

    public ChocolateTask() {
        this.setGroup("chocolate");
    }

    public ChocolateTask(Project project, MinecraftExtension minecraftExtension) {
        this.setGroup("chocolate");
        this.project = project;
        this.minecraftExtension = minecraftExtension;
    }

}
