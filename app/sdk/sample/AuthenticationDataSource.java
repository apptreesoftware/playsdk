package sdk.sample;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import sdk.auth.AuthenticationSource;
import sdk.auth.LoginResponse;
import sdk.sample.model.Session;
import sdk.sample.model.User;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Response;

import java.util.Date;
import java.util.UUID;

import static javafx.scene.input.KeyCode.R;

/**
 * Created by matthew on 5/12/16.
 */
public class AuthenticationDataSource implements AuthenticationSource {
    @Override
    public LoginResponse login(String username, String password, AuthenticationInfo authenticationInfo) {
        User user = SampleDatabase.getInstance().getUserFinder().where()
                .and(Expr.eq("username", username), Expr.eq("password", password)).findUnique();
        if ( user == null ) {
            return new LoginResponse.Builder().withMessage("Invalid username or password").build();
        } else {
            Session session = createSession(user);
            return new LoginResponse.Builder().withSuccess(session.token,user.id + "", session.username, null).build();
        }
    }

    @Override
    public Response logout(AuthenticationInfo authenticationInfo) {
        Session session = SampleDatabase.getInstance().getSessionFinder()
                .where().eq("token", authenticationInfo.getToken()).findUnique();
        if ( session != null ) {
            SampleDatabase.getInstance().performTransaction(session::delete);
            return new Response(true, "Logout Complete");
        } else {
            return new Response(false, "Logout failed. No session exists");
        }
    }

    @Override
    public Response validateAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        Session session = SampleDatabase.getInstance().getSessionFinder()
                .where().eq("token", authenticationInfo.getToken()).findUnique();
        if ( session == null ) {
            return new Response(false, "Invalid session");
        }
        return new Response(true,null);
    }

    private Session createSession(User user) {
        Session session = new Session();
        session.token = UUID.randomUUID().toString();
        session.loginDate = new Date();
        session.username = user.username;
        Ebean.beginTransaction();
        session.insert();
        Ebean.endTransaction();
        return session;
    }
}
