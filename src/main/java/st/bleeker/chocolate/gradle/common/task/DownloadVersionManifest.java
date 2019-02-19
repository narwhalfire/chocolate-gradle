package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static st.bleeker.chocolate.gradle.common.util.Constants.TIMEOUT;

public class DownloadVersionManifest extends ChocolateTask {

    private String versionID;
    private File manifest;

    @Inject
    public DownloadVersionManifest(Project project, MinecraftExtension minecraftExtension) {
        super(project, minecraftExtension);
    }

    @TaskAction
    public void execute() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        URL url = provider.getManifestUrl();
        FileUtils.copyURLToFile(url, getManifest(), TIMEOUT, TIMEOUT);
        setVersionID(provider.resolveVersionID(getManifest(), getVersionID(), minecraftExtension.mcVersionType));

    }

    @Input
    public String getVersionID() {
        return versionID;
    }
    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    @OutputFile
    public File getManifest() {
        return manifest;
    }
    public void setManifest(File manifest) {
        this.manifest = manifest;
    }

}
