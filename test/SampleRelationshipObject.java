import sdk.annotations.Relationship;

public class SampleRelationshipObject {

    @Relationship(index = 0)
    private SampleListItem sampleListItem;

    @Relationship(index = 1)
    private java.util.List<SampleListItem> sampleList;



}
