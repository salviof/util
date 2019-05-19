source ./SBProjeto.prop
source ./SBProjeto.prop
mysqladmin -u root create $NOME_BANCO
mysql -u root $NOME_BANCO < ./$NOME_BANCO.Homologacao.sql
if [ "$NOME_BANCO" == "superComprasModel" ]
then
mysql -u root superComprasModel < ./CategoriadoProduto.sql
mysql -u root superComprasModel < ./Produto.sql
fi
