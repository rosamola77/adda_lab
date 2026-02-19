package ejercicio1;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ejercicio1.Datos1.Candidato;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public class Solucion1 {
	
	public static Solucion1 create(List<Integer> ls) {
		return new Solucion1(ls);
	}

	private Double valTotal, gasto;
	private List<Integer> seleccion;
	private Set<String> cualidades;
	private List<String> incompatibles;

	private Solucion1(List<Integer> ls) {
		valTotal = gasto = 0.;
		seleccion = List2.empty();
		cualidades = Set2.empty();
		incompatibles = List2.empty();
		
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				Candidato c = Datos1.getCandidato(i);
				seleccion.add(i);
				gasto += c.sueldo();
				valTotal += c.valoracion();
				cualidades.addAll(c.cualidades());
				final int id = i;
				seleccion.stream()
				.filter(elem->elem!=id && c.incompatibilidades().contains(elem))
				.forEach(elem -> incompatibles.add(
						String.format("(%s,%s)", c.nombre(), Datos1.getNombre(elem))));
			}
		}

	}

	@Override
	public String toString() {
		String s = String.format("\nValoracion total: %.1f; Gasto: %.1f", valTotal, gasto);
		return seleccion.stream().map(e->Datos1.getCandidato(e).toString())
		.collect(Collectors.joining("\n", "Candidatos Seleccionados:\n", s));
	}

}
