package st.bleeker.chocolate.gradle.common.task;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;
import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static st.bleeker.chocolate.gradle.common.util.Constants.TIMEOUT;

public class DownloadVersionMeta extends ChocolateTask {

    private String assetID;
    private String versionID;
    private File manifest;
    private File versionMeta;

    @Inject
    public DownloadVersionMeta(Project project, MinecraftExtension minecraftExtension) {
        super(project, minecraftExtension);
    }

    @TaskAction
    public void execute() throws IOException {

        MinecraftProvider provider = minecraftExtension.getMinecraftProvider();
        URL url = provider.getVersionMetaUrl(getManifest(), getVersionID());

//        InputStream inputStream = url.openStream();
//        OutputStream outputStream = Files.newOutputStream(getVersionMeta().toPath());
//
//        int b;
//        while ((b = inputStream.read()) != -1) {
//            outputStream.write(b);
//        }
//        inputStream.close();
//        outputStream.close();


        FileUtils.copyURLToFile(url, getVersionMeta(), TIMEOUT, TIMEOUT);
        setAssetID(provider.getAssetID(getVersionMeta(), getVersionID()));

    }

    public String getAssetID() {
        return assetID;
    }
    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    @Input
    public String getVersionID() {
        return versionID;
    }
    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    @InputFile
    public File getManifest(){
        return manifest;
    }
    public void setManifest(File manifest) {
        this.manifest = manifest;
    }

    @OutputFile
    public File getVersionMeta() {
        return versionMeta;
    }
    public void setVersionMeta(File versionMeta) {
        this.versionMeta = versionMeta;
    }

}
