package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

import static st.bleeker.chocolate.gradle.common.util.Constants.TIMEOUT;

public class DownloadMinecraftJars extends ChocolateTask {

    private String versionID;
    private File versionMeta;
    private Map<String, File> jarMap;

    @Inject
    public DownloadMinecraftJars(Project project, MinecraftExtension minecraftExtension) {
        super(project, minecraftExtension);
    }

    @TaskAction
    public void task() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        for (Map.Entry<String, File> entry : jarMap.entrySet()) {
            URL url = provider.getJarUrl(getVersionMeta(), getVersionID(), entry.getKey());
            FileUtils.copyURLToFile(url, entry.getValue(), TIMEOUT, TIMEOUT);
        }

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

    @OutputFiles
    public Collection<File> getOutput() {
        return jarMap.values();
    }
    public void setOutput(Map<String, File> jarMap) {
        this.jarMap = jarMap;
    }
    public void addOutput(String k, File v) {
        this.jarMap.put(k, v);
    }
    public void removeOutput(String k) {
        this.jarMap.remove(k);
    }

}
