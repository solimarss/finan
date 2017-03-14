package br.com.solimar.finan.core;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Solimar
 */

@Startup
@Singleton
public class StartUp {

	@PostConstruct
	public void init() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jfinan");  
		EntityManager em = emf.createEntityManager();                               
		
		em.close();                                                                 
		emf.close();    

	}

}
