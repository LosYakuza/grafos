package grafos;

public class ProbadorColores {
	
	public static boolean test(GrafoNDNP g){
		int [] nc = g.getColores();
		for(int act=0;act<g.getSize()-2;act++){
			if(nc[act] == 0) return false;
			for(int cmp =act+1;cmp<g.getSize();cmp++){
				if(nc[act]==nc[cmp] && g.estaConectado(act, cmp))
					return false;
			}
		}
		return true;
	}
}
