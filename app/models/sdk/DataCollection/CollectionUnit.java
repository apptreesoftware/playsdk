package models.sdk.DataCollection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by alexis on 5/3/16.
 */
public class CollectionUnit {
    private ArrayList<CollectionUnitItem> mUnitItems;

    /**
     * Creates a new ATCollectionUnit for use with ATDataCollections
     */
    public CollectionUnit() {
        mUnitItems = new ArrayList<CollectionUnitItem>();
    }

    /**
     * Adds a collection unit item to this collection
     * @param unitItem
     */
    public void addCollectionUnitItem(CollectionUnitItem unitItem) {
        if ( unitItem != null ) {
            mUnitItems.add(unitItem);
        }
    }

    /**
     * Adds a collection of collection unit items to this collection unit
     * @param unitItems
     */
    public void addCollectionUnitItems(Collection<CollectionUnitItem> unitItems) {
        mUnitItems.addAll(unitItems);
    }

    /**
     * Removes the specified collection unit item from this collection unit
     * @param unitItem
     */
    public void removeCollectionUnitItem(CollectionUnitItem unitItem) {
        mUnitItems.remove(unitItem);
    }

    /**
     * Returns the collection unit items
     * @return an ArrayList of ATCollectionUnitItems
     */
    public ArrayList<CollectionUnitItem> getUnitItems() { return mUnitItems; }
}
