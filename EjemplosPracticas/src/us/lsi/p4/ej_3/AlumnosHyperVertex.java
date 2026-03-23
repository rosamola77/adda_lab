package us.lsi.p4.ej_3;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.hypergraphs.VirtualHyperVertex;

public record AlumnosHyperVertex(Integer index, List<Integer> remaining) 
  implements VirtualHyperVertex<AlumnosHyperVertex,AlumnosHyperEdge,Integer,List<Integer>> {

	public static AlumnosHyperVertex initial() {
		return of(0, List2.nCopies(DatosAlumnos.getTamGrupo(), DatosAlumnos.getNumGrupos()));
	}

	public static AlumnosHyperVertex of(Integer i, List<Integer> rest) {
		return new AlumnosHyperVertex(i, rest);
	}
	
	public List<Integer> remaining(){
		return List.copyOf(this.remaining);
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if(index < DatosAlumnos.getNumAlumnos()) {
			alternativas = IntStream.range(0, DatosAlumnos.getNumGrupos())
				.filter(j -> DatosAlumnos.getAfinidad(index, j)>0 && remaining.get(j)>0)
				.boxed().toList();
		}
		return alternativas;
	}

	@Override
	public String toString() {
		return String.format("%d", index);
	}
	
	public String toGraph() {
		return String.format("%d", index);
	}

	@Override
	public Boolean isBaseCase() {
		return this.index() == DatosAlumnos.getNumAlumnos();	
	}

	@Override
	public Double baseCaseWeight() {
		return 0.;	
	}

	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public List<Integer> baseCaseSolution() {
		return List.of();
	}

	@Override
	public List<AlumnosHyperVertex> neighbors(Integer a) {
		return List.of(of(index+1, List2.set(remaining, a, remaining.get(a)-1)));
	}

	@Override
	public AlumnosHyperEdge edge(Integer a) {
		List<AlumnosHyperVertex> targets = this.neighbors(a);
		return AlumnosHyperEdge.of(this,targets,a);
	}

	@Override
	public List<Integer> solution(Integer a, List<List<Integer>> solutions) {
		List<Integer> res = List2.copy(solutions.get(0));
		res.add(0, a);
		return res;
	}

	public static Integer valor(List<Integer> solution) {
		Integer af_tot = 0;
		for(int i=0; i<solution.size(); i++) {
			Integer j = solution.get(i);
			af_tot += DatosAlumnos.getAfinidad(i, j);
		}
		return af_tot;
	}

}
