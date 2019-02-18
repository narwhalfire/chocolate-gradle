package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadVersionManifest extends ChocolateTask {

    private Project project;
    private MinecraftExtension minecraftExtension;

    private Property<String> versionID;
    private RegularFileProperty manifest;

    @Inject
    public DownloadVersionManifest(Project project, MinecraftExtension minecraftExtension) {
        this.project = project;
        this.minecraftExtension = minecraftExtension;

        this.versionID = this.project.getObjects().property(String.class).convention(this.minecraftExtension.mcVersionID);
        this.manifest = this.project.getObjects().fileProperty();
        this.manifest.set(new File(getMinecraftCache(), "version_manifest.json"));
    }

    @TaskAction
    public void downloadVersionManifest() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        URL url = provider.getManifestUrl();
        FileUtils.copyURLToFile(url, evalManifest());
        System.out.println(evalVersionID());
        String a = provider.resolveVersionID(evalManifest(), evalVersionID(), minecraftExtension.mcVersionType);
        setVersionID(a);
        System.out.println(a);
        System.out.println(evalVersionID());
    }

    @Input
    public Property<String> getVersionID() {
        return versionID;
    }
    public void setVersionID(Property<String> versionID) {
        this.versionID.set(versionID);
    }
    private String evalVersionID() {
        return versionID.get();
    }
    private void setVersionID(String versionID) {
        this.versionID.set(versionID);
    }

    @OutputFile
    public RegularFileProperty getManifest() {
        return manifest;
    }
    public void setManifest(RegularFileProperty manifest) {
        this.manifest.set(manifest);
    }
    private File evalManifest() {
        return manifest.getAsFile().get();
    }
    private void setManifest(File manifest) {
        this.manifest.set(manifest);
    }

}
