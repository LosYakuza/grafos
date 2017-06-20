
public class ProbadorColores {
	
	public static boolean test(GrafoNDNP g){
		int [] nc = g.getColores();
		for(int act=0;act<g.getSize()-2;act++){
			for(int cmp =act+1;cmp<g.getSize()-1;cmp++){
				if(nc[act]==nc[cmp] && g.estaConectado(act, cmp))
					return false;
			}
		}
		return true;
	}
}
