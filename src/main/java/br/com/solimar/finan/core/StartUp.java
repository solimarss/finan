package br.com.solimar.finan.core;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.solimar.finan.entity.ContaApp;

/**
 * @author Solimar
 */

@Startup
@Singleton
public class StartUp {
	
	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	public void init() {
	              
		em.find(ContaApp.class, 1L);                                                         
	

	}

}
