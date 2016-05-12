import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;
import org.junit.*;

import play.libs.Json;
import sdk.data.DataSetItemAttribute;
import sdk.list.ListItem;
import sdk.models.*;

import static org.junit.Assert.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {

    }

    @Test
    public void testListItemOutput() {
        String expectedOutput = "{\"id\":\"Test\",\"orderBy\":-1,\"value\":\"Test\",\"attribute01\":\"1\",\"attribute02\":\"{\\\"r\\\":50,\\\"g\\\":50,\\\"b\\\":50,\\\"a\\\":0}\",\"attribute03\":\"Y\",\"attribute04\":\"2016-05-08 10:34:32\",\"attribute05\":\"{\\\"from\\\":\\\"2016-05-10\\\",\\\"to\\\":\\\"2016-05-16\\\"}\",\"attribute06\":\"{\\\"from\\\":\\\"2016-05-08 10:34:32\\\",\\\"to\\\":\\\"2016-05-24 10:34:32\\\"}\",\"attribute07\":\"1.45\",\"attribute08\":\"42\",\"attribute09\":\"{\\\"latitude\\\":-45.6789,\\\"longitude\\\":512.56797,\\\"bearing\\\":0.0,\\\"speed\\\":0.0,\\\"accuracy\\\":0.0,\\\"elevation\\\":0.0}\",\"attribute10\":\"{\\\"imageURL\\\":\\\"http://appd01.apptreesoftware.com/AppTreeImages/ic_add_shopping_cart_black_24dp.png\\\"}\",\"latitude\":0.0,\"longitude\":0.0}";
        JsonNode expectedJson = Json.parse(expectedOutput);
        //create list item
        ListItem item = new ListItem("Test");

        int intValue = 1;
        Color color = new Color(50, 50, 50, 0);
        boolean bool = true;
        DateTime time = DateTime.parse("2016-05-08 10:34:32", DataSetItemAttribute.AppTreeDateTimeFormat);
        DateRange dateRange = new DateRange(DateTime.parse("2016-05-10", DataSetItemAttribute.AppTreeDateFormat), DateTime.parse("2016-05-16", DataSetItemAttribute.AppTreeDateFormat));
        DateTimeRange dateTimeRange = new DateTimeRange(DateTime.parse("2016-05-08 10:34:32", DataSetItemAttribute.AppTreeDateTimeFormat), DateTime.parse("2016-05-24 10:34:32", DataSetItemAttribute.AppTreeDateTimeFormat));
        double doubleVal = 1.45;
        long longVal = 42;
        Location location = new Location(-45.67890, 512.56797);
        Image image = new Image();
        image.imageURL = "http://appd01.apptreesoftware.com/AppTreeImages/ic_add_shopping_cart_black_24dp.png";

        item.setAttributeForIndex(intValue, ListItem.ATTRIBUTE_1);
        item.setAttributeForIndex(color, ListItem.ATTRIBUTE_2);
        item.setAttributeForIndex(bool, ListItem.ATTRIBUTE_3);
        item.setAttributeForIndex(time, ListItem.ATTRIBUTE_4);
        item.setAttributeForIndex(dateRange, ListItem.ATTRIBUTE_5);
        item.setAttributeForIndex(dateTimeRange, ListItem.ATTRIBUTE_6);
        item.setAttributeForIndex(doubleVal, ListItem.ATTRIBUTE_7);
        item.setAttributeForIndex(longVal, ListItem.ATTRIBUTE_8);
        item.setAttributeForIndex(location, ListItem.ATTRIBUTE_9);
        item.setAttributeForIndex(image, ListItem.ATTRIBUTE_10);

        //turn it into json
        JsonNode listItemJson = Json.toJson(item);

        //confirm the json.toString matches an expected string
        assertTrue(expectedJson.equals(listItemJson));
    }
}
