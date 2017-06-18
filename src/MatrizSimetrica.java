public class MatrizSimetrica {

	private int[] mat;
	private int size;
	
	public MatrizSimetrica(int size) {
		this.size = size;
		this.mat = new int[((size-1)*size)/2];
	}
	
	private int getIndex(int x, int y) throws IndexOutOfBoundsException{
		if(x == y) throw new IndexOutOfBoundsException();
		if(y > x){
			int tmp = x;
			x = y;
			y = tmp;
		}
		return y* (this.size - 1 -y) +x;
	}
	
	public void set(int x, int y, int value) throws IndexOutOfBoundsException{
		this.mat[getIndex(x, y)]=value;
	}
	
	public int get(int x, int y) throws IndexOutOfBoundsException{
		return this.mat[getIndex(x, y)];
	}
	
}
