package us.lsi.p4.ej_1.manual;

import java.util.List;
import us.lsi.alg.multiconjuntos.DatosMulticonjunto;
import us.lsi.common.List2;
import us.lsi.common.Multiset;
import us.lsi.p4.ej_1.MulticonjuntoHyperVertexI;

public record MulticonjuntoProblemPD(Integer index, Integer remaining) {

	public static MulticonjuntoProblemPD initial() {
		return of(0, DatosMulticonjunto.SUM);
	}

	public static MulticonjuntoProblemPD of(Integer i, Integer rest) {
		return new MulticonjuntoProblemPD(i, rest);
	}
	
	public Boolean isBaseCase() {
		return (this.remaining == 0 || this.index >= DatosMulticonjunto.NUM_E-1);
	}
	
	public Integer baseCaseWeight() {
		Integer r = null;
		if (this.remaining == 0) r = 0;
		else if (this.index == DatosMulticonjunto.NUM_E-1) {
			if (this.remaining % DatosMulticonjunto.getElemento(this.index) == 0)
				r = this.remaining / DatosMulticonjunto.getElemento(this.index);
		}
		return r;
	}

	public Multiset<Integer> baseCaseSolution() {
		Multiset<Integer> r = null;
		if (this.remaining == 0) r = Multiset.empty();
		else if (this.index == DatosMulticonjunto.NUM_E-1) {
			Integer elem = DatosMulticonjunto.getElemento(this.index);
			if (this.remaining % elem == 0) {
				r = Multiset.empty();
				Integer nu = this.remaining / elem;
				r.add(elem, nu);
			}
		}
		return r;
	}

	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if(index < DatosMulticonjunto.NUM_E) {
			Integer value = DatosMulticonjunto.getElemento(index);
			Integer options = remaining / value;
			if(index == DatosMulticonjunto.NUM_E-1) {
				if(remaining % value == 0)
					alternativas = List2.of(remaining / value);
				else
					alternativas = List2.of(0);
			} else {
				alternativas = List2.rangeList(0, options + 1);
			}
		}
		return alternativas;
	}

	public MulticonjuntoProblemPD neighbor(Integer a) {
		return of(index + 1, remaining - a*DatosMulticonjunto.getElemento(index));
	}

}
