package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.Project;
import org.gradle.api.tasks.*;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

public class DownloadMinecraftAssets extends ChocolateTask {

    private Project project;
    private MinecraftExtension minecraftExtension;

    private String assetID;
    private File assetMeta;
    private File assetDir;

    @Inject
    public DownloadMinecraftAssets(Project project, MinecraftExtension minecraftExtension){
        this.project = project;
        this.minecraftExtension = minecraftExtension;
    }

    @TaskAction
    public void downloadMinecraftAssets() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        provider.saveAssetsToDir(getAssetDir(), getAssetMeta(), getAssetID());

    }

    @Input
    public String getAssetID() {
        return assetID;
    }
    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    @InputFile
    public File getAssetMeta() {
        return assetMeta;
    }
    public void setAssetMeta(File assetMeta) {
        this.assetMeta = assetMeta;
    }

    @OutputDirectory
    public File getAssetDir() {
        return assetDir;
    }
    public void setAssetDir(File assetDir) {
        this.assetDir = assetDir;
    }

}
