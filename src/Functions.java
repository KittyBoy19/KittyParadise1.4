import java.util.ArrayList;

public class Functions {
	public class SelectedTool {
	private int tool = 0;
		public int GetTool(){
			return tool;
		}
		public void SetTool(int settool){
		tool = settool;	
		}
	}
	public class CharLocation{
	private int location = 0;
	private int cursor = 1;
	private char facing = 1;
	public int GetLocation(ArrayList<Tile> mapTiles){
		for(int i = 0; i < mapTiles.size(); i++){
			if(mapTiles.get(i).GetLayer(3) == 418) location = i;
			if(mapTiles.get(i).GetLayer(3) == 419) location = i;
			if(mapTiles.get(i).GetLayer(3) == 420) location = i;
			if(mapTiles.get(i).GetLayer(3) == 421) location = i;
		}
		return location;
	}
	char GetLastMove(){
	return facing;	
	}
	void SetLastMove(char last){
	facing = last;
	}
	int GetCursor(){
		return cursor;
	}
	void SetCursor(int position){
	cursor = position;	
	}
	public int GetLocation(ArrayList<Tile> mapTiles, int layer, int id) {
		for(int i = 0; i < mapTiles.size(); i++){
			if(mapTiles.get(i).GetLayer(layer) == id) location = i;
			if(mapTiles.get(i).GetLayer(layer) == id) location = i;
			if(mapTiles.get(i).GetLayer(layer) == id) location = i;
			if(mapTiles.get(i).GetLayer(layer) == id) location = i;
		}
		return location;
	}
	}
}
