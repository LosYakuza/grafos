package tp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import grafos.GrafoGenerator;
import grafos.GrafoNDNP;
import grafos.ProbadorColores;

public class Main {

	private static int CORRIDAS = 10000;
	
	public static void main(String[] args) throws IOException {
		GrafoNDNP g;
		
		// corridas
		
		// analisis para 600 a 60%
		System.err.println("Ejecutando para 600 a 60%");
		g = GrafoGenerator.porcAdyacencia(600, 60);
		ejecutar(g,"60060");
		// analisis para 600 a 90%
		System.err.println("Ejecutando para 600 a 90%");
		g = GrafoGenerator.porcAdyacencia(600, 90);
		ejecutar(g,"60090");
		
		// analisis para 600 a 40%
		System.err.println("Ejecutando para 600 a 40%");
		g = GrafoGenerator.porcAdyacencia(600, 40);
		ejecutar(g,"60040");
		
		//regular 1000 a 50%
		System.err.println("Ejecutando para regular 1000 a 50%");
		g = GrafoGenerator.regularPorcAdyacencia(1000, 50);
		ejecutar(g,"R100050");
		
		//regular 1000 a 75%
		System.err.println("Ejecutando para regular 1000 a 75%");
		g = GrafoGenerator.regularPorcAdyacencia(1000, 75);
		ejecutar(g,"R100075");
	}
	
	private static void ejecutar(GrafoNDNP g, String file) throws IOException{
		int rminMatula=0;
		int rminSecuecial=0;
		int rminWP=0;
		// minimos
		int minMatula=0;
		int minSecuecial=0;
		int minWP=0;
		long startTime = System.currentTimeMillis();
		for(int i=0;i<CORRIDAS;i++){
			int colores = g.colorearSecuencialAleatorio();
			if(colores<minSecuecial || i==0){
				minSecuecial = colores;
				rminSecuecial = i;
			}
			colores = g.colorearMatula();

			if(colores<minMatula || i==0){
				minMatula = colores;
				rminMatula = i;
			}
			
			colores = g.colorearWP();
			if(colores<minWP || i==0){
				minWP = colores;
				rminWP= i;
			}
			if(i!=0 && i % 100 == 0){
				System.out.println("Tiempo estimado ("+i+"/"+CORRIDAS+"): "+
			((((System.currentTimeMillis() - startTime)/i)*(CORRIDAS-i))/60000)+"mins");
			}
		}

		System.out.println("Secuencial aleatorio \n\tMinimo: "+minSecuecial+"\n\tCorrida: "+rminSecuecial);
		System.out.println("Matula \n\tMinimo: "+minMatula+"\n\tCorrida: "+rminMatula);
		System.out.println("Welsh-Powell \n\tMinimo: "+minWP+"\n\tCorrida: "+rminWP);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
		writer.write("Secuencial aleatorio \n\tMinimo: "+minSecuecial+"\n\tCorrida: "+rminSecuecial);
		writer.newLine();
		writer.write("Matula \n\tMinimo: "+minMatula+"\n\tCorrida: "+rminMatula);
		writer.newLine();
		writer.write("Welsh-Powell \n\tMinimo: "+minWP+"\n\tCorrida: "+rminWP);
		writer.close();
	}

}
