package us.lsi.p4.ej_3;

import java.util.List;

import us.lsi.hypergraphs.SimpleHyperEdge;

public record AlumnosHyperEdge(AlumnosHyperVertex source, List<AlumnosHyperVertex> targets, Integer action) 
implements SimpleHyperEdge<AlumnosHyperVertex,AlumnosHyperEdge,Integer> {

	public static AlumnosHyperEdge of(AlumnosHyperVertex source, List<AlumnosHyperVertex> targets, Integer action) {	
		return new AlumnosHyperEdge(source, targets, action);
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action);
	}

	@Override
	public Double weight(List<Double> targetsWeight) {
		return targetsWeight.get(0) + DatosAlumnos.getAfinidad(source.index(), action);
	}

}