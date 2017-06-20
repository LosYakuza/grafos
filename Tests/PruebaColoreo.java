import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import grafos.GrafoGenerator;
import grafos.GrafoNDNP;
import grafos.ProbadorColores;

public class PruebaColoreo {

	@Test
	public void coloreoSimple() throws IOException {
		GrafoNDNP g = GrafoGenerator.porcAdyacencia(50, 70);
		g.colorearSecuencialAleatorio();
		g.aArchivo(new File("in"));
		g.aArchivoColores(new File("out"));
		Assert.assertEquals(true, ProbadorColores.test(g));

		g = GrafoGenerator.desdeArchivo(new File("in"), new File("out"));
		Assert.assertEquals(true, ProbadorColores.test(g));
	}

	@Test
	public void coloreoWP() throws IOException {
		GrafoNDNP g = GrafoGenerator.porcAdyacencia(50, 70);
		 g.colorearWP();
		g.aArchivo(new File("in"));
		g.aArchivoColores(new File("out"));
		Assert.assertEquals(true, ProbadorColores.test(g));

		g = GrafoGenerator.desdeArchivo(new File("in"), new File("out"));
		Assert.assertEquals(true, ProbadorColores.test(g));
	}

	@Test
	public void coloreoMatula() throws IOException {
		GrafoNDNP g = GrafoGenerator.porcAdyacencia(50, 70);
		 g.colorearMatula();
		g.aArchivo(new File("in"));
		g.aArchivoColores(new File("out"));
		Assert.assertEquals(true, ProbadorColores.test(g));

		g = GrafoGenerator.desdeArchivo(new File("in"), new File("out"));
		Assert.assertEquals(true, ProbadorColores.test(g));
	}

	@Test
	public void probadorError() throws IOException {
		GrafoNDNP g = GrafoGenerator.porcAdyacencia(10, 100);
		g.colorearSecuencialAleatorio();

		if (g.getColor(2) - 1 == 0) {
			g.setColor(2, 2);
		} else {
			g.setColor(2, g.getColor(2) - 1);
		}
		Assert.assertEquals(false, ProbadorColores.test(g));

	}

}
