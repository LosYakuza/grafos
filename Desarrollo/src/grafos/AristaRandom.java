package grafos;

import java.util.Comparator;

public class AristaRandom {
	private int nodo1;
	private int nodo2;
	private int randomValue;
	
	public AristaRandom(int nodo1, int nodo2, int value) {
		this.nodo1 = nodo1;
		this.nodo2 = nodo2;
		this.randomValue = value;
	}

	public static Comparator<AristaRandom> comparatorSegunRandom() {
		return new Comparator<AristaRandom>() {
			@Override
			public int compare(AristaRandom a1, AristaRandom a2) {
				if (a1.randomValue > a2.randomValue)
					return -1;
				if (a1.randomValue < a2.randomValue)
					return 1;
				return 0;
			};
		};
	}
	
	public int getRandomValue() {
		return randomValue;
	}
	
	public int getNodo1() {
		return nodo1;
	}
	
	public int getNodo2() {
		return nodo2;
	}
}
