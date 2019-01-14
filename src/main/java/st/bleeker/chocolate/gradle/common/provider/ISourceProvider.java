package st.bleeker.chocolate.gradle.common.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface ISourceProvider {
    public default void decompile(InputStream in, OutputStream out) {

    }

    public default void deobfuscate(InputStream in, OutputStream out, Map<String, String> mappings) {
        
    }

    public default void recompile(InputStream in, OutputStream out) {

    }

    public default void reobfuscate(InputStream in, OutputStream out, Map<String, String> mappings) {
        
    }

    public default void toSource(InputStream in, OutputStream out, Map<String, String> mappings) {
        ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
        this.decompile(in, tempOut);
        ByteArrayInputStream tempIn = new ByteArrayInputStream(tempOut.toByteArray());
        this.deobfuscate(tempIn, out, mappings);
    }

    public default void toBinary(InputStream in, OutputStream out, Map<String, String> mappings) {
        ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
        this.reobfuscate(in, tempOut, mappings);
        ByteArrayInputStream tempIn = new ByteArrayInputStream(tempOut.toByteArray());
        this.recompile(tempIn, out);
    }
}