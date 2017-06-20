import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GrafoGenerator {

	public static GrafoNDNP probArista(int N, double prob) {
		GrafoNDNP g = new GrafoNDNP(N);
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (Math.random() < prob / 100) {
					g.conenct(i, j);
				}
			}
		}
		return g;
	}

	public static GrafoNDNP porcAdyacencia(int N, double porc) {
		GrafoNDNP g = new GrafoNDNP(N);
		boolean connect = true;
		if(porc>50){
			porc =100-porc;
			connect = false;
			g.init(1);
		}
		int conexiones = (int) Math.round((((N - 1) * N) / 2) * porc/100);
	
		while (conexiones > 0) {
			int y = (int) Math.round(Math.random() * (N - 2));
			int x = (1 + y) + (int) Math.round(Math.random() * (N - 2 - y));
			while (connect == g.isConencted(x, y)) {
				x++;
				if (x >= N - 1) {
					y++;
					if (y >= N - 1) {
						y = 0;
					}
					x = y + 1;
				}
			}
			g.set(x, y, connect?1:0);
			conexiones--;
		}
		return g;

	}

	public static GrafoNDNP regular(int N, int grado) {
		GrafoNDNP g = new GrafoNDNP(N);
		if (grado>=N || N*grado % 2 !=0){
			throw new RuntimeException("No se puede generar grafo con los parametros solicitados.");
		}
		for(int step=1;step<grado;step++){ // ejecuta hasta llegar al grado
			for(int n=0;n<N;n++){ // recorre nodos uniendo
				if(g.getGrade(n)<grado){
					if(n+step >= N){
						g.conenct(n, n+step-N);
					}else{
						g.conenct(n, n+step);
					}
				}
			}
			
		}
		return g;
		
	}

	public static GrafoNDNP regularPorcAdyacencia(int N, double porc) {
		return regular(N, (int)Math.round((N-1)*(porc/100)));
		
	}

	public static GrafoNDNP npartitio(int N, int ng) {
		int [] grupos = new int [N];
		GrafoNDNP g = new GrafoNDNP(N);
		int i,j;
		for(i=0;i<ng;i++){ // asegura un nodo por grupo
			grupos[i]=i;
		}
		for(;i<N;i++){
			grupos[i]=(int)Math.round(Math.random()*(ng-1));
		}
		
		for(i=0;i<N-1;i++){
			for(j=i+1;j<N;j++){
				if(grupos[i]!=grupos[j]){
					g.conenct(i, j);
				}
			}
		}
		
		return g;
	}
	
	public static GrafoNDNP desdeArchivo(File f) throws FileNotFoundException{
		Scanner s = new Scanner(f);
		int N = s.nextInt();
		int CA = s.nextInt();
		GrafoNDNP g = new GrafoNDNP(N);
		s.nextLine();
		while(CA>0){
			g.conenct(s.nextInt(), s.nextInt());
			CA--;
		}
		s.close();
		return g;
	}
	
	public static GrafoNDNP desdeArchivo(File f, File colores) throws FileNotFoundException{
		GrafoNDNP g = desdeArchivo(f);
		Scanner s = new Scanner(colores);
		s.nextLine();
		int n = g.getSize();
		while(n>0){
			g.setColor(s.nextInt(), s.nextInt());
			n--;
		}
		s.close();
		return g;
	}
	
}
