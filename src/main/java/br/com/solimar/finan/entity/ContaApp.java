package br.com.solimar.finan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="CONTA_APP")
public class ContaApp implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CODIGO")
	private Long codigo;
	
	@Column(name = "CREATED_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "UPDATE_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@Column(name = "START_DAY")
	private Integer startDay;
	
	@Column(name = "USE_START_DAY")
	private Boolean useStartDay;

	
	
	public ContaApp() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getStartDay() {
		return startDay;
	}

	@Transient
	public Integer getStartDayAsString() {
		String dia = startDay.toString();
		if(dia.length() == 1){
			dia = "0"+dia;
		}
		return startDay;
	}
	
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	public Boolean getUseStartDay() {
		if(useStartDay == null){
			useStartDay = false;
		}
		return useStartDay;
	}

	public void setUseStartDay(Boolean useStartDay) {
		this.useStartDay = useStartDay;
	}

	
}
