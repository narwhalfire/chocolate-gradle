package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
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

import static st.bleeker.chocolate.gradle.common.util.Constants.TIMEOUT;

public class DownloadAssetMeta extends ChocolateTask {

    private String assetID;
    private String versionID;
    private File versionMeta;
    private File assetMeta;

    @Inject
    public DownloadAssetMeta(Project project, MinecraftExtension minecraftExtension) {
        this.project = project;
        this.minecraftExtension = minecraftExtension;
    }

    @TaskAction
    public void execute() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        setAssetID(provider.getAssetID(getVersionMeta(), getVersionID()));
        URL url = provider.getAssetsUrl(getVersionMeta(), getAssetID());
        FileUtils.copyURLToFile(url, getAssetMeta(), TIMEOUT, TIMEOUT);

    }

    public String getAssetID() {
        return assetID;
    }
    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    @Input
    public String getVersionID() {
        return versionID;
    }
    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    @InputFile
    public File getVersionMeta() {
        return versionMeta;
    }
    public void setVersionMeta(File versionMeta) {
        this.versionMeta = versionMeta;
    }

    @OutputFile
    public File getAssetMeta() {
        return assetMeta;
    }
    public void setAssetMeta(File assetMeta) {
        this.assetMeta = assetMeta;
    }

}
