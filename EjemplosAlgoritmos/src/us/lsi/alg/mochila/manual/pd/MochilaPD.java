package us.lsi.alg.mochila.manual.pd;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import us.lsi.alg.mochila.pd.MochilaVertexPD;
import us.lsi.common.Map2;
import us.lsi.common.Multiset;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;


public class MochilaPD {
	
	public static record Spm(Integer a, Double weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Double weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}

	public MochilaVertexPD startVertex;

	public static Map<MochilaVertexPD, Spm> memory;

	public static void search() {
		memory =  Map2.empty();
		pd_search(MochilaVertexPD.of());
	}

	private static Spm pd_search(MochilaVertexPD prob) {
		Spm res = null;
		if (memory.containsKey(prob)) {
			res = memory.get(prob);
		} else if (prob.isBaseCase()) {
			Double w = prob.baseCaseWeight();
			res = Spm.of(null,w);
			memory.put(prob, res);
		} else {
			List<Spm> sps = new ArrayList<>();
			for (Integer action : prob.actions()) {
				MochilaVertexPD neighbor = prob.neighbors(action).get(0);
				Spm spNeighbor = pd_search(neighbor);
				Spm amp = Spm.of(action, spNeighbor.weight() + action * DatosMochila.getValor(prob.index()));
				sps.add(amp);
			}
			res = sps.stream().max(Comparator.naturalOrder()).orElse(null);
			memory.put(prob, res);
		}
		return res;
	}

	public static Multiset<ObjetoMochila> getSolucion() {
		Multiset<ObjetoMochila> sol = Multiset.empty();
		MochilaVertexPD prob = MochilaVertexPD.of();
		while (!prob.isBaseCase()) {
			Spm spm = memory.get(prob);
			sol.add(DatosMochila.getObjeto(prob.index()), spm.a);
			prob = prob.neighbors(spm.a).get(0);
		}
		return sol.add(prob.baseCaseSolution());
	}
		
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.iniDatos("ficheros/mochila/objetosMochila.txt");
		MochilaVertexPD.capacidadInicial = 78;

		search();
		System.out.println(getSolucion());
		System.out.println("valor: " + memory.get(MochilaVertexPD.of()).weight);
		
	}

}
