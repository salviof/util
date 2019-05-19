/*
 *  Desenvolvido pela equipe Super-Bits.com CNPJ 20.019.971/0001-90

 */
package requisito;

import com.google.gson.Gson;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.SBCore.modulos.Controller.WS.RespostaWebService;
import com.super_bits.modulosSB.SBCore.modulos.requisito.ComentarioRequisitoEnvioNovo;
import com.super_bits.modulosSB.SBCore.modulos.requisito.Requisito;
import org.junit.Test;

/**
 *
 * @author desenvolvedor
 */
public class ClienteComentarioRequisitoTest {

    public ClienteComentarioRequisitoTest() {
    }

    @Test
    public void testAdicionarComentario() {
        System.out.println("adicionarComentario");
        ComentarioRequisitoEnvioNovo novoComentario = new ComentarioRequisitoEnvioNovo();
        novoComentario.setUsuario("salviof@gmail.com");
        novoComentario.setSenha("teste");
        novoComentario.setComentario("testekdfjaklsdfjaklsdjflkajsdf");

        ClienteComentarioRequisito instance = new ClienteComentarioRequisito();
        ItfResposta expResult = null;

        RespostaWebService resp = (RespostaWebService) instance.obterRequisitoDeAcaoDoProjeto("FabAcaoFornecedor.CAMPANHA_FRM_CANCELAR");

        System.out.println("REsposta=" + resp.getRetornoJson());
        Requisito req = new Gson().fromJson(resp.getRetornoJson(), Requisito.class);

        System.out.println(req.getUrlRequisito());

        //RespostaWebService result = (RespostaWebService) instance.adicionarComentario(novoComentario);
    }

}
