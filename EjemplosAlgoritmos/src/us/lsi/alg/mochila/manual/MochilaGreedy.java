package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;

public class MochilaGreedy {
	
	public static GraphPath<MochilaVertex,MochilaEdge> greedy(MochilaVertex start,  Graph<MochilaVertex,MochilaEdge> graph) {
		List<MochilaVertex> vertices = new ArrayList<>();
		Double weight = 0.;
		MochilaVertex v = start;		
		while(!v.goal()) {
			Integer a = v.greedyAction();
			MochilaEdge e = v.edge(a);
			v = v.neighbor(a);
			vertices.add(v);
			weight += e.weight();
		} 
		return new GraphWalk<MochilaVertex,MochilaEdge>(graph,vertices,weight);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.iniDatos("ficheros/mochila/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		List<ObjetoMochila> objetos = DatosMochila.getObjetos();
//		Collections.shuffle(objetos);
//		Collections.sort(objetos,Comparator.naturalOrder());
		Collections.sort(objetos,Comparator.reverseOrder());
		MochilaVertex v1 = MochilaVertex.initialVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = 
				EGraph.virtual(v1)
				.build();
		
		GraphPath<MochilaVertex, MochilaEdge> mg = MochilaGreedy.greedy(v1, graph);
		System.out.println("Peso de camino = "+mg.getWeight());    
	}
}
