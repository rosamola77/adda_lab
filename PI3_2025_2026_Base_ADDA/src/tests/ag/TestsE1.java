package tests.ag;

import java.util.List;

import ejercicio1.Cromosoma1;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class TestsE1 {

	public static void main(String[] args) {
		AlgoritmoAG.POPULATION_SIZE = 50;  // aumentar si no se obtiene un optimo
		StoppingConditionFactory.NUM_GENERATIONS = 500; // aumentar si no se obtiene un optimo
		
		List.of(1,2,3).forEach(i -> { // indique los tests a realizar
			var cr = new Cromosoma1("datos_entrada/ejercicio1/DatosEntrada"+i+".txt");
			var ag = AlgoritmoAG.of(cr);
			ag.ejecuta();
			
			System.out.println("================================");
			System.out.println(ag.bestSolution());
			System.out.println("================================");
		});
	}	
}