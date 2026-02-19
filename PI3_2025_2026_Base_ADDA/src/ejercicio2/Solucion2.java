package ejercicio2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ejercicio2.Datos2.Producto;
import us.lsi.common.Map2;

public class Solucion2 {
	
	public static Solucion2 create(List<Integer> ls) {
		return new Solucion2(ls);
	}

	private Double beneficio;
	private Map<Producto, Integer> solucion;

	private Solucion2(List<Integer> cr) {
		beneficio = 0.;
		solucion = Map2.empty();
		int n = cr.size();
		for(int i=0; i<n; i++) {
			if(cr.get(i)>0) {
				beneficio += Datos2.getPrecioProd(i)*cr.get(i);
				solucion.put(Datos2.getProducto(i), cr.get(i));
			}
		}
	}
	
	@Override
	public String toString() {
		return solucion.entrySet().stream()
		.map(p -> p.getKey().nombre()+": "+p.getValue()+" unidades")
		.collect(Collectors.joining("\n", "Productos Seleccionados:\n", String.format("\nBeneficio: %.1f", beneficio)));
	}
	
}