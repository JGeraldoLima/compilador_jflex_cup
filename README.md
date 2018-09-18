After import the project on Eclipse, add again the JFLEX and Cup .jars files which are not being found.

**the tests are automated. So to make them work just add the snippet<files/*.txt> inside projects "Run Configurations". All .txt file added there must have only Pascal code.**

**COMMANDS**: 
	if you edit only the flex, compile both. If you edit only the cup, just compile it. 
	After that, refresh project.
	
	- run jflex: java -jar .\JFlex.jar .\Pascal.flex
	- run cup: java java -jar .\java-cup-11a.jar -expect <value> .\Pascal.cup


SCOPE:
### ### - Procedures/Functions
### ### - Boolean expressions (conected or not by *and*, *or*, *not*) e literals (integers, strings, booleans)
### ### - Condicional commands
