package ejercicio4;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.List2;

public class Solucion4 {

    public static Solucion4 create(List<Integer> ls) {
        return new Solucion4(ls);
    }

    private String camino, txt;
    private Double totalTime, totalEffort;
    private Double avgTime, avgEffort, monCons;

    private Solucion4(List<Integer> ls) {
    	txt = "";
         List<Integer> aux = List2.addLast(ls, ls.getFirst());
        
    	camino = aux.stream().map(i -> Datos4.getVertex(i)+"")
        	.collect(Collectors.joining(" ->\n\t", "Recorrido:\n\t", ""));
        	
        totalTime = null; // TODO tiempo total empleado
        avgTime = totalTime / Datos4.N;
        
        totalEffort = null; // TODO esfuerzo total empleado
        avgEffort = totalEffort / Datos4.N;
        
        monCons = null; // TODO Nº de lugares con monumento consecutivos a otro con monumento
     }

	@Override
    public String toString() {
    	String s1 = String.format("\nTiempos (total/medio/maximo): %.1f / %.1f / %.1f", totalTime, avgTime, Datos4.maxTime);
    	String s2 = String.format("\nEsfuerzos (total/medio): %.1f / %.1f", totalEffort, avgEffort);
    	String s3 = String.format("\nNº de lugares con monumento consecutivos a otro con monumento: %d", monCons.intValue());
        return txt+camino+s1+s2+s3;
    }

}
