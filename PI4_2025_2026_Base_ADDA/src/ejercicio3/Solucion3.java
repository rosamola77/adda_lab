package ejercicio3;

import java.util.List;

public class Solucion3 {
	
	/* public static Solucion3 create(GraphPath<---, ---> gp) { Para A* y BT
		TODO obtiene la lista de alternativas del camino y llama al otro factoria
	}*/

    public static Solucion3 create(List<Integer> ls) {
        return new Solucion3(ls);
    }

    private String camino;
    private Double totalTime, totalEffort, totalMns;

    private Solucion3(List<Integer> ls) {  // Lista de acciones/alternativas
    	
    	/* TODO Las propiedades de la clase son:    	
    		camino = secuencia de vertices visitados
    		totalTime = tiempo total empelado
    		totalEffort = esfuerzo total empleado
    		totalMns = monumentos visitados consecutivamente (antes o despues de otro) */
     }

	@Override
    public String toString() {
    	String s1 = String.format("\nTiempos (total/maximo): %.1f / %.1f", totalTime, Datos3.maxTime);
    	String s2 = String.format("\nEsfuerzo total: %.1f", totalEffort);
    	String s3 = String.format("\nNº de monumentos visitados antes o despues de otro: %d", totalMns.intValue());
    	return camino+s1+s2+s3;
    }

}