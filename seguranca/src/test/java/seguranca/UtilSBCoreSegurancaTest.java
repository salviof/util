/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package seguranca;

import seguranca.UtilSBCoreSeguranca;
import org.junit.Test;

/**
 *
 * @author SalvioF
 */
public class UtilSBCoreSegurancaTest {

    @Test
    public void testGetChaveSecretaRandomica() {
        String chaveRandomica = UtilSBCoreSeguranca.getChaveSecretaRandomica();
        UtilSBCoreSeguranca.getTOTPCode(chaveRandomica);
        System.out.println("Chava=" + chaveRandomica);

    }
}
