package st.bleeker.chocolate.gradle.common.util.provider;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

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
            throw new RuntimeException("could not read version json");
        }

    }

    String getAssetsVersion() {
        return versionJSON.assets;
    }

    String getAssetsUrl() {
        return versionJSON.assetIndex.url;
    }

    String getServerJarUrl() {
        return versionJSON.downloads.server.url;
    }

    String getClientJarUrl() {
        return versionJSON.downloads.client.url;
    }

    String getServerJarSha1() {
        return versionJSON.downloads.server.sha1;
    }

    String getClientJarSha1() {
        return versionJSON.downloads.client.sha1;
    }





    /* For the version json */

    private class VersionJSON {
        AssetIndex assetIndex;
        String assets;
        Downloads downloads;
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
        String url;
    }

    private class Downloads {
        Download client;
        Download server;
    }

    private class Download {
        String sha1;
        long size;
        String url;
    }

    private class Library {
        LibDownload downloads;
        String name;
    }

    private class LibDownload {
        Artifact artifact;
        Classifiers classifiers;
        Natives natives;
        Rule[] rules;
    }

    private class Artifact {
        String path;
        String sha1;
        long size;
        String url;
    }

    private class Classifiers {
        Artifact javadoc;
        Artifact sources;
        @SerializedName("natives-linux")
        Artifact nativesLinux;
        @SerializedName("natives-macos")
        Artifact nativesMacOS;
        @SerializedName("natives-windows")
        Artifact nativesWindows;
    }

    private class Natives {
        String linux;
        String osx;
        String windows;
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
        String url;
    }


}
