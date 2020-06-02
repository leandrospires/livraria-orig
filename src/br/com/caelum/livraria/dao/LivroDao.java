package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@SuppressWarnings("serial")
public class LivroDao implements Serializable {
	
	@Inject
	private EntityManager manager; //new EntityManager
	
	private DAO<Livro> livroDao;
	
	public List<Livro> listaTodos() {
		return livroDao.listaTodos();
	}

	@PostConstruct
	void init() {
		this.livroDao = new DAO<Livro>(this.manager, Livro.class);
	}

	public int quantidadeDeElementos() {
		return livroDao.quantidadeDeElementos();
	}

	public void adiciona(Livro t) {
		livroDao.adiciona(t);
	}

	public void remove(Livro t) {
		livroDao.remove(t);
	}

	public void atualiza(Livro t) {
		livroDao.atualiza(t);
	}

	public Livro buscaPorId(Integer id) {
		return livroDao.buscaPorId(id);
	}

	public int contaTodos() {
		return livroDao.contaTodos();
	}

	public Integer qtdLivrosPorAutor(Autor autor) {
		return livroDao.qtdLivrosPorAutor(autor);
	}

	public List<Livro> listaTodosPaginada(int firstResult, int maxResults) {
		return livroDao.listaTodosPaginada(firstResult, maxResults);
	}
	
	

}
