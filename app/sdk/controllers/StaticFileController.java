package sdk.controllers;

import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;

public class StaticFileController extends Controller {

    public Result html(String path) {
        File file = new File(path);
        return ok(file, true);
    }

    public Result tester(String path) {
        File file = new File("sdk/public/validator/" + path);
        return ok(file, true);
    }

    public Result isRunningPage() {
        File file = new File("sdk/public/validate.html");
        return ok(file, true);
    }
}
