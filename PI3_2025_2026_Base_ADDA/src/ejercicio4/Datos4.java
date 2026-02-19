package ejercicio4;

import java.io.IOException;
import java.util.List;

import org.jgrapht.Graph;

import us.lsi.common.String2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Datos4 {
	
	public record Interseccion(int id, String nombre, double relevancia) {
	    public static Interseccion ofFormat(String[] v) {
	    	Interseccion r = null;
	    	if(v.length==1)
	    		r = new Interseccion(Integer.parseInt(v[0].trim()), "", -1.);
	    	else {
	            String name = v[1].trim();
	            int id = Integer.parseInt(v[0].trim());
	            double relev = Double.parseDouble(v[2].trim());
	            r = new Interseccion(id, name, relev);
	    	}
	        return r;
	    }
	    
	    public boolean conMonumento() {
	    	return !nombre.isBlank();
	    }

	    @Override
	    public String toString() {
	        return nombre.isBlank()? id+"": String.format("%d: %s (*%.0f)", id, nombre.toUpperCase(), relevancia);
	    }
	}

	public record Calle(int id, double tiempo, double esfuerzo) {
	    private static int cont = 0;

	    public static Calle ofFormat(String[] formato) {
	        double time = Double.parseDouble(formato[2].trim());
	        double effort = Double.parseDouble(formato[3].trim());
	        return new Calle(cont++, time, effort);
	    }

	    @Override
	    public String toString() {
	        return String.format("%d: (%.1f min., %.1f esf.)", id, tiempo, esfuerzo);
	    }

		public static void reset() { cont=0; }
	}	
	
	public static Integer N;
	public static Double maxTime;

	private static final Double TP = 0.75;
	private static String inputFile;
	private static Graph<Interseccion, Calle> grafo;
	private static List<Interseccion> vertices;
	
	public static void iniDatos(String file) {
		iniDatos(file, false);
	}
	
	public static void iniDatos(String file, boolean display) {
		Calle.reset();
		inputFile = file;
		grafo = GraphsReader.newGraph(file,
			Interseccion::ofFormat, Calle::ofFormat, Graphs2::simpleGraph);
		
		vertices = List.copyOf(grafo.vertexSet());
        N = vertices.size();
        
        maxTime = grafo.edgeSet().stream()
        	.mapToDouble(e->e.tiempo()).sum()*TP;
        
        if(display)
        	toConsole();
	}
	
	public static Interseccion getVertex(Integer i) {
		return vertices.get(i);
	}
	
	public static Double tiempo(Integer i, Integer j) {
		Interseccion v1 = vertices.get(i);
		Interseccion v2 = vertices.get(j);
		return grafo.containsEdge(v1,v2)? grafo.getEdge(v1,v2).tiempo(): 1000.;
	}

	public static Double esfuerzo(Integer i, Integer j) {
		Interseccion v1 = vertices.get(i);
		Interseccion v2 = vertices.get(j);
		return grafo.containsEdge(v1,v2)? grafo.getEdge(v1,v2).esfuerzo(): 1000.;
	}
	
	public static Boolean sonMonumentos(Integer i, Integer j) {
		return vertices.get(i).conMonumento() && vertices.get(j).conMonumento();
	}
	
	public static void toConsole() {
		String2.toConsole("Datos para el grafo %s:", nombre(inputFile));
		String2.toConsole(vertices, "Vertices");
		String2.toConsole(grafo.edgeSet(), "Aristas");
		String2.toConsole(String2.linea());
	}

	private static String nombre(String s) {
		int p1 = s.lastIndexOf("/");
		int p2 = s.lastIndexOf(".");
		return s.substring(p1+1, p2);
	}

	// Test de la lectura del fichero
	public static void main(String[] args) throws IOException {
		iniDatos("datos_entrada/ejercicio4/DatosEntrada1.txt", true);
		iniDatos("datos_entrada/ejercicio4/DatosEntrada2.txt", true);
		iniDatos("datos_entrada/ejercicio4/DatosEntrada3.txt", true);
	}

}