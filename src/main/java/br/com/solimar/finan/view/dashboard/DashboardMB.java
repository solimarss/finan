package br.com.solimar.finan.view.dashboard;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
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

	private HorizontalBarChartModel chartBarTipoCategoria;

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

	public void chartBarTipoCategoria(List<ValueByGroup> valoresCat, List<ValueByGroup> valoresTipo) {
		chartBarTipoCategoria = new HorizontalBarChartModel();

		for (ValueByGroup valor : valores) {

		}

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Boys");
		boys.set("2004", 50);
		boys.set("2005", 96);
		boys.set("2006", 44);
		boys.set("2007", 55);
		boys.set("2008", 25);

		ChartSeries girls = new ChartSeries();
		girls.setLabel("Girls");
		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 82);
		girls.set("2007", 35);
		girls.set("2008", 120);

		chartBarTipoCategoria.addSeries(boys);
		chartBarTipoCategoria.addSeries(girls);

		chartBarTipoCategoria.setTitle("Horizontal and Stacked");
		chartBarTipoCategoria.setLegendPosition("e");
		chartBarTipoCategoria.setStacked(true);

		Axis xAxis = chartBarTipoCategoria.getAxis(AxisType.X);
		xAxis.setLabel("Births");
		xAxis.setMin(0);
		xAxis.setMax(200);

		Axis yAxis = chartBarTipoCategoria.getAxis(AxisType.Y);
		yAxis.setLabel("Gender");

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

	public HorizontalBarChartModel getChartBarTipoCategoria() {
		return chartBarTipoCategoria;
	}

	public void setChartBarTipoCategoria(HorizontalBarChartModel chartBarTipoCategoria) {
		this.chartBarTipoCategoria = chartBarTipoCategoria;
	}

}