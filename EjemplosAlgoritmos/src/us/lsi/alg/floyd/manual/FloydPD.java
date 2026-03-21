package us.lsi.alg.floyd.manual;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import org.jgrapht.GraphPath;
import us.lsi.alg.floyd.DatosFloyd;
import us.lsi.alg.floyd.FloydVertex;

import us.lsi.graphs.SimpleEdge;


public class FloydPD {
	
	public FloydVertex startVertex;
	
	public static record Spm(Boolean a, Double weight) implements Comparable<Spm> {
		public static Spm of(Boolean a, Double weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}

	public static FloydPD of(FloydVertex startVertex) {
		return new FloydPD(startVertex);
	}
	
	private FloydPD(FloydVertex startVertex) {
		this.startVertex = startVertex;	
	}
	
	public Map<FloydVertex,Spm> search() {
		Map<FloydVertex,Spm> memory = new HashMap<>();
		search(this.startVertex, memory);
		return memory;
	}

	public Spm search(FloydVertex actual, Map<FloydVertex, Spm> memory) {
		Spm r = null;
		if (memory.containsKey(actual)) {
			r = memory.get(actual);
		} else if (actual.isBaseCase()) {
			Double w = actual.baseCaseWeight();
			if (w != null) r = Spm.of(false,w);
			memory.put(actual, r);
		} else {
			List<Spm> sps = new ArrayList<>();
			for (Boolean a : actual.actions()) {
				List<FloydVertex> neighbors = actual.neighbors(a);
				Integer nbn = neighbors.size();
				List<Double> nbWeights = new ArrayList<>();
				for (FloydVertex v : neighbors) {
					Spm s = search(v, memory);
					if (s == null) break;
					nbWeights.add(s.weight().doubleValue());
				}
				Spm spa = nbWeights.size() == nbn ? Spm.of(a, nbWeights.stream().mapToDouble(x -> x).sum())  : null;
				sps.add(spa);
			}
			r = sps.stream().filter(s -> s != null).min(Comparator.naturalOrder()).orElse(null);
			memory.put(actual, r);
		}
		return r;
	}

	private static GraphPath<Integer, SimpleEdge<Integer>> solution(FloydVertex prob, Map<FloydVertex, Spm> mem) {
		GraphPath<Integer, SimpleEdge<Integer>> path = null;
		if (prob.isBaseCase()) {
			path = prob.baseCaseSolution();
		} else {
			Spm sp = mem.get(prob);
			List<FloydVertex> vecinos = prob.neighbors(sp.a());
			path = solution(vecinos.get(0), mem);
			if (sp.a()) {
				GraphPath<Integer, SimpleEdge<Integer>> path2 = solution(vecinos.get(1), mem);
				path = FloydVertex.concat(path, path2);
			} 
		}
		return path;
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		DatosFloyd.datos();
		
		System.out.println(DatosFloyd.graph);
		System.out.println(DatosFloyd.graphI);
		
		Integer origen = DatosFloyd.graphI.index(v->v.nombre().equals("Sevilla"));
		Integer destino = DatosFloyd.graphI.index(v->v.nombre().equals("Almeria"));
		
		FloydVertex.graph = DatosFloyd.graphI;
		FloydVertex.n = DatosFloyd.graphI.vertexSet().size();
		
		FloydVertex start = FloydVertex.initial(origen,destino);
		
		FloydPD a =FloydPD.of(start);
		
		Map<FloydVertex, Spm> r = a.search();
		
		GraphPath<Integer, SimpleEdge<Integer>> sol = solution(start, r);
		
		System.out.println(sol.getVertexList().stream()
				.map(i->DatosFloyd.graphI.vertex(i))
				.toList());
	}

}
