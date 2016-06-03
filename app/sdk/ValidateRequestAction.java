package sdk;

import org.apache.commons.codec.binary.Base64;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by matthew on 6/3/16.
 */
public class ValidateRequestAction extends Action.Simple {
    private static String SDK_API_HEADER = "APPLICATION-API-KEY";
    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        if ( AppTree.needsAPIKeyValidation() && !checkAPIKey(ctx.request()) ) {
            return CompletableFuture.completedFuture(status(440,"API Key Check failed"));
        }
        return delegate.call(ctx);
    }

    private boolean checkAPIKey(Http.Request request) {
        String apiKey = request.getHeader(SDK_API_HEADER);
        String decodedHeader;
        if ( apiKey == null ) return false;

        String secret = AppTree.getApplicationSecret();
        byte[] apiKeyBytes = Base64.decodeBase64(apiKey);
        try {
            decodedHeader = decrypt(apiKeyBytes, secret);
        } catch (Exception e) {
            Logger.error("Could not decrypt API key", e);
            return false;
        }
        return validateHeaderDate(decodedHeader, 1);
    }

    private static boolean validateHeaderDate(String timestampString, long allowedOffset) {
        Date now = GetUTCdatetimeAsDate();

        Date headerDate = StringDateToDate(timestampString);
        long difference;
        difference = now.getTime() - headerDate.getTime();
        difference /= 1000;

        long sec_allow = 60 * allowedOffset;

        difference = Math.abs(difference);
        if (difference > sec_allow) {
            SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
            Logger.debug("The time between the server and client do not match. Client is " + dt.format(headerDate) + " and  Server is " +dt.format(now)  );
            return false;
        }
        return true;
    }

    static final String DATEFORMAT = "yyyyMMdd HH:mm:ss";

    private static Date GetUTCdatetimeAsDate() {
        return StringDateToDate(GetUTCdatetimeAsString());
    }

    private static String GetUTCdatetimeAsString() {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(new Date());
    }

    private static Date StringDateToDate(String StrDate) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

        try {
            dateToReturn = dateFormat.parse(StrDate);
        } catch (ParseException e) {
            Logger.error("Could not parse date", e);
        }

        return dateToReturn;
    }
    // private static String decrypt(String string, String seed) {
    private static String decrypt(byte[] apiKeyBytes, String seed) throws Exception {
        String plainText;
        DESKeySpec keySpec = new DESKeySpec(seed.getBytes("UTF8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance("DES");// cipher is not thread
        // safe
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainTextPwdBytes = (cipher.doFinal(apiKeyBytes));
        plainText = new String(plainTextPwdBytes);
        return plainText;
    }
}
