#!/bin/bash

#TODO não permitir executar este script em qualquer diretorio
cd :diretorio
ls  | while read -r FILE
do
    newfile="$(echo ${FILE} |sed -e 's/:textoAntigo/:novoTexto/')" ;
    mv "${FILE}" "${newfile}" ;
done

