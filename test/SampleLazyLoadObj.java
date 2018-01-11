import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.annotations.Relationship;

import java.util.List;

/**
 * Author: Karis Sponsler
 * Date: 1/11/18
 */
public class SampleLazyLoadObj {
    @PrimaryKey
    public int pk;

    @Relationship(index = 0, eager = false)
    public List<LazilyLoadedObj> objList;

    public static class LazilyLoadedObj {
        @PrimaryKey
        public int pk;

        @Attribute(index = 0)
        public String name;
    }
}
