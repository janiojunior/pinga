package br.unitins.topicos12021.model;

public enum Sexo {
	
	FEMININO(1, "Feminino"), 
	MASCULINO(2, "Masculino");
	
	private int id;
	private String label;
	
	Sexo(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}

}
