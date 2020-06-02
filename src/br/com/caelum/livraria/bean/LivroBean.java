package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();
	private Integer autorId;
	private Integer livroId;
	private Integer qtdLivros;
	private Integer qtdLivrosporAutor;
	
	private List<Livro> livros;
	
	//Injeção de dependências
	@Inject
	private LivroDao livroDao; //CDI faz new AutorDao() e injeta
	
	@Inject
	private AutorDao autorDao;

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
		
		this.livro = livroDao.buscaPorId(getLivroId());
	}
	
	public List<Autor> getAutores() {

		return autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		qtdLivrosporAutor = this.livro.getAutores().size();
		return this.livro.getAutores();
	}

	public List<Livro> getLivros() {
		
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}
		
		qtdLivros = livros.size();
		
		return livros;
	}
	
	public Integer verQtdLivros () {
		
		List<Livro> listaLivros = livroDao.listaTodos();
		
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
		Autor autor = autorDao.buscaPorId(this.autorId);
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

		if(this.livro.getId() == null ) {
			livroDao.adiciona(this.livro);
			this.livros = livroDao.listaTodos();
		} else {
			livroDao.atualiza(this.livro);
		}
		
		qtdLivros = livros.size();
		this.livro = new Livro();
		
		
	}

	public void remove(Livro livro) {
		System.out.println("Removendo livro: " + this.livro.getTitulo());

		livroDao.remove(livro);

		qtdLivros = livros.size();
		this.livros = livroDao.listaTodos();
	
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
