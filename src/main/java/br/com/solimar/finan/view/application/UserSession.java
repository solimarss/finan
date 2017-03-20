package br.com.solimar.finan.view.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Usuario;

@SessionScoped
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private ContaApp contaApp;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ContaApp getContaApp() {
		contaApp = new ContaApp();
		contaApp.setId(1L);
		return contaApp;
	}

	public void setContaApp(ContaApp contaApp) {
		this.contaApp = contaApp;
	}

}
