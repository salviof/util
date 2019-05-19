/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguranca;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author SalvioF
 */
public class UtilSBCoreSeguranca {

    public static String getChaveSecretaRandomica() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String secretKey = base32.encodeToString(bytes);
        // make the secret key more human-readable by lower-casing and
        // inserting spaces between each group of 4 characters
        return secretKey.toLowerCase().replaceAll("(.{4})(?=.{4})", "$1 ");
    }

    public static String getTOTPCode(String secretKey) {
        String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(normalizedBase32Key);
        String hexKey = Hex.encodeHexString(bytes);
        long time = (System.currentTimeMillis() / 1000) / 30;
        String hexTime = Long.toHexString(time);

        return null;//TOTP.generateTOTP(hexKey, hexTime, "6");
    }

    public static String gerarChaveCredencial() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();

        final GoogleAuthenticatorKey key = gAuth.createCredentials();
        System.out.println(key.getScratchCodes());
        GoogleAuthenticatorQRGenerator qrCode = new GoogleAuthenticatorQRGenerator();

        return key.getKey();
    }

    public static boolean autenticar(String chaveSecreta, int senha) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(chaveSecreta, senha);

    }

}
