package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.Project;
import org.gradle.api.tasks.*;
import st.bleeker.chocolate.gradle.common.util.CacheUtils;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DecompileJars extends ChocolateTask {

    private String versionID;
    private File versionMeta;
    private File libraryDir;
    private File decompDir;
    private Map<String, File> jars;

    @Inject
    public DecompileJars(Project project, MinecraftExtension minecraftExtension) {
        super(project, minecraftExtension);
    }

    @TaskAction
    public void task() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        List<Path> paths = provider.listLibraries(getVersionMeta(), getVersionID());
        String javaexec = Paths.get(
                System.getProperty("java.home"),
                "bin", "java.exe").toAbsolutePath().toString();
        String cfrjar = new File(org.benf.cfr.reader.Main.class
                                         .getProtectionDomain().getCodeSource().getLocation()
                                         .getFile()).getAbsolutePath();
        List<String> extracp = paths.stream()
                                    .map(path -> getLibraryDir().toPath().resolve(path).toString())
                                    .collect(Collectors.toList());
        List<Process> decompProcs = new ArrayList<>();
        for (Map.Entry<String, File> entry : jars.entrySet()) {
            String source = entry.getValue().getAbsolutePath();
            String output = new File(getDecompiledDir(), entry.getKey()).getAbsolutePath();
            new File(output).mkdirs();
            List<String> args = new LinkedList<>();
            args.add(javaexec);
            args.add("-jar");
            args.add(cfrjar);
            args.add(source);
            extracp.forEach(lib -> {
                args.add("--extraclasspath");
                args.add(lib);
            });
            args.add("--outputdir");
            args.add(output);
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(args);
            processBuilder.redirectError(ProcessBuilder.Redirect.to(Paths.get(output, "outerr.txt").toFile()));
            processBuilder.redirectOutput(ProcessBuilder.Redirect.to(Paths.get(output, "out.txt").toFile()));
            Process process = processBuilder.start();
            Runtime.getRuntime().addShutdownHook(new Thread(process::destroy));
            decompProcs.add(process);
        }

        for (Process process : decompProcs) {

            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

    @InputFiles
    public Collection<File> getJars() {
        return jars.values();
    }
    public void setJars(Map<String, File> jarMap) {
        this.jars = jarMap;
    }
    public void addJar(String k, File v) {
        this.jars.put(k, v);
    }
    public void removeJar(String k) {
        this.jars.remove(k);
    }

    @OutputDirectory
    public File getDecompiledDir() {
        return decompDir;
    }
    public void setDecompiledDir(File decompDir) {
        this.decompDir = decompDir;
    }

    public File getLibraryDir() {
        return libraryDir;
    }
    public void setLibraryDir(File libraryDir) {
        this.libraryDir = libraryDir;
    }

}
