package st.bleeker.chocolate.gradle.plugin.user;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import st.bleeker.chocolate.gradle.common.task.*;

import java.util.Collections;

public class UserPlugin implements Plugin<Project> {
    private MinecraftExtension minecraftExtension;
    private Project project;

    @Override
    public void apply(Project target) {
        this.project = target;
        this.project.apply(Collections.singletonMap("plugin", "java"));
        this.minecraftExtension = this.project.getExtensions().create("minecraft", MinecraftExtension.class,
                                                                      this.project);

        this.project.getTasks().create("downloadVersionManifest", DownloadVersionManifest.class,
                                       this.project);
        this.project.getTasks().create("downloadVersionMeta", DownloadVersionMeta.class,
                                       this.project, minecraftExtension.mcVersionID, minecraftExtension.mcVersionType);
        this.project.getTasks().create("downloadMinecraftJars", DownloadMinecraftJars.class,
                                       this.project);
        this.project.getTasks().create("downloadMinecraftAssets", DownloadMinecraftAssets.class,
                                       this.project);
        this.project.getTasks().create("downloadLibraryJars", DownloadLibraryJars.class,
                                       this.project);



    }
}
