package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
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

    private File manifest;

    @Inject
    public DownloadVersionManifest(Project project, MinecraftExtension minecraftExtension) {
        this.project = project;
        this.minecraftExtension = minecraftExtension;
    }

    @TaskAction
    public void downloadVersionManifest() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        URL url = provider.getManifestUrl();
        FileUtils.copyURLToFile(url, getManifest());

    }

    @OutputFile
    public File getManifest() {
        return manifest;
    }
    public void setManifest(File manifest) {
        this.manifest = manifest;
    }

}
