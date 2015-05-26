package utils;

public class Objeto extends TipoSemantico{
	private String id, tipo;
	private int lineError;
	private Object valor;

	public Objeto(String id, String tipo, Object valor, Integer lineError) {
		this.id = id;
		this.tipo = tipo;
		this.valor = valor;
		this.lineError = lineError;
	}

	public int getLineError() {
		return lineError;
	}

	public void setLineError(int lineError) {
		this.lineError = lineError;
	}

	public String getId() {
		return id;
	}
	
	public String getNome() {
		return id;
	}

	public String getTipo() {
		return tipo;
	}
	
	public Object getValor() {
		return valor;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	public String toString(){
		return id + " " +tipo;
	}
	
	public String getTipoSemantico(){
		return "Variavel";
		
	}
}
