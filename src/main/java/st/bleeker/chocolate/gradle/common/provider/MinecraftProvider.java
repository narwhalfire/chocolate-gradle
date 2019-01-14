package st.bleeker.chocolate.gradle.common.provider;

public class MinecraftProvider implements IDependencyProvider {
    @Override
    public String getTarget() {
        return "minecraft";
    }

}