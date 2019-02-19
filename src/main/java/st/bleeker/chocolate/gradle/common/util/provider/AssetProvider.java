package st.bleeker.chocolate.gradle.common.util.provider;

import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static st.bleeker.chocolate.gradle.common.util.Constants.TIMEOUT;

class AssetProvider {

    private AssetJSON assetJSON;
    private static final String baseUrl = "http://resources.download.minecraft.net/";

    private AssetProvider(AssetJSON assetJSON) {
        this.assetJSON = assetJSON;
    }

    static AssetProvider newAssetProvider(File assets) {

        try {
            InputStreamReader assetReader = new InputStreamReader(Files.newInputStream(assets.toPath()));
            AssetJSON assetJSON = new GsonBuilder().create().fromJson(assetReader, AssetJSON.class);
            return new AssetProvider(assetJSON);
        } catch (IOException e) {
            //todo: log could not read asset json
            System.out.println("could not read asset json");
            throw new RuntimeException("could not read asset json");
        }

    }

    void saveAssetsToDir(File dir) throws IOException {

        //todo: add support for old version (before 1.7.2)
        for (Asset asset : assetJSON.objects.values()) {
            Path path = Paths.get(asset.hash.substring(0, 2), asset.hash);
            File dest = dir.toPath().resolve(path).toFile();
            URL src = new URL(baseUrl + path.toString().replace(File.separatorChar, '/'));
            FileUtils.copyURLToFile(src, dest, TIMEOUT, TIMEOUT);
        }

    }


    private class AssetJSON {
        Map<String, Asset> objects;
    }

    private class Asset {
        String hash;
        long size;
    }

}
