package us.lsi.alg.matrices.manual;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import us.lsi.alg.matrices.DatosMatrices;
import us.lsi.alg.matrices.MatrixVertex;


public class MatricesPD {
	
	public static MatricesPD of(MatrixVertex startVertex) {
		return new MatricesPD(startVertex);
	}
	
	public MatrixVertex startVertex;

	public static record Spm(Integer a, Double weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Double weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}

	private MatricesPD(MatrixVertex startVertex) {
		this.startVertex = startVertex;	
	}
	
	public Map<MatrixVertex, Spm> search(){
		Map<MatrixVertex,Spm> memory = new HashMap<>();
		search(this.startVertex,memory);
		return memory;
	}

	public Spm search(MatrixVertex actual, Map<MatrixVertex, Spm> memory) {
		Spm r = null;
		if (memory.containsKey(actual)) {
			r = memory.get(actual);
		} else if (actual.isBaseCase()) {
			Double w = actual.baseCaseWeight();
			if (w != null) r = Spm.of(null,w);
			memory.put(actual, r);
		} else {
			List<Spm> sps = new ArrayList<>();
			for (Integer a : actual.actions()) {
				List<MatrixVertex> neighbors = actual.neighbors(a);
				Double sumWeights = 0.;
				for (MatrixVertex v : neighbors) {
					Spm s = search(v, memory);
					sumWeights += s.weight();
				}
				Spm spa = Spm.of(a, sumWeights + DatosMatrices.nf(actual.i())*DatosMatrices.nf(a)*DatosMatrices.nc(actual.j()-1));
				sps.add(spa);
			}
			r = sps.stream().min(Comparator.naturalOrder()).orElse(null);
			memory.put(actual, r);
		}
		return r;
	}
	
	private static String solution(MatrixVertex prob, Map<MatrixVertex, Spm> mem) {
		String sol;
		if (prob.isBaseCase()) {
			sol = prob.baseCaseSolution();
		} else {
			Spm sp = mem.get(prob);
			List<MatrixVertex> vecinos = prob.neighbors(sp.a());
			String sol1 = solution(vecinos.get(0), mem);
			String sol2 = solution(vecinos.get(1), mem);
			sol = prob.solution(sp.a(), List.of(sol1, sol2));
		}
		return sol;
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		DatosMatrices.leeFichero("ficheros/matrices/matrices.txt");
		
		MatrixVertex start = MatrixVertex.of(0,DatosMatrices.n);
		
		MatricesPD a = MatricesPD.of(start);
		
		Map<MatrixVertex, Spm> r = a.search();
		
		String sol = solution(start, r);
					
		System.out.println(sol);
	}

}
