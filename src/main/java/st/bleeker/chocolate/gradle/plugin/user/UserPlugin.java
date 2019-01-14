package st.bleeker.chocolate.gradle.plugin.user;

import java.util.Collections;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

public class UserPlugin implements Plugin<Project> {
    private UserExtension extension;
    private Project project;

    @Override
    public void apply(Project target) {
        this.project = target;
        this.extension = this.project.getExtensions().create("user", UserExtension.class, this.project);

        this.project.apply(Collections.singletonMap("plugin", "java"));

        Configuration configMinecraft = this.project.getConfigurations().maybeCreate("minecraft");
        Configuration configMod = this.project.getConfigurations().maybeCreate("mod");

        this.project.getRepositories().maven(repo -> {
            repo.setName("Mojang");
            repo.setUrl("https://libraries.minecraft.net");
        })
    }
}