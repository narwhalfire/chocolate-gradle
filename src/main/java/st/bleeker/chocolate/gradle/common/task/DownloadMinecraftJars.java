package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.Project;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

public class DownloadMinecraftJars extends ChocolateTask {

    private Project project;
    private MinecraftExtension minecraftExtension;

    private File input;
    private Collection<File> output;

    @Inject
    public DownloadMinecraftJars(Project project, MinecraftExtension minecraftExtension) {
        this.project = project;
        this.minecraftExtension = minecraftExtension;
        setInput(getMinecraftVersionCache(minecraftExtension.mcVersionID)
                         .toPath().resolve(minecraftExtension.mcVersionID + ".json").toFile());
        Path path = getMinecraftVersionCache(minecraftExtension.mcVersionID).toPath();
        setOutput(Arrays.asList(path.resolve("client.jar").toFile(),
                                path.resolve("server.jar").toFile()));
    }

    @TaskAction
    public void downloadMinecraftJars() {



    }

    @InputFile
    public File getInput() {
        return input;
    }

    @OutputFiles
    public Collection<File> getOutput() {
        return output;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public void setOutput(Collection<File> output) {
        this.output = output;
    }

}
