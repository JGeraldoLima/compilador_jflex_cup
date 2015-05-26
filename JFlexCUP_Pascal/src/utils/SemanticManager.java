package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SemanticManager {

	private static SemanticManager instance = null;

	public static int lineError = 0; // ADICIONAR EM TODOS OS METODOS

	// Chave = nome; Valor = tipo de retorno
	HashMap<String, String> functions = new HashMap<String, String>();

	// Chave = nome; Valor = lista com os tipos dos parametros
	HashMap<String, ArrayList<String>> funcParams = new HashMap<String, ArrayList<String>>();

	// Chave = nome; Valor = lista com os tipos dos parametros
	HashMap<String, ArrayList<String>> procedures = new HashMap<String, ArrayList<String>>();

	// Todas as variáveis já declaradas no código; Chave = nome; Valor = tipo.
	HashMap<String, String> variables = new HashMap<String, String>();

	// HashMap<String, String> constants = new HashMap<String, String>();

	// Valores das variáveis; Chave = nome da variável; Valor = lista com todos
	// os valores assumidos pela mesma
	HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();

	// Labels já declarados no código
	ArrayList<Integer> labels = new ArrayList<Integer>();

	// Tipos suportados pela linguagem
	ArrayList<String> types = new ArrayList<String>();

	// Todos os identificadores já declarados no código (nome de variável,
	// função, procedure, etc.)
	ArrayList<String> identifiers = new ArrayList<String>();

	// Guarda os tipos derivados de cada um
	ArrayList<String> integer, intSign, reals, strings, bool;

	private SemanticManager() {
		this.types = new ArrayList<String>(Arrays.asList("byte", "word",
				"cardinal", "shortint", "smallint", "integer", "longint",
				"real", "string", "char", "boolean"));
		this.integer = new ArrayList<String>(Arrays.asList("byte", "word",
				"cardinal", "shortint", "smallint", "integer", "longint"));
		this.reals = new ArrayList<String>(Arrays.asList("real"));
		this.strings = new ArrayList<String>(Arrays.asList("string", "char"));
		this.bool = new ArrayList<String>(Arrays.asList("boolean"));
	}

	public static SemanticManager getInstance() {
		if (instance == null) {
			instance = new SemanticManager();
		}
		return instance;
	}

	public static void setInstance(){
		instance = null;
	}
	/* ---------- AUX methods --------- */
	public void defaultValue(String variable) {
		ArrayList<String> value = values.get(variable.toLowerCase());
		if (value == null) {
			value = new ArrayList<String>();
			String tipo = variables.get(variable.toLowerCase());
			if (integer.contains(tipo) || intSign.contains(tipo)) {
				value.add("0");
			}
			if (reals.contains(tipo)) {
				value.add("0.0");
			}
			if (strings.contains(tipo)) {
				value.add("");
			}
			if (bool.contains(tipo)) {
				value.add("false");
			}
			values.put(variable.toLowerCase(), value);
		}

	}

	/* ---------- ADD methods --------- */
	public void addFunction(String identifier, String returnedType) {
		String identifierSearched = identifier.toLowerCase();
		String returnedTypeSearched = returnedType.toLowerCase();
		if (functions.containsKey(identifierSearched)
				&& identifiers.contains(identifierSearched)) {
			System.out.println("Identifier " + identifier + " already exists");
			System.exit(1);
		} else {
			if (!types.contains(returnedTypeSearched)) {
				System.out.println("Pascal does not regonizes the type: "
						+ returnedType);
				System.exit(1);
			} else {
				functions.put(identifierSearched, returnedTypeSearched);
				funcParams.put(identifierSearched, null);
			}
		}
	}

	public void addFunctionParams(String identifier,
			String functionReturnedType, ArrayList<String> params) {
		String searchedIdentifier = identifier.toLowerCase();
		String searchedIdentifierType = functionReturnedType.toLowerCase();
		if (functions.containsKey(searchedIdentifier)
				|| identifiers.contains(searchedIdentifier)) {
			if (functions.get(searchedIdentifier)
					.equals(searchedIdentifierType)) {
				funcParams.put(searchedIdentifier, params);
			} else {
				System.out
						.println("Function " + identifier
								+ " was not declared with type "
								+ functionReturnedType);
				System.exit(1);
			}
		} else {
			System.out.println("Function " + identifier + " does not exists");
			System.exit(1);
		}
	}

	public void addProcedure(String identifier) {
		String identifierSearched = identifier.toLowerCase();
		if (procedures.containsKey(identifierSearched)
				&& identifiers.contains(identifierSearched)) {
			System.out.println("Identifier " + identifier + " already exists");
			System.exit(1);
		} else {
			procedures.put(identifierSearched, null);
		}

	}

	public void addProcedureParams(String identifier, ArrayList<String> params) {
		String searchedIdentifier = identifier.toLowerCase();
		if (procedures.containsKey(searchedIdentifier)
				|| identifiers.contains(searchedIdentifier)) {
			funcParams.put(searchedIdentifier, params);
		} else {
			System.out.println("Function " + identifier + " does not exists");
			System.exit(1);
		}

	}

	public void addVariable(String identifier, String type, String value) {
		if (variables.containsKey(identifier)
				|| identifiers.contains(identifier)) {
			System.out.println("Duplicated identifier: " + identifier);
			System.exit(1);
		} else {
			identifiers.add(identifier);
			variables.put(identifier, null);
			if(type != null){
				if (!types.contains(type)) {
					System.out.println("Pascal does not support the type: " + type);
					System.exit(1);
				} else {
						variables.put(identifier, type);
						if (value != null){
							ArrayList<String> variableValues = values.get(identifier);
							if (variableValues == null) {
								variableValues = new ArrayList<String>();
							} else {
								variableValues.add(value);
							}
							values.put(identifier, variableValues);
						}
					}
			}
		}
	}

	public void addLabel(Integer label) {
		if (labels.contains(label)) {
			System.err.println("Label already declared: " + label + ". Error in line: " + lineError);
			System.exit(1);
		} else {
			labels.add(label);
		}
	}

	public void addIdentifier(String identifier) {
		if (identifiers.contains(identifier.toLowerCase())) {
			System.out.println("Duplicated identifier: " + identifier);
			System.exit(1);
		} else {
			identifiers.add(identifier);
		}
	}

	/* ---------- GET methods --------- */
	public String getVariableType(String variable) {
		String variableSearched = variable.toLowerCase();
		if (variables.containsKey(variableSearched)) {
			return variables.get(variableSearched).toLowerCase();
		}
		return "";
	}

	public String getVariableValue(String variable) {
		String variableSearched = variable.toLowerCase();
		if (values.containsKey(variableSearched)) {
			if (values.get(variableSearched) == null) {
				defaultValue(variableSearched);
			}
			int index = values.get(variableSearched).size();
			return values.get(variableSearched).get(index - 1);
		} else {
			return "";
		}
	}

	/* ---------- CHECK methods --------- */

	public boolean checkTypesHierarchy(String typeOfCalledMethod,
			String typeOfDefinition) {
		String typeOfCalledMethodTemp = typeOfCalledMethod.toLowerCase();
		String typeOfDefinitionTemp = typeOfDefinition.toLowerCase();

		if (integer.contains(typeOfCalledMethodTemp)
				&& integer.contains(typeOfDefinitionTemp)) {
			if (typeOfDefinitionTemp.equals("byte")
					&& typeOfCalledMethodTemp.equals("byte")) {
				return true;
			} else if (typeOfDefinitionTemp.equals("word")
					&& (typeOfCalledMethodTemp.equals("byte") || typeOfCalledMethodTemp
							.equals("word"))) {
				return true;
			} else if (typeOfDefinitionTemp.equals("cardinal")
					&& (typeOfCalledMethodTemp.equals("byte")
							|| typeOfCalledMethodTemp.equals("word") || typeOfCalledMethodTemp
								.equals("cardinal"))) {
				return true;
			} else if (typeOfDefinitionTemp.equals("shortint")
					&& typeOfCalledMethodTemp.equals("shortint")) {
				return true;
			} else if (typeOfDefinitionTemp.equals("smallint")
					&& (typeOfCalledMethodTemp.equals("shortint") || typeOfCalledMethodTemp
							.equals("smallint"))) {
				return true;
			} else if (typeOfDefinitionTemp.equals("integer")
					&& (typeOfCalledMethodTemp.equals("byte")
							|| typeOfCalledMethodTemp.equals("word")
							|| typeOfCalledMethodTemp.equals("cardinal")
							|| typeOfCalledMethodTemp.equals("shortint")
							|| typeOfCalledMethodTemp.equals("smallint") || typeOfCalledMethodTemp
								.equals("integer"))) {
				return true;
			} else if (typeOfDefinitionTemp.equals("longint")
					&& (typeOfCalledMethodTemp.equals("shortint")
							|| typeOfCalledMethodTemp.equals("smallint")
							|| typeOfCalledMethodTemp.equals("integer") || typeOfCalledMethodTemp
								.equals("longint"))) {
				return true;
			} else {
				System.out.println("Incompatible types: " + typeOfCalledMethod
						+ ", " + typeOfDefinition);
				return false;
			}
		} else if (reals.contains(typeOfCalledMethodTemp)
				&& reals.contains(typeOfDefinitionTemp)) {
			return true;
		} else if (bool.contains(typeOfCalledMethodTemp)
				&& bool.contains(typeOfDefinitionTemp)) {
			return true;
		} else if (strings.contains(typeOfCalledMethodTemp)
				&& strings.contains(typeOfDefinitionTemp)) {
			if (typeOfDefinitionTemp.equals("char")
					&& typeOfCalledMethodTemp.equals("char")) {
				return true;
			} else if (typeOfDefinitionTemp.equals("string")
					&& (typeOfCalledMethodTemp.equals("char") || typeOfCalledMethodTemp
							.equals("string"))) {
				return true;
			} else {
				System.out.println("Incompatible types: " + typeOfCalledMethod
						+ ", " + typeOfDefinition);
				return false;
			}
		} else {
			System.out.println("Incompatible types: " + typeOfCalledMethod
					+ ", " + typeOfDefinition);
			return false;
		}
	}

	public boolean checkFunctionParams(String identifier, String[] funcTypes) {
		String searchedIdentifier = identifier.toLowerCase();
		if (functions.containsKey(searchedIdentifier)
				|| identifiers.contains(searchedIdentifier)) {
			ArrayList<String> params = funcParams.get(searchedIdentifier);
			if (params.size() != funcTypes.length - 1) {
				System.out.println("Incorrect params lenght in function "
						+ identifier);
				return false;
			} else {
				for (int i = 1; i < funcTypes.length; i++) {
					String typeOfCalledFunction = funcTypes[i];
					String typeOfDefinition = params.get(i - 1);

					if (!checkTypesHierarchy(typeOfCalledFunction,
							typeOfDefinition)) {
						System.out.println("Expected param of type "
								+ params.get(i) + " found param type "
								+ funcTypes[i]);
						return false;
					}
				}
				return true;
			}

		}
		return false;

	}

	public boolean checkProcedureParams(String identifier,
			String[] procedureTypes) {
		String searchedIdentifier = identifier.toLowerCase();
		if (procedures.containsKey(searchedIdentifier)
				|| identifiers.contains(searchedIdentifier)) {
			ArrayList<String> params = procedures.get(searchedIdentifier);
			if (params.size() != procedureTypes.length - 1) {
				System.out.println("Incorrect params lenght in procedure "
						+ identifier);
				return false;
			} else {
				for (int i = 1; i < procedureTypes.length; i++) {
					String typeOfCalledFunction = procedureTypes[i];
					String typeOfDefinition = params.get(i - 1);

					if (!checkTypesHierarchy(typeOfCalledFunction,
							typeOfDefinition)) {
						System.out.println("Expected param of type "
								+ params.get(i) + " found param type "
								+ procedureTypes[i]);
						return false;
					}
				}
				return true;
			}

		}
		return false;
	}

	public void checkIfThenElseExpression(String type) throws Exception {
		if (type == null || !type.equals("boolean")) {
			throw new Exception(
					"IF-THEN-ELSE expression must receive a type boolean. Found: "
							+ type + ". Error in line: " + lineError);
		}
	}

	public void checkWhileExpression(String type) throws Exception {
		// System.out.println("Booleano = " + type);
		if (type == null || !type.equals("boolean")) {
			throw new Exception(
					"While expression must receive a type boolean. Found: "
							+ type + ". Error in line: " + lineError);
		}
	}

	public void checkCaseExpression(String type) throws Exception {
		// System.out.println("Expressao = " + tipo);
		if (type != null && (type.equals("boolean") || type.equals("nil"))) {
			throw new Exception("CASE expression cannot receive type " + type
					+ ". Line error: " + lineError);

		}
	}

	public void checkGoToExpression(String type) throws Exception {
		// System.out.println("Expressao = " + tipo);
		if (!type.equals("integer")) {
			throw new Exception(
					"GOTO expression must receive an integer, found " + type
							+ ". Line error: " + lineError);
		}
	}

	public void checkBooleanExpression(String type) throws Exception {
		// System.out.println("Booleano = " + type);
		if (type == null || !type.equals("boolean")) {
			throw new Exception(
					"Boolean expression must receive a type boolean. Found: "
							+ type + ". Error in line: " + lineError);
		}
	}

	public void checkAssignment(String type) throws Exception {
		// System.out.println("Atribuicao = " + tipo);
		if (type == null || !type.equals("assignment")) {
			throw new Exception(
					"Assignment expression type error. Line error: "
							+ lineError);
		}
	}

	/* ---------- OVERLOAD methods --------- */
	// verifica apenas se os operandos sao do mesmo tipo(no cup)

	public void checkArithmetic(String operator, String type) throws Exception {
		if (type == null || !type.equals("arithmetic")) {
			throw new Exception(
					"Arithmetic expression type error. Both operands must be of same type. Line error: "
							+ lineError);
		}

	}

	public void checkComparising(String operator, String type) throws Exception {
		if (type == null) {
			throw new Exception(
					"Comparising expression error. Types cannot be null. Line error: "
							+ lineError);
		}

	}

	public boolean checkFunctionOverload(String identifier,
			ArrayList<String> params) {
		String searchedIdentifier = identifier.toLowerCase();
		if (functions.containsKey(searchedIdentifier)
				|| identifiers.contains(searchedIdentifier)) {
			ArrayList<String> existingParams = funcParams
					.get(searchedIdentifier);
			if (existingParams.equals(params)) {
				System.out.println("It is not possible to create function "
						+ identifier + ": duplicated params");
				return false;
			}
		}
		return true;
	}
}
