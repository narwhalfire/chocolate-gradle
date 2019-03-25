package st.bleeker.chocolate.gradle.plugin.user;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;
import st.bleeker.chocolate.gradle.common.task.DecompileJars;
import st.bleeker.chocolate.gradle.common.task.DownloadLibraryJars;
import st.bleeker.chocolate.gradle.common.task.DownloadMinecraftAssets;
import st.bleeker.chocolate.gradle.common.task.DownloadMinecraftJars;
import st.bleeker.chocolate.gradle.common.util.CacheUtils;
import st.bleeker.chocolate.gradle.common.util.Constants;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class UserPlugin implements Plugin<Project> {

    private Project project;
    private MinecraftExtension minecraftExtension;
    private MinecraftProvider minecraftProvider;

    private TaskProvider<DownloadMinecraftJars>         dlmcJars;
    private TaskProvider<DownloadMinecraftAssets>       dlmcAssets;
    private TaskProvider<DownloadLibraryJars>           dlLibJars;
    private TaskProvider<DecompileJars>                 decompJars;

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

    /**
     * Registers the tasks for the project. Should only be assignment and registration.
     */
    private void registerTasks() {
        dlmcJars =          this.project.getTasks().register("downloadMinecraftJars",
                                                             DownloadMinecraftJars.class,
                                                             this.project, minecraftExtension);
        dlmcAssets =        this.project.getTasks().register("downloadMinecraftAssets",
                                                             DownloadMinecraftAssets.class,
                                                             this.project, minecraftExtension);
        dlLibJars =         this.project.getTasks().register("downloadLibraryJars",
                                                             DownloadLibraryJars.class,
                                                             this.project, minecraftExtension);
        decompJars =        this.project.getTasks().register("decompJars",
                                                             DecompileJars.class,
                                                             this.project, minecraftExtension);
    }

    /**
     * Configuration of the tasks. This needs to be run after the build script is evaluated since it
     * relies on properties assigned in the "minecraft" extension. Before actual configuration, the manifest
     * is fetched, version ids are resolved, version meta is fetched, and asset meta is fetched. Then
     * configuration is done as normal based on the fetched data.
     */
    private void configureTasks() {
        project.afterEvaluate(project -> {
            Date threedaysago = new Date(System.currentTimeMillis() - 3 * Constants.DAY_LENGTH);
            MinecraftProvider provider = minecraftProvider;
            MinecraftExtension extension = minecraftExtension;

            URL manifestURL = provider.getManifestUrl();
            File manifest = new File(CacheUtils.getMinecraftCache(project), "version_manifest.json");

            CacheUtils.fetchURL(manifest, manifestURL, threedaysago);

            String versionID = provider.resolveVersionID(manifest, extension.mcVersionID, extension.mcVersionType);
            extension.setVersion(versionID);
            extension.setMcVersionResID(versionID);

            URL versionMetaURL = provider.getVersionMetaUrl(manifest, versionID);
            File versionMeta = new File(CacheUtils.getMinecraftVersionCache(project, versionID), versionID + ".json");

            CacheUtils.fetchURL(versionMeta, versionMetaURL, threedaysago);

            String assetID = provider.getAssetID(versionMeta, versionID);
            URL assetMetaURL = provider.getAssetsUrl(versionMeta, assetID);
            File assetMeta = new File(CacheUtils.getMinecraftAssetIndexCache(project), assetID + ".json");

            CacheUtils.fetchURL(assetMeta, assetMetaURL, threedaysago);

            dlmcJars.configure(task -> {
                task.setVersionID(versionID);
                task.setVersionMeta(versionMeta);
                File cache = CacheUtils.getMinecraftVersionCache(project, versionID);
                task.setOutput(new HashMap<>());
                task.addOutput("client", new File(cache, "client.jar"));
                task.addOutput("server", new File(cache, "server.jar"));
            });
            dlmcAssets.configure(task -> {
                task.setAssetID(assetID);
                task.setAssetMeta(assetMeta);
                task.setAssetDir(CacheUtils.getMinecraftAssetObjectCache(project));
            });
            dlLibJars.configure(task -> {
                task.setVersionID(versionID);
                task.setVersionMeta(versionMeta);
                task.setLibraryDir(CacheUtils.getMinecraftLibraryCache(project));
            });
            decompJars.configure(task -> {
                task.dependsOn(dlmcJars);
                task.dependsOn(dlLibJars);
                task.setVersionID(versionID);
                task.setVersionMeta(versionMeta);
                task.setDecompiledDir(new File(CacheUtils.getMinecraftVersionCache(project, versionID),
                                               "decomp" + "-" + extension.decompiler));
                task.setLibraryDir(CacheUtils.getMinecraftLibraryCache(project));
                File cache = CacheUtils.getMinecraftVersionCache(project, versionID);
                task.setJars(new HashMap<>());
                task.addJar("client", new File(cache, "client.jar"));
                task.addJar("server", new File(cache, "server.jar"));
            });
        });


    }
}
