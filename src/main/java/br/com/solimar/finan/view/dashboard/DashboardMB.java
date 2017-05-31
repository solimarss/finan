package br.com.solimar.finan.view.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;
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

	private BigDecimal totalDespesa = BigDecimal.ZERO;
	private BigDecimal totalReceita = BigDecimal.ZERO;
	private BigDecimal saldo = BigDecimal.ZERO;

	private List<ValueByGroup> valoresByCategoria;

	@Inject
	private LancamentoRN lancamentoRN;

	@PostConstruct
	private void init() {

		valoresByCategoria = lancamentoRN.sumValorGroupByCategoria(LancamentoTipoEnum.S, userSession.getContaApp(),
				userSession.getMes(), userSession.getAno());
		createChartPieValorByCategoria(valoresByCategoria);

		for (ValueByGroup valueByGroup : valoresByCategoria) {
			totalDespesa = totalDespesa.add(valueByGroup.getValor());
		}

		totalReceita = lancamentoRN.sumValorEntrada(userSession.getContaApp(), userSession.getMes(),
				userSession.getAno());
		
		if (totalReceita == null) {
			totalReceita = BigDecimal.ZERO;
		}

		saldo = totalReceita.add(totalDespesa);

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

	public PieChartModel createChartPieValorByTipo(String categoriaNome) {

		PieChartModel chartPieValorByTipo = new PieChartModel();

		for (ValueByGroup valueCat : valoresByCategoria) {
			if (categoriaNome.equals(valueCat.getGroupName())) {
				for (ValueByGroup valueTipo : valueCat.getSubGroup()) {
					chartPieValorByTipo.set(valueTipo.getGroupName(), valueTipo.getValor());
				}
			}

		}
		chartPieValorByTipo.setTitle("Categoria: " + categoriaNome);
		chartPieValorByTipo.setLegendPosition("e");
		chartPieValorByTipo.setFill(false);
		chartPieValorByTipo.setShowDataLabels(true);
		chartPieValorByTipo.setDiameter(150);

		return chartPieValorByTipo;
	}

	public PieChartModel getChartPieValorBycategoria() {
		return chartPieValorBycategoria;
	}

	public void setChartPieValorBycategoria(PieChartModel chartPieValorBycategoria) {
		this.chartPieValorBycategoria = chartPieValorBycategoria;
	}

	public List<ValueByGroup> getValoresByCategoria() {
		return valoresByCategoria;
	}

	public void setValoresByCategoria(List<ValueByGroup> valoresByCategoria) {
		this.valoresByCategoria = valoresByCategoria;
	}

	public BigDecimal getTotalDespesa() {
		return totalDespesa;
	}

	public void setTotalDespesa(BigDecimal totalDespesa) {
		this.totalDespesa = totalDespesa;
	}

	public BigDecimal getTotalReceita() {
		return totalReceita;
	}

	public void setTotalReceita(BigDecimal totalReceita) {
		this.totalReceita = totalReceita;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}