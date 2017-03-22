package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.CartaoCredito;
import br.com.solimar.finan.entity.ContaBancaria;
import br.com.solimar.finan.persistence.CartaoCreditoDAO;

@Stateless
public class CartaoCreditoRN implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private  CartaoCreditoDAO cartaoCreditoDAO;
	
	public void insert(CartaoCredito entity) {
		cartaoCreditoDAO.insert(entity);
	}
	
	
	
	public CartaoCredito find(Long id) {
		return cartaoCreditoDAO.find(id);
	}
	
	public List<CartaoCredito> findByNumero(CartaoCredito contaBancaria) {
		return cartaoCreditoDAO.findByNumero(contaBancaria);
	}
	

}
