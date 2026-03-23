package ejercicio1;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ejercicio1.Datos1.Candidato;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public class Solucion1 {
	
	/* public static Solucion1 create(GraphPath<---, ---> gp) { Para A* y BT
		TODO obtiene la lista de alternativas del camino y llama al otro factoria
	}*/
	
	public static Solucion1 create(List<Integer> ls) {
		return new Solucion1(ls);
	}

	private Double valTotal, gasto;
	
	private List<Integer> seleccion;
	private Set<String> cualidades;
	private List<String> incompatibilidades;

	private Solucion1(List<Integer> ls) { // Lista de acciones/alternativas
		valTotal = gasto = 0.;
		seleccion = List2.empty();
		cualidades = Set2.empty();
		incompatibilidades = List2.empty();
		
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				Candidato cAct = Datos1.getCandidato(i);
				gasto += cAct.sueldo();
				valTotal += cAct.valoracion();
				cualidades.addAll(cAct.cualidades());
				seleccion.stream().filter(elem->cAct.incompatibilidades().contains(elem))
				.forEach(elem -> incompatibilidades.add(
						String.format("(%s,%s)", Datos1.getNombre(elem), cAct.nombre())));
				
				seleccion.add(i);
			}
		}
	}

	@Override
	public String toString() {
		String s = String.format("\nValoracion total: %.1f; Gasto: %.1f", valTotal, gasto);
		return seleccion.stream().map(e->Datos1.getCandidato(e).toString())
		.collect(Collectors.joining("\n", check()+"Candidatos Seleccionados:\n", s));
	}
	
	private String joining(Collection<String> s) {
		return s.stream().collect(Collectors.joining("; "));
	}
	
	private String check() { // Comprueba que la solucion es valida
		String txt;
		Double r1 = gasto-Datos1.getPresupuestoMax();
		Set<String> r2 = Set2.difference(Datos1.getCualidades(),cualidades);
		if(r1<=0 && r2.isEmpty() && incompatibilidades.isEmpty())
			txt = "La solucion es valida y cumple todas las restricciones.\n";
		else {
			txt = "La solucion no es valida:\n";
			if(r1>0)
				txt += String.format("\tLa seleccion supera el presupuesto en %.1f\n", r1);
			if(!r2.isEmpty())
				txt += String.format("\tLa seleccion no cubre las cualidades %s \n", joining(r2));
			if(!incompatibilidades.isEmpty())
				txt += "\tLa seleccion incluye las siguientes incompatibilidades: "+
				joining(incompatibilidades)+"\n";
		}
		return txt;
	}

}