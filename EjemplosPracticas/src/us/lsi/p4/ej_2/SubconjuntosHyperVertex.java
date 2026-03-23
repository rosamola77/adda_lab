package us.lsi.p4.ej_2;

import us.lsi.common.IntegerSet;
import us.lsi.hypergraphs.VirtualHyperVertex;

public interface SubconjuntosHyperVertex extends 
	VirtualHyperVertex<SubconjuntosHyperVertex, SubconjuntosHyperEdge, Integer, SolucionSubconjuntos2> {
	
	Integer index();
	IntegerSet remaining();
	String toGraph();

	public static SubconjuntosHyperVertex initial() {
		return SubconjuntosHyperVertexI.of(0, IntegerSet.of(DatosSubconjuntos.universo()));
	}
}