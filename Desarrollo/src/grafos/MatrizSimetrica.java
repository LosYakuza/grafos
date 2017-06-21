package grafos;
public class MatrizSimetrica {

	private int[] mat;
	private int size;
	
	public MatrizSimetrica(int size) {
		this.size = size;
		this.mat = new int[((size-1)*size)/2];
	}
	
	private int getIndex(int x, int y) throws IndexOutOfBoundsException{
		if(x == y || x>= size ||  y>= size) throw new IndexOutOfBoundsException(x+","+y);
		if(y > x){
			int tmp = x;
			x = y;
			y = tmp;
		}

		return y*(size-1)-((y-1)*y)/2 + (x-1-y);
	}
	
	public void set(int x, int y, int value) throws IndexOutOfBoundsException{
		this.mat[getIndex(x, y)]=value;
	}
	
	public int get(int x, int y) throws IndexOutOfBoundsException{
		return this.mat[getIndex(x, y)];
	}
	
	public int getSize(){return this.size;}
	
	public void init(int val){
		for(int i=0;i<this.mat.length;i++){
			this.mat[i]=val;
		}
	}
	
}
