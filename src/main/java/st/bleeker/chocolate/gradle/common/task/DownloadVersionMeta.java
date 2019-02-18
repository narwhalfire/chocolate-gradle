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

public class DownloadVersionMeta extends ChocolateTask {

    private Project project;
    private MinecraftExtension minecraftExtension;

    private String versionID;
    private File manifest;
    private File versionMeta;

    @Inject
    public DownloadVersionMeta(Project project, MinecraftExtension minecraftExtension) {
        this.project = project;
        this.minecraftExtension = minecraftExtension;
    }

    @TaskAction
    public void downloadVersionMeta() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        URL url = provider.getVersionMetaUrl(getManifest(), getVersionID());
        FileUtils.copyURLToFile(url, getVersionMeta());

    }

    @Input
    public String getVersionID() {
        return versionID;
    }
    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    @InputFile
    public File getManifest(){
        return manifest;
    }
    public void setManifest(File manifest) {
        this.manifest = manifest;
    }

    @OutputFile
    public File getVersionMeta() {
        return versionMeta;
    }
    public void setVersionMeta(File versionMeta) {
        this.versionMeta = versionMeta;
    }

}
