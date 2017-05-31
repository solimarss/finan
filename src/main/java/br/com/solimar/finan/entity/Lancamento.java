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
@Table(name = "LANCAMENTO")
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CODIGO")
	private Long codigo;

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
	@Column(name = "TIPOES", nullable = false, length = 1)
	private LancamentoTipoEnum tipoES;

	@ManyToOne(fetch = FetchType.EAGER)
	private Conta conta;

	@ManyToOne(fetch = FetchType.EAGER)
	private Fatura cartaoCreditoFatura;

	@ManyToOne(fetch = FetchType.EAGER)
	private Tipo tipo;

	@Column(name = "IS_ABASTECIMENTO")
	private Boolean isAbastecimento = false;
	
	@Column(name = "KILOMETRAGEM", length = 300)
	private Integer kilometragem;
	
	@Column(name = "CATEGORIZADO")
	private boolean categorizado = true;

	@Column(name = "CREATED_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "UPDATE_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	private ContaApp contaApp;

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

	public LancamentoTipoEnum getTipoES() {
		return tipoES;
	}

	public void setTipoES(LancamentoTipoEnum tipoES) {
		this.tipoES = tipoES;
	}

	public ContaApp getContaApp() {
		return contaApp;
	}

	public void setContaApp(ContaApp contaApp) {
		this.contaApp = contaApp;
	}

	public Fatura getCartaoCreditoFatura() {
		return cartaoCreditoFatura;
	}

	public void setCartaoCreditoFatura(Fatura cartaoCreditoFatura) {
		this.cartaoCreditoFatura = cartaoCreditoFatura;
	}

	
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public boolean isCategorizado() {
		return categorizado;
	}

	public void setCategorizado(boolean categorizado) {
		this.categorizado = categorizado;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Boolean getIsAbastecimento() {
		return isAbastecimento;
	}

	public void setIsAbastecimento(Boolean isAbastecimento) {
		this.isAbastecimento = isAbastecimento;
	}

	public Integer getKilometragem() {
		return kilometragem;
	}

	public void setKilometragem(Integer kilometragem) {
		this.kilometragem = kilometragem;
	}

}
