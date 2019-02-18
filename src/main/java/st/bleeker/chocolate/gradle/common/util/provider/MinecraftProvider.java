package st.bleeker.chocolate.gradle.common.util.provider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MinecraftProvider {

    private static final String MANIFEST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    private static boolean checkVersionType = false;

    public URL getManifestUrl() {
        try {
            return new URL(MANIFEST_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //todo: log and stuff
            throw new RuntimeException(e);
        }
    }

    public URL getVersionMetaUrl(File manifest, String versionID, String versionType) {

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

    public URL getVersionMetaUrl(File manifest, String versionID) {
        return VersionManifestProvider.newVersionManifestProvider(manifest).getVersionMetaURL(versionID);
    }

    public URL getJarUrl(File version, String versionID, String side) {
        return VersionMetaProvider.newVersionMetaProvider(version).getJarUrl(side);
    }

    public URL getAssetsUrl(File version, String versionID) {
        return VersionMetaProvider.newVersionMetaProvider(version).getAssetsUrl();
    }

    public void saveLibrariesToDir(File dir, File version, String versionID) throws IOException {
        VersionMetaProvider.newVersionMetaProvider(version).saveLibrariesToDir(dir);
    }

}
