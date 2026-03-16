package ejercicio2;

import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma2 implements RangeIntegerData<Solucion2> {
	
	public Cromosoma2(String file) {
		Datos2.iniDatos(file);
	}

	@Override
	public Integer max(Integer i) {					 // MAX Y MIN INDICAN EL INTERVALO
		return Datos2.getUnidsSemanaProd(i)+1;				// EL +1 ES PQ EL INTERVALO EN LA LIBRERIA ES ABIERTO Y QUEREMOS QUE SEA CERRADO PARA QUE SE COJAN TODOS LOS VALORES
	}		// Ademas, esto ya me cubre R1: para cada producto no puede superarse el numero maximo de unidades que pueden venderse semanalmente

	@Override
	public Integer min(Integer i) {
		return 0;
	}

	@Override
	public Integer size() {
		return Datos2.getNumProductos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Double goal = ingresos(value);
		Double totalTiempoProd = totalTiempoProd(value);
		Double totalTiempoElab = totalTiempoElab(value);

		return goal - (1000*totalTiempoProd) - (1000*totalTiempoElab);
	}

	private Double totalTiempoElab(List<Integer> value) {
		Double tiempoElaboracion = 0.0;
		for (Integer i = 0; i < size(); i++) {
			tiempoElaboracion += (value.get(i) * Datos2.getTiempoElabProd(i));
		}
		if (tiempoElaboracion <= Datos2.getTiempoElabTotal()) {				// Como es una funcion de penalizaciÃ³n si se cumple la conmdicion, no devolvemos nada, es decir, 0. SOLO NOS INTERESA CUANOD NO CUMPLE LA CONDICION
			return 0.0;
		}
		return tiempoElaboracion - Datos2.getTiempoElabTotal();		// TIEMPO TOTAL QUE NOS EXCEDEMOS
	}

	private Double totalTiempoProd(List<Integer> value) {
		Double tiempoProduccion = 0.0;
		for (Integer i = 0; i < size(); i++) {
			tiempoProduccion += (value.get(i) * Datos2.getTiempoProdProd(i));
		}
		if (tiempoProduccion <= Datos2.getTiempoProdTotal()) {
			return 0.0;
		}
		return tiempoProduccion - Datos2.getTiempoProdTotal();		// TIEMPO TOTAL QUE NOS EXCEDEMOS
	}

	private Double ingresos(List<Integer> value) {
		Double ingresos = 0.0;
		for (Integer i = 0; i < size(); i++) {
			ingresos += (value.get(i) * Datos2.getPrecioProd(i));
		}
		return ingresos;
	}

	@Override
	public Solucion2 solution(List<Integer> value) {
		return Solucion2.create(value);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}

}
