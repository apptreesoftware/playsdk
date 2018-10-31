package sdk.converter;

import java.util.Objects;

public class UniqueClassConfig {
    public Class clazz;
    public String configName;

    public UniqueClassConfig(Class clazz, String configName) {
        this.clazz = clazz;
        this.configName = configName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueClassConfig that = (UniqueClassConfig) o;
        return Objects.equals(clazz, that.clazz) &&
               Objects.equals(configName, that.configName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, configName);
    }
}
