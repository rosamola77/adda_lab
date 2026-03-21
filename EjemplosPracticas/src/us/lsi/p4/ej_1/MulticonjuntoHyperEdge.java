package us.lsi.p4.ej_1;

import java.util.List;

import us.lsi.hypergraphs.SimpleHyperEdge;

public record MulticonjuntoHyperEdge(MulticonjuntoHyperVertex source, List<MulticonjuntoHyperVertex> targets, Integer action) 
           implements SimpleHyperEdge<MulticonjuntoHyperVertex, MulticonjuntoHyperEdge, Integer> {

	public static MulticonjuntoHyperEdge of(MulticonjuntoHyperVertex source, List<MulticonjuntoHyperVertex> targets, Integer action) {
		MulticonjuntoHyperEdge a = new MulticonjuntoHyperEdge(source, targets, action);
		return a;
	}

	@Override
	public Double weight(List<Double> targetsWeight) {
		return targetsWeight.get(0) + this.action;
	}

}
