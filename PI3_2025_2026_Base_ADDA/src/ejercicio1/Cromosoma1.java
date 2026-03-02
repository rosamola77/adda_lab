package ejercicio1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ejercicio1.Datos1.Candidato;
import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements BinaryData<Solucion1> {
	
	public Cromosoma1(String file) {
		Datos1.iniDatos(file, false);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
	@Override
	public Integer size() {
		return Datos1.getNumCandidatos();
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {

		double valoracionTotal = 0.0;
		double gastoTotal = 0.0;
		
		Set<String> cualidadesCubiertas = new HashSet<>();
		int incompatibilidadesEncontradas = 0;
		
		for (int i = 0; i < cr.size(); i++) {
			if (cr.get(i) == 1) { // si el candidato i es seleccionado
				Candidato c = Datos1.getCandidato(i);
				valoracionTotal += c.valoracion();
				gastoTotal += c.sueldo();
				
				// añadir sus cualidades
				cualidadesCubiertas.addAll(c.cualidades());
				
				// revisar si hemos elegido a alguien con quien es incompatible
				for (Integer inc : c.incompatibilidades()) {
					if (cr.get(inc) == 1) {
						incompatibilidadesEncontradas++;
					}
				}
			}
		}
		
		// ya que las incompatibilidades se cuentan dos veces (i -> j y j -> i), dividimos por dos para quedarnos con las incompatibilidades reales
		incompatibilidadesEncontradas /= 2;
		
		double fitness = valoracionTotal;
		double K = 100000.0; // Constante de penalización K (valor muy alto para penalizar mucho las restricciones que no sean cumplidas)
		
		// 1. Penalización por pasarse de presupuesto
		if (gastoTotal > Datos1.getPresupuestoMax()) {
			fitness -= K * (gastoTotal - Datos1.getPresupuestoMax());
		}
		
		// 2. Penalización por cualidades no cubiertas
		int cualidadesFaltantes = Datos1.getNumCualidades() - cualidadesCubiertas.size();

		if (cualidadesFaltantes > 0) {
			fitness -= K * cualidadesFaltantes;
		}
		
		// Penalización por incompatibilidades
		if (incompatibilidadesEncontradas > 0) {
			fitness -= K * incompatibilidadesEncontradas;
		}
		
		return fitness;
	}

	@Override
	public Solucion1 solution(List<Integer> value) {
		return Solucion1.create(value);
	}
	

}