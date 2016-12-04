package sdk.sample;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionFactory;
import sdk.auth.AuthenticationSource;
import sdk.auth.LoginResponse;
import sdk.sample.model.Session;
import sdk.sample.model.User;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Response;

import java.util.Date;
import java.util.UUID;

/**
 * Created by matthew on 5/12/16.
 */
public class AuthenticationDataSource implements AuthenticationSource {
    @Override
    public LoginResponse login(String username, String password, AuthenticationInfo authenticationInfo) {

        ExpressionFactory exp = SampleDatabase.getExpressionFactory();
        User user = SampleDatabase.getServer()
                .find(User.class).where()
                .eq("username", username)
                .findUnique();
        if ( user == null ) {
            return new LoginResponse.Builder().withMessage("Invalid username or password").build();
        } else {
            Session session = createSession(user);
            return new LoginResponse.Builder().withSuccess(session.token,user.id + "", session.username, null).build();
        }
    }

    @Override
    public Response logout(AuthenticationInfo authenticationInfo) {
        Session session = SampleDatabase.getServer().find(Session.class).where().eq("token", authenticationInfo.getToken()).findUnique();
        if ( session != null ) {
            SampleDatabase.getInstance().performTransaction(server -> server.delete(session));
            return new Response(true, "Logout Complete");
        } else {
            return new Response(false, "Logout failed. No session exists");
        }
    }

    @Override
    public boolean validateAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        Session session = SampleDatabase.getServer().find(Session.class)
                .where().eq("token", authenticationInfo.getToken()).findUnique();
        return session != null;
    }

    private Session createSession(User user) {
        Session session = new Session();
        session.token = UUID.randomUUID().toString();
        session.loginDate = new Date();
        session.username = user.username;
        EbeanServer server = SampleDatabase.getServer();
        server.beginTransaction();
        server.insert(session);
        server.commitTransaction();
        return session;
    }
}
