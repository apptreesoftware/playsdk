import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;

/**
 * Author: Karis Sponsler
 * Date: 11/2/17
 */
public class SampleInheritanceTree {
    @PrimaryKey
    @Attribute(index = 0)
    public String first;


    public static class ChildObject extends SampleInheritanceTree {
        @Attribute(index = 1)
        public String second;
    }


    public static class GrandChildObject extends ChildObject {
        @Attribute(index = 2)
        public String third;
    }


    public static class GrandChildRedefinesPK extends ChildObject {
        @Attribute(index = 2)
        @PrimaryKey
        public String third;
    }


    public static class GrandChildDuplicateIndex extends ChildObject {
        @Attribute(index = 1)
        public String third;
    }
}
