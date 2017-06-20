import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class PruebaColoreo {

	@Test
	public void coloreoSimple() throws IOException {
		GrafoNDNP g = GrafoGenerator.porcAdyacencia(5, 40);
		g.colorearSecuencialAleatorio();
		g.aArchivo(new File("in"));
		g.aArchivoColores(new File("out"));
		Assert.assertEquals(true, ProbadorColores.test(g));
		
		g = GrafoGenerator.desdeArchivo(new File("in"), new File("out"));
		Assert.assertEquals(true, ProbadorColores.test(g));
	}

	@Test
	public void probadorError() {
		GrafoNDNP g = GrafoGenerator.porcAdyacencia(10, 40);
		g.colorearSecuencialAleatorio();
		
		if(g.getColor(2)-1 == 0){
			g.setColor(2, 2);
		}else{
			g.setColor(2, g.getColor(2)-1);
		}
		
		Assert.assertEquals(false, ProbadorColores.test(g));
	}
	
}
