package us.lsi.p4.ej_2;


import java.util.Comparator;
import java.util.List;
import java.util.Set;

import us.lsi.common.IntegerSet;
import us.lsi.common.Set2;
import us.lsi.p4.ej_1.MulticonjuntoHyperEdge;
import us.lsi.p4.ej_1.MulticonjuntoHyperVertex;

public record SubconjuntosHyperVertexI(Integer index, IntegerSet remaining) 
	implements  SubconjuntosHyperVertex {

	public static SubconjuntosHyperVertex initial() {
		return of(0, IntegerSet.of(DatosSubconjuntos.universo()));
	}

	public static SubconjuntosHyperVertex of(Integer i, IntegerSet rest) {
		return new SubconjuntosHyperVertexI(i, rest);
	}

	public String toGraph() {
		return String.format("%s,%s)",DatosSubconjuntos.nombre(this.index),this.remaining().isEmpty()?"Y":"N");
	}
	
	public List<Integer> actions() {
		List<Integer> r;
		if ((index == DatosSubconjuntos.NUM_SC) || remaining.isEmpty()) r = List.of();
		else if (this.index == DatosSubconjuntos.NUM_SC-1) {
			if (DatosSubconjuntos.conjunto(index).containsAll(this.remaining)) r = List.of(1);
			else r = List.of();
		} else {
			Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.conjunto(index));
			if (rest.equals(remaining)) r = List.of(0);
			else r = List.of(1,0);
		}
		return r;
	}

	@Override
	public String toString() {
		return String.format("%d; %d", index, remaining.size());
	}

	@Override
	public Boolean isBaseCase() {
		return ((index >= DatosSubconjuntos.NUM_SC-1) || remaining.isEmpty());
	}

	@Override
	public Double baseCaseWeight() {
		Double res = null;
		if ((index == DatosSubconjuntos.NUM_SC) || remaining.isEmpty()) res = 0.;
		else { // index == DatosSubconjuntos.NUM_SC-1
			if (DatosSubconjuntos.conjunto(index).containsAll(this.remaining)) {
				res = DatosSubconjuntos.peso(index);
			}
		}
		return res;
	}

	@Override
	public SolucionSubconjuntos2 baseCaseSolution() {
		SolucionSubconjuntos2 res = null;
		if ((index == DatosSubconjuntos.NUM_SC) || remaining.isEmpty()) {
			res = SolucionSubconjuntos2.of(List.of());
		}
		else { // index == DatosSubconjuntos.NUM_SC-1
			if (DatosSubconjuntos.conjunto(index).containsAll(this.remaining)) {
				res = SolucionSubconjuntos2.of(Set.of(index));
			}
		}
		return res;
	}

	@Override
	public List<SubconjuntosHyperVertex> neighbors(Integer a) {
		IntegerSet rest = a==0 ? IntegerSet.copy(remaining):
			remaining.difference(DatosSubconjuntos.conjunto(index));
		return List.of(of(index+1, rest));
	}

	@Override
	public Boolean isValid() {
		return index >= 0 && index <= DatosSubconjuntos.NUM_SC;
	}

	@Override
	public SolucionSubconjuntos2 solution(Integer a, List<SolucionSubconjuntos2> solutions) {
		SolucionSubconjuntos2 solVecino = solutions.get(0);
		Double peso = solVecino.peso();
		Set<String> conjuntos = Set2.copy(solVecino.conjuntos());
		if (a == 1) {
			peso += DatosSubconjuntos.peso(index);
			conjuntos.add(DatosSubconjuntos.nombre(index));
		}
//		Set<String> conjs = Set.copyOf(conjuntos);
		return new SolucionSubconjuntos2(peso, conjuntos); 
	}

	@Override
	public SubconjuntosHyperEdge edge(Integer a) {
		List<SubconjuntosHyperVertex> targets = this.neighbors(a);
		return SubconjuntosHyperEdge.of(this,targets,a);
	}

	public static Double valor(SolucionSubconjuntos2 solution) {
		return solution.peso();
	}
}
