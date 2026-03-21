package us.lsi.p4.ej_2;

import java.util.List;

import us.lsi.hypergraphs.SimpleHyperEdge;

public record SubconjuntosHyperEdge(SubconjuntosHyperVertex source, List<SubconjuntosHyperVertex> targets, Integer action) 
			implements SimpleHyperEdge<SubconjuntosHyperVertex,SubconjuntosHyperEdge,Integer> {

	public static SubconjuntosHyperEdge of(SubconjuntosHyperVertex source, List<SubconjuntosHyperVertex> targets, Integer action) {
		return new SubconjuntosHyperEdge(source, targets, action);
	}

	@Override
	public Double weight(List<Double> targetsWeight) {
		return targetsWeight.get(0) + DatosSubconjuntos.peso(source.index())*action;
	}
}
