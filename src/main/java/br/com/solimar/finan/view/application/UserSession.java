package br.com.solimar.finan.view.application;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Usuario;
import br.com.solimar.finan.util.DataUtil;

@SessionScoped
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private ContaApp contaApp;
	private Integer mes;
	private Integer ano;

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

	public int getMes() {
		if (mes == null) {
			mes = DataUtil.getCurrentMonth();
		}

		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		if (ano == null) {
			ano = DataUtil.getCurrentYear();
		}
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public void nextMoth() {
		mes = getMes() + 1;
		if (mes > 12) {
			mes = 1;
			ano = ano + 1;
		}
	}

	public void previousMoth() {
		mes = getMes() - 1;
		if (mes < 1) {
			mes = 12;
			ano = ano - 1;
		}
	}

	public String getMesAsString() {
		String s = "01/" + getMes() + "/2000";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dt;
		try {
			dt = df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";

		}
		DateFormat df2 = new SimpleDateFormat("MMMMM", new Locale("pt", "BR"));

		return df2.format(dt);
	}

}
