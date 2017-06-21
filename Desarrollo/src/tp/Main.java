package tp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import grafos.GrafoGenerator;
import grafos.GrafoNDNP;
import grafos.ProbadorColores;

public class Main {

	public static int CORRIDAS = 10000;
	public static int RUNNING = 0;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ArrayList<MulticoreEject> tareas = new ArrayList<>();
		int processors = Runtime.getRuntime().availableProcessors();
		processors = processors -1; // dejame usar la pc un toque
		System.out.println("Procesadores disponibles: "+processors);
		tareas.add( new MulticoreEject(GrafoGenerator.porcAdyacencia(600, 60), "60060"));
		tareas.add( new MulticoreEject(GrafoGenerator.porcAdyacencia(600, 90), "60090"));
		tareas.add( new MulticoreEject(GrafoGenerator.porcAdyacencia(600, 40), "60040"));
		tareas.add( new MulticoreEject(GrafoGenerator.regularPorcAdyacencia(1000, 50), "R100050"));
		tareas.add( new MulticoreEject(GrafoGenerator.regularPorcAdyacencia(1000, 75), "R100075"));
		
		while(!tareas.isEmpty()){
			while(RUNNING< processors){
				Iterator<MulticoreEject> it = tareas.iterator();
				while(it.hasNext()){
					MulticoreEject p = it.next();
					if(p.getState()==Thread.State.NEW){
						RUNNING++;
						p.start();
					}else{
						if(p.getState()==Thread.State.TERMINATED){
							tareas.remove(p);
						}
					}
				}
			}
			Thread.sleep(1000);
		}
	}
	
	

}
