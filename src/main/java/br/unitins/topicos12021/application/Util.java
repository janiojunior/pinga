package br.unitins.topicos12021.application;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;

import br.unitins.topicos12021.model.Usuario;

public class Util {
	
	public static void main(String[] args) {
		System.out.println(hash("janio"));
		System.out.println(hash("janio"));
		System.out.println(hash("Janio").length());
	}
	
	public static String hash(Usuario usuario) {
		return hash(usuario.getCpf()+usuario.getSenha());
	}
	
	private static String hash(String valor) {
		return DigestUtils.sha256Hex(valor);
	}
	
	private static void addMessage(String msg, Severity severity) {
		FacesContext.getCurrentInstance()
		.addMessage(null, new FacesMessage(severity, msg, null));
	}
	
	public static void addErrorMessage(String msg) {
		addMessage(msg,FacesMessage.SEVERITY_ERROR);
	}
	
	public static void addWarnMessage(String msg) {
		addMessage(msg,FacesMessage.SEVERITY_WARN);
	}
	
	public static void addInfoMessage(String msg) {
		addMessage(msg,FacesMessage.SEVERITY_INFO);
	}

}
