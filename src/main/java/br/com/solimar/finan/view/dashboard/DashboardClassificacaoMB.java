package br.com.solimar.finan.view.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.enums.TipoClassificacaoEnum;
import br.com.solimar.finan.view.application.UserSession;
import br.com.solimar.finan.vo.ValueByGroup;

@Named
@ViewScoped
public class DashboardClassificacaoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserSession userSession;

	private PieChartModel chartPieValorBycategoria;

	private BigDecimal totalDespesa = BigDecimal.ZERO;
	private BigDecimal totalReceita = BigDecimal.ZERO;

	private List<ValueByGroup> valoresByCategoria;

	@Inject
	private LancamentoRN lancamentoRN;

	@PostConstruct
	private void init() {

		
		//TODO terminar a implemtação do dashboard classificação cria o xml
		List<Lancamento> lancamentos = lancamentoRN.findSaidas(userSession.getContaApp(), userSession.getMes(), userSession.getAno());
		
		valoresByCategoria = new ArrayList<>();
		List<Lancamento> of = new ArrayList<>();
		List<Lancamento> ov = new ArrayList<>();
		List<Lancamento> nof = new ArrayList<>();
		List<Lancamento> nov = new ArrayList<>();
		List<Lancamento> naoClassificado = new ArrayList<>();
		for (Lancamento lancamento : lancamentos) {
			if(lancamento.getTipo().getClassificacao()== null){
				naoClassificado.add(lancamento);
			}
			else if(lancamento.getTipo().getClassificacao().equals(TipoClassificacaoEnum.OF)){
				of.add(lancamento);
			}else if(lancamento.getTipo().getClassificacao().equals(TipoClassificacaoEnum.OV)){
				ov.add(lancamento);
			}else if(lancamento.getTipo().getClassificacao().equals(TipoClassificacaoEnum.NOF)){
				nof.add(lancamento);
			}else if(lancamento.getTipo().getClassificacao().equals(TipoClassificacaoEnum.NOV)){
				nov.add(lancamento);
			}else {
				naoClassificado.add(lancamento);
			}
		}
		
		BigDecimal totalOF = BigDecimal.ZERO;
		for (Lancamento lancamento : of) {
			totalOF = totalOF.add(lancamento.getValor());
		}
		ValueByGroup valueByGroupOF = new ValueByGroup();
		valueByGroupOF.setValor(totalOF);
		valueByGroupOF.setGroupName("OF");
		
		BigDecimal totalOV = BigDecimal.ZERO;
		for (Lancamento lancamento : ov) {
			totalOV = totalOV.add(lancamento.getValor());
		}
		ValueByGroup valueByGroupOV = new ValueByGroup();
		valueByGroupOV.setValor(totalOV);
		valueByGroupOV.setGroupName("OV");
		
		
		BigDecimal totalNOF = BigDecimal.ZERO;
		for (Lancamento lancamento : nof) {
			totalNOF = totalNOF.add(lancamento.getValor());
		}
		ValueByGroup valueByGroupNOF = new ValueByGroup();
		valueByGroupNOF.setValor(totalNOF);
		valueByGroupNOF.setGroupName("NOF");
		
		
		BigDecimal totalNOV = BigDecimal.ZERO;
		for (Lancamento lancamento : nov) {
			totalNOV = totalNOV.add(lancamento.getValor());
		}
		ValueByGroup valueByGroupNOV = new ValueByGroup();
		valueByGroupNOV.setValor(totalNOV);
		valueByGroupNOV.setGroupName("NOV");
		
		BigDecimal totalNC = BigDecimal.ZERO;
		for (Lancamento lancamento : naoClassificado) {
			totalNC = totalNC.add(lancamento.getValor());
		}
		ValueByGroup valueByGroupNC = new ValueByGroup();
		valueByGroupNC.setValor(totalNC);
		valueByGroupNC.setGroupName("NC");
		
		
		
		
		valoresByCategoria.add(valueByGroupOF);
		valoresByCategoria.add(valueByGroupOV);
		valoresByCategoria.add(valueByGroupNOF);
		valoresByCategoria.add(valueByGroupNOV);
		valoresByCategoria.add(valueByGroupNC);
		
		
		createChartPieValorByCategoria(valoresByCategoria);

		for (ValueByGroup valueByGroup : valoresByCategoria) {
			totalDespesa = totalDespesa.add(valueByGroup.getValor());
		}

		totalReceita = lancamentoRN.sumValorEntrada(userSession.getContaApp(), userSession.getMes(),
				userSession.getAno());

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

}