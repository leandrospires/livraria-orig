package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@SuppressWarnings("serial")
public class DAO<T> implements Serializable {

	private final Class<T> classe;
	private EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) {
		this.em = manager;
		this.classe = classe;
	}

	public int quantidadeDeElementos() {
        //EntityManager em = new JPAUtil().getEntityManager();
        long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n")
                .getSingleResult();

        return (int) result;
    }
	
	public void adiciona(T t) {

		// consegue a entity manager
		//EntityManager em = new JPAUtil().getEntityManager();

		// abre transacao
		em.getTransaction().begin();

		// persiste o objeto
		em.persist(t);

		// commita a transacao
		em.getTransaction().commit();

		// fecha a entity manager
	}

	public void remove(T t) {
		//EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.remove(em.merge(t));

		em.getTransaction().commit();
	}

	public void atualiza(T t) {
		//EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.merge(t);

		em.getTransaction().commit();
	}

	public List<T> listaTodos() {
		//EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}
	
	public T buscaPorId(Integer id) {
		//EntityManager em = new JPAUtil().getEntityManager();
		T instancia = em.find(classe, id);
		return instancia;
	}


	public int contaTodos() {
		//EntityManager em = new JPAUtil().getEntityManager();
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();

		return (int) result;
	}

	public Integer qtdLivrosPorAutor(Autor autor) {
		
		//EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Livro> query = em.createQuery(
				"select l from Livro l join fetch l.autores a where a.id = :pAutor", Livro.class);

		query.setParameter("pAutor", autor.getId());
		
		Integer resultado;
		
		try {
			resultado = query.getResultList().size();
		} catch(NoResultException ex) {
			return 0;
		}
		
		
		//return autor.getId();
		return resultado;
		
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		//EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		return lista;
	}

}
