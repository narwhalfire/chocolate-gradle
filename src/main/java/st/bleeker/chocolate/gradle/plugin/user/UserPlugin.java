package st.bleeker.chocolate.gradle.plugin.user;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskProvider;
import st.bleeker.chocolate.gradle.common.task.*;

import java.util.Collections;

public class UserPlugin implements Plugin<Project> {

    private Project project;
    private MinecraftExtension minecraftExtension;

    @Override
    public void apply(Project target) {
        this.project = target;
        this.project.apply(Collections.singletonMap("plugin", "java"));
        this.minecraftExtension = this.project.getExtensions().create("minecraft", MinecraftExtension.class,
                                                                      this.project);

        this.project.getTasks().register("downloadVersionManifest", DownloadVersionManifest.class,
                                         this.project, minecraftExtension);
        this.project.getTasks().register("downloadVersionMeta", DownloadVersionMeta.class,
                                         this.project, minecraftExtension);
        this.project.getTasks().register("downloadMinecraftJars", DownloadMinecraftJars.class,
                                         this.project, minecraftExtension);
//        this.project.getTasks().register("downloadMinecraftAssets", DownloadMinecraftAssets.class,
//                                         this.project, minecraftExtension);
//        this.project.getTasks().register("downloadLibraryJars", DownloadLibraryJars.class,
//                                         this.project, minecraftExtension);

    }
}
