public class Tile {
	private int layer1 = 0;
	private int layer2 = 0;
	private int layer3 = 0;
	public void SetLayers(int one, int two, int three){
		if(one != 0) layer1 = one;
		if(two != 0) layer2 = two;
		if(three != 0) layer3 = three;
	}
	public int GetLayer(int layer){
		if(layer == 1) {
		return layer1;
		} else if(layer == 2){
		return layer2;
		} else if(layer == 3){
		return layer3;
		} else return 0;
	}
	public void DeleteLayer(int layer){
		if(layer == 1) {
		layer1 = 0;
		}
		if(layer == 2){
		layer2 = 0;
		}
		if(layer == 3){
		layer3 = 0;
		}
	}
	public String PrintLayer(int layer){
		if(layer == 1) {
		return "icon_" + layer1;
		} else if(layer == 2){
		return "icon_" + layer2;
		} else if(layer == 3){
		return "icon_" + layer3;
		} else return "icon_416";
	}
}
