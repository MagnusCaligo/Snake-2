import java.util.ArrayList;

public class Genome {

	private ArrayList<Gene> genes;
	private int numInputs;
	private int numOutputs;
	
	
	
	public Genome(newANN network){
		genes = new ArrayList<Gene>();
		numInputs = network.inputs;
		numOutputs = network.outputs;
		
		for(int i =0; i < network.dendrites.size(); i++){
			Dendrite d = network.dendrites.get(i);
			Gene g = new Gene(d.age, d.inputNode.age, d.outputNode.age, d.weight, d.active);
			genes.add(g);
			//System.out.println("Created Gene: Age: " + g.innovation + "  Input: " + g.input+ "  Output: " + g.output);
		}
		

	}
	
	public static newANN buildNewANN(Genome g){
		//return null;
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Dendrite> dendrites = new ArrayList<Dendrite>();
		int nodeAge = 0;
		
		for(int i = 0; i < g.numInputs; i++){
			Node n = new Node(nodeAge);
			nodeAge++;
			n.type = 1;
			nodes.add(n);
		}
		for(int i = 0; i < g.numOutputs; i++){
			Node n = new Node(nodeAge);
			nodeAge++;
			n.type = 2;
			nodes.add(n);
		}
		
		for(Gene gene: g.getGenes()){
			Node output;
			Node input;
			if(gene.input >= nodes.size()){
				input = new Node(gene.input);
			}else{
				input = nodes.get(gene.input);
			}
			
			if(gene.output >= nodeAge){
				output = new Node(nodeAge);
				nodeAge++;
				nodes.add(output);
			}else{
				output = nodes.get(gene.output);
			}
			
			Dendrite den = new Dendrite(input, output, gene.innovation, gene.weight);
			if(!gene.active){
				den.active = false;
			}
			input.addNewOutput(den);
			dendrites.add(den);
			
		}
		
		newANN ann= new newANN(nodes,dendrites,g.numInputs,g.numOutputs);
		ann.inputs = g.numInputs;
		ann.outputs = g.numOutputs;
		if(g.getGenes().size() != 0)
			ann.innovation = g.getGenes().get(g.getGenes().size()-1).innovation;
		else
			ann.innovation = 0;
		ann.nodeAge = nodeAge;
		return ann;
	}
	
	public Genome(ArrayList<Gene> genes, int inputs, int outputs){
		this.genes = genes;
		this.numInputs = inputs;
		this.numOutputs = outputs;
	}
	
	public static newANN breedNewAnn(Genome network1, Genome network2){
		int innovation = network1.getGenes().size();
		ArrayList<Gene> genes = new ArrayList<Gene>();
		
		int net1loc = 0;
		int net2loc = 0;
		
		while(net1loc != innovation){
			Gene g1 = network1.getGenes().get(net1loc);
			Gene g2;
			if(net2loc < network2.getGenes().size())
				g2 = network2.getGenes().get(net2loc);
			else
				g2 = g1;
			
			if(g1.innovation > g2.innovation){
				genes.add(g2);
				net2loc++;
			}else if(g1.innovation < g2.innovation){
				genes.add(g1);
				net1loc++;
			}else{
				double rand = Math.random();
				if(rand > .5){
					genes.add(g1);
				}else{
					genes.add(g2);
				}
				net1loc++;
				net2loc++;
			}
			double rand = Math.random();
			if(rand >=.9){
				double val = genes.get(genes.size()-1).weight;
				double amt = Math.random() * 2;
				amt--;
				if(amt == 0) amt = 1;
				double percent = .10 * amt;
				val *= percent;
				genes.get(genes.size()-1).weight += val;
				System.out.println("Dendrite weight changed to: " + genes.get(genes.size()-1).weight);
			}
		}
		
		Genome genome = new Genome(genes, network1.numInputs, network1.numOutputs);
		
		newANN ann = Genome.buildNewANN(genome);
		ann.setVisible(true);
		
		double rand = Math.random();
		if(rand>=.9){
			System.out.println("Mutation has Occured");
			rand = Math.random();
			if(rand<=.6){
				System.out.println("New Dendrite");
				ann.addRandomDendrite();
			}else if(rand>.6){
				ann.addRandomNode();
			}
		}
		
		return ann;
	}
	
	
	public ArrayList<Gene> getGenes(){
		return genes;
	}
	
}
