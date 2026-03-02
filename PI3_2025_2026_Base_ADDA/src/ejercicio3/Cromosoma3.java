package ejercicio3;

import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma3 implements BinaryData<Solucion3> {
	
	public Cromosoma3(String file) {
		Datos3.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
	@Override
	public Integer size() {
		return Datos3.getNumElementos();
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		return null;
	}
	
	@Override
	public Solucion3 solution(List<Integer> value) {
		return Solucion3.create(value);
	}
}
