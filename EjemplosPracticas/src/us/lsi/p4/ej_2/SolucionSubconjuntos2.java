package us.lsi.p4.ej_2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;

public record SolucionSubconjuntos2(Double peso, Set<String> conjuntos) 
           implements Comparable<SolucionSubconjuntos2>{
	
	public static SolucionSubconjuntos2 of(GraphPath<SubconjuntosVertex, SubconjuntosEdge> path) {
		List<Integer> la = path.getEdgeList().stream().map(e->e.action()).toList();
		return SolucionSubconjuntos2.of(la);
	}

	public static SolucionSubconjuntos2 of(List<Integer> ls) {
		Set<String> cs = new HashSet<>();
		Double ps = 0.;
		Set<Integer> s = new HashSet<>();
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) == 1) {
				cs.add(DatosSubconjuntos.nombre(i));
				ps += DatosSubconjuntos.peso(i);
				s.addAll(DatosSubconjuntos.conjunto(i));
			}
		}
		return new SolucionSubconjuntos2(ps,cs);
	}

	public static SolucionSubconjuntos2 of(Set<Integer> indices) {
		Set<String> cs = new HashSet<>();
		Double ps = 0.;
		Set<Integer> s = new HashSet<>();
		for (Integer i : indices) {
			cs.add(DatosSubconjuntos.nombre(i));
			ps += DatosSubconjuntos.peso(i);
			s.addAll(DatosSubconjuntos.conjunto(i));
		}
		return new SolucionSubconjuntos2(ps,cs);		
	}

	@Override
	public int compareTo(SolucionSubconjuntos2 other) {
		return this.peso().compareTo(other.peso());
	}
}
