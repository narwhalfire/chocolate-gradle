package st.bleeker.chocolate.gradle.common.task;

import org.gradle.api.DefaultTask;

public abstract class Task extends DefaultTask {
    public Task() {
        this.setGroup("chocolate");
    }
}