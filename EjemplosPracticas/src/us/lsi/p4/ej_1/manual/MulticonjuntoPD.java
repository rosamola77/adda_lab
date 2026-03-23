package us.lsi.p4.ej_1.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


import us.lsi.alg.multiconjuntos.DatosMulticonjunto;
import us.lsi.alg.multiconjuntos.SolucionMulticonjunto;
import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.Multiset;


public class MulticonjuntoPD {

	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}

	public static Map<MulticonjuntoProblemPD, Spm> memory;

	public static Multiset<Integer> search() {
		memory =  Map2.empty();
		pd_search(MulticonjuntoProblemPD.initial());
		return getSolucion3();
	}

	private static Spm pd_search(MulticonjuntoProblemPD prob) {
		Spm res = null;
		if (memory.containsKey(prob)) {
			res = memory.get(prob);
		} else if (prob.isBaseCase()) {
			Integer w = prob.baseCaseWeight();
			if (w!=null) res = Spm.of(null,w);
			else res = null;
			memory.put(prob, res);
		} else {
			List<Spm> sps = new ArrayList<>();
			for (Integer action : prob.actions()) {
				MulticonjuntoProblemPD neighbor = prob.neighbor(action);
				Spm spNeighbor = pd_search(neighbor);
				if (spNeighbor != null) {
					Spm amp = Spm.of(action, spNeighbor.weight() + action);
					sps.add(amp);
				}
			}
			res = sps.stream().min(Comparator.naturalOrder()).orElse(null);
			memory.put(prob, res);
		}
		return res;
	}

	public static Multiset<Integer> getSolucion3() {
		Multiset<Integer> sol = null;
		MulticonjuntoProblemPD prob = MulticonjuntoProblemPD.initial();
		if (memory.get(prob) == null) return null;
		sol = Multiset.empty();
		while (!prob.isBaseCase()) {
			Spm spm = memory.get(prob);
			sol.add(DatosMulticonjunto.getElemento(prob.index()), spm.a);
			prob = prob.neighbor(spm.a);
		}
		return sol.add(prob.baseCaseSolution());
	}

	public static SolucionMulticonjunto getSolucion() {
		List<Integer> acciones = List2.empty();
		MulticonjuntoProblemPD prob = MulticonjuntoProblemPD.initial();
		Spm spm = memory.get(prob);
		while (spm != null && spm.a != null) {
			MulticonjuntoProblemPD old = prob;
			acciones.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = memory.get(prob);
		}
		return SolucionMulticonjunto.of(acciones);
	}

}
