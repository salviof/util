/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.shellcommands.model;

import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;

/**
 *
 * Todos os comandos e scripts devem ser cadastrados neste facture
 *
 * Os parametros dos comandos serão configurados automaticamente lendo a sintaxe
 * do comando ou script no formato :nomeParametro
 *
 * Para cadastrar scripts, crie um arquivo .sh em resources utilize parametros
 * diretamente dentro do Script conforme nomeclatura acima
 *
 *
 * @author sfurbino
 */
public enum TIPOCMD implements ItfFabrica {

    /**
     *
     * Atribui chmod + x a um arquivo, parametro:
     *
     * [arquivoExecucao]
     *
     */
    /**
     *
     * Atribui chmod + x a um arquivo, parametro:
     *
     * [arquivoExecucao]
     *
     */
    LNXPERMICAO_EXECUTAR {

        @Override
        public Comando getComando() {
            Comando permExecutar = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "chmod +x :arquivoExecucao ");

            permExecutar.setTipoComando(LNXPERMICAO_EXECUTAR);
            permExecutar.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.SEMRESPOSTA);

            return permExecutar;
        }

    },
    /**
     * Ping, parametro: [ip]
     */
    LNXPING {

        @Override
        public Comando getComando() {
            Comando ping = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "ping :ip -c 2 ");

            ping.setTipoComando(this);
            ping.setRegexResultadoEsperado("time=");

            return ping;
        }

    },
    /**
     * COPIA PASTA PARA OUTRO DIRETORIO, parametros: (certifique de criar o
     * subdiretorio para a pasta de destino) [pastaCopOri] [pastaCopDest]
     */
    LNXDIR_COPIAR_PASTA {

        @Override
        public Comando getComando() {
            Comando copiarPasta = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "cp :pastaCopOri :pastaCopDest -r");

            copiarPasta.setTipoComando(this);
            copiarPasta.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.SEMRESPOSTA);

            return copiarPasta;
        }

    },
    /**
     * SUBISTITUI TODAS AS PALAVRAS ENCONTRADAS EM UM ARQUIVO POR OUTRA, OS
     * PARAMETROS SÃO:
     *
     * [textoAntigo] [novoTexto] [arqReplaceString]
     *
     *
     */
    LNXSUBSTITUIR_PALAVRA_EM_ARQUIVO {

        @Override
        public Comando getComando() {
            Comando substituirPalavras = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "sed -i s/:textoAntigo/:novoTexto/g :arqReplaceString");
            substituirPalavras.setTipoComando(LNXSUBSTITUIR_PALAVRA_EM_ARQUIVO);

            return substituirPalavras;
        }

    },
    /**
     * SUBSTITUI TODOS AS PALAVRAS ENCONTRADAS DENTRO DOS ARQUIVOS TEXTO POR
     * OUTRA, BUSCA ESTES ARQUIVOS DE FORMA RECURSIVA OS PARAMETROS SÃO:
     * [arquivosPesquisados] [pastaRecursiva] (EX:*.java) [textoAntigo]
     * [novoTexto]
     *
     */
    LNXSUBSTITUIR_PALAVRA_EM_ARQUIVOS {

        @Override
        public Comando getComando() {
            Comando substituirPalavras = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, "lnxReplaceRecursive.sh");
            //"find ./ -name '*.xml' | xargs perl -i.bkp -p -e 's/SuperBitsWPStarter/teste 1/ig;'"
            //"find ./ -name '*.xml'    -exec perl -i.bkp -p -e \'s/teste/teste3/ig;\' {} +"
            //bash -c sed -i s/teste/teste5/g *.xml

            substituirPalavras.setTipoComando(this);
            substituirPalavras.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.SEMRESPOSTA);
            //substituirPalavras.criarParamentro("textoOriginal");
            //substituirPalavras.criarParamentro("novoTexto");
            //substituirPalavras.criarParamentro("pastaReplaceString");
            return substituirPalavras;
        }

    },
    /**
     * RENOMEIA TODAS AS PASTA E ARQUIVOS DENTRO DE UM DIRETORIO
     *
     * OS PARAMETROS NESCESSÁRIOS SÃO:
     *
     * [diretorio] [textoAntigo] [novoTexto]
     */
    LNX_RENOMEAR_TODOS_ARQUIVOS_E_PASTAS_DO_DIRETORIO_ {
        @Override
        public Comando getComando() {

            Comando renomearArqivosPasta = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, "lnxReplaceNomeArquivosEPastasDoDiretorio.sh");
            renomearArqivosPasta.setTipoComando(this);
            renomearArqivosPasta.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.MACH);
            return renomearArqivosPasta;

        }

    },
    /**
     *
     * Remove todos os arquivos com este nome em um diretorio --------
     * Configurar parametros: ####################################
     * [diretorioExclusaoRecursiva] [arquivo] .#############################
     * Aceita caracteres coringa de diretorio como *
     *
     *
     */
    LNX_REMOVER_TODOS_ARQUIVOS_COM_ESTE_NOME {

        @Override
        public Comando getComando() {
            Comando removerArquivos = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, "deleteArquivoRecursive.sh");
            removerArquivos.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.SEMRESPOSTA);
            removerArquivos.setTipoComando(LNX_REMOVER_TODOS_ARQUIVOS_COM_ESTE_NOME);
            return removerArquivos;
        }
    },
    /**
     * REmove todas as pastas com um nome especifico, realiza a busca de forma
     * recursiva
     *
     * Configurar parametros: [pastaRecursiva] [nomePastaExclusao] *Aceita
     * caracteres coringa de diretorio como *
     *
     *
     */
    LNX_REMOVER_TODAS_PASTAS_COM_ESTE_NOME {

        @Override
        public Comando getComando() {
            Comando removerPasta = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, "deletePastaRecursive.sh");
            removerPasta.setRegexResultadoEsperado("Arquivo ou diretório");
            return removerPasta;
        }

    },
    /**
     *
     * Adiciona todos os arquivos a um diretorio svn
     *
     */
    LNXSVN_ADCIONAR_TODOS_ARQUIVOS {

        @Override
        public Comando getComando() {

            Comando substituirPalavras = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "");
            substituirPalavras.setTipoComando(LNXSVN_ADCIONAR_TODOS_ARQUIVOS);
            substituirPalavras.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.SEMRESPOSTA);
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            //return substituirPalavras;
        }

    },
    /**
     *
     * Entra em um diretorio svn e adiciona o arquivo ao repositorio multiplis
     * arquivos podem ser enviados separando cada um por espaço no parametro
     * [arquivo]
     *
     *
     *
     */
    LNXSVN_ADD_ARQUIVO_REPOSITORIO {

        @Override
        public Comando getComando() {
            Comando addSVNRepositorio = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "svn add :arquivo ");
            addSVNRepositorio.setCaminhoOBrigatorio(true);
            addSVNRepositorio.setTipoComando(LNXSVN_ADD_ARQUIVO_REPOSITORIO);
            addSVNRepositorio.setCaminhoOBrigatorio(true);
            addSVNRepositorio.setRegexResultadoEsperado("A        ");
            return addSVNRepositorio;

        }

    },
    NXSVN_EFETUARCOMIIT {

        @Override
        public Comando getComando() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    },
    /**
     * EXECUTA O COMANDO STATUS Configurar parametros [pastaSvnStatus]
     *
     *
     */
    LNXSVN_LISTARSTATUS {

        @Override
        public Comando getComando() {
            Comando svnStatus = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "svn status :pastaSvnStatus");

            svnStatus.setTipoComando(LNXSVN_ADCIONAR_TODOS_ARQUIVOS);
            svnStatus.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.SEMRESPOSTA);

            return svnStatus;
        }

    },
    /**
     * EXECUTA O CHEOUT DE UM REPOSITORIO SVN
     *
     * Precisa setar o nome da :pasta e o caminho onde a pasta será criada no
     * diretorioExecucao
     *
     * parametro [usuario] [senha] [caminhoRepostorio] [pasta]
     *
     */
    LNXSVN_CHECKOUT {

        @Override
        public Comando getComando() {

            Comando svnCheckout = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, "svnCheckout.sh");

            svnCheckout.setTipoComando(LNXSVN_CHECKOUT);
            svnCheckout.setRegexResultadoEsperado("de trabalho para revis");

            return svnCheckout;

        }

    },
    /**
     *
     * Cria diretorios de maneira recursiva parametros: [pastaCriar]
     */
    LNXDIR_MAKEDIR {

        @Override
        public Comando getComando() {

            Comando substituirPalavras = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "mkdir :pastaCriar -p");

            substituirPalavras.setTipoComando(LNXDIR_MAKEDIR);
            substituirPalavras.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.SEMRESPOSTA);

            return substituirPalavras;

        }

    },
    /**
     * MOVE PASTA PARA UM OUTRO DIRETORIO (CERTIFIQUE QUE A SUBPASTA DO NOVO
     * DIRETORIO EXISTA)
     *
     * [pastaMovOri] [pastaMovDest]
     *
     *
     */
    LNXDIR_MOVERPASTA {

        @Override
        public Comando getComando() {
            Comando moverPasta = new Comando(Comando.TIPO_EXECUCAO.DIRETO, "mv :pastaMovOri :pastaMovDest ");
            return moverPasta;
        }

    },
    /**
     * MOVE PASTA PARA UM OUTRO DIRETORIO (CERTIFIQUE QUE A SUBPASTA DO NOVO
     * DIRETORIO EXISTA)
     *
     * [pastaMovOri] [pastaMovDest]
     *
     *
     */
    LNXDIR_MOVER_SUBSTITUINDO {

        @Override
        public Comando getComando() {
            Comando moverPasta = new Comando(Comando.TIPO_EXECUCAO.DIRETO, " rsync -a :pastaMovOri :pastaMovDest --remove-sent-files --ignore-existing --whole-file ");
            return moverPasta;
        }

    };

    public abstract Comando getComando();

    @Override
    public Comando getRegistro() {
        return this.getComando();
    }

}
