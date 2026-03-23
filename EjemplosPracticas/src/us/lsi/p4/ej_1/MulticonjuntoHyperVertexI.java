package us.lsi.p4.ej_1;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.common.Multiset;

public record MulticonjuntoHyperVertexI(Integer indice,Integer sumaRestante) 
	implements  MulticonjuntoHyperVertex {


	public static Integer n_elementos = DatosMulticonjunto.NUM_E;

	public static MulticonjuntoHyperVertex of(Integer i, Integer sr) {
		return  new MulticonjuntoHyperVertexI(i, sr);
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
				alternativas = IntStream.rangeClosed(0,max_div)
						.boxed()
						.collect(Collectors.toList());
				Collections.reverse(alternativas);
			}
		}	
		return alternativas;
	}

	private Integer greedyAction() {
		return this.sumaRestante / DatosMulticonjunto.getElemento(this.indice);
	}
	
	@Override
	public Boolean isBaseCase() {
		return (this.sumaRestante == 0 || this.indice >= MulticonjuntoHyperVertexI.n_elementos-1);
	}

	@Override
	public Double baseCaseWeight() {
		Double r = null;
		if (this.sumaRestante == 0) r = 0.;
		else if (this.indice == MulticonjuntoHyperVertexI.n_elementos-1) {
			if (this.sumaRestante % DatosMulticonjunto.getElemento(this.indice) == 0)
				r = greedyAction().doubleValue();
		}
		return r;
	}

	@Override
	public Multiset<Integer> baseCaseSolution() {
		Multiset<Integer> r = null;
		if (this.sumaRestante == 0) r = Multiset.empty();
		else if (this.indice == MulticonjuntoHyperVertexI.n_elementos-1) {
			Integer elem = DatosMulticonjunto.getElemento(this.indice);
			if (this.sumaRestante % elem == 0) {
				r = Multiset.empty();
				Integer nu = greedyAction();
				r.add(elem, nu);
			}
		}
		return r;
	}

	@Override
	public Multiset<Integer> solution(Integer a, List<Multiset<Integer>> solutions) {
		Integer elem = DatosMulticonjunto.getElemento(this.indice);
		Multiset<Integer> ms = solutions.get(0);
		ms.add(elem, a);
		return ms;
	}

	@Override
	public List<MulticonjuntoHyperVertex> neighbors(Integer a) {
		Integer sr = this.sumaRestante - a * DatosMulticonjunto.getElemento(this.indice);
		return List.of(MulticonjuntoHyperVertexI.of(indice + 1, sr));
	}

	@Override
	public MulticonjuntoHyperEdge edge(Integer a) {
		List<MulticonjuntoHyperVertex> targets = this.neighbors(a);
		return MulticonjuntoHyperEdge.of(this,targets,a);
	}

	public static Integer valor(Multiset<Integer> s) {
		return s.size();
	}

}

