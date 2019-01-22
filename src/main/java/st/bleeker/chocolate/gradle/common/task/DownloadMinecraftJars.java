package st.bleeker.chocolate.gradle.common.task;

import st.bleeker.chocolate.gradle.plugin.user.MinecraftExtension;

import java.util.jar.JarFile;

public class DownloadMinecraftJars extends ChocolateTask {

    private MinecraftExtension minecraftExtension = getProject().getExtensions().findByType(MinecraftExtension.class);
//    private JarFile serverJar = getMinecraftCache();


}
