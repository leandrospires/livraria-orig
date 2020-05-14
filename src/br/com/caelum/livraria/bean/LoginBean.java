package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {
	
	private Usuario usuario = new Usuario();
	
	public String efetuarLogin() {
		System.out.println("Efetuando Login: " + this.usuario.getEmail());
		FacesContext context  = FacesContext.getCurrentInstance();
		boolean existe = new UsuarioDao().existe(this.usuario);
		
		if (existe) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		} else {
			
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage("Usu�rio ou senha inv�lidos!"));
			//"login:email"
			return "login?faces-redirect=true";
		}

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String deslogar() {
		FacesContext context  = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "index?faces-redirect=true";
	}
	
	public String usuarioLogado() {
	
		FacesContext context  = FacesContext.getCurrentInstance();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if (usuarioLogado != null) {
			return usuarioLogado.getEmail();
		}
		
		return "Visitante";
	}

}