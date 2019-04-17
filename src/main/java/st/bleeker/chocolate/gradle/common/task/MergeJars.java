package st.bleeker.chocolate.gradle.common.task;

import net.fabricmc.stitch.merge.JarMerger;
import net.minecraftforge.mergetool.Merger;
import org.gradle.api.Project;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

public class MergeJars extends ChocolateTask {

    private File serverJar;
    private File clientJar;
    private File mergedJar;

    @Inject
    public MergeJars(Project project, MinecraftExtension minecraftExtension) {
        super(project, minecraftExtension);
    }

    @TaskAction
    public void task() throws IOException {

        boolean fabric = true;

        if (fabric) {
            // fabric
            JarMerger jarMerger = new JarMerger(getClientJar(), getServerJar(), getMergedJar());
            jarMerger.enableSnowmanRemoval();
            jarMerger.enableSyntheticParamsOffset();
            jarMerger.merge();
            jarMerger.close();
        } else {
            // forge
            Merger merger = new Merger(getClientJar(), getServerJar(), getMergedJar());
            merger.process();
        }

    }

    @InputFile
    public File getServerJar() {
        return serverJar;
    }
    public void setServerJar(File serverJar) {
        this.serverJar = serverJar;
    }

    @InputFile
    public File getClientJar() {
        return clientJar;
    }
    public void setClientJar(File clientJar) {
        this.clientJar = clientJar;
    }

    @OutputFile
    public File getMergedJar() {
        return mergedJar;
    }
    public void setMergedJar(File mergedJar) {
        this.mergedJar = mergedJar;
    }

}
