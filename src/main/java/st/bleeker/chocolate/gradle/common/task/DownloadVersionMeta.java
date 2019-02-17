package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
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

    private File input;
    private File output;

    @Inject
    public DownloadVersionMeta(Project project, MinecraftExtension minecraftExtension) {
        this.project = project;
        this.minecraftExtension = minecraftExtension;
        setInput(getMinecraftCache().toPath().resolve("version_manifest.json").toFile());
        setOutput(getMinecraftVersionCache(minecraftExtension.mcVersionID)
                          .toPath().resolve(minecraftExtension.mcVersionID + ".json").toFile());
    }

    @TaskAction
    public void downloadVersionMeta() {

        try {
            URL url = MinecraftProvider.getVersionMetaUrl(
                    getInput(), minecraftExtension.mcVersionID, minecraftExtension.mcVersionType);
            FileUtils.copyURLToFile(url, getOutput());
        } catch (IOException e) {
            //todo: log could not download version json
            System.out.println("could not download version json");
            throw new RuntimeException("could not download version json");
        }

    }

    @InputFile
    public File getInput(){
        return input;
    }

    @OutputFile
    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public void setInput(File input) {
        this.input = input;
    }

}
