package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LivroDao livroDao;

	public PieChartModel getEstoqueLivros() {
    	
    	List<Livro> livros = livroDao.listaTodos();
    	PieChartModel estoqueLivros = new PieChartModel();
    	Integer total = 0;
    	
    	for (Livro livro : livros) {
    		estoqueLivros.set(livro.getTitulo() + " (" + livro.getEstoque().toString() + ")", livro.getEstoque());
    		total = total + livro.getEstoque();
    	}
    	

    	estoqueLivros.setTitle("Estoque Total: " + total.toString());
    	estoqueLivros.setLegendPosition("w");
    	estoqueLivros.setShadow(false);    	
    	
        return estoqueLivros;
    }


	public BarChartModel getVendasModel() {

		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries vendasSerie = new ChartSeries();
		vendasSerie.setLabel("Vendas 2020");

		List<Venda> vendas = getVendas(1234);
		for (Venda venda : vendas) {
			vendasSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		ChartSeries vendasSerie2021 = new ChartSeries();
		vendasSerie2021.setLabel("Vendas Proje��o");
		
		vendas = getVendas(4321);
		for (Venda venda : vendas) {
			vendasSerie2021.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		model.addSeries(vendasSerie);
		model.addSeries(vendasSerie2021);

		return model;
	}

	public List<Venda> getVendas(long seed) {

		List<Livro> livros = livroDao.listaTodos();

		List<Venda> vendas = new ArrayList<Venda>();

		Random random = new Random(seed);
		for (Livro livro : livros) {
		
			Integer quantidade = random.nextInt(500);

			vendas.add(new Venda(livro, quantidade));
		}

		return vendas;
	}

}
