package br.com.solimar.finan.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column( name = "ID" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CODIGO")
	private Long codigo;

	@Column(name = "NOME", length = 300)
	private String nome;

	@Column(name = "LOGIN", length = 100)
	private String login;

	@Column(name = "SENHA", length = 200)
	private String senha;

	@Column(name = "EMAIL", length = 300)
	private String email;
	
	@Column(name = "CREATED_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "UPDATE_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedAt;
	

	@ManyToMany
    @JoinTable(name = "USUARIO_HAS_CONTAAPP", joinColumns = { @JoinColumn(name = "USUARIO_ID") }, inverseJoinColumns = { @JoinColumn(name = "CONTAAPP_ID") })
    private List<ContaApp> contas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ContaApp> getContas() {
		return contas;
	}

	public void setContas(List<ContaApp> contas) {
		this.contas = contas;
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

	
	
	

}
