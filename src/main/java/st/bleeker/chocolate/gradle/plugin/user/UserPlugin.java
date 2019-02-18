package st.bleeker.chocolate.gradle.plugin.user;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;
import st.bleeker.chocolate.gradle.common.task.*;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;

import java.io.File;
import java.util.Collections;

public class UserPlugin implements Plugin<Project> {

    private Project project;
    private MinecraftExtension minecraftExtension;
    private MinecraftProvider minecraftProvider;

    private TaskProvider<DownloadVersionManifest> dlVersionManifest;
    private TaskProvider<DownloadVersionMeta> dlVersionMeta;
    private TaskProvider<DownloadMinecraftJars> dlmcJars;
    private TaskProvider<DownloadMinecraftAssets> dlmcAssets;
    private TaskProvider<DownloadLibraryJars> dlLibJars;

    @Override
    public void apply(Project target) {



        minecraftProvider = new MinecraftProvider();

        this.project = target;
        this.project.apply(Collections.singletonMap("plugin", "java"));
        this.minecraftExtension = this.project.getExtensions().create("minecraft", MinecraftExtension.class,
                                                                      this.project, minecraftProvider);

        registerTasks();
        configureTasks();

    }

    private void registerTasks() {
        dlVersionManifest = this.project.getTasks().register("downloadVersionManifest",
                                                             DownloadVersionManifest.class,
                                                             this.project, minecraftExtension);
        dlVersionMeta = this.project.getTasks().register("downloadVersionMeta",
                                                         DownloadVersionMeta.class,
                                                         this.project, minecraftExtension);
        dlmcJars = this.project.getTasks().register("downloadMinecraftJars",
                                                    DownloadMinecraftJars.class,
                                                    this.project, minecraftExtension);
        dlmcAssets = this.project.getTasks().register("downloadMinecraftAssets",
                                                      DownloadMinecraftAssets.class,
                                                      this.project, minecraftExtension);
        dlLibJars = this.project.getTasks().register("downloadLibraryJars",
                                                     DownloadLibraryJars.class,
                                                     this.project, minecraftExtension);
    }

    private void configureTasks() {
        dlVersionManifest.configure(task -> {
            task.setManifest(new File(task.getMinecraftCache(), "version_manifest.json"));
        });
        dlVersionMeta.configure(task -> {
            task.dependsOn(dlVersionManifest.get());
            task.setVersionID(minecraftExtension.mcVersionID);
            task.setManifest(dlVersionManifest.get().getManifest());
            task.setVersionMeta(new File(task.getMinecraftVersionCache(task.getVersionID()),
                                         task.getVersionID() + ".json"));
        });
        dlmcJars.configure(task -> {
            task.dependsOn(dlVersionMeta.get());
            task.setVersionID(dlVersionMeta.get().getVersionID());
            task.setVersionMeta(dlVersionMeta.get().getVersionMeta());
            File cache = task.getMinecraftVersionCache(task.getVersionID());
            task.addOutput("client", new File(cache, "client.jar"));
            task.addOutput("server", new File(cache, "server.jar"));
        });
        dlmcAssets.configure(task -> {

        });
        dlLibJars.configure(task -> {

        });
    }
}
