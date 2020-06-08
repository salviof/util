#!/bin/bash
source ./CJTes_SBProjeto.prop
mysqldump -u root  -psenhaDev#123 $NOME_BANCO > $NOME_BANCO.Homologacao.sql
