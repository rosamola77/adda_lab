package us.lsi.p4.ej_2;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Multiset;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.graphs.alg.PD.Sp;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;
import us.lsi.p4.ej_1.MulticonjuntoHyperEdge;
import us.lsi.p4.ej_1.MulticonjuntoHyperVertex;
import us.lsi.p4.ej_1.MulticonjuntoHyperVertexI;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.PD;


public class TestPDGeneral {

		public static void main(String[] args) {

			// Set up
			Locale.setDefault(Locale.of("en", "US"));

			for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {

				DatosSubconjuntos.iniDatos("ficheros/p4/subconjuntos" + id_fichero + ".txt");
				System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//				DatosSubconjuntos.toConsole();
				// V�rtices clave

				SubconjuntosHyperVertex start = SubconjuntosHyperVertex.initial();

				// Grafo
				
				System.out.println("\n\n#### Algoritmo PD ####");

				System.out.println(start);				
				SimpleVirtualHyperGraph<SubconjuntosHyperVertex,SubconjuntosHyperEdge,Integer> graph3 = 
						SimpleVirtualHyperGraph.simpleVirtualHyperGraph(start);
				
				PD<SubconjuntosHyperVertex, SubconjuntosHyperEdge, Integer, SolucionSubconjuntos2> a = 
						PD.dynamicProgrammingSearch(graph3,PDType.Min);
				
//				a.withGraph = true;
				a.search();
				
				Map<SubconjuntosHyperVertex, Sp<Integer, SubconjuntosHyperEdge>> s = a.getSolutionsTree();
				
				if (s.get(start) == null) {
					System.out.println("No hay solución");
				} else {			
					GraphTree<SubconjuntosHyperVertex,SubconjuntosHyperEdge,Integer,SolucionSubconjuntos2> tree = 
							GraphTree.graphTree(start,s);

					System.out.println(tree.solution());

					System.out.println(SubconjuntosHyperVertexI.valor(tree.solution()));
				}

				
			}
				
		}

	}



