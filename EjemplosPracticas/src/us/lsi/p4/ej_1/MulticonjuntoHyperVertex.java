package us.lsi.p4.ej_1;

import us.lsi.common.Multiset;
import us.lsi.hypergraphs.VirtualHyperVertex;

public interface MulticonjuntoHyperVertex extends
	VirtualHyperVertex<MulticonjuntoHyperVertex, MulticonjuntoHyperEdge, Integer, Multiset<Integer>> {

	Integer indice();
	Integer sumaRestante();

	public static MulticonjuntoHyperVertex start() {
		return  new MulticonjuntoHyperVertexI(0, DatosMulticonjunto.SUM);
	}
}