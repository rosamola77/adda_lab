package us.lsi.alg.multiconjuntos;

import java.util.List;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record MulticonjuntoVertex(Integer indice,Integer sumaRestante) 
	implements VirtualVertex<MulticonjuntoVertex, MulticonjuntoEdge, Integer> {


	public static MulticonjuntoVertex of(Integer i, Integer sumaRestante) {
		return new MulticonjuntoVertex(i,sumaRestante);
	}
	
	public static MulticonjuntoVertex start() {
		return  new MulticonjuntoVertex(0, DatosMulticonjunto.SUM);
	}
		
	public static Integer nElementos = DatosMulticonjunto.NUM_E;
	
	@Override
	public Boolean goal() {
		return  this.indice == DatosMulticonjunto.NUM_E;
	}
	
	@Override
	public Boolean goalHasSolution() {
		return  this.sumaRestante == 0;
	}
	
	public String toGraph() {
		return String.format("(%d,%d)", this.indice, this.sumaRestante);
	}

	// M�todos auxiliares

	public String toString() {
		return String.format("(%d,%d)", this.indice, this.sumaRestante);
	}

	// M�todos del grafo

	@Override
	public Boolean isValid() {
		return this.indice >= 0 && this.indice <= DatosMulticonjunto.NUM_E && sumaRestante >= 0;
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if (this.indice < DatosMulticonjunto.NUM_E) {
			if (this.indice == DatosMulticonjunto.NUM_E - 1) {
				if (this.sumaRestante % DatosMulticonjunto.getElemento(this.indice) == 0) {
					Integer max_div = this.greedyAction();
					alternativas.add(max_div);
				}
			} else {
				Integer max_div = this.greedyAction();
				alternativas = List2.rangeList(0, max_div + 1);
			}
		}	
		return alternativas = List2.reverse(alternativas);
	}

	@Override
	public MulticonjuntoVertex neighbor(Integer a) {
		Integer sumaRestante = this.sumaRestante() - (DatosMulticonjunto.getElemento(this.indice) * a);
		return MulticonjuntoVertex.of(this.indice + 1, sumaRestante);
	}

	@Override
	public MulticonjuntoEdge edge(Integer a) {
		return MulticonjuntoEdge.of(this, this.neighbor(a), a);
	}
	
	@Override
	public Integer greedyAction() {
		return this.sumaRestante / DatosMulticonjunto.getElemento(this.indice);
	}
	
	public Double accionReal() {
		return ((double)this.sumaRestante)/ DatosMulticonjunto.getElemento(this.indice);
	}

}

