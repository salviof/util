/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package seguranca;

import seguranca.UtilCRCSeguranca;
import org.junit.Test;

/**
 *
 * @author SalvioF
 */
public class UtilCRCSegurancaTest {

    @Test
    public void testGetChaveSecretaRandomica() {
        String chaveRandomica = UtilCRCSeguranca.getChaveSecretaRandomica();
        UtilCRCSeguranca.getTOTPCode(chaveRandomica);
        System.out.println("Chava=" + chaveRandomica);

    }
}
