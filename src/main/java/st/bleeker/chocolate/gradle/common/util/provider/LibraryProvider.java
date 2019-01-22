package st.bleeker.chocolate.gradle.common.util.provider;

public class LibraryProvider {

    private Artifact[] artifacts;
    private String name;



    public String getName() {
        return name;
    }



    public static class Artifact {
        private String path;
        private String sha1;
        long size;
        private String url;
    }
}
