package us.lsi.p4.ej_3;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.PD;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.alg.PD.PDType;
import us.lsi.graphs.alg.PD.Sp;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class TestPDGeneral {

	public static void main(String[] args) {
		DatosAlumnos.iniDatos("ficheros/p4/alumnos_1.txt");

		AlumnosHyperVertex vInicial = AlumnosHyperVertex.initial();
		
		
		System.out.println("\n\n#### Algoritmo PD ####");

		System.out.println(vInicial);				
		SimpleVirtualHyperGraph<AlumnosHyperVertex,AlumnosHyperEdge,Integer> graph3 = 
				SimpleVirtualHyperGraph.simpleVirtualHyperGraph(vInicial);
		
		PD<AlumnosHyperVertex, AlumnosHyperEdge, Integer, List<Integer>> a = 
				PD.dynamicProgrammingSearch(graph3,PDType.Max);
		
//		a.withGraph = true;
		a.search();
		
		Map<AlumnosHyperVertex, Sp<Integer, AlumnosHyperEdge>> s = a.getSolutionsTree();
		
		if (s.get(vInicial) == null) {
			System.out.println("No hay solución");
		} else {			
			GraphTree<AlumnosHyperVertex,AlumnosHyperEdge,Integer,List<Integer>> tree = 
					GraphTree.graphTree(vInicial,s);

			System.out.println(tree.solution());

			System.out.println(AlumnosHyperVertex.valor(tree.solution()));
		}

		
	}



}
