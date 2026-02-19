package ejercicio1;

import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements BinaryData<Solucion1> {
	public Cromosoma1(String file) {
		//TODO 
	}

	@Override
	public ChromosomeType type() {
		// TODO Cromosoma de tipo binario
		return null;
	}
	
	@Override
	public Integer size() {
		// TODO Tantos genes como candidatos
		return null;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Implementar segun el modelo
		return null;
	}

	@Override
	public Solucion1 solution(List<Integer> value) {
		return Solucion1.create(value);
	}

}