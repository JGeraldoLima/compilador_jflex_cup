Ao importar o projeto no eclipse, adicionar novamente os .jar do JFLEX e do Cup que não estão sendo encontrados.

**Os testes estão automatizados. Portanto, para fazê-los funcionar basta adicionar o trecho <files/*.txt> no "Run Configurations" do projeto. Todo arquivo .txt que tiver lá deverá ter somente códigos em pascal.**

**COMANDOS**: 
	se editar apenas o flex, compila os dois. Se apenas o cup, compila só ele. Depois da um refresh no projeto.
	
	- rodar o jflex: java -jar .\JFlex.jar .\Pascal.flex
	- rodar o cup: java java -jar .\java-cup-11a.jar -expect <valor> .\Pascal.cup


ESCOPO:
### ### - Procedures/Funções
### ### - Expressões booleanas (conectadas ou não via *and*, *or*, *not*) e literais (inteiros, strings, booleanos)
### ### - Comandos condicionais