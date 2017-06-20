import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class GrafoNDNP extends MatrizSimetrica {

	private int[] colores;

	public GrafoNDNP(int nodos) {
		super(nodos);
		this.colores = new int[nodos];
	}

	public void conectar(int nodo1, int nodo2) {
		this.set(nodo1, nodo2, 1);
	}

	public boolean estaConectado(int nodo1, int nodo2) {
		return this.get(nodo1, nodo2) != 0;
	}

	public int getGrade(int n) {
		int c = 0;
		for (int i = 0; i < this.getSize(); i++)
			if (n != i && estaConectado(n, i))
				c++;
		return c;
	}

	public int getCA() {
		int c = 0;
		for (int y = 0; y < this.getSize() - 1; y++) {
			for (int x = y + 1; x < this.getSize(); x++) {
				if (estaConectado(x, y))
					c++;
			}
		}
		return c;
	}

	public int[] getColores() {
		return this.colores;
	}

	public void setColores(int[] c) {
		this.colores = c;
	}

	public void setColor(int n, int c) {
		this.colores[n] = c;
	}

	public int getColor(int n) {
		return this.colores[n];
	}

	public int cantidadColores() {
		int m = 0;
		for (int i = 0; i < colores.length; i++) {
			if (colores[i] > m)
				m = colores[i];
		}
		return m;
	}

	public void aArchivo(File f) throws IOException {
		DecimalFormat df = new DecimalFormat("#.##");
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		int ca = this.getCA();
		int n = this.getSize();
		writer.write(this.getSize() + " "); // cantidad de nodos
		writer.write(ca + " "); // cantidad de aristas
		writer.write(df.format(((float) ca / (((n - 1) * n) / 2) * 100)) + " "); // porc
																					// ady
		int gm = -1;
		int gmin = n;
		for (int i = 0; i < n; i++) {
			int g = getGrade(i);
			if (g > gm)
				gm = g;
			if (g < gmin)
				gmin = g;
		}
		writer.write(gm + " " + gmin);
		writer.newLine();
		writer.write(this.toString());
		writer.close();
	}

	public void aArchivoColores(File f) throws IOException {
		DecimalFormat df = new DecimalFormat("#.##");
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		int ca = this.getCA();
		int n = this.getSize();
		writer.write(this.getSize() + " "); // cantidad de nodos
		writer.write(this.cantidadColores() + " "); // cantidad de colores
		writer.write(ca + " "); // cantidad de aristas
		writer.write(df.format(((float) ca / (((n - 1) * n) / 2) * 100)) + " "); // porc
																					// ady
		int gm = -1;
		int gmin = n;
		for (int i = 0; i < n; i++) {
			int g = getGrade(i);
			if (g > gm)
				gm = g;
			if (g < gmin)
				gmin = g;
		}
		writer.write(gm + " " + gmin);
		writer.newLine();
		for (int i = 0; i < this.getSize(); i++) {
			writer.write(i + " " + colores[i]);
			writer.newLine();
		}
		writer.close();
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < this.getSize(); i++) {
			for (int j = i + 1; j < this.getSize(); j++) {
				if (estaConectado(i, j)) {
					s += i + " " + j + "\n";
				}
			}
		}
		return s;

	}

	private void limpiarColores() {
		for (int i = 0; i < colores.length; i++)
			colores[i] = 0;
	}
	
	private int getRnd(int min, int max){
		return min + (int) Math.round(Math.random()*(max-min));
	}

	public int colorearSecuencialAleatorio() {
		limpiarColores();
		int c =0;
		int act;
		int maxC=1;
		int sColor;
		
		while(c!=getSize()){
			act = getRnd(0, getSize()-1);
			while(colores[act]!=0){ // busco primer lugar no coloreado desde random
				act++;
				if(act>=getSize())
					act=0;
			}
			c++;
			sColor = 0;
			for(int i=1;sColor ==0 && i<=maxC;i++){ //busco color disponible
				sColor=i;
				for(int j=0;j<getSize();j++){
					if(act!=j && estaConectado(act, j) && i == colores[j])
						sColor=0;
				}
			}
			if(sColor==0){
				sColor = ++maxC;
			}
			colores[act]=sColor;
		}
		return maxC;
	}
}
