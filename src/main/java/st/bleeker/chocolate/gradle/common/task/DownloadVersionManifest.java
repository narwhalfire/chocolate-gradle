package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadVersionManifest extends ChocolateTask {

    private Project project;
    private File output = getMinecraftCache().toPath().resolve("version_manifest.json").toFile();

    @Inject
    public DownloadVersionManifest(Project project) {
        this.project = project;
    }

    @TaskAction
    public void downloadVersionManifest() {

        try {
            URL url = new URL(MinecraftProvider.getManifestUrl());
            FileUtils.copyURLToFile(url, getOutput());
        } catch (IOException e) {
            //todo: log could not download version manifest
            System.out.println("could not download version manifest");
            throw new RuntimeException("could not download version manifest");
        }

    }

    @OutputFile
    public File getOutput() {
        return output;
    }

}
