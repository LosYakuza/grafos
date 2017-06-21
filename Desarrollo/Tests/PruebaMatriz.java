import org.junit.Assert;
import org.junit.Test;

import grafos.MatrizSimetrica;


public class PruebaMatriz {

	@Test(expected=IndexOutOfBoundsException.class)
	public void testDiagonal() {
		MatrizSimetrica m = new MatrizSimetrica(10);
		m.set(2, 2, 5);
	}
	
	@Test
	public void testGeneral() {
		MatrizSimetrica m = new MatrizSimetrica(10);
		m.set(1, 5, 5);
		Assert.assertEquals(5, m.get(1, 5));
		Assert.assertEquals(5, m.get(5, 1));
		m.set(0, 1, 50);
		Assert.assertEquals(50, m.get(0, 1));
		Assert.assertEquals(50, m.get(1, 0));
		m.set(8, 9, 70);
		Assert.assertEquals(70, m.get(8, 9));
		Assert.assertEquals(70, m.get(9, 8));
	}

}
