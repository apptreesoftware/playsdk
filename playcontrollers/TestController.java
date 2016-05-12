package sdk.playcontrollers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.test;

/**
 * Created by matthew on 5/10/16.
 */
public class TestController extends Controller {
    public Result testIndex() {
        return ok(test.render("Test"));
    }
}
