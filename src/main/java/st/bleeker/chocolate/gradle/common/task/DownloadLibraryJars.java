package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;

public class DownloadLibraryJars extends ChocolateTask {

    private Project project;
    private MinecraftExtension minecraftExtension;

    private File input;
    private File output;

    @Inject
    public DownloadLibraryJars(Project project, MinecraftExtension minecraftExtension) {
        this.project = project;
        this.minecraftExtension = minecraftExtension;
    }

    @TaskAction
    public void downloadLibraryJars() {

    }

}
