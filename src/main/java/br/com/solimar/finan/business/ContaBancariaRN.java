package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.ContaBancaria;
import br.com.solimar.finan.persistence.ContaBancariaDAO;

@Stateless
public class ContaBancariaRN implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private  ContaBancariaDAO contaBancariaDAO;
	
	public void insert(ContaBancaria entity) {
		contaBancariaDAO.insert(entity);
	}
	
	
	public ContaBancaria find(Long id) {
		return contaBancariaDAO.find(id);
	}
	
	public List<ContaBancaria> findByAgenciaAndConta(ContaBancaria contaBancaria) {
		return contaBancariaDAO.findByAgenciaAndConta(contaBancaria);
	}
	

}
