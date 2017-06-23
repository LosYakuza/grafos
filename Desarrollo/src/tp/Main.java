package tp;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import grafos.GrafoGenerator;
import grafos.GrafoNDNP;


public class Main {

	public static int CORRIDAS = 10000;
	public static int RUNNING = 0;
	private static ArrayList<MulticoreEject> tareas = new ArrayList<>();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		int processors = Runtime.getRuntime().availableProcessors();
		processors = processors -1; // dejame usar la pc un toque
		System.out.println("Procesadores disponibles: "+processors);
		
		tareas.add( new MulticoreEject(GrafoGenerator.porcAdyacencia(600, 40), "60040",CORRIDAS));
		
		tareas.add( new MulticoreEject(GrafoGenerator.porcAdyacencia(600, 60), "60060",CORRIDAS));
		
		
		/*divido en mas procesos tareas largas*/
		GrafoNDNP g = GrafoGenerator.porcAdyacencia(600, 90);
		g.setGrados();
		tareas.add( new MulticoreEject(g, "60090-0",CORRIDAS/2));
		tareas.add( new MulticoreEject(g, "60090-1",CORRIDAS/2));
		
		g = GrafoGenerator.regularPorcAdyacencia(1000, 50);
		g.setGrados();
		tareas.add( new MulticoreEject(g, "R100050-0",CORRIDAS/2));
		tareas.add( new MulticoreEject(g, "R100050-1",CORRIDAS/2));
		
		g = GrafoGenerator.regularPorcAdyacencia(1000, 75);
		g.setGrados();
		tareas.add( new MulticoreEject(g, "R100075-0",CORRIDAS/4));
		tareas.add( new MulticoreEject(g, "R100075-1",CORRIDAS/4));
		tareas.add( new MulticoreEject(g, "R100075-2",CORRIDAS/4));
		tareas.add( new MulticoreEject(g, "R100075-3",CORRIDAS/4));
		
		while(!tareas.isEmpty()){
			while(RUNNING< processors && !tareas.isEmpty()){
				try{
					Iterator<MulticoreEject> it = tareas.iterator();
					while(it.hasNext() && RUNNING< processors){
						MulticoreEject p = it.next();
						if(p.getState()==Thread.State.NEW){
							RUNNING++;
							p.start();
						}
					}
				}catch(Exception e){}
			}
			Thread.sleep(1000);
		}
		System.err.println("Fin ejecucion");
	}
	public static void finalizo(MulticoreEject p){
		tareas.remove(p);
		RUNNING--;
	}
	

}
