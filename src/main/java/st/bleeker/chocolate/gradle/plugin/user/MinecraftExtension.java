package st.bleeker.chocolate.gradle.plugin.user;

import org.gradle.api.Project;

import javax.inject.Inject;

public class MinecraftExtension {

    public String mcVersionID = "latest";
    public String mcVersionType = "release";

    @Inject
    public MinecraftExtension(Project project) {

    }

}
