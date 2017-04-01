package br.com.solimar.finan.view.dashboard;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.view.application.UserSession;
import br.com.solimar.finan.vo.ValueByGroup;

@Named
@ViewScoped
public class DashboardMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserSession userSession;

	private PieChartModel chartPieValorBycategoria;

	private PieChartModel chartPieValorByTipo;

	@Inject
	private LancamentoRN lancamentoRN;

	@PostConstruct
	private void init() {
		List<ValueByGroup> valoresByCategoria = lancamentoRN.sumValorGroupByCategoria(LancamentoTipoEnum.S,
				userSession.getContaApp(), userSession.getMes(), userSession.getAno());
		createChartPieValorByCategoria(valoresByCategoria);
		
		List<ValueByGroup> chartPieValorByTipo = lancamentoRN.sumValorGroupByTipo(LancamentoTipoEnum.S,
				userSession.getContaApp(), userSession.getMes(), userSession.getAno());
		createChartPieValorByTipo(chartPieValorByTipo);

	}

	public void createChartPieValorByCategoria(List<ValueByGroup> valores) {
		chartPieValorBycategoria = new PieChartModel();

		for (ValueByGroup valorCat : valores) {
			chartPieValorBycategoria.set(valorCat.getGroupName(), valorCat.getValor());
		}

		chartPieValorBycategoria.setTitle("Despesa por Categoria");
		chartPieValorBycategoria.setLegendPosition("e");
		chartPieValorBycategoria.setFill(false);
		chartPieValorBycategoria.setShowDataLabels(true);
		chartPieValorBycategoria.setDiameter(150);
	}

	public void createChartPieValorByTipo(List<ValueByGroup> valores) {
		chartPieValorByTipo = new PieChartModel();

		for (ValueByGroup valorCat : valores) {
			chartPieValorByTipo.set(valorCat.getGroupName(), valorCat.getValor());
		}

		chartPieValorByTipo.setTitle("Despesa por Tipo");
		chartPieValorByTipo.setLegendPosition("e");
		chartPieValorByTipo.setFill(false);
		chartPieValorByTipo.setShowDataLabels(true);
		chartPieValorByTipo.setDiameter(150);
	}

	public PieChartModel getChartPieValorBycategoria() {
		return chartPieValorBycategoria;
	}

	public void setChartPieValorBycategoria(PieChartModel chartPieValorBycategoria) {
		this.chartPieValorBycategoria = chartPieValorBycategoria;
	}

	public PieChartModel getChartPieValorByTipo() {
		return chartPieValorByTipo;
	}

	public void setChartPieValorByTipo(PieChartModel chartPieValorByTipo) {
		this.chartPieValorByTipo = chartPieValorByTipo;
	}

}