package st.bleeker.chocolate.gradle.plugin.user;

import org.gradle.api.Project;
import st.bleeker.chocolate.gradle.common.util.provider.MinecraftProvider;

import javax.inject.Inject;

public class MinecraftExtension {

    public String mcVersionID = "latest";
    public String mcVersionType = "release";

    private Project project;
    private MinecraftProvider minecraftProvider;

    @Inject
    public MinecraftExtension(Project project, MinecraftProvider minecraftProvider) {
        this.project = project;
        this.minecraftProvider = minecraftProvider;
    }

    public void setVersion(String version) {
        this.mcVersionID = version;
    }

    public MinecraftProvider getMinecraftProvider() {
        return minecraftProvider;
    }

    public Project getProject() {
        return project;
    }

}
