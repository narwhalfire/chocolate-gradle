package st.bleeker.chocolate.gradle.common.util;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class CacheUtils {

    public static File getChocolateCache(Project project) {
        return ensureDirs(new File(project.getBuildDir(), "chocolate"));
    }

    public static File getMinecraftCache(Project project) {
        return ensureDirs(new File(getChocolateCache(project), "minecraft"));
    }

    public static File getMinecraftVersionCache(Project project, String mcVersionID) {
        return ensureDirs(new File(getMinecraftCache(project), "versions" + File.separator + mcVersionID));
    }

    public static File getMinecraftLibraryCache(Project project) {
        return ensureDirs(new File(getMinecraftCache(project), "libraries"));
    }

    public static File getMinecraftAssetCache(Project project) {
        return ensureDirs(new File(getMinecraftCache(project), "assets"));
    }

    public static File getMinecraftAssetIndexCache(Project project) {
        return ensureDirs(new File(getMinecraftAssetCache(project), "index"));
    }

    public static File getMinecraftAssetObjectCache(Project project) {
        return ensureDirs(new File(getMinecraftAssetCache(project), "objects"));
    }

    private static File ensureDirs(File file) {
        file.mkdirs();
        return file;
    }

    public static void fetchURL(File dest, URL src) {
        fetchURL(dest, src, null);
    }

    public static void fetchURL(File dest, URL src, Date date) {
        try {
            if (!dest.exists()) {
                FileUtils.copyURLToFile(src, dest);

            } else {
                if (date != null && new Date(dest.lastModified()).before(date)) {
                    FileUtils.copyURLToFile(src, dest);
                }
            }
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

}
