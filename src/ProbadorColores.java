
public class ProbadorColores {
	
	private GrafoNDNP g;
	
	/**
	 * Constructor con grafo ya coloreado
	 * @param g
	 */
	public ProbadorColores(GrafoNDNP g) {
		this.g = g;
	}
	
	public boolean test(){
		int [] nc = g.getColores();
		for(int act=0;act<g.getSize()-2;act++){
			for(int cmp =act+1;cmp<g.getSize()-1;cmp++){
				if(nc[act]==nc[cmp] && g.isConencted(act, cmp))
					return false;
			}
		}
		return true;
	}
}
