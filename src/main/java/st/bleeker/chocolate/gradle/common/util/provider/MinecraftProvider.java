package st.bleeker.chocolate.gradle.common.util.provider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

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

    public String resolveVersionID(File manifest, String versionID, String versionType) {

        VersionManifestProvider provider = VersionManifestProvider.newVersionManifestProvider(manifest);

        String resolved = versionID;

        if (versionID.equals("latest")) {
            if (versionType.equals("release")) {
                resolved = provider.getLatestRelease();
            } else if (versionType.equals("snapshot")) {
                resolved = provider.getLatestSnapshot();
            } else {
                //todo: log invalid version type
                System.out.println("bad version type");
                throw new RuntimeException("bad version type");
            }
        }
        return resolved;
    }

    public URL getVersionMetaUrl(File manifest, String versionID) {
        return VersionManifestProvider.newVersionManifestProvider(manifest).getVersionMetaURL(versionID);
    }

    public URL getJarUrl(File version, String versionID, String side) {
        return VersionMetaProvider.newVersionMetaProvider(version).getJarUrl(side);
    }

    public URL getAssetsUrl(File version, String assetID) {
        return VersionMetaProvider.newVersionMetaProvider(version).getAssetsUrl();
    }

    public String getAssetID(File version, String versionID) {
        return VersionMetaProvider.newVersionMetaProvider(version).getAssetID();
    }

    public void saveLibrariesToDir(File dir, File version, String versionID) throws IOException {
        VersionMetaProvider.newVersionMetaProvider(version).saveLibrariesToDir(dir);
    }

    public void saveAssetsToDir(File dir, File version, String assetID) throws IOException {
        AssetProvider.newAssetProvider(version).saveAssetsToDir(dir);
    }

    public List<Path> listLibraries(File version, String versionID) {
        return VersionMetaProvider.newVersionMetaProvider(version).listLibraries();
    }

}
