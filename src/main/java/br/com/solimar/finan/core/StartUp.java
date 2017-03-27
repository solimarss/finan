package br.com.solimar.finan.core;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.util.GeradorCodigo;

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
			
			
			Categoria c1 = new Categoria();
			c1.setNome("Alimentação");
			c1.setCodigo(GeradorCodigo.gerar());
			c1.setContaApp(contaApp);
			c1.setCreatedAt(new Date());
			c1.setTipo(LancamentoTipoEnum.S);
			c1.setUpdatedAt(new Date());
			
			Categoria c2 = new Categoria();
			c2.setNome("Moradia");
			c2.setCodigo(GeradorCodigo.gerar());
			c2.setContaApp(contaApp);
			c2.setCreatedAt(new Date());
			c2.setTipo(LancamentoTipoEnum.S);
			c2.setUpdatedAt(new Date());
			
			Categoria c3 = new Categoria();
			c3.setNome("Salário");
			c3.setCodigo(GeradorCodigo.gerar());
			c3.setContaApp(contaApp);
			c3.setCreatedAt(new Date());
			c3.setTipo(LancamentoTipoEnum.E);
			c3.setUpdatedAt(new Date());
			
			Categoria c4 = new Categoria();
			c4.setNome("Comissão");
			c4.setCodigo(GeradorCodigo.gerar());
			c4.setContaApp(contaApp);
			c4.setCreatedAt(new Date());
			c4.setTipo(LancamentoTipoEnum.E);
			c4.setUpdatedAt(new Date());
			
			em.persist(c1);
			em.persist(c2);
			em.persist(c3);
			em.persist(c4);
			
		}
		
		
		


	}

}
