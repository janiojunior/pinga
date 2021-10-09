package br.unitins.topicos12021.model;

public enum Perfil {
	
	ADMINISTRADOR(1, "Administrador"), 
	FUNCIONARIO(2, "Funcionário"), 
	CLIENTE(3, "Cliente");
	
	private int id;
	private String label;
	
	Perfil(int id, String label) {
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
