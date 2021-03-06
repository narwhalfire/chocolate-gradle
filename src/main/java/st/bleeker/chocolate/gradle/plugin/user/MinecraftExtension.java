package st.bleeker.chocolate.gradle.plugin.user;

import org.gradle.api.Project;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;

import javax.inject.Inject;

public class MinecraftExtension {

    public String mcVersionID = "latest";
    public String mcVersionType = "release";
    public String decompiler = "ff";
    private String mcVersionResID = null;

    private Project project;
    private MinecraftProvider minecraftProvider;

    @Inject
    public MinecraftExtension(Project project, MinecraftProvider minecraftProvider) {
        this.project = project;
        this.minecraftProvider = minecraftProvider;
    }

    void apply() {

    }

    public void setVersion(String version) {
        this.mcVersionID = version;
    }

    void setMcVersionResID(String version) {
        this.mcVersionResID = version;
    }

    public String getVersion() {
        return mcVersionResID;
    }

    public MinecraftProvider getMinecraftProvider() {
        return minecraftProvider;
    }

    public Project getProject() {
        return project;
    }

}
