import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ANN extends JPanel{
	
	private ArrayList<ArrayList<Node>> nodes;
	private ArrayList<Dendrite> dendrites;
	
	private JFrame frame;
	private int inputs;
	private int outputs;
	
	
	public static void main(String args[]){
		ANN n = new ANN();
	}
	
	public ANN(){
	
		
		nodes = new ArrayList<ArrayList<Node>>();
		nodes.add(new ArrayList<Node>());
		nodes.add(new ArrayList<Node>());
		
		dendrites = new ArrayList<Dendrite>();
		
		inputs = 3;
		outputs = 3;
		
		for(int i = 0; i<inputs;i++)
			this.addNewNode(0, i, 0,false);
		for(int i =0; i< outputs;i++)
			this.addNewNode(1, i, 0,false);
		
		int randAmt = 4;
		
		for(int i = (int) (randAmt*Math.random());i<randAmt;i++){
			for(int p = (int) (randAmt*Math.random()), m = p; p<randAmt;p++){
				Node n;
				if(p==m)
					n = this.addNewNode(i, p, 0, true);
				else
					n = this.addNewNode(i, p, 0, false);
				while(n == null)
					n = this.addNewNode(i, p, 0, false);
				
			}
		}
		
		this.addNewNode(1, 0, 0, true);
	
		
		
		
		
		frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setVisible(true);
		
		frame.add(this);
		this.setVisible(true);
		
	}
	
	public void addRandomNode(Node node){
		
	}
	
	public Node addNewNode(int x, int y, int age, boolean insert){
		Node n = null;
		
		if(!insert){
			if(x >= 0 && x <= nodes.size()-1){
				 n = new Node(true, age);
				if(y>=0 && y<=nodes.get(x).size()-1)
					nodes.get(x).add(n);
				else
					nodes.get(x).add(n);
			}else if(x>0 && x==nodes.size()){
				n = new Node(true, age);
				nodes.add(new ArrayList<Node>());
				nodes.get(nodes.size()-1).add(n);
			}
		}else{
			n = new Node(true, age);
			if(x>=0 && x <=nodes.size()-1){
				nodes.add(x, new ArrayList<Node>());
				nodes.get(x).add(n);
			}
		}
		
		if(n!= null){
			if(x<nodes.size()-2){
				Node output = nodes.get(x+1).get((int) ((nodes.get(x+1).size()-1)*Math.random()));
				n.addNewOutput(new Dendrite(n,output));
			}
			if(x>0){
				Node output = nodes.get(x-1).get((int) ((nodes.get(x-1).size()-1)*Math.random()));
				output.addNewOutput(new Dendrite(output,n));
			}
		}
		
		return n;
	}
	
	public void addNewNode(Node node, int x, int y, int age, Node input, Node output){
		
	}
	
	public void addRandomDendrite(Dendrite den){
		
	}
	
	public void addNewDendrite(Dendrite den){
		
	}
	
	
	public void paintComponent(Graphics g){
		int distX = frame.getWidth()/(nodes.size()+1);
		g.setColor(Color.RED);
		
		
		for(int i = 0; i < nodes.size(); i++){
			int distY = frame.getHeight()/(nodes.get(i).size() +1);
			for(int m = 0; m < nodes.get(i).size(); m++){
				
				for(Dendrite d: nodes.get(i).get(m).outputs){
					Node n = d.outputNode;
								
					for(int xN = 0; xN < nodes.size();xN++){
						for(int yN =0; yN <nodes.get(xN).size(); yN++){
							if(n==nodes.get(xN).get(yN)){
								int dY = frame.getHeight()/(nodes.get(xN).size()+1);
								g.setColor(Color.green);
								g.drawLine(distX+(i*distX), distY+(m*distY), distX + (xN*distX), dY+(yN*dY));
								break;
							}
						}
					}
				}
				
				g.setColor(Color.RED);
				g.fillOval(distX + (i*distX)-25, distY+(m*distY)-25, 50, 50);
			}
		}
	}
	
	
	private class Node{
		
		private boolean active;
		private int age; 
		public ArrayList<Double> input;
		public ArrayList<Dendrite> outputs;
		
		public Node(boolean a, int m){
			active = a;
			age = m;
			
			input = new ArrayList<Double>();
			outputs = new ArrayList<Dendrite>();
		}
		
		public void addNewOutput(Dendrite d){
			outputs.add(d);
		}
		
		public void removeOutput(Dendrite d){
			for(Dendrite m : outputs){
				if(d==m){
					System.out.println("Still need to do this");
					//TODO
				}
			}
		}
		
		
		public void addToInput(double b){
			input.add(b);
		}
		
		public void output(){
			double value = 0;
			for(double val : input){
				value += val;
			}
			
			for(Dendrite d : outputs){
				d.getInput(value);
			}
		}
		
		
	}
	
	private class Dendrite{
		
		private double weight;
		public Node inputNode;
		public Node outputNode;
		
		public Dendrite(Node m,Node n){
			inputNode = m;
			outputNode = n;
			weight = Math.random();
		}
		
		public void getInput(double input){
			double output = weight*input;
			outputNode.addToInput(output);
		}
		
		public Node getNode(){
			return outputNode;
		}
	}

}
