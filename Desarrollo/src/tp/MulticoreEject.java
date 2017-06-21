package tp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import grafos.GrafoNDNP;

public class MulticoreEject extends Thread{
	
	private String file;
	private GrafoNDNP g;

	
	public MulticoreEject(GrafoNDNP g, String name) {
		this.g = g;
		this.file = name;
	}
	
	@Override
	public void run() {
		System.out.println("Iniciado "+file);
		Main.RUNNING++;
		try {
			ejecutar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void ejecutar() throws IOException{
		int rminMatula=0;
		int rminSecuecial=0;
		int rminWP=0;
		// minimos
		int minMatula=0;
		int minSecuecial=0;
		int minWP=0;
		long startTime = System.currentTimeMillis();
		for(int i=0;i<Main.CORRIDAS;i++){
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
				System.out.println("("+file+")Tiempo estimado ("+i+"/"+Main.CORRIDAS+"): "+
			((((System.currentTimeMillis() - startTime)/i)*(Main.CORRIDAS-i))/60000)+"mins");
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
