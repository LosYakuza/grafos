package grafos;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class GrafoNDNP extends MatrizSimetrica {

	private int[] colores;
	private int [] grados;

	public GrafoNDNP(int nodos) {
		super(nodos);
		this.colores = new int[nodos];
		grados = null;
	}

	
	public GrafoNDNP clone(){
		GrafoNDNP g = new GrafoNDNP(getSize());
		g.setMat(this.getMat());
		g.colores = this.colores.clone();
		if(this.grados!=null)
			g.grados = this.grados.clone();
		return g;
	} 
	
	public void conectar(int nodo1, int nodo2) {
		this.set(nodo1, nodo2, 1);
	}

	public boolean estaConectado(int nodo1, int nodo2) {
		return this.get(nodo1, nodo2) != 0;
	}

	public int getGrado(int n) {
		if(grados == null){
			int c =0;
			for(int i =0; i<this.getSize();i++)
				if(n!=i && estaConectado(n, i))
					c++;
			return c;
		}
		return grados[n];
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
			int g = getGrado(i);
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
			int g = getGrado(i);
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
					s += (i+1) + "-" + (j+1) + ",";
				}
			}
		}
		return "http://g.ivank.net/#"+this.getSize()+":"+s.substring(0, s.length()-1);

	}

	private void limpiarColores() {
		for (int i = 0; i < colores.length; i++)
			colores[i] = 0;
	}
	
	private int getRnd(int min, int max){
		return min + (int) Math.round(Math.random()*(max-min));
	}

	/**
	 * 
	 * @param n
	 * @param maxC
	 * @return genero color nuevo
	 */
	private boolean colorear(int n, int maxC){
		int sColor = 0;
		for(int i=1;sColor == 0 && i<=maxC;i++){ //busco color disponible
			sColor=i;
			for(int j=0;j<getSize();j++){
				if(n!=j && estaConectado(n, j) && i == colores[j])
					sColor=0;
			}
		}
		if(sColor==0){
			colores[n] = maxC+1;
			return true;
		}else{
			colores[n]=sColor;
			return false;
		}
	}
	
	public int colorearSecuencialAleatorio() {
		limpiarColores();
		int c =0;
		int act;
		int maxC=1;
		
		while(c!=getSize()){
			act = getRnd(0, getSize()-1);
			while(colores[act]!=0){ // busco primer lugar no coloreado desde random
				act++;
				if(act>=getSize())
					act=0;
			}
			if(colorear(act, maxC)){
				maxC++;
			}
			c++;
			
		}
		return maxC;
	}

	public void setGrados(){
		grados = new int[getSize()];
		for(int n =0;n<getSize();n++){
			int c =0;
			for(int i =0; i<this.getSize();i++)
				if(n!=i && estaConectado(n, i))
					c++;
			grados[n]=c;
		}
	}
	
	public int colorearWP(){
		int maxC=1;
		int c =0;
		if(grados == null){
			setGrados();
		}
		this.limpiarColores();
		int act; //actual grado maximo
		int lMax = -1; //ultimo maximo
		int maxG = 0; // maximo de busqueda actual
		int pos =0; // posicion de maximo actual
		
		while(c!=getSize()){
			act = getRnd(0, getSize()-1); // empieza desde cualquier punto
			maxG = -1;
			for(int i=0; i<getSize();i++){ // busca grado maximo
				if(colores[act]==0){
					if(grados[act]==lMax){
						pos = act;
						maxG = lMax;
						break;
					}
					if(grados[act]>maxG){
						maxG=grados[act];
						pos = act;
					}
				}
				act ++;
				if(act>=getSize())
					act=0;
			}
			if(colorear(pos, maxC)){
				maxC++;
			}
			lMax = maxG; // setea maximo grado
			c++;
			
		}
		return maxC;
	}
	
	public int colorearMatula(){
		int maxC=1;
		int c =0;
		if(grados == null){
			setGrados();
		}
		this.limpiarColores();
		int act; //actual grado maximo
		int lMax = -1; //ultimo maximo
		int maxG = -1; // maximo de busqueda actual
		int pos =0; // posicion de maximo actual
		
		while(c!=getSize()){
			act = getRnd(0, getSize()-1); // empieza desde cualquier punto
			maxG = -1;
			for(int i=0; i<getSize();i++){ // busca grado maximo
				if(colores[act]==0){
					if(grados[act]==lMax){
						pos = act;
						maxG = lMax;
						break;
					}
					if(grados[act]<maxG || maxG==-1){
						maxG=grados[act];
						pos = act;
					}
				}
				act ++;
				if(act>=getSize())
					act=0;
			}
			if(colorear(pos, maxC)){
				maxC++;
			}
			lMax = maxG; // setea maximo grado
			c++;
			
		}
		return maxC;
	}
	
	public int[] getAdyacentes(int nodo) {
		int[] result = new int[getGrado(nodo)];
		
		int pos = 0;
		for (int i = 0; i < getSize(); i++) {
			if (i != nodo && estaConectado(nodo, i)) {
				result[pos] = i;
				pos++;
			}
		}
		return result;
	}
}
