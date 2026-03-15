package ejercicio1;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.common.String2;

public class Datos1 {

	public record Candidato(String nombre, List<String> cualidades, Double sueldo, Integer valoracion, List<Integer> incompatibilidades) {
		
		public static Candidato create(String s) {
			String[] v0 = s.split(":");
			String[] v1 = v0[1].split(";");
			List<String> a = List2.parse(v1[0], ", ", x->x);
			Double b = Double.parseDouble(v1[1].trim());
			Integer c = Integer.parseInt(v1[2].trim());
			Function<String, Integer> f = n -> Integer.valueOf(n.substring(1))-1;
			List<Integer> d = List2.parse(v1[3], ", ", f);
			return new Candidato(v0[0].trim(), a, b, c, d);
		}	
		
		@Override
		public String toString() {		
			return String.format("%s: %s; S=%.1f; V=%d; %s", nombre, qlts(), sueldo, valoracion, incmptbls());
		}
		private String qlts() {
			return "{Q}= "+cualidades.stream().collect(Collectors.joining(" + ", "{", "}"));
		}
		private String incmptbls() {
			Function<Integer,String> f = e -> e<10? "C0"+e: "C"+e;
			return "{I}= "+incompatibilidades.stream()
			.map(i->f.apply(i+1)).collect(Collectors.joining(" & ", "{", "}"));
		}

	}	
	
	private static Integer presupuestoMax;
	private static List<String> cualidades;
	private static List<Candidato> candidatos;
	
	public static void iniDatos(String file) {
		iniDatos(file, false);
	}
	public static void iniDatos(String file, boolean show) {
		List<String> ls = Files2.linesFromFile(file);
		String cad = ls.remove(0).split(": ")[1];
		cualidades = List2.parse(cad, ", ", x->x);
		
		presupuestoMax = Integer.parseInt(ls.remove(0).split(": ")[1]);
		
		candidatos = List2.empty();
		ls.forEach(s -> candidatos.add(Candidato.create(s)));
		if(show)
			toConsole(name(file));
	}
	
	private static String name(String filePath) {
		int p1 = filePath.lastIndexOf('/');
		int p2 = filePath.lastIndexOf('.');
		return filePath.substring(p1+1, p2);
	}

	public static Integer getNumCandidatos() {
		return candidatos.size();
	}
	
	public static Integer getNumCualidades() {
		return cualidades.size();
	}
	
	public static Integer getPresupuestoMax() {
		return presupuestoMax;
	}
	
	public static Set<String> getCualidades() {
		return Set2.copy(cualidades);
	}
	
	public static Integer getValoracion(Integer i) {
		return candidatos.get(i).valoracion();
	}
	
	public static Double getSueldoMin(Integer i) {
		return candidatos.get(i).sueldo();
	}
	
	public static List<String> getCualidades(Integer i) {
		return candidatos.get(i).cualidades();
	}
	
	public static Set<Integer> getCualidadesInt(Integer i) {
		return IntStream.range(0, getNumCualidades())
			.filter(j-> getTieneCualidad(i, j)).boxed().collect(Collectors.toSet());
	}
	
	public static Boolean getTieneCualidad(Integer i, Integer j) {
		String s = cualidades.get(j);
		return candidatos.get(i).cualidades().contains(s);
	}
	
	public static Boolean getSonIncompatibles(Integer i, Integer j) {
		return candidatos.get(i).incompatibilidades().contains(j);
	}
	
	public static Candidato getCandidato(Integer i) {
		return candidatos.get(i);
	}
	
	public static String getNombre(Integer i) {
		return getCandidato(i).nombre();
	}

	public static void toConsole(String file) {
		String2.toConsole("%s %s:", String2.line("_",58), file);
		String2.toConsole("Presupuesto Max.: %d\nCualidades: %s", presupuestoMax, cualidades);
		String2.toConsole(candidatos,"Candidatos");
		String2.toConsole(String2.line("·",72));
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) throws IOException {
		iniDatos("datos_entrada/ejercicio1/DatosEntrada1.txt", true);
		iniDatos("datos_entrada/ejercicio1/DatosEntrada2.txt", true);
		iniDatos("datos_entrada/ejercicio1/DatosEntrada3.txt", true);
	}	

}