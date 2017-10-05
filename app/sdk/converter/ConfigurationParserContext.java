package sdk.converter;

import java.util.*;

/**
 * Created by Orozco on 8/28/17.
 */
class ConfigurationParserContext {
    private Class classParent;
    private int occurence;
    void setParentClass(Class clazz) {
        if(classParent == null){
            classParent = clazz;
        }
    }

    boolean addClass(Class clazz) {
        if(clazz == classParent) {
            return occurence++ < 2;
        }
        return true;
    }

}


