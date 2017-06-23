import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import grafos.GrafoGenerator;
import grafos.GrafoNDNP;

public class PruebaGrafo {

	@Test
	public void test() throws IOException {
		GrafoGenerator.probArista(4, 70);
		GrafoNDNP g = GrafoGenerator.porcAdyacencia(10, 50);
		g.aArchivo(new File("50por"));
		
	}
	
	@Test
	public void regular() throws IOException {
		GrafoNDNP g = GrafoGenerator.regular(9, 6);
		for(int i=0;i<9;i++){
			Assert.assertEquals(6,g.getGrado(i));
		}
		g.aArchivo(new File("9-6reg"));
		regularLoad();
	}
	
	@Test
	public void regularporc() {
		GrafoNDNP g = GrafoGenerator.regularPorcAdyacencia(1000,  50);
		for(int i=0;i<9;i++){
			Assert.assertEquals(500,g.getGrado(i));
		}
	}
	
	@Test
	public void npartito() {
		GrafoNDNP g = GrafoGenerator.npartitio(6, 3);
	}
	
	@Test
	public void clonetest() {
		GrafoNDNP g1 = GrafoGenerator.regularPorcAdyacencia(1000,  50);
		GrafoNDNP g = g1.clone();
		for(int i=0;i<9;i++){
			Assert.assertEquals(500,g.getGrado(i));
		}
	}
	
	public void regularLoad() throws IOException {
		GrafoNDNP g = GrafoGenerator.desdeArchivo(new File("9-6reg"));
		for(int i=0;i<9;i++){
			Assert.assertEquals(6,g.getGrado(i));
		}
	}
}
