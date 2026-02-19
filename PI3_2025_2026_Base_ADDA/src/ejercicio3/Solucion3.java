package ejercicio3;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.common.Map2;

public class Solucion3 {
	
	public static Solucion3 create(List<Integer> ls) {
		return new Solucion3(ls);
	}

	private Map<String, List<String>> solucion;

	private Solucion3(List<Integer> ls) {
		solucion = Map2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)<Datos3.getNumContenedores()) {
				String cont = Datos3.getContenedor(ls.get(i)).nombre();
				solucion.computeIfAbsent(cont, k->List2.empty())
				.add(Datos3.getElemento(i).nombre());
			}
		}
	}
	
	@Override
	public String toString() {
		return solucion.entrySet().stream().map(e->e.getKey()+": "+e.getValue())
		.collect(Collectors.joining("\n", "Reparto obtenido:\n", "\n"));
	}

}