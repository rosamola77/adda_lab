package ejercicio2;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;

public class Datos2 {
	
	public record Producto(int id, String nombre, Double precio, Map<String,Integer> componentes, Integer max) {
		private static int cont = 0;
		public static Producto create(String s) {
			String[] v0 = s.split("->");
			String[] v1 = v0[1].trim().split(";");
			Double a = Double.parseDouble(v1[0].split("=")[1].trim());
			
			Map<String,Integer> b = Map2.empty();
			String[] v2 = v1[1].split("=")[1].trim().split(",");
			for(String e: v2) {
				String[] v3 = e.trim().split("[:()]");
				b.put(v3[1].trim(), Integer.parseInt(v3[2].trim()));
			}
			Integer c = Integer.parseInt(v1[2].split("=")[1].trim());
			
			return new Producto(cont++, v0[0].trim(),a, b, c);
		}
		
		static List<Producto> reset() { cont = 0; return List2.empty();	}
		
		public Integer getMaxUnidadesPosibles(Integer tpa, Integer tea) {
			return Math.min(max, Math.min(tpa/tp(), tea/te()));
		}
		
		public Double getBeneficioPosible(Integer tpa, Integer tea) {
			return precio*getMaxUnidadesPosibles(tpa, tea);
		}
		
		public Integer tp() {
			return componentes.entrySet().stream()
			.mapToInt(e->e.getValue()*Datos2.getComp(e.getKey()).tp()).sum();
		}
		
		public Integer te() {
			return componentes.entrySet().stream()
			.mapToInt(e->e.getValue()*Datos2.getComp(e.getKey()).te()).sum();
		}

		@Override
		public String toString() {		
			return String.format("%s: %.1f; %s; %d (%d,%d)", nombre, precio, componentes, max, tp(), te());
		}
	}
	
	public record Componente(int id, String nombre, Integer tp, Integer te) {
		private static int cont = 0;
		public static Componente create(String s) {
			String[] v0 = s.split(":");
			String[] v1 = v0[1].split(";");
			String a = v1[0].split("=")[1].trim();
			String b = v1[1].split("=")[1].trim();
			return new Componente(cont++, v0[0].trim(), Integer.parseInt(a), Integer.parseInt(b));
		}
		
		static List<Componente> reset() { cont = 0; return List2.empty();	}
		
		@Override
		public String toString() {		
			return String.format("%s: TP=; TE=", nombre, tp, te);
		}
	}
	
	private static Integer T_prod, T_elab;
	private static List<Producto> productos;
	private static List<Componente> componentes;
	
	public static void iniDatos(String file) {
		iniDatos(file, false);
	}
	
	public static void iniDatos(String file, boolean show) {		
		productos = Producto.reset();
		componentes = Componente.reset();
		
		List<String> ls = Files2.linesFromFile(file);
		
		T_prod = Integer.parseInt(ls.remove(0).split("=")[1].trim());
		T_elab = Integer.parseInt(ls.remove(0).split("=")[1].trim());
		for(String s: ls) {
			if(s.startsWith("//"))
				continue;
			else if(s.startsWith("C"))
				componentes.add(Componente.create(s));
			else if(s.startsWith("P"))
				productos.add(Producto.create(s));
		}
		if(show)
			toConsole();
	}

	public static void ordenar() {
		ordenar(T_prod, T_elab);
	}
	
	public static void ordenar(int tpr, int ter) {
		Comparator<Producto> cmp = Comparator.comparing(p -> p.getBeneficioPosible(tpr, ter));
		productos.sort(cmp.reversed());
	}

	public static List<Producto> getProductos() {
		return productos;
	}
	
	public static Integer getNumProductos() {
		return productos.size();
	}
	
	public static Integer getNumComponentes() {
		return componentes.size();
	}
	
	public static Integer getTiempoProdTotal() {
		return T_prod;
	}
	
	public static Integer getTiempoElabTotal() {
		return T_elab;
	}
	
	public static Producto getProducto(Integer i) {
		return productos.get(i);
	}
	
	public static Componente getComp(String name) {
		return componentes.stream().filter(c->c.nombre.equals(name)).findFirst().get();
	}
	
	public static Double getPrecioProd(Integer i) {
		return productos.get(i).precio();
	}
	
	public static Integer getUnidsSemanaProd(Integer i) {
		return productos.get(i).max();
	}
	
	public static Integer getTiempoProdComp(Integer j) {
		return componentes.get(j).tp();
	}
	
	public static Integer getTiempoElabComp(Integer j) {
		return componentes.get(j).te();
	}

	public static Integer getUnidadesComponente(Integer i, Integer j) {
		String s = componentes.get(j).nombre();
		Integer r = productos.get(i).componentes().get(s);
		return r==null? 0: r;
	}
	
	public static Integer getTiempoProduccCompProd(Integer i, Integer j) {
		return getUnidadesComponente(i, j)*getTiempoProdComp(j);
	}	
	
	public static Integer getTiempoElabCompProd(Integer i, Integer j) {
		return getUnidadesComponente(i, j)*getTiempoElabComp(j);
	}	
	
	public static Integer getTiempoProdProd(Integer i) {
		return getProducto(i).tp();
	}	
	
	public static Integer getTiempoElabProd(Integer i) {
		return getProducto(i).te();
	}
	
	public static Integer getMaxUnidadesPosibles(Integer i, Integer tpr, Integer ter) {
		return productos.get(i).getMaxUnidadesPosibles(tpr, ter);
	}
	
	public static void toConsole() {
		String2.toConsole(productos,"Productos");
		String2.toConsole(componentes,"Componentes");
		String2.toConsole("Tiempo Produccion Total: %d\nTiempo Elaboracion Total: %d\n%s",T_prod, T_elab, String2.linea());
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) throws IOException {
		iniDatos("datos_entrada/ejercicio2/DatosEntrada1.txt", true);
		iniDatos("datos_entrada/ejercicio2/DatosEntrada2.txt", true);
		iniDatos("datos_entrada/ejercicio2/DatosEntrada3.txt", true);
	}
	
}