package br.com.solimar.finan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.solimar.finan.enums.LancamentoTipoEnum;

@Entity
@Table(name="LANCAMENTO")
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DESCRICAO", length = 300)
	private String descricao;
	
	@Column(name = "TRANSACTIONID", length = 300)
	private String transactionId;

	@Column(name = "MEMO", length = 300)
	private String memo;

	@Column(name = "VALOR")
	private BigDecimal valor;

	// O lançamento tem seu valor desconsiderado quando
	// trata-se de uma tranferecia de valores entre contas
	// ou o valor foi dividido entre outros lançamentos.
	// Uma RECEITA será uma entrada que tem o valor considerado.
	// Uma DESPESA será uma saída que tem o valor considerado.
	@Column(name = "VALOR_CONSIDERADO")
	private boolean valorConsiderado = true;

	@Column(name = "DATA")
	@Temporal(value = TemporalType.DATE)
	private Date data;

	// No caso de uma despesa a data do pagamento,
	// Nas despesas de cartão de credito a data do pagamento,
	// da fatura
	@Column(name = "DATA_EFETIVACAO")
	@Temporal(value = TemporalType.DATE)
	private Date dataPagamento;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", nullable = false, length = 1)
	private LancamentoTipoEnum tipo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ContaApp contaApp;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ContaBancaria contaBancaria;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private CartaoCreditoFatura cartaoCreditoFatura;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Item item;
	
	@Column(name = "CATEGORIZADO")
	private boolean categorizado = true;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isValorConsiderado() {
		return valorConsiderado;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setValorConsiderado(boolean valorConsiderado) {
		this.valorConsiderado = valorConsiderado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public LancamentoTipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(LancamentoTipoEnum tipo) {
		this.tipo = tipo;
	}

	public ContaApp getContaApp() {
		return contaApp;
	}

	public void setContaApp(ContaApp contaApp) {
		this.contaApp = contaApp;
	}

	public CartaoCreditoFatura getCartaoCreditoFatura() {
		return cartaoCreditoFatura;
	}

	public void setCartaoCreditoFatura(CartaoCreditoFatura cartaoCreditoFatura) {
		this.cartaoCreditoFatura = cartaoCreditoFatura;
	}

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isCategorizado() {
		return categorizado;
	}

	public void setCategorizado(boolean categorizado) {
		this.categorizado = categorizado;
	}

	
	

}
