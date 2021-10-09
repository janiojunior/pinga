package br.unitins.topicos12021.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Telefone {

	@Size(min = 2, max = 2, message = "Informe ao menos 2 digitos para codigo de área.")
	@NotNull(message = "o código de área é obrigatório.")
	private String codigoArea;
	
	@Size(min = 9, max = 10, message="O número deve ter ao menos 9 digitos.")
	@Pattern(regexp = "([0-9]{5}[-]?[0-9]{4})")
	private String numero;

	public String getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
