package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.Conta;
import br.com.solimar.finan.persistence.ContaBancariaDAO;

@Stateless
public class ContaRN implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private  ContaBancariaDAO contaBancariaDAO;
	
	public void insert(Conta entity) {
		contaBancariaDAO.insert(entity);
	}
	
	
	public Conta find(Long id) {
		return contaBancariaDAO.find(id);
	}
	
	public List<Conta> findByContaAndBanco(Conta contaBancaria) {
		return contaBancariaDAO.findByContaAndBanco(contaBancaria);
	}
	

}
