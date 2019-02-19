package st.bleeker.chocolate.gradle.plugin.user;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;
import st.bleeker.chocolate.gradle.common.task.*;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class UserPlugin implements Plugin<Project> {

    private Project project;
    private MinecraftExtension minecraftExtension;
    private MinecraftProvider minecraftProvider;

    private TaskProvider<DownloadVersionManifest>       dlVersionManifest;
    private TaskProvider<DownloadVersionMeta>           dlVersionMeta;
    private TaskProvider<DownloadAssetMeta>             dlAssetMeta;
    private TaskProvider<DownloadMinecraftJars>         dlmcJars;
    private TaskProvider<DownloadMinecraftAssets>       dlmcAssets;
    private TaskProvider<DownloadLibraryJars>           dlLibJars;

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
        dlVersionMeta =     this.project.getTasks().register("downloadVersionMeta",
                                                             DownloadVersionMeta.class,
                                                             this.project, minecraftExtension);
        dlAssetMeta =       this.project.getTasks().register("downloadAssetMeta",
                                                             DownloadAssetMeta.class,
                                                             this.project, minecraftExtension);
        dlmcJars =          this.project.getTasks().register("downloadMinecraftJars",
                                                             DownloadMinecraftJars.class,
                                                             this.project, minecraftExtension);
        dlmcAssets =        this.project.getTasks().register("downloadMinecraftAssets",
                                                             DownloadMinecraftAssets.class,
                                                             this.project, minecraftExtension);
        dlLibJars =         this.project.getTasks().register("downloadLibraryJars",
                                                             DownloadLibraryJars.class,
                                                             this.project, minecraftExtension);
    }

    private void configureTasks() {
        dlVersionManifest.configure(task -> {
            task.setVersionID(minecraftExtension.mcVersionID);
            task.setManifest(new File(task.getMinecraftCache(), "version_manifest.json"));
        });
        dlVersionMeta.configure(task -> {
            //todo: make this only execute when needed
            try {
                dlVersionManifest.get().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            task.dependsOn(dlVersionManifest.get());
            task.setVersionID(dlVersionManifest.get().getVersionID());
            task.setManifest(dlVersionManifest.get().getManifest());
            task.setVersionMeta(new File(task.getMinecraftVersionCache(task.getVersionID()),
                                         task.getVersionID() + ".json"));
        });
        dlAssetMeta.configure(task -> {
            //todo: make this only execute when needed
            try {
                dlVersionMeta.get().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            task.dependsOn(dlVersionMeta.get());
            task.setAssetID(dlVersionMeta.get().getAssetID());
            task.setVersionID(dlVersionMeta.get().getVersionID());
            task.setVersionMeta(dlVersionMeta.get().getVersionMeta());
            task.setAssetMeta(new File(task.getMinecraftAssetIndexCache(),
                                       task.getAssetID() + ".json"));
        });
        dlmcJars.configure(task -> {
            task.dependsOn(dlVersionMeta.get());
            task.setVersionID(dlVersionMeta.get().getVersionID());
            task.setVersionMeta(dlVersionMeta.get().getVersionMeta());
            File cache = task.getMinecraftVersionCache(task.getVersionID());
            task.setOutput(new HashMap<>());
            task.addOutput("client", new File(cache, "client.jar"));
            task.addOutput("server", new File(cache, "server.jar"));
        });
        dlmcAssets.configure(task -> {
            task.dependsOn(dlAssetMeta.get());
            task.setAssetID(dlAssetMeta.get().getAssetID());
            task.setAssetMeta(dlAssetMeta.get().getAssetMeta());
            task.setAssetDir(task.getMinecraftAssetObjectCache());
        });
        dlLibJars.configure(task -> {
            task.dependsOn(dlVersionMeta.get());
            task.setVersionID(dlVersionMeta.get().getVersionID());
            task.setVersionMeta(dlVersionMeta.get().getVersionMeta());
            task.setLibraryDir(task.getMinecraftLibraryCache());
        });
    }
}
