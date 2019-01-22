package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class DownloadVersionMeta extends ChocolateTask {

    private Project project;
    private String mcVersionID;
    private String mcVersionType;

    private File input = getMinecraftCache().toPath().resolve("version_manifest.json").toFile();
    private File output = getMinecraftCache().toPath().resolve(Paths.get(mcVersionID, mcVersionID + ".json")).toFile();

    @Inject
    public DownloadVersionMeta(Project project, String mcVersionID, String mcVersionType) {
        this.project = project;
        this.mcVersionID = mcVersionID;
        this.mcVersionType = mcVersionType;
    }

    @TaskAction
    public void downloadVersionMeta() {

        try {
            URL url = new URL(MinecraftProvider.getVersionMetaUrl(getInput(), mcVersionID, mcVersionType));
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

}
