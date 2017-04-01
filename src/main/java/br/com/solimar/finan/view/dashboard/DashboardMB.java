package br.com.solimar.finan.view.dashboard;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class DashboardMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserSession userSession;

	 private PieChartModel pieMan;
	 
		@Inject
		private LancamentoRN lancamentoRN;
	
	@PostConstruct
	private void init() {
		createPieMan();
		lancamentoRN.countGroupByCategoria(LancamentoTipoEnum.E, userSession.getContaApp(), userSession.getMes(), userSession.getAno());
	}
	
	public void createPieMan() {
		pieMan = new PieChartModel();
         
		 pieMan.set("Brand 1", 540);
		 pieMan.set("Brand 2", 325);
		 pieMan.set("Brand 3", 702);
		 pieMan.set("Brand 4", 421);
	         
		 pieMan.setTitle("Custom Pie");
		 pieMan.setLegendPosition("e");
		 pieMan.setFill(false);
		 pieMan.setShowDataLabels(true);
		 pieMan.setDiameter(150);
	}


	public PieChartModel getPieMan() {
		return pieMan;
	}

	public void setPieMan(PieChartModel pieMan) {
		this.pieMan = pieMan;
	}

	

}