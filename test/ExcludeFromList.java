import sdk.annotations.Attribute;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.list.ListItem;

/**
 * Created by Orozco on 8/15/17.
 */
public class ExcludeFromList extends SampleConfig {

    @Attribute(index = 0)
    public String name;

    @Attribute(index = 1)
    private String type;

    @Attribute(index = 2)
    private String brand;

    @Attribute(index = 3, excludeFromList = true)
    private ExcludeFromList excludeFromList;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ExcludeFromList getExcludeFromList() {
        return excludeFromList;
    }

    public void setExcludeFromList(ExcludeFromList excludeFromList) {
        this.excludeFromList = excludeFromList;
    }



    public static ExcludeFromList getExcludeFromListObject(){
        ExcludeFromList excludeFromList = new ExcludeFromList();
        excludeFromList.setName("Nike name");
        excludeFromList.setBrand("Nike");
        excludeFromList.setType("Shoe");
        ExcludeFromList newExcludeFromList = new ExcludeFromList();
        newExcludeFromList.setName("Reebok");
        newExcludeFromList.setBrand("Reebok");
        newExcludeFromList.setType("Soccer");
        ExcludeFromList excludeFromList1 = new ExcludeFromList();
        excludeFromList1.setName("Third");
        newExcludeFromList.setExcludeFromList(excludeFromList1);
        excludeFromList.setExcludeFromList(newExcludeFromList);
        return excludeFromList;
      }


      public static DataSetItem getTestDataSetItem(){
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(ExcludeFromList.class));
        dataSetItem.setString("Nike name", 0);
        dataSetItem.setString("Nike", 2);
        dataSetItem.setString("Shoe", 1);
        ListItem newItem = new ListItem();
        newItem.setString("Reebok", 0);
        newItem.setString("Reebok", 1);
        newItem.setString("Soccer", 2);
        dataSetItem.setListItem(newItem, 3);
        return dataSetItem;
      }


}
