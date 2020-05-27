package br.com.caelum.livraria.bean;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	private Integer qtdLivros;
	private Integer qtdLivrosporAutor;
	
	private List<Livro> livros;

	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");

	public List<String> getGeneros() {
	    return generos;
	}
	
	public Livro getLivro() {
		return livro;
	}

	public void carregarLivroPelaId () {
		if (getLivroId() == null)
			return;
		
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(getLivroId());
	}
	
	public List<Autor> getAutores() {
		// return new DAO<Autor>(Autor.class).listaTodos();
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		qtdLivrosporAutor = this.livro.getAutores().size();
		return this.livro.getAutores();
	}

	public List<Livro> getLivros() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		
		if (this.livros == null) {
			this.livros = dao.listaTodos();
		}
		
		qtdLivros = livros.size();
		
		return livros;
	}
	
	
	/* 
	public List<Livro> getLivros() {
		
		List<Livro> listaLivros = new DAO<Livro>(Livro.class).listaTodos();
		
		qtdLivros = listaLivros.size();
		
		return listaLivros;
	}
	*/
	
	public Integer verQtdLivros () {
		
		List<Livro> listaLivros = new DAO<Livro>(Livro.class).listaTodos();
		
		return listaLivros.size();
		
	}

	public String formIndex() {
		System.out.println("Index!");
		return "index?faces-redirect=true";
	}
	
	public String formAutor() {
		System.out.println("Chamado o form Autor!");
		return "autor?faces-redirect=true";
	}

	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Erro: Autor: " + autor.getNome());
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor"));
			return;
		}

		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if(this.livro.getId() == null ) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		} else {
			dao.atualiza(this.livro);
		}
		
		qtdLivros = livros.size();
		this.livro = new Livro();
		
		
	}

	public void remove(Livro livro) {
		System.out.println("Removendo livro: " + this.livro.getTitulo());

		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		dao.remove(livro);

		qtdLivros = livros.size();
		this.livros = dao.listaTodos();
	
	}
	
	public void removerAutordoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}
	
	public void carregar(Livro livro) {
		
		System.out.println("Carregando livro " + this.livro.getTitulo());
		this.livro = livro;
		
		
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN Deve iniciar com '1'"));
		}
		
	}
	
	public void valorMaiorQueZero(FacesContext fc, UIComponent cp, Object value) throws ValidatorException {
		double valor = (Double) value;
		
		if(valor <= 0) {
			throw new ValidatorException(new FacesMessage("Preço deve ser maior que 0!"));
		}
	}

	public void dataInferiorHoje(FacesContext fc, UIComponent cp, Object value) throws ValidatorException {
		Calendar valor = (Calendar) value;

		Calendar dataAtual = Calendar.getInstance();
		
		if (valor.after(dataAtual)) {
			throw new ValidatorException(new FacesMessage("Data do Lançamento deve ser maior que a atual!"));
		}
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Integer getQtdLivros() {
		return qtdLivros;
	}

	public void setQtdLivros(Integer qtdLivros) {
		this.qtdLivros = qtdLivros;
	}

	public Integer getQtdLivrosporAutor() {
		return qtdLivrosporAutor;
	}

	public void setQtdLivrosporAutor(Integer qtdLivrosporAutor) {
		this.qtdLivrosporAutor = qtdLivrosporAutor;
	}
	
}
