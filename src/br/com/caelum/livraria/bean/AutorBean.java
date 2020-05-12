package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();

	private Integer autorId;
	
	public Autor getAutor() {
		return autor;
	}

	public void carregarAutorPelaId () {
		if (autorId == null)
			return;
		
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		//new DAO<Autor>(Autor.class).adiciona(this.autor);
		
		if(this.autor.getId() == null ) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
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
		new DAO<Autor>(Autor.class).remove(autor);
	
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
}
