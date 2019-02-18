package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadVersionMeta extends ChocolateTask {

    private Project project;
    private MinecraftExtension minecraftExtension;

    private Property<String> versionID;
    private Property<String> assetID;
    private RegularFileProperty manifest;
    private RegularFileProperty versionMeta;

    @Inject
    public DownloadVersionMeta(Project project, MinecraftExtension minecraftExtension) {
        this.project = project;
        this.minecraftExtension = minecraftExtension;

        this.versionID = this.project.getObjects().property(String.class);
        this.assetID = this.project.getObjects().property(String.class);
        this.manifest = this.project.getObjects().fileProperty();
        this.versionMeta = this.project.getObjects().fileProperty();
    }

    @TaskAction
    public void downloadVersionMeta() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        URL url = provider.getVersionMetaUrl(evalManifest(), evalVersionID());
        setVersionMeta(new File(getMinecraftVersionCache(evalVersionID()), evalVersionID() + ".json"));
        FileUtils.copyURLToFile(url, evalVersionMeta());

    }

    public Property<String> getAssetID() {
        return assetID;
    }
    public void setAssetID(Property<String> assetID) {
        this.versionID.set(assetID);
    }
    private String evalAssetID() {
        return assetID.get();
    }
    private void setAssetID(String assetID) {
        this.assetID.set(assetID);
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

    @InputFile
    public RegularFileProperty getManifest(){
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

//    @OutputFile
    public RegularFileProperty getVersionMeta() {
        return versionMeta;
    }
    public void setVersionMeta(RegularFileProperty versionMeta) {
        this.versionMeta.set(versionMeta);
    }
    private File evalVersionMeta() {
        return versionMeta.getAsFile().get();
    }
    public void setVersionMeta(File versionMeta) {
        this.versionMeta.set(versionMeta);
    }

}
