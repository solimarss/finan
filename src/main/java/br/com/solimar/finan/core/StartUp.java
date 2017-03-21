package br.com.solimar.finan.core;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.solimar.finan.entity.ContaApp;

/**
 * @author Solimar
 */

@Startup
@Singleton
@ApplicationScoped
public class StartUp {

	@Inject
	private EntityManager em;
	
	@PostConstruct
	public void init() {
		System.out.println("INIT");
		ContaApp contaApp = em.find(ContaApp.class, 1L);

		if (contaApp == null) {
			
			contaApp = new ContaApp();
			contaApp.setId(1L);
			contaApp = em.merge(contaApp);

		}
		


	}

}
