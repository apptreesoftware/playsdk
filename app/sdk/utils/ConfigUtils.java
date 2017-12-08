package sdk.utils;

import play.Play;

import static sdk.utils.ValidationUtils.NullOrEmpty;

public class ConfigUtils {


    public static String getStringFromConfigWithException(String varName, String errorMessage) {
        String value = Play.application().configuration().getString(varName);
        if(NullOrEmpty(value)) {
            throw new RuntimeException(errorMessage);
        }
        return value;
    }

    public static String getStringFromConfig(String varName) {
        return Play.application().configuration().getString(varName);
    }
}
