package st.bleeker.chocolate.gradle.common.util.provider;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

class VersionManifestProvider {

    private ManifestJSON manifestJSON;
    /** Cache the version when it is first queried. It is likely that only one version will be used per
     * instance, but queried multiple times. */
    private ManifestVersion cachedVersion = null;

    private VersionManifestProvider(ManifestJSON manifestJSON) {
        this.manifestJSON = manifestJSON;
    }


    static VersionManifestProvider newVersionManifestProvider(File manifest) {

        try {
            InputStreamReader manifestReader = new InputStreamReader(Files.newInputStream(manifest.toPath()));
            ManifestJSON manifestJSON = new GsonBuilder().create().fromJson(manifestReader, ManifestJSON.class);
            return new VersionManifestProvider(manifestJSON);
        } catch (IOException e) {
            //todo: log could not read version manifest
            System.out.println("could not read version manifest");
            throw new RuntimeException("could not read version manifest");
        }

    }

    String getLatestRelease() {
        return manifestJSON.latest.release;
    }

    String getLatestSnapshot() {
        return manifestJSON.latest.snapshot;
    }

    String getVersionMetaURL(String versionID) {
        return getMatchingVersion(versionID).url;
    }

    private ManifestVersion getMatchingVersion(String versionID) {
        if (cachedVersion != null && cachedVersion.id.equals(versionID)) {
            return cachedVersion;
        }
        for (ManifestVersion manifestVersion : manifestJSON.versions) {
            if (versionID.equals(manifestVersion.id)) {
                cachedVersion = manifestVersion;
                return cachedVersion;
            }
        }
        //todo: log couldn't find specified version
        System.out.println("couldn't find specified version");
        throw new RuntimeException("couldn't find specified version");
    }


    /* For the manifest json */

    private class ManifestJSON {
        ManifestLatest latest;
        ManifestVersion[] versions;
    }

    private class ManifestLatest {
        String release;
        String snapshot;
    }

    private class ManifestVersion {
        String id;
        String type;
        String url;
        String time;
        String releaseTime;
    }
}
