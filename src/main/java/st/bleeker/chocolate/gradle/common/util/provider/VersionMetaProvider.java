package st.bleeker.chocolate.gradle.common.util.provider;

import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

import static st.bleeker.chocolate.gradle.common.util.Constants.TIMEOUT;

class VersionMetaProvider {

    private VersionJSON versionJSON;

    private VersionMetaProvider(VersionJSON versionJSON) {
        this.versionJSON = versionJSON;
    }

    static VersionMetaProvider newVersionMetaProvider(File version) {

        try {
            InputStreamReader versionReader = new InputStreamReader(Files.newInputStream(version.toPath()));
            VersionJSON versionJSON = new GsonBuilder().create().fromJson(versionReader, VersionJSON.class);
            return new VersionMetaProvider(versionJSON);
        } catch (IOException e) {
            //todo: log could not read version json
            System.out.println("could not read version json");
            throw new RuntimeException("could not read version json", e);
        }

    }

    String getID() {
        return versionJSON.id;
    }

    String getAssetID() {
        return versionJSON.assetIndex.id;
    }

    String getAssetsVersion() {
        return versionJSON.assets;
    }

    URL getAssetsUrl() {
        return versionJSON.assetIndex.url;
    }

    URL getJarUrl(String side) {
        return versionJSON.downloads.get(side).url;
    }

    String getJarSha1(String side) {
        return versionJSON.downloads.get(side).sha1;
    }

    void saveLibrariesToDir(File dir) throws IOException {

        for (Library library : versionJSON.libraries) {
            LibDownload libDownload = library.downloads;
            if (libDownload.artifact != null) {
                FileUtils.copyURLToFile(libDownload.artifact.url, new File(dir, libDownload.artifact.path), TIMEOUT, TIMEOUT);
            }
            if (libDownload.classifiers != null) {
                if (libDownload.classifiers.containsKey("javadoc")) {
                    FileUtils.copyURLToFile(libDownload.classifiers.get("javadoc").url,
                                            new File(dir, libDownload.classifiers.get("javadoc").path), TIMEOUT, TIMEOUT);
                }
                if (libDownload.classifiers.containsKey("sources")) {
                    FileUtils.copyURLToFile(libDownload.classifiers.get("sources").url,
                                            new File(dir, libDownload.classifiers.get("sources").path), TIMEOUT, TIMEOUT);
                }
                if (libDownload.classifiers.containsKey("natives-windows")) {
                    FileUtils.copyURLToFile(libDownload.classifiers.get("natives-windows").url,
                                            new File(dir, libDownload.classifiers.get("natives-windows").path), TIMEOUT, TIMEOUT);
                }
            }
        }

    }



    /* For the version json */

    private class VersionJSON {
        AssetIndex assetIndex;
        String assets;
        Map<String, Download> downloads;
        String id;
        Library[] libraries;
        Logging logging;
        String mainClass;
        int minimumLauncherVersion;
        String releaseTime;
        String time;
        String type;
    }

    private class AssetIndex {
        String id;
        String sha1;
        long size;
        long totalSize;
        URL url;
    }

    private class Download {
        String sha1;
        long size;
        URL url;
    }

    private class Library {
        LibDownload downloads;
        String name;
    }

    private class LibDownload {
        Artifact artifact;
        Map<String, Artifact> classifiers;
        Map<String, String> natives;
        Rule[] rules;
    }

    private class Artifact {
        String path;
        String sha1;
        long size;
        URL url;
    }

    private class Rule {
        String action;
        OS os;
    }

    private class OS {
        String name;
    }

    private class Logging {
        LoggingClient client;
    }

    private class LoggingClient {
        String argument;
        LoggingFile file;
        String type;
    }

    private class LoggingFile {
        String id;
        String sha1;
        long size;
        URL url;
    }


}
