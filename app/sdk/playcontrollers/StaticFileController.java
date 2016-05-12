package sdk.playcontrollers;

import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;

public class StaticFileController extends Controller {

    public Result html(String path) {
        File file = new File(path);
        return ok(file, true);
    }
}
