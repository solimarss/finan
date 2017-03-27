package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.Conta;
import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.persistence.ContaDAO;

@Stateless
public class ContaRN implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private  ContaDAO contaDAO;
	
	public void insert(Conta entity) {
		contaDAO.insert(entity);
	}
	
	public void update(Conta entity) {
		contaDAO.update(entity);
	}
	
	public void save(Conta entity) {
		if(entity.getId() == null){
			insert(entity);
		}else{
			update(entity);
		}
	}
	
	public Conta find(Long id) {
		return contaDAO.find(id);
	}
	
	public List<Conta> findLancamentoManual(ContaApp contaApp) {
		return contaDAO.findLancamentoManual(contaApp);
	}
	public List<Conta> findByContaAndBanco(Conta contaBancaria) {
		return contaDAO.findByContaAndBanco(contaBancaria);
	}
	
	public List<Conta> listAll(ContaApp contaApp) {
		return contaDAO.listAll(contaApp);
	}

}
