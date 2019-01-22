package st.bleeker.chocolate.gradle.common.util.provider;

import java.io.File;

public class MinecraftProvider {

    private static final String MANIFEST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    private static boolean checkVersionType = false;


    public static String getManifestUrl() {
        return MANIFEST_URL;
    }

    /**
     * Grabs the version JSON from the version manifest JSON. If the version is specified as latest, then the
     * latest release or snapshot will be grabbed per the specified version type.
     * @param versionID id of the version (e.g. 1.13.1-pre1, 1.12.2, 1.7.10, 19w02a, 18w44a)
     * @param versionType type of the version (either "release" or "snapshot")
     * @return class representing the metadata of the version
     */
    public static String getVersionMetaUrl(File manifest, String versionID, String versionType) {

        VersionManifestProvider provider = VersionManifestProvider.newVersionManifestProvider(manifest);

        if (versionID.equals("latest")) {
            if (versionType.equals("release")) {
                versionID = provider.getLatestRelease();
            } else if (versionType.equals("snapshot")) {
                versionID = provider.getLatestSnapshot();
            } else {
                //todo: log invalid version type
                System.out.println("bad version type");
                throw new RuntimeException("bad version type");
            }
        }
        return provider.getVersionMetaURL(versionID);
    }








}
