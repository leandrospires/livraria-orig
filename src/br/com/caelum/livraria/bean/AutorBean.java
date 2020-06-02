package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped //javax.faces.view.ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();
	private Integer autorId;
	private Integer qtdAutores;

	//Injeção de dependências
	@Inject
	private AutorDao autorDao; //CDI faz new AutorDao() e injeta
	
	public Autor getAutor() {
		return autor;
	}

	public String formIndex() {
		System.out.println("Index!");
		return "index?faces-redirect=true";
	}
	
	public void carregarAutorPelaId () {
		if (autorId == null)
			return;
		
		this.autor = this.autorDao.buscaPorId(autorId);
	}
	
	public String formLivro() {
		System.out.println("Chamado o form Livro!");
		return "livro?faces-redirect=true";
	}
	public List<Autor> getAutores() {
		
		List<Autor> listaAutores = this.autorDao.listaTodos();
		
		qtdAutores = listaAutores.size();
		return listaAutores;//new DAO<Autor>(Autor.class).listaTodos();

	}
	
	public Integer verQtdAutores() {
		
		List<Autor> listaAutores = this.autorDao.listaTodos();
		return listaAutores.size();
	}
	
	public Integer qtdLivrosPorAutor(Autor autor) {
		System.out.println("Autor: " + autor.getNome());
		Integer qtd = this.autorDao.qtdLivrosPorAutor(autor);
		
		return qtd;

	}
	
	public void gravar() {
		System.out.println("Gravando autor: " + this.autor.getNome());
		System.out.println("Gravando ID: " + this.autor.getId());

		//new DAO<Autor>(Autor.class).adiciona(this.autor);
		
		if(this.autor.getId() == null ) {
			this.autorDao.adiciona(this.autor);
		} else {
			this.autorDao.atualiza(this.autor);
		}
		
		this.autor = new Autor();
		
		//return "livro?face-redirect=true";
		
		//RedirectView rv = new RedirectView("livro");
		//return new RedirectView("livro");
	}
	
	public RedirectView voltar() {
		return new RedirectView("livro");
	}
	
	public void remover(Autor autor) {
		
		System.out.println("Removendo Autor " + autor.getNome());
		this.autorDao.remove(autor);
	
	}
	
	public void carregar(Autor autor) {
		System.out.println("Carregando Autor " + autor.getNome());
		this.autor = autor;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getQtdAutores() {
		return qtdAutores;
	}

	public void setQtdAutores(Integer qtdAutores) {
		this.qtdAutores = qtdAutores;
	}
}
