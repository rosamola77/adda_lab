package ejercicio3;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma3 implements RangeIntegerData<Solucion3> {
	
	final double K = 99999.;
	
	public Cromosoma3(String file) {
		Datos3.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}
	
	@Override
	public Integer size() {
		return Datos3.getNumElementos();
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double incompatibilidades = incompatibilidades(cr);
		double restriccion1 = AuxiliaryAg.distanceToEqZero(incompatibilidades);
		
		return getContenedoresCompletos(cr) - K * (restriccion1);
	}
	
	@Override
	public Solucion3 solution(List<Integer> value) {
		return Solucion3.create(value);
	}

	@Override
	public Integer max(Integer i) {
		return Datos3.getNumContenedores() + 1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	private Double getContenedoresCompletos(List<Integer> cr) {
		Double res = 0.;
		Integer tamañoElementosEnJ = 0;
		for (int j = 0; j < Datos3.getNumContenedores(); j++) {
			tamañoElementosEnJ += calculaTamaños(cr, j);
			if (tamañoElementosEnJ == Datos3.getTamContenedor(j)) {
				res++;
			}
		}
		return res;
	}
	
	private Integer calculaTamaños(List<Integer> cr, int j) {
		Integer res = 0;
		for (int i = 0; i < cr.size(); i++) {
			if (Datos3.getPuedeUbicarse(i, cr.get(i))) {
				res += Datos3.getTamElemento(i);
			}
		}
		return res;
	}
	
	private Double incompatibilidades(List<Integer> cr) {
		Double res = 0.;
		for (int i = 0; i < cr.size(); i++) { 
			if (Datos3.getNoPuedeUbicarse(i, cr.get(i))) {
				res++;
			}
		}
		return res;
	}
	
}


