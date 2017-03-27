package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.Fatura;
import br.com.solimar.finan.persistence.FaturaDAO;

@Stateless
public class FaturaRN implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private  FaturaDAO cartaoCreditoFaturaDAO;
	
	public void insert(Fatura entity) {
		cartaoCreditoFaturaDAO.insert(entity);
	}
	
	
	
	public Fatura find(Long id) {
		return cartaoCreditoFaturaDAO.find(id);
	}
	
	public List<Fatura> findByVencimentoAndCartao(Fatura fatura) {
		return cartaoCreditoFaturaDAO.findByVencimentoAndCartao(fatura);
	}
	

}
