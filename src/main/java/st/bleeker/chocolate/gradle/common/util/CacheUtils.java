package st.bleeker.chocolate.gradle.common.util;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class CacheUtils {

    /** cache directory for the chocolate project */
    public static File getChocolateCache(Project project) {
        return ensureDirs(new File(project.getBuildDir(), "chocolate"));
    }

    /** cache directory for minecraft things */
    public static File getMinecraftCache(Project project) {
        return ensureDirs(new File(getChocolateCache(project), "minecraft"));
    }

    /** cache directory for a specific minecraft version */
    public static File getMinecraftVersionCache(Project project, String mcVersionID) {
        return ensureDirs(new File(getMinecraftCache(project), "versions" + File.separator + mcVersionID));
    }

    /** cache directory for minecraft libraries */
    public static File getMinecraftLibraryCache(Project project) {
        return ensureDirs(new File(getMinecraftCache(project), "libraries"));
    }

    /** cache directory for minecraft assets */
    public static File getMinecraftAssetCache(Project project) {
        return ensureDirs(new File(getMinecraftCache(project), "assets"));
    }

    /** cache directory for minecraft asset indicies */
    public static File getMinecraftAssetIndexCache(Project project) {
        return ensureDirs(new File(getMinecraftAssetCache(project), "index"));
    }

    /** cache directory for minecraft asset objects */
    public static File getMinecraftAssetObjectCache(Project project) {
        return ensureDirs(new File(getMinecraftAssetCache(project), "objects"));
    }

    /** ensures that the directory exists */
    private static File ensureDirs(File file) {
        file.mkdirs();
        return file;
    }

    /** fetches the url to the destination if it doesn't already exist */
    public static void fetchURL(File dest, URL src) {
        fetchURL(dest, src, null);
    }

    /** fetches the url to the destination if it doesn't already exist or has expired */
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
