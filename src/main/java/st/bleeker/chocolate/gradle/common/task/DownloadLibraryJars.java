package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

/**
 * Downloads the libraries that minecraft depends on.
 */
public class DownloadLibraryJars extends ChocolateTask {

    private String versionID;
    private File versionMeta;
    private File libraryDir;

    @Inject
    public DownloadLibraryJars(Project project, MinecraftExtension minecraftExtension) {
        super(project, minecraftExtension);
    }

    @TaskAction
    public void task() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        provider.saveLibrariesToDir(getLibraryDir(), getVersionMeta(), getVersionID());

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

    @OutputDirectory
    public File getLibraryDir() {
        return libraryDir;
    }
    public void setLibraryDir(File libraryDir) {
        this.libraryDir = libraryDir;
    }

}
