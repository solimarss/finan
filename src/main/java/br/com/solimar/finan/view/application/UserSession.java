package br.com.solimar.finan.view.application;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Usuario;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.util.DataUtil;

@Named
@SessionScoped
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private ContaApp contaApp;
	private Integer mes;
	private Integer ano;
	private LancamentoTipoEnum tipoES;

	@PostConstruct
	public void init() {
		contaApp = new ContaApp();
		contaApp.setId(1L);
		contaApp.setStartDay(20);
		contaApp.setUseStartDay(true);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ContaApp getContaApp() {
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

	public String nextMoth() {
		mes = getMes() + 1;
		if (mes > 12) {
			mes = 1;
			ano = ano + 1;
		}
		return getCurrentViewId();
	}

	public String previousMoth() {
		mes = getMes() - 1;
		if (mes < 1) {
			mes = 12;
			ano = ano - 1;
		}
		return getCurrentViewId();
	}

	public String getMesAsString() {

		System.out.println("contaApp.getUseStartDay(): " + contaApp.getUseStartDay());

		if (contaApp.getUseStartDay()) {

			Calendar calendarInicio = Calendar.getInstance();
			calendarInicio.set(getAno(), getMes()-1, contaApp.getStartDay());

			Calendar calendarFim = Calendar.getInstance();
			calendarFim.set(getAno(), getMes()-1, contaApp.getStartDay());
			calendarFim.add(Calendar.MONTH, 1);

			DateFormat df = new SimpleDateFormat("dd/MMM/yy", new Locale("pt", "BR"));
			String inicio = df.format(calendarInicio.getTime());
			String fim = df.format(calendarFim.getTime());

			return inicio + " - " + fim;

		} else {

			String s = "01/" + getMes() + "/" + getAno();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date dt;
			try {
				dt = df.parse(s);
			} catch (ParseException e) {
				e.printStackTrace();
				return "";

			}
			DateFormat df2 = new SimpleDateFormat("MMMMM", new Locale("pt", "BR"));

			String mes = df2.format(dt);
			System.out.println("getMesAsString " + mes);
			mes = mes + "/" + getAno();
			return mes;
		}
	}

	private String getCurrentViewId() {
		FacesContext context = FacesContext.getCurrentInstance();
		String currentViewId = context.getViewRoot().getViewId();
		return currentViewId;
	}

	public LancamentoTipoEnum getTipoES() {
		return tipoES;
	}

	public void setTipoES(LancamentoTipoEnum tipoES) {
		this.tipoES = tipoES;
	}

	public boolean isTipoESReceita() {
		if (tipoES == null) {
			return false;
		}

		if (tipoES.equals(LancamentoTipoEnum.E)) {
			return true;
		}
		return false;
	}

	public boolean isTipoESDespesa() {

		if (tipoES == null) {
			return false;
		}

		if (tipoES.equals(LancamentoTipoEnum.S)) {
			return true;
		}
		return false;
	}

}
