package us.lsi.p4.ej_1;

import java.util.Locale;
import java.util.Map;

import us.lsi.common.Multiset;
import us.lsi.graphs.alg.PD;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.graphs.alg.PD.Sp;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class TestPDGeneral {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));
		for (Integer id_fichero = 0; id_fichero < 10; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/p4/multiconjuntos.txt", id_fichero);
			System.out.println("=============");
			System.out.println("\tResultados para el test " + id_fichero + "\n");
			
			DatosMulticonjunto.toConsole();

			// V�rtices clave

			MulticonjuntoHyperVertex p = MulticonjuntoHyperVertex.start();

			// Grafo

			System.out.println("\n\n#### Algoritmo PD ####");

			// Algoritmo PD
			
			System.out.println(p);				
			SimpleVirtualHyperGraph<MulticonjuntoHyperVertex,MulticonjuntoHyperEdge,Integer> graph3 = 
					SimpleVirtualHyperGraph.simpleVirtualHyperGraph(p);
			
			PD<MulticonjuntoHyperVertex, MulticonjuntoHyperEdge, Integer,Multiset<Integer>> a = 
					PD.dynamicProgrammingSearch(graph3,PDType.Min);
			
//			a.withGraph = true;
			a.search();
			
			Map<MulticonjuntoHyperVertex, Sp<Integer, MulticonjuntoHyperEdge>> s = a.getSolutionsTree();
			
			if (s.get(p) == null) {
				System.out.println("No hay solución");
			} else {			
				GraphTree<MulticonjuntoHyperVertex,MulticonjuntoHyperEdge,Integer,Multiset<Integer>> tree = 
						GraphTree.graphTree(p,s);

				System.out.println(tree.solution());

				System.out.println(MulticonjuntoHyperVertexI.valor(tree.solution()));
			}
		}
	}
}

