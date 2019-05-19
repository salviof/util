#!/bin/bash
cd :pastaRecursiva
find ./ -name ':arquivosPesquisados' -exec perl -i -p -e 's/:textoAntigo/:novoTexto/ig;' {} +


