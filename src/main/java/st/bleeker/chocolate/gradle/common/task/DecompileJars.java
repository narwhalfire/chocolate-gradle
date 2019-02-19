package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.org.mozilla.javascript.Decompiler;
import org.jetbrains.java.decompiler.main.decompiler.BaseDecompiler;
import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler;
import org.jetbrains.java.decompiler.main.decompiler.PrintStreamLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DecompileJars extends ChocolateTask {

    private String versionID;
    private File versionMeta;
    private File libraryDir;

    @Inject
    public DecompileJars(Project project, MinecraftExtension minecraftExtension) {
        super(project, minecraftExtension);
    }

    @TaskAction
    public void execute() {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        List<Path> paths = provider.listLibraries(getVersionMeta(), getVersionID());
//        List<String> libArgs = paths.stream().map(path -> {
//            Path p = getLibraryDir().toPath().resolve(path);
//            return "-e=\"" + p.toString() + "\"";
//        }).collect(Collectors.toList());
//
//        List<String> args = new LinkedList<>();

        Decompiler decompiler = new Decompiler(new File("E:/a"), new HashMap<>(), new PrintStreamLogger(System.out));
        decompiler.addSource(new File(getMinecraftVersionCache(getVersionID()), "server.jar"));
        paths.stream().map(path -> new File(getLibraryDir(), path.toString())).forEach(decompiler::addLibrary);
        decompiler.decompileContext();


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

    public File getLibraryDir() {
        return libraryDir;
    }
    public void setLibraryDir(File libraryDir) {
        this.libraryDir = libraryDir;
    }

    class Decompiler extends ConsoleDecompiler {

        protected Decompiler(File destination, Map<String, Object> options, IFernflowerLogger logger) {
            super(destination, options, logger);
        }
    }

}
