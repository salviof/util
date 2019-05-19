-- MySQL dump 10.13  Distrib 5.6.41, for Linux (x86_64)
--
-- Host: localhost    Database: SBTarefaAgendadaModel
-- ------------------------------------------------------
-- Server version	5.6.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AcaoDoSistema`
--

DROP TABLE IF EXISTS `AcaoDoSistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AcaoDoSistema` (
  `tipoAcaoDB` varchar(31) NOT NULL,
  `id` int(11) NOT NULL,
  `cor` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `diretorioBaseArquivos` varchar(255) DEFAULT NULL,
  `iconeAcao` varchar(255) DEFAULT NULL,
  `idDescritivoJira` varchar(255) DEFAULT NULL,
  `nomeAcao` varchar(255) DEFAULT NULL,
  `nomeSlugDominio` varchar(255) DEFAULT NULL,
  `nomeUnico` varchar(255) DEFAULT NULL,
  `precisaPermissao` bit(1) NOT NULL,
  `tipoAcao` varchar(255) DEFAULT NULL,
  `tipoAcaoGenerica` varchar(255) DEFAULT NULL,
  `acaoTemModal` bit(1) DEFAULT NULL,
  `campoJustificativa` varchar(255) DEFAULT NULL,
  `idMetodo` int(11) DEFAULT NULL,
  `precisaComunicacao` bit(1) DEFAULT NULL,
  `precisaJustificativa` bit(1) DEFAULT NULL,
  `temLogExecucao` bit(1) DEFAULT NULL,
  `textoComunicacaoPersonalizado` varchar(255) DEFAULT NULL,
  `xhtmlModalVinculado` varchar(255) DEFAULT NULL,
  `nomeDominio` varchar(255) DEFAULT NULL,
  `estadoFormulario` int(11) DEFAULT NULL,
  `xhtml` varchar(255) DEFAULT NULL,
  `nomeUnicoAcoesDisponiveis` tinyblob,
  `modulo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnff94k77k0bke2qf07y84ips5` (`modulo_id`),
  CONSTRAINT `FKnff94k77k0bke2qf07y84ips5` FOREIGN KEY (`modulo_id`) REFERENCES `ModuloAcaoSistema` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AcaoDoSistema`
--

LOCK TABLES `AcaoDoSistema` WRITE;
/*!40000 ALTER TABLE `AcaoDoSistema` DISABLE KEYS */;
/*!40000 ALTER TABLE `AcaoDoSistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Bairro`
--

DROP TABLE IF EXISTS `Bairro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Bairro` (
  `id` int(11) NOT NULL,
  `coordenadas` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `cidade_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc6grs1jdy5u4dhdfsyd61jgk5` (`cidade_id`),
  CONSTRAINT `FKc6grs1jdy5u4dhdfsyd61jgk5` FOREIGN KEY (`cidade_id`) REFERENCES `Cidade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bairro`
--

LOCK TABLES `Bairro` WRITE;
/*!40000 ALTER TABLE `Bairro` DISABLE KEYS */;
/*!40000 ALTER TABLE `Bairro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cidade`
--

DROP TABLE IF EXISTS `Cidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cidade` (
  `id` int(11) NOT NULL,
  `ativo` bit(1) NOT NULL,
  `dataAlteracao` date DEFAULT NULL,
  `dataCriacao` date DEFAULT NULL,
  `nome` varchar(255) NOT NULL,
  `id_Localidade` int(11) DEFAULT NULL,
  `unidadeFederativa_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgj6br5dxt0ht6uje8hy4n2wgw` (`id_Localidade`),
  KEY `FK8fbd6ik5ht9qwwaik1mi6jgf0` (`unidadeFederativa_id`),
  CONSTRAINT `FK8fbd6ik5ht9qwwaik1mi6jgf0` FOREIGN KEY (`unidadeFederativa_id`) REFERENCES `UnidadeFederativa` (`id`),
  CONSTRAINT `FKgj6br5dxt0ht6uje8hy4n2wgw` FOREIGN KEY (`id_Localidade`) REFERENCES `Localidade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cidade`
--

LOCK TABLES `Cidade` WRITE;
/*!40000 ALTER TABLE `Cidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ConfiguracaoDePermissao`
--

DROP TABLE IF EXISTS `ConfiguracaoDePermissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ConfiguracaoDePermissao` (
  `id` int(11) NOT NULL,
  `nomeConfig` varchar(255) DEFAULT NULL,
  `permitirCriacaoDeGrupos` bit(1) NOT NULL,
  `permitirPermissaoDeUsuario` bit(1) NOT NULL,
  `ultimaVersaoBanco` varchar(255) DEFAULT NULL,
  `grupoUsuarioPadrao_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKah1otecpdqxj49533o8geb7c0` (`grupoUsuarioPadrao_id`),
  CONSTRAINT `FKah1otecpdqxj49533o8geb7c0` FOREIGN KEY (`grupoUsuarioPadrao_id`) REFERENCES `GrupoUsuarioSB` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ConfiguracaoDePermissao`
--

LOCK TABLES `ConfiguracaoDePermissao` WRITE;
/*!40000 ALTER TABLE `ConfiguracaoDePermissao` DISABLE KEYS */;
INSERT INTO `ConfiguracaoDePermissao` VALUES (0,NULL,'\0','\0','8968349657',NULL);
/*!40000 ALTER TABLE `ConfiguracaoDePermissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GrupoUsuarioSB`
--

DROP TABLE IF EXISTS `GrupoUsuarioSB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GrupoUsuarioSB` (
  `tipoGrupoUsuario` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `dataHoraAlteracao` datetime(6) DEFAULT NULL,
  `dataHoraInsersao` datetime(6) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `paginaInicial` varchar(255) DEFAULT NULL,
  `tipoGrupoNativo` bit(1) NOT NULL,
  `moduloPrincipal_id` int(11) DEFAULT NULL,
  `usuarioAlteracao_id` int(11) DEFAULT NULL,
  `usuarioInsercao_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3wv40cn2xjarnckhmw50xibgh` (`nome`),
  KEY `FKrcx4qo1ruqe8koyfg2vb238jg` (`moduloPrincipal_id`),
  KEY `FKn20xy684qdisrx1r0wyeffosn` (`usuarioAlteracao_id`),
  KEY `FK7s5bl86yjv1r1rfn9i964cq7g` (`usuarioInsercao_id`),
  CONSTRAINT `FK7s5bl86yjv1r1rfn9i964cq7g` FOREIGN KEY (`usuarioInsercao_id`) REFERENCES `UsuarioSB` (`id`),
  CONSTRAINT `FKn20xy684qdisrx1r0wyeffosn` FOREIGN KEY (`usuarioAlteracao_id`) REFERENCES `UsuarioSB` (`id`),
  CONSTRAINT `FKrcx4qo1ruqe8koyfg2vb238jg` FOREIGN KEY (`moduloPrincipal_id`) REFERENCES `ModuloAcaoSistema` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GrupoUsuarioSB`
--

LOCK TABLES `GrupoUsuarioSB` WRITE;
/*!40000 ALTER TABLE `GrupoUsuarioSB` DISABLE KEYS */;
/*!40000 ALTER TABLE `GrupoUsuarioSB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Localidade`
--

DROP TABLE IF EXISTS `Localidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Localidade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `dataAlteracao` date DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Localidade`
--

LOCK TABLES `Localidade` WRITE;
/*!40000 ALTER TABLE `Localidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `Localidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Localizacao`
--

DROP TABLE IF EXISTS `Localizacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Localizacao` (
  `tipoLocalizacao` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `complemento` varchar(255) DEFAULT NULL,
  `latitude` bigint(20) NOT NULL,
  `longitude` bigint(20) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `logradouro` varchar(255) DEFAULT NULL,
  `bairro_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1x27cqle98ktlx448eo2lticm` (`bairro_id`),
  CONSTRAINT `FK1x27cqle98ktlx448eo2lticm` FOREIGN KEY (`bairro_id`) REFERENCES `Bairro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Localizacao`
--

LOCK TABLES `Localizacao` WRITE;
/*!40000 ALTER TABLE `Localizacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `Localizacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ModuloAcaoSistema`
--

DROP TABLE IF EXISTS `ModuloAcaoSistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ModuloAcaoSistema` (
  `tipoModulo` varchar(31) NOT NULL,
  `id` int(11) NOT NULL,
  `dataHoraCriacao` date DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `iconeDaClasse` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `umModuloNativo` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ModuloAcaoSistema`
--

LOCK TABLES `ModuloAcaoSistema` WRITE;
/*!40000 ALTER TABLE `ModuloAcaoSistema` DISABLE KEYS */;
/*!40000 ALTER TABLE `ModuloAcaoSistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Negado_Grupos`
--

DROP TABLE IF EXISTS `Negado_Grupos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Negado_Grupos` (
  `id` int(11) NOT NULL,
  `acesso_id` int(11) DEFAULT NULL,
  `grupo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhd45weeoe1avo5qunwppgnfhm` (`acesso_id`),
  KEY `FK7m9qmg1btyx5cwglbveiugmbm` (`grupo_id`),
  CONSTRAINT `FK7m9qmg1btyx5cwglbveiugmbm` FOREIGN KEY (`grupo_id`) REFERENCES `GrupoUsuarioSB` (`id`),
  CONSTRAINT `FKhd45weeoe1avo5qunwppgnfhm` FOREIGN KEY (`acesso_id`) REFERENCES `PermissaoSB` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Negado_Grupos`
--

LOCK TABLES `Negado_Grupos` WRITE;
/*!40000 ALTER TABLE `Negado_Grupos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Negado_Grupos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Negado_Usuarios`
--

DROP TABLE IF EXISTS `Negado_Usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Negado_Usuarios` (
  `id` int(11) NOT NULL,
  `acesso_id` int(11) DEFAULT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK39gufe3ixex80jpg9lshm4y1v` (`usuario_id`,`acesso_id`),
  KEY `FKgm8m18ifbnjd04dyl9drki4va` (`acesso_id`),
  CONSTRAINT `FK8wgk7y8p69ttbgf7if2k5n1pt` FOREIGN KEY (`usuario_id`) REFERENCES `UsuarioSB` (`id`),
  CONSTRAINT `FKgm8m18ifbnjd04dyl9drki4va` FOREIGN KEY (`acesso_id`) REFERENCES `PermissaoSB` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Negado_Usuarios`
--

LOCK TABLES `Negado_Usuarios` WRITE;
/*!40000 ALTER TABLE `Negado_Usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `Negado_Usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ObjetoComPersistenciaTeste`
--

DROP TABLE IF EXISTS `ObjetoComPersistenciaTeste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ObjetoComPersistenciaTeste` (
  `id` int(11) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ObjetoComPersistenciaTeste`
--

LOCK TABLES `ObjetoComPersistenciaTeste` WRITE;
/*!40000 ALTER TABLE `ObjetoComPersistenciaTeste` DISABLE KEYS */;
/*!40000 ALTER TABLE `ObjetoComPersistenciaTeste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PermissaoSB`
--

DROP TABLE IF EXISTS `PermissaoSB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PermissaoSB` (
  `id` int(11) NOT NULL,
  `idAcaoGestao` int(11) NOT NULL,
  `idacaoDoSistema` int(11) NOT NULL,
  `nomeAcesso` varchar(255) DEFAULT NULL,
  `tipoAutenticacao` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PermissaoSB`
--

LOCK TABLES `PermissaoSB` WRITE;
/*!40000 ALTER TABLE `PermissaoSB` DISABLE KEYS */;
/*!40000 ALTER TABLE `PermissaoSB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Permitido_Grupos`
--

DROP TABLE IF EXISTS `Permitido_Grupos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Permitido_Grupos` (
  `id` int(11) NOT NULL,
  `acesso_id` int(11) DEFAULT NULL,
  `grupo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKonpllqmu9mxhrobrxvvdvqgw3` (`acesso_id`),
  KEY `FKqy7db3uujsot9o8hi9tr16ifw` (`grupo_id`),
  CONSTRAINT `FKonpllqmu9mxhrobrxvvdvqgw3` FOREIGN KEY (`acesso_id`) REFERENCES `PermissaoSB` (`id`),
  CONSTRAINT `FKqy7db3uujsot9o8hi9tr16ifw` FOREIGN KEY (`grupo_id`) REFERENCES `GrupoUsuarioSB` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Permitido_Grupos`
--

LOCK TABLES `Permitido_Grupos` WRITE;
/*!40000 ALTER TABLE `Permitido_Grupos` DISABLE KEYS */;
/*!40000 ALTER TABLE `Permitido_Grupos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Permitido_Usuarios`
--

DROP TABLE IF EXISTS `Permitido_Usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Permitido_Usuarios` (
  `id` int(11) NOT NULL,
  `acesso_id` int(11) DEFAULT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9j92iu9201tx0dm010v5fq4wo` (`usuario_id`,`acesso_id`),
  KEY `FKeb1qf23eyq6brt5o1sbbka3oq` (`acesso_id`),
  CONSTRAINT `FKeb1qf23eyq6brt5o1sbbka3oq` FOREIGN KEY (`acesso_id`) REFERENCES `PermissaoSB` (`id`),
  CONSTRAINT `FKkk1bvscvwf4sxlf15t8pk4e10` FOREIGN KEY (`usuario_id`) REFERENCES `UsuarioSB` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Permitido_Usuarios`
--

LOCK TABLES `Permitido_Usuarios` WRITE;
/*!40000 ALTER TABLE `Permitido_Usuarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `Permitido_Usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Regiao`
--

DROP TABLE IF EXISTS `Regiao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Regiao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alteradoEm` date DEFAULT NULL,
  `ativo` bit(1) NOT NULL,
  `criadoEm` date DEFAULT NULL,
  `nomeRegiao` varchar(255) DEFAULT NULL,
  `quantidadeCidades` int(11) NOT NULL,
  `sigla` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Regiao`
--

LOCK TABLES `Regiao` WRITE;
/*!40000 ALTER TABLE `Regiao` DISABLE KEYS */;
/*!40000 ALTER TABLE `Regiao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Regiao_Bairro`
--

DROP TABLE IF EXISTS `Regiao_Bairro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Regiao_Bairro` (
  `Regiao_id` int(11) NOT NULL,
  `bairros_id` int(11) NOT NULL,
  KEY `FKew8f3d6bl78s7v7htp3thaj1d` (`bairros_id`),
  KEY `FKewbhodadapv1i3pyarhm5vpsa` (`Regiao_id`),
  CONSTRAINT `FKew8f3d6bl78s7v7htp3thaj1d` FOREIGN KEY (`bairros_id`) REFERENCES `Bairro` (`id`),
  CONSTRAINT `FKewbhodadapv1i3pyarhm5vpsa` FOREIGN KEY (`Regiao_id`) REFERENCES `Regiao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Regiao_Bairro`
--

LOCK TABLES `Regiao_Bairro` WRITE;
/*!40000 ALTER TABLE `Regiao_Bairro` DISABLE KEYS */;
/*!40000 ALTER TABLE `Regiao_Bairro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UnidadeFederativa`
--

DROP TABLE IF EXISTS `UnidadeFederativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UnidadeFederativa` (
  `id` int(11) NOT NULL,
  `UF` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UnidadeFederativa`
--

LOCK TABLES `UnidadeFederativa` WRITE;
/*!40000 ALTER TABLE `UnidadeFederativa` DISABLE KEYS */;
/*!40000 ALTER TABLE `UnidadeFederativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UsuarioSB`
--

DROP TABLE IF EXISTS `UsuarioSB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UsuarioSB` (
  `tipoUsuario` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apelido` varchar(255) DEFAULT NULL,
  `ativo` bit(1) NOT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `dataCadastro` date DEFAULT NULL,
  `dataHoraAlteracao` datetime(6) DEFAULT NULL,
  `dataHoraInsersao` datetime(6) DEFAULT NULL,
  `email` varchar(120) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(60) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `grupo_id` int(11) NOT NULL,
  `localizacao_id` int(11) DEFAULT NULL,
  `usuarioAlteracao_id` int(11) DEFAULT NULL,
  `usuarioInsercao_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d8hre5rs465kuoya4b9epuot7` (`apelido`),
  UNIQUE KEY `UK_m1rxw56li2lkr3guust77ltso` (`email`),
  KEY `FKpu9xpcabqjpw3jjdb6mu4w3a9` (`grupo_id`),
  KEY `FKddt81m657meu8v89qakv0792x` (`localizacao_id`),
  KEY `FKa0hk7be13ip4xg104xlxghvba` (`usuarioAlteracao_id`),
  KEY `FKg5805u50psplpao25esj3i4om` (`usuarioInsercao_id`),
  CONSTRAINT `FKa0hk7be13ip4xg104xlxghvba` FOREIGN KEY (`usuarioAlteracao_id`) REFERENCES `UsuarioSB` (`id`),
  CONSTRAINT `FKddt81m657meu8v89qakv0792x` FOREIGN KEY (`localizacao_id`) REFERENCES `Localizacao` (`id`),
  CONSTRAINT `FKg5805u50psplpao25esj3i4om` FOREIGN KEY (`usuarioInsercao_id`) REFERENCES `UsuarioSB` (`id`),
  CONSTRAINT `FKpu9xpcabqjpw3jjdb6mu4w3a9` FOREIGN KEY (`grupo_id`) REFERENCES `GrupoUsuarioSB` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UsuarioSB`
--

LOCK TABLES `UsuarioSB` WRITE;
/*!40000 ALTER TABLE `UsuarioSB` DISABLE KEYS */;
/*!40000 ALTER TABLE `UsuarioSB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modulos_grupo`
--

DROP TABLE IF EXISTS `modulos_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modulos_grupo` (
  `grupo_id` int(11) NOT NULL,
  `modulo_id` int(11) NOT NULL,
  UNIQUE KEY `UKngph303pxo2b2mrtpfkoacmwq` (`grupo_id`,`modulo_id`),
  KEY `FK1616eg4vq1ubds5aof20ci82b` (`modulo_id`),
  CONSTRAINT `FK1616eg4vq1ubds5aof20ci82b` FOREIGN KEY (`modulo_id`) REFERENCES `ModuloAcaoSistema` (`id`),
  CONSTRAINT `FKf5g7yb014obr43smkvslw1hmu` FOREIGN KEY (`grupo_id`) REFERENCES `GrupoUsuarioSB` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modulos_grupo`
--

LOCK TABLES `modulos_grupo` WRITE;
/*!40000 ALTER TABLE `modulos_grupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `modulos_grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regiao_cidades`
--

DROP TABLE IF EXISTS `regiao_cidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regiao_cidades` (
  `regiao_id` int(11) NOT NULL,
  `cidade_id` int(11) NOT NULL,
  KEY `FKl4njys9fksqduv028a4j3mera` (`cidade_id`),
  KEY `FKgcjw6nyiocwydbhjayj1q0qe2` (`regiao_id`),
  CONSTRAINT `FKgcjw6nyiocwydbhjayj1q0qe2` FOREIGN KEY (`regiao_id`) REFERENCES `Regiao` (`id`),
  CONSTRAINT `FKl4njys9fksqduv028a4j3mera` FOREIGN KEY (`cidade_id`) REFERENCES `Cidade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regiao_cidades`
--

LOCK TABLES `regiao_cidades` WRITE;
/*!40000 ALTER TABLE `regiao_cidades` DISABLE KEYS */;
/*!40000 ALTER TABLE `regiao_cidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_grupo`
--

DROP TABLE IF EXISTS `usuario_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_grupo` (
  `grupo_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  UNIQUE KEY `usuarioDuplicado` (`usuario_id`,`grupo_id`),
  KEY `FKeq1sxhjeq2ml8suqspdgh1esk` (`grupo_id`),
  CONSTRAINT `FKeq1sxhjeq2ml8suqspdgh1esk` FOREIGN KEY (`grupo_id`) REFERENCES `GrupoUsuarioSB` (`id`),
  CONSTRAINT `FKr399r2larmugqgxwl0rbtywfi` FOREIGN KEY (`usuario_id`) REFERENCES `UsuarioSB` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_grupo`
--

LOCK TABLES `usuario_grupo` WRITE;
/*!40000 ALTER TABLE `usuario_grupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario_grupo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-03 14:10:12
