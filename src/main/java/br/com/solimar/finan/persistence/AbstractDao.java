package br.com.solimar.finan.persistence;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;

import br.com.solimar.finan.entity.Lancamento;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDao<T extends Serializable> implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Class<T> clazz;
	@Inject
	protected EntityManager em;
	@Resource
	protected SessionContext context;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T find(Object id) {
		return em.find(clazz, id);
	}

	public void insert(final T entity) {
		em.persist(entity);
	}

	public T update(final T entity) {
		return em.merge(entity);
	}

	public void delete(final Object id) {
		Object entity =  em.find(Lancamento.class, id);
		em.remove(entity);
	}

	public List<T> findAll() {
		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder()
				.createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		return em.createQuery(criteriaQuery).getResultList();
	}

	public void deleteAll() {
		final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder()
				.createCriteriaDelete(clazz);
		criteriaDelete.from(clazz);
		em.createQuery(criteriaDelete).executeUpdate();
	}
}