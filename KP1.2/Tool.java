public class Tool {
	private int id;
	private int quant;
	public void IncrementQuant(){
		quant++;
	}
	public void DecrementQuant(){
		quant--;
	}
	public void SetQuant(int x){
		quant = x;
	}
	public int GetQuant(){
		return quant;
	}
	public Tool(int ID){
		quant = 1;
		id = ID;
	}
	public int GetId(){
		return id;
	}
}
