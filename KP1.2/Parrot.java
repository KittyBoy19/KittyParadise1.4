import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Parrot{
	JFileChooser fileDialog;
	ArrayList<Integer> Map = new ArrayList<>(); //holds all my values for my grid
	ArrayList<Integer> Inventory = new ArrayList<>();
	ArrayList<Integer> Command = new ArrayList<>();
	ArrayList<Integer> CommandTop = new ArrayList<>();
	ImageIcon[] icons = new ImageIcon[500];
	Timer timer2, timer1;
	private JLabel label, layer1, layer2, layer3, layer4;
	JPanel inventoryright, SouthContainer;
	JPanel inventoryleft;
	Scanner scanInput = new Scanner(System.in);
	int i = 0;
	boolean editor = false;
	private JLayeredPane layered;
	Functions fx = new Functions();
	Random rand = new Random(); 
	ArrayList<Tool> Tools;
	ArrayList<Tile> MapTiles;
	JPanel panel;
	Functions.CharLocation locate = fx.new CharLocation();
	Functions.SelectedTool selectedtool = fx.new SelectedTool();
	private ImageIcon inventorybg15 = new ImageIcon(getClass().getResource("InventoryBG.png"));
	private ImageIcon inventoryselect = new ImageIcon(getClass().getResource("InventorySelect.png"));
	JPanel grid;
	public Parrot(){
		MapTiles = new ArrayList<Tile>();
		Tools = new ArrayList<Tool>();
		int charstart = 370;
		////////////////////////////
		for(int i = 1; i < 485; i++){
			String name = "icon_" + i +".gif";
			icons[i] = new ImageIcon(getClass().getResource(name));
		}
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel(new BorderLayout());
		frame.add(panel);
		//South Panel
		SouthContainer = new JPanel();
		SouthContainer.setLayout(new BoxLayout(SouthContainer,BoxLayout.X_AXIS));
		inventoryleft = new JPanel(new GridLayout(3,3));
		SouthContainer.add(inventoryleft);
		inventoryright = new JPanel(new GridLayout(3,17));
		SouthContainer.add(inventoryright);
		panel.add(SouthContainer, BorderLayout.SOUTH);
		inventoryleft.setPreferredSize(new Dimension(96,96));
		inventoryleft.setMaximumSize(new Dimension(96,96));
		inventoryright.setPreferredSize(new Dimension(544,96));
		inventoryright.setMaximumSize(new Dimension(544,96));
		InputMap imInv = SouthContainer.getInputMap(JPanel.WHEN_FOCUSED);
		ActionMap amInv = SouthContainer.getActionMap();
		imInv.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "W");
		amInv.put("W", new ArrowAction("W"));
		imInv.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "A");
		amInv.put("A", new ArrowAction("A"));
		imInv.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");
		amInv.put("S", new ArrowAction("S"));
		imInv.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "D");
		amInv.put("D", new ArrowAction("D"));
		imInv.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "E");
		amInv.put("E", new ArrowAction("E"));
		imInv.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "I");
		amInv.put("I", new ArrowAction("I"));
		//Center Panel
		JPanel CenterContainer = new JPanel();
		CenterContainer.setLayout(new BoxLayout(CenterContainer,BoxLayout.Y_AXIS));
		grid = new JPanel(new GridLayout(0,20));
		CenterContainer.add(grid);
		panel.add(CenterContainer, BorderLayout.CENTER);
		grid.setPreferredSize(new Dimension(640,640));
		grid.setMaximumSize(new Dimension(640,640));
		InitializeGrid(1, charstart); //CHOOSE MAP
		InitializeChar(charstart,1);
		frame.setTitle("Kitty Paradise");
		frame.setResizable(false);
		frame.pack();
		grid.requestFocusInWindow();
		frame.setVisible(true);
		RedrawGrid();
		RedrawCommand();
		InputMap im = grid.getInputMap(JPanel.WHEN_FOCUSED);
		ActionMap am = grid.getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "W");
		am.put("W", new ArrowAction("W"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.SHIFT_MASK), "ww");
		am.put("ww", new ArrowAction("WSHIFT"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_MASK), "ss");
		am.put("ss", new ArrowAction("SSHIFT"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.SHIFT_MASK), "dd");
		am.put("dd", new ArrowAction("DSHIFT"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "A");
		am.put("A", new ArrowAction("A"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.SHIFT_MASK), "aa");
		am.put("aa", new ArrowAction("ASHIFT"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.SHIFT_MASK), "ee");
		am.put("ee", new ArrowAction("E"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK), "ctrl");
		am.put("ctrl", new ArrowAction("CTRLS"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");
		am.put("S", new ArrowAction("S"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "D");
		am.put("D", new ArrowAction("D"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "E");
		am.put("E", new ArrowAction("E"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "I");
		am.put("I", new ArrowAction("I"));
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0), "O");
		am.put("O", new ArrowAction("O"));
		Timer timer2 = new Timer(10000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			Grow();
			}
			});
			timer2.start();
	}
	private void display() {
        String[] items = {"Save", "Load"};
        JComboBox combo = new JComboBox(items);
        JTextField field1 = new JTextField("MySaveName");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(combo);
        panel.add(new JLabel("Field 1:"));
        panel.add(field1);
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(combo.getSelectedItem()
                + " " + field1.getText()
                + " ");
    		System.out.println("Input or output name");
    		if(combo.getSelectedItem() == "Save"){
    			save(field1.getText());
    		}else Load(field1.getText());
        } else {
            System.out.println("Cancelled");
        }
    }
	//Recreates the grid with the user inputed information.
	public void MoveChar(char direction){
		if(grid.isFocusOwner()){
			int character = locate.GetLocation(MapTiles);
				locate.SetLastMove(direction);
				if(direction == 'a' && character % 20 != 0 && PassableTile(character-1) == true){
					MapTiles.get(character-1).SetLayers(0, 0, 418);
					MapTiles.get(character).DeleteLayer(3);
				} else if(direction == 'a')MapTiles.get(character).SetLayers(0, 0, 418);
				if(direction == 'w' && character >= 20 && PassableTile(character-20) == true){
					MapTiles.get(character-20).SetLayers(0, 0, 419);
					MapTiles.get(character).DeleteLayer(3);
				} else if(direction == 'w')MapTiles.get(character).SetLayers(0, 0, 419);
				if(direction == 'd' && (character+1) % 20 != 0 && PassableTile(character+1) == true){
					//AniCharRight();
					MapTiles.get(character+1).SetLayers(0, 0, 420);
					MapTiles.get(character).DeleteLayer(3);
				} else if(direction == 'd')MapTiles.get(character).SetLayers(0, 0, 420);
				if(direction == 's' && character < 380 && PassableTile(character+20) == true){
					MapTiles.get(character+20).SetLayers(0, 0, 421);
					MapTiles.get(character).DeleteLayer(3);
				} else if(direction == 's')MapTiles.get(character).SetLayers(0, 0, 421);
				RedrawGrid();
			} else if(SouthContainer.isFocusOwner()){
			int cursor = locate.GetCursor();
			if(direction == 'a' && cursor % 17 != 0){
				locate.SetCursor(locate.GetCursor()-1);
				RedrawCommand();
			}
			if(direction == 'w' && cursor >= 17){
				locate.SetCursor(locate.GetCursor()-17);
				RedrawCommand();
			}
			if(direction == 'd' && (cursor+1) % 17 != 0){
				locate.SetCursor(locate.GetCursor()+1);
				RedrawCommand();
			}
			if(direction == 's' && cursor < 34){
				locate.SetCursor(locate.GetCursor()+17);
				RedrawCommand();
			}
		}
	}
	public int NextBlockInFront(){
	int nextblockindex = 0;
	int holder;
		holder = MapTiles.get(locate.GetLocation(MapTiles)).GetLayer(3);
		if(holder == 418){
		nextblockindex = locate.GetLocation(MapTiles)-1;
		} else if(holder == 419){
		nextblockindex = locate.GetLocation(MapTiles)-20;
		} else if(holder == 420){
		nextblockindex = locate.GetLocation(MapTiles)+1;
		} else if(holder == 421){
		nextblockindex = locate.GetLocation(MapTiles)+20;
		}
		return nextblockindex;
	}
	public void RedrawGrid(){
		grid.removeAll();
		for (int i = 0; i < 400; i++) {
			PrintTile(i);
			}
		grid.revalidate();
		grid.repaint();
		return;
	}
	public void RedrawCommand(){
		inventoryleft.removeAll();
		inventoryright.removeAll();
		for (int i = 0; i < 51; i++) {
			if( i >= 1 && i <= 9){
				if(i == 5){
				layered = new JLayeredPane();
				layered.setBounds(0, 0, 32, 32);
				layer1 = new JLabel(inventorybg15);
				layer1.setBounds(0, 0, 32, 32);
				layer1.setOpaque(true);
				layer2 = new JLabel(icons[selectedtool.GetTool()]);
				layer2.setBounds(0, 0, 32, 32);
				layer2.setOpaque(false);
				layered.add(layer1, new Integer(0), 0);
				layered.add(layer2, new Integer(1), 0);
				inventoryleft.add(layered);
				} else {
					label = new JLabel(icons[405+i]);
					inventoryleft.add(label);
				}
			}
			if(i < Tools.size()){
				if(Tools.get(i).GetQuant() != 0){
				layered = new JLayeredPane();
				layered.setBounds(0, 0, 32, 32);
				layer1 = new JLabel(inventorybg15);
				layer1.setBounds(0, 0, 32, 32);
				layer1.setOpaque(true);
				layered.add(layer1, new Integer(0), 0);
					if(i <= Tools.size()-1){
					layer2 = new JLabel(icons[Tools.get(i).GetId()]);
					layer2.setBounds(0, 0, 32, 32);
					layer2.setOpaque(false);
					layered.add(layer2, new Integer(1), 0);
					if(Tools.get(i).GetQuant() > 1){
					layer3 = new JLabel(""+Tools.get(i).GetQuant());
					layer3.setFont(new Font("Serif", Font.BOLD, 10));
					layer3.setForeground(Color.WHITE);
					layer3.setHorizontalAlignment(SwingConstants.RIGHT);
					layer3.setBounds(0, 0, 32, 32);
					layer3.setOpaque(false);
					layered.add(layer3, new Integer(2), 0);
					}
					}
					if( locate.GetCursor() == i){
						layer4 = new JLabel(inventoryselect);
						layer4.setBounds(0, 0, 32, 32);
						layer4.setOpaque(false);
						layered.add(layer4, new Integer(3), 0);	
					}
				inventoryright.add(layered);
			} else {
				if(Tools.get(i).GetQuant() == 0) Tools.remove(i);
				selectedtool.SetTool(442);
			}
			} else {
			layered = new JLayeredPane();
			layered.setBounds(0, 0, 32, 32);
			layer1 = new JLabel(inventorybg15);
			layer1.setBounds(0, 0, 32, 32);
			layer1.setOpaque(true);
			layered.add(layer1, new Integer(0), 0);
			inventoryright.add(layered);
			}
		}
		inventoryleft.revalidate();
		inventoryleft.repaint();
		inventoryright.revalidate();
		inventoryright.repaint();
		return;
	}
	public void InitializeGrid(int map, int charstartplace){
		int weeds = 0;
		Load("HomeBase");
		for(int i = 0; i < 400; i++){
			if(i != charstartplace){
			if(rand.nextInt(8) == 1){
				weeds = 422;
			} else if(rand.nextInt(9) == 1){
				weeds = 426;
			} else if(rand.nextInt(10) == 1){
				weeds = 427;
			} else weeds = 0;
			}
			if(MapTiles.get(i).GetLayer(1) == 423 || MapTiles.get(i).GetLayer(1) == 417 || MapTiles.get(i).GetLayer(1) == 415){
				if(MapTiles.get(i).GetLayer(2) == 0){
					if(MapTiles.get(i).GetLayer(3) == 0) MapTiles.get(i).SetLayers(0, weeds, 0);
				}
			}
	}
		Tools.removeAll(Tools);
		if(editor == false){
			Tools.add(new Tool(442));
			Tools.add(new Tool(1));
			Tools.add(new Tool(2));
			Tools.add(new Tool(3));
			Tools.add(new Tool(4));
			Tools.add(new Tool(5));
			Tools.add(new Tool(17));
			Tools.add(new Tool(25));
			Tools.get(6).SetQuant(10);
			Tools.get(7).SetQuant(10);
			} else {
				Tools.add(new Tool(415));
				Tools.get(Tools.size()-1).SetQuant(1);
				Tools.add(new Tool(417));
				Tools.get(Tools.size()-1).SetQuant(1);
				Tools.add(new Tool(423));
				Tools.get(Tools.size()-1).SetQuant(1);
				Tools.add(new Tool(429));
				Tools.get(Tools.size()-1).SetQuant(2);
				Tools.add(new Tool(428));
				Tools.get(Tools.size()-1).SetQuant(2);
				Tools.add(new Tool(5));
				Tools.get(Tools.size()-1).SetQuant(2);
				Tools.add(new Tool(416));
				Tools.get(Tools.size()-1).SetQuant(2);
			}
	}
	public void InitializeChar(int location, int facing){
		if(locate.GetLocation(MapTiles) == 0){
		MapTiles.get(location).SetLayers(0, 0, 419);
		locate.SetLastMove('w');
		selectedtool.SetTool(5);
		}
	}
	public void Action(){
		if(editor == false){
		int character = locate.GetLocation(MapTiles);
		int facing = MapTiles.get(character).GetLayer(3);
			if(MapTiles.get(NextBlockInFront()).GetLayer(1) == 423){//farm land
				if(selectedtool.GetTool() == 2){
					if(PassableTile(NextBlockInFront()))MapTiles.get(NextBlockInFront()).SetLayers(424, 0, 0);
				}
			}
			if(MapTiles.get(NextBlockInFront()).GetLayer(1) == 424){//tilled land
				if(selectedtool.GetTool() == 5){
					MapTiles.get(NextBlockInFront()).SetLayers(425, 0, 0);
				}
				if(selectedtool.GetTool() == 2){
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
					MapTiles.get(NextBlockInFront()).SetLayers(424, 0, 0);
				}
				if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 0){ //Seeds
					if(selectedtool.GetTool() == 17 && Tools.get(ToolLocation(17)).GetQuant() > 0){ //strawberry
						MapTiles.get(NextBlockInFront()).SetLayers(424, 432, 0);
						Tools.get(ToolLocation(17)).DecrementQuant();
					}
					if(selectedtool.GetTool() == 25 && Tools.get(ToolLocation(25)).GetQuant() > 0){ //carrot
						MapTiles.get(NextBlockInFront()).SetLayers(424, 438, 0);
						Tools.get(ToolLocation(25)).DecrementQuant();
					}
				}
			}
			if(MapTiles.get(NextBlockInFront()).GetLayer(1) == 425 ){//watered land
				if(selectedtool.GetTool() == 2){
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
					MapTiles.get(NextBlockInFront()).SetLayers(424, 0, 0);
				}
			}
			if(selectedtool.GetTool() == 1){ //Sickle
				if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 422){
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
				}
				if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 437){
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
				}
			}
			if(selectedtool.GetTool() == 442){ //Hand
				if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 435){//Hand_Strawberry
				if(ToolExists(436)){
					Tools.get(ToolLocation(436)).IncrementQuant();
					MapTiles.get(NextBlockInFront()).SetLayers(424,433,0);
				} else {
					Tools.add(new Tool(436));
					MapTiles.get(NextBlockInFront()).SetLayers(424,433,0);
				}
				}
				if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 440){//Hand_carrot
					if(ToolExists(441)){
						Tools.get(ToolLocation(441)).IncrementQuant();
						MapTiles.get(NextBlockInFront()).DeleteLayer(2);
						MapTiles.get(NextBlockInFront()).SetLayers(424,0,0);
					} else {
						Tools.add(new Tool(441));
						MapTiles.get(NextBlockInFront()).DeleteLayer(2);
						MapTiles.get(NextBlockInFront()).SetLayers(424,0,0);
					}
					}
				if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 428){ //Hand_Refined Stone
				if(ToolExists(428)){
					Tools.get(ToolLocation(428)).IncrementQuant();
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
				} else {
					Tools.add(new Tool(428));
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
				}
				}
				if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 429){ //Hand_Refined Log
				if(ToolExists(429)){
					Tools.get(ToolLocation(429)).IncrementQuant();
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
				} else {
					Tools.add(new Tool(429));
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
				}
				}
			}
			if(PassableTile(NextBlockInFront()) && MapTiles.get(NextBlockInFront()).GetLayer(2) == 0){
				if(selectedtool.GetTool() == 428){ //place stone
					MapTiles.get(NextBlockInFront()).SetLayers(0, 428, 0);
					Tools.get(ToolLocation(428)).DecrementQuant();
				}
				if(selectedtool.GetTool() == 429){ //place log
					MapTiles.get(NextBlockInFront()).SetLayers(0, 429, 0);
					Tools.get(ToolLocation(429)).DecrementQuant();
				}
				RedrawCommand();
			}
			if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 426){//rocks
				if(selectedtool.GetTool() == 4){
					HammerAni();
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
					if(ToolExists(428)){
						Tools.get(ToolLocation(428)).IncrementQuant();
					} else Tools.add(new Tool(428));
				}
			}
			if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 427){//logs
				if(selectedtool.GetTool() == 3){
					MapTiles.get(NextBlockInFront()).DeleteLayer(2);
					if(ToolExists(429)){
						Tools.get(ToolLocation(429)).IncrementQuant();
					} else Tools.add(new Tool(429));
				}
			}
			if(MapTiles.get(NextBlockInFront()).GetLayer(2) == 427);
		} else {
			 if(Tools.get(ToolLocation(selectedtool.GetTool())).GetQuant() == 2){
				 MapTiles.get(NextBlockInFront()).SetLayers(0, selectedtool.GetTool(), 0);
			 }else if(Tools.get(ToolLocation(selectedtool.GetTool())).GetQuant() == 12){
				 MapTiles.get(NextBlockInFront()).SetLayers(selectedtool.GetTool(), selectedtool.GetTool(), 0);
			 }else if(Tools.get(ToolLocation(selectedtool.GetTool())).GetQuant() == 123){
				 MapTiles.get(NextBlockInFront()).SetLayers(selectedtool.GetTool(), selectedtool.GetTool(), selectedtool.GetTool());
			 }else if(Tools.get(ToolLocation(selectedtool.GetTool())).GetQuant() == 23){
				 MapTiles.get(NextBlockInFront()).SetLayers(0, selectedtool.GetTool(), selectedtool.GetTool());
			 }else MapTiles.get(NextBlockInFront()).SetLayers(selectedtool.GetTool(), 0, 0);
	
		}
		RedrawGrid();
		RedrawCommand();
	}
	public class ArrowAction extends AbstractAction {
	    private String cmd;
	    public ArrowAction(String cmd) {
	        this.cmd = cmd;
	    }
	    public void actionPerformed(ActionEvent e) {
	        if (cmd.equalsIgnoreCase("W")) {
	        	if(grid.isFocusOwner()) {  
		        	MoveChar('w');
		        	}else if(SouthContainer.isFocusOwner()) {
		        		MoveChar('w');
		        	}
	        } else if (cmd.equalsIgnoreCase("A")) {
	        	if(grid.isFocusOwner()) { 
		        	MoveChar('a');
		        	}else if(SouthContainer.isFocusOwner()) {
		        		MoveChar('a');
		        	}
	        } else if (cmd.equalsIgnoreCase("S")) {
	        	if(grid.isFocusOwner()) { 
		        	MoveChar('s');
		        	}else if(SouthContainer.isFocusOwner()) {
		        		MoveChar('s');
		        	}
	        } else if (cmd.equalsIgnoreCase("D")) {
	        	if(grid.isFocusOwner()) { 
		        	MoveChar('d');
		        	}else if(SouthContainer.isFocusOwner()) {
		        		MoveChar('d');
		        	}
	        } else if (cmd.equalsIgnoreCase("E")) {
	        	if(grid.isFocusOwner()) { 
		        	Action();
		        	}else if(SouthContainer.isFocusOwner()) {
		        	selectedtool.SetTool(Tools.get(locate.GetCursor()).GetId());
		        	grid.requestFocusInWindow();
		        	RedrawCommand();
		        	}		        	
	        } else if (cmd.equalsIgnoreCase("I")) {
	        	if(grid.isFocusOwner()) { 
	        	SouthContainer.requestFocusInWindow();
	        	}else if(SouthContainer.isFocusOwner()) {
	        		grid.requestFocusInWindow();
	        		RedrawGrid();
	        	}        	
	        } else if (cmd.equalsIgnoreCase("WSHIFT")) {
	        	if(grid.isFocusOwner()) { 
		        	MoveChar('w', true);
	        	}else if(SouthContainer.isFocusOwner()) {
	        		MoveChar('w');
		        	}		
	        } else if (cmd.equalsIgnoreCase("ASHIFT")) {
	        	if(grid.isFocusOwner()) { 
		        	MoveChar('a', true);
		        	}else if(SouthContainer.isFocusOwner()) {
		        	}		
	        } else if (cmd.equalsIgnoreCase("SSHIFT")) {
	        	if(grid.isFocusOwner()) { 
		        	MoveChar('s', true);
		        	}else if(SouthContainer.isFocusOwner()) {
		        	}		
	        } else if (cmd.equalsIgnoreCase("DSHIFT")) {
	        	if(grid.isFocusOwner()) { 
		        	MoveChar('d', true);
		        	locate.SetLastMove('p');
		        	}else if(SouthContainer.isFocusOwner()) {
		        	}		
	        } else if (cmd.equalsIgnoreCase("O")) {
	        	if(editor == true) { 
	        		System.out.println("Enter Item Number: ");
		        	int itemadd = scanInput.nextInt();
		        	if(itemadd != 0){Tools.add(new Tool(itemadd));
		        	System.out.println("Enter Item quant: ");
		        	int itemquant = scanInput.nextInt();
		        	Tools.get(Tools.size()-1	).SetQuant(itemquant);
		        	} else Tools.remove(Tools.size()-1);
		        	RedrawCommand();
		        	}else if(SouthContainer.isFocusOwner()) {
		        	}		
	        } else if (cmd.equalsIgnoreCase("CTRLS")) {
	        	if(grid.isFocusOwner()) { 
	        	display();
		        	}else if(SouthContainer.isFocusOwner()) {
		        		display();
		        	}		
	        }
	}
	}
	public void PrintTile(int tile){
		layered = new JLayeredPane();
		layered.setBounds(0, 0, 32, 32);
		layer1 = new JLabel(icons[MapTiles.get(tile).GetLayer(1)]);
		layer1.setBounds(0, 0, 32, 32);
		layer1.setOpaque(false);
		layered.add(layer1, new Integer(1), 0);
		layer2 = new JLabel(icons[MapTiles.get(tile).GetLayer(2)]);
		layer2.setBounds(0, 0, 32, 32);
		layer2.setOpaque(false);
		layered.add(layer2, new Integer(2), 0);
		layer3 = new JLabel(icons[MapTiles.get(tile).GetLayer(3)]);
		layer3.setBounds(0, 0, 32, 32);
		layer3.setOpaque(false);
		layered.add(layer3, new Integer(3), 0);
		grid.add(layered);
	}

	public void MoveChar(char direction, boolean b) {
		if(grid.isFocusOwner()){
			int character = locate.GetLocation(MapTiles);
				locate.SetLastMove(direction);
				if(direction == 'a' && character % 20 != 0 == true){
					MapTiles.get(character).SetLayers(0, 0, 418);
				} else if(direction == 'w' && character >= 20 == true){
					MapTiles.get(character).SetLayers(0, 0, 419);
				} else if(direction == 'd' && (character+1) % 20 != 0 == true){
					MapTiles.get(character).SetLayers(0, 0, 420);
				} else if(direction == 's' && character < 380){
					MapTiles.get(character).SetLayers(0, 0, 421);
				}
		}
		RedrawGrid();
	}
	public boolean PassableTile(int location){
		boolean result = true;
		if(editor == false){
		int[] passable = {422, 426, 427, 435, 437, 428, 429, 440, 473, 474, 475, 476, 481, 482, 483, 484};
		for(int i = 0; i < passable.length; i++){
		if(MapTiles.get(location).GetLayer(2) == passable[i]) result = false;
		if(MapTiles.get(location).GetLayer(1) == passable[i]) result = false;
		}
		}else result = true;
		return result;
	}
	public boolean ToolExists(int exists){
		boolean result = false;
		for(int i = 0; i < Tools.size(); i++){
			if(Tools.get(i).GetId() == exists){
				result = true;
			}
		}
		return result;
	}
	public int ToolLocation(int exists){
		int result = 0;
		for(int i = 0; i < Tools.size(); i++){
			if(Tools.get(i).GetId() == exists){
				result = i;
			}
		}
		return result;
	}
	public void Grow(){
		int[] plant = {432,433,434,435,438,439,440};
			for(int j = 0; j < plant.length; j++)
				for(int i = 0; i < 400; i++){
				if(MapTiles.get(i).GetLayer(2) == plant[j]){
				if(rand.nextInt(15) == 1 && MapTiles.get(i).GetLayer(1) == 424) MapTiles.get(i).SetLayers(0, 437, 0);
				if(rand.nextInt(7) == 1 && PassableTile(i)) MapTiles.get(i).SetLayers(0, plant[j]+1, 0);	
				}
			}
		RedrawGrid();
	}
	public void Load(String userloadfromfile){
		MapTiles.removeAll(MapTiles);
		Tools.removeAll(Tools);
		 System.out.println(userloadfromfile);
		if ( userloadfromfile.isEmpty() ) {
		   System.out.println("No Data in file");
		}
		else {
		   System.out.println("Reading save data...");
		   String dataEntry;
		   try(InputStream file = Parrot.class.getResourceAsStream(userloadfromfile + ".txt")){
			   BufferedReader reader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
		      for(int i = 0; (dataEntry = reader.readLine()) != null; i++) {
		             // Read one line from the file, containing one name/number pair.
		    	  System.out.println(userloadfromfile);
		         int separatorPosition = dataEntry.indexOf('%');
		         int separatorPosition2 = dataEntry.indexOf('@');
		         int separatorPosition3 = dataEntry.indexOf('$');
		         if (separatorPosition == -1) throw new IOException("File is not the correct data file.");
		         if (separatorPosition2 == -1) throw new IOException("File is not the correct data file.");
		         String firstdatachunk = dataEntry.substring(0, separatorPosition);
		         String seconddatachunk = dataEntry.substring(separatorPosition+1, separatorPosition2);
		         String thirddatachunk = dataEntry.substring(separatorPosition2+1, separatorPosition3);
		         String fourthdatachunk = dataEntry.substring(separatorPosition3+1);
		         int holder = Integer.parseInt(firstdatachunk);
		         int holder2 = Integer.parseInt(seconddatachunk);
		         MapTiles.add(new Tile());
		         MapTiles.get(i).SetLayers(holder, holder2, 0);
		         System.out.println(thirddatachunk);
		         if(!thirddatachunk.isEmpty()){
		         holder = Integer.parseInt(thirddatachunk);
		         holder2 = Integer.parseInt(fourthdatachunk);
		         Tools.add(new Tool(holder));
		         Tools.get(Tools.size()-1).SetQuant(holder2);
		         }
      }
		}
	   catch (IOException e) {
	      System.out.println("Error in data file.");
	      System.out.println("This program cannot continue.");
	      System.exit(1);
	   }
		InitializeChar(169,1);
		RedrawGrid();
		RedrawCommand();
		}
	}
	public void save(String outputtingdataFile){
			  System.out.println("Saving data to file " + 
			         outputtingdataFile + ".txt");
			   PrintWriter out;
			   try {
			      out = new PrintWriter(new FileWriter("src/" +outputtingdataFile + ".txt"));
			   }
			   catch (IOException e) {
			      System.out.println("ERROR: Can't open data file for output.");
			      return;
			   }
			   for ( int i = 0; i < 400; i++ ){
			      if(i < Tools.size()) {
			    	  out.println(MapTiles.get(i).GetLayer(1)+ "%" + MapTiles.get(i).GetLayer(2) + 
			    	"@" + Tools.get(i).GetId() + "$" + Tools.get(i).GetQuant());
			      } else out.println(MapTiles.get(i).GetLayer(1)+ "%" + MapTiles.get(i).GetLayer(2) + 
					    	"@" + "$");
			   }
			   out.flush();
			   out.close();
			   if (out.checkError())
			      System.out.println("ERROR: Some error occurred while writing data file.");
			   else
			      System.out.println("Done.");

	}
	public void intro(){		
//		panel.removeAll();
//		layered = new JLayeredPane();
//		layer1 = new JLabel(icons[430]);
//		layer1.setOpaque(false);
//		layer1.setBounds(0, 0, 640, 736);
//		layered.add(layer1, new Integer(2), 0);
//		layer2 = new JLabel(icons[431]);
//		layer2.setOpaque(true);
//		layer2.setBounds(0, 0, 640, 736);
//		layered.add(layer2, new Integer(1), 0);
//		panel.add(layered);
//		InputMap imintro = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
//		ActionMap amintro = panel.getActionMap();
//		imintro.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "E");
//		amintro.put("E", new ArrowAction("E"));
//		panel.revalidate();
//		panel.repaint();
	}
	public void AniCharRight(){
		int startingspot = locate.GetLocation(MapTiles);
		timer1 = new Timer(40, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(i == 0) MapTiles.get(startingspot).SetLayers(0, 0, 443);
			if(i == 1) MapTiles.get(startingspot).SetLayers(0, 0, 444);
			if(i == 2) MapTiles.get(startingspot).SetLayers(0, 0, 448);
			if(i == 1) MapTiles.get(startingspot+1).SetLayers(0, 0, 446);
			if(i == 2) {
				MapTiles.get(startingspot+1).SetLayers(0, 0, 447);
				//timer.stop();
			}
			if(i == 3) {
				MapTiles.get(startingspot).SetLayers(0, 0, 449);
				MapTiles.get(startingspot).DeleteLayer(3);
			}
			if(i == 4){
				MapTiles.get(locate.GetLocation(MapTiles)+1).SetLayers(0, 0, 420);
			}
			RedrawGrid();
			if(i == 4) {
				i = 0;
				timer1.stop();
			}
			i++;
			}
			});
		timer1.start();
	}
	public void HammerAni(){
		if(NextBlockInFront()-locate.GetLocation(MapTiles) == 1){
		int startingspot = locate.GetLocation(MapTiles);
		timer2 = new Timer(45, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(i == 0) MapTiles.get(startingspot).SetLayers(0, 452, 449);
			if(i == 1) MapTiles.get(startingspot).SetLayers(0, 453, 449);
			if(i == 2) MapTiles.get(startingspot).SetLayers(0, 452, 450);
			if(i == 3){
				i = 0;
				timer2.stop();
				MapTiles.get(startingspot).DeleteLayer(2);
				MapTiles.get(startingspot).SetLayers(0, 0, 420);
			}else i++;
			RedrawGrid();
			}
			});
		timer2.start();
		}
		if(NextBlockInFront()-locate.GetLocation(MapTiles) == -1){
			int startingspot = locate.GetLocation(MapTiles);
			timer2 = new Timer(70, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				if(i == 0) MapTiles.get(startingspot).SetLayers(0, 454, 472);
				if(i == 1) MapTiles.get(startingspot).SetLayers(0, 455, 472);
				if(i == 2) MapTiles.get(startingspot).SetLayers(0, 456, 472);
				if(i == 3){
					i = 0;
					timer2.stop();
					MapTiles.get(startingspot).DeleteLayer(2);
					MapTiles.get(startingspot).SetLayers(0, 0, 418);
				}else i++;
				RedrawGrid();
				}
				});
			timer2.start();
			}
	}
	
}

