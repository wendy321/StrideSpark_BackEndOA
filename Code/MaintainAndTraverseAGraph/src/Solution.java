import java.util.HashMap; 
import java.util.Set; 
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class Solution {
	List<String> input;
	HashMap<Integer,Set<Integer>> hm;
	List<String> output;
	
	public Solution(){
		input=new ArrayList<String>();
		hm=new HashMap<Integer,Set<Integer>>();
		output=new ArrayList<String>();
	}
		
	public void MaintainTraverseGraph(List<String> inputs){
	  setInput(inputs);
		
	  for(String command:getInput()){
		//parse command
		String[] strarray=command.split(" ",0);
		if(strarray.length<3 || strarray.length>4){
			System.out.println("Invalid input:"+command+", continue....");
			continue;
		}
		
		HashMap<Integer,Set<Integer>> graph=getGraph();		
		if(strarray.length==3){
			int value1=Integer.valueOf(strarray[1]);
			int value2=Integer.valueOf(strarray[2]);
			//Add command
			if(strarray[0].equalsIgnoreCase("add")&&
			   strarray.length==3 &&
			   strarray[1].matches("\\d+")&&
			   strarray[2].matches("\\d+")){
				//Both nodes exist
				if(graph.containsKey(value1) && graph.containsKey(value2)){
					//If they have not linked to each other yet, then link them
					if(!isLinked(value1,value2,graph)){
						graph.get(value1).add(value2);
						graph.get(value2).add(value1);
					}
					//If they are already linked to each other, then do nothing
				
				}
				//Only node1 exists
				else if(graph.containsKey(value1)){
					graph.get(value1).add(value2);
					Set<Integer> set=new HashSet<Integer>();
					set.add(value1);
					graph.put(value2, set);
				}
				//Only node2 exists
				else if(graph.containsKey(value2)){
					graph.get(value2).add(value1);
					Set<Integer> set=new HashSet<Integer>();
					set.add(value2);
					graph.put(value1, set);				
				
				}
				//Both nodes don't exist
				else{
					Set<Integer> set1=new HashSet<Integer>();
					set1.add(value2);
					graph.put(value1, set1);
					Set<Integer> set2=new HashSet<Integer>();
					set2.add(value1);				
					graph.put(value2, set2);
				}
				
			}
			//Remove Command
			else if(strarray[0].equalsIgnoreCase("remove")&&
				strarray.length==3 &&
				strarray[1].matches("\\d+")&&
				strarray[2].matches("\\d+")){
			
				//Both nodes exist
				if(graph.containsKey(value1) && graph.containsKey(value2)){
					//If they are already linked to each other, then remove the link				
					if(isLinked(value1,value2,graph)){
						graph.get(value1).remove(value2);
						graph.get(value2).remove(value1);
					}
					//If they have not linked to each other yet, then do nothing
				}
				//If only one node or both nodes exist(s), then do nothing
				
			}
			//Other invalid command, just continue
			else{
				System.out.println("Invalid input:"+command+", continue....");
				continue;
			}
		}
		
		if(strarray.length==4){
			int value1=Integer.valueOf(strarray[2]);
			int value2=Integer.valueOf(strarray[3]);
			//Is Linked Command
			if(strarray[0].equalsIgnoreCase("is")&&
					strarray[1].equalsIgnoreCase("linked")&&
					strarray.length==4&&
					strarray[2].matches("\\d+")&&
					strarray[3].matches("\\d+")){

				getOutput().add(String.valueOf(isLinked(value1, value2, graph)));
			}
			//Other invalid command, just continue
			else{
				System.out.println("Invalid input:"+command+", continue....");
				continue;				
			}
		}		
	  }
	}

	public boolean isLinked(int value1,int value2, HashMap<Integer,Set<Integer>> graph){
		if(!graph.containsKey(value1) || !graph.containsKey(value2)) return false;
		
		HashMap<Integer,Boolean> visited=new HashMap<Integer,Boolean>(graph.size());
		for(Integer key:graph.keySet()){
			visited.put(key, false);
		}
		
		return isLinkedhelper(value1,value2,graph,visited);
	}
	
	public boolean isLinkedhelper(int value1,int value2, HashMap<Integer,Set<Integer>> graph, HashMap<Integer,Boolean> visited){
		visited.replace(value1,true);
		boolean result=false;
		//BFS search
		for(Integer integer2:graph.get(value1)){
			if(integer2==value2){
				result=true;
				return result;
			}
		}
		
		if(!result){
			for(Integer integer2:graph.get(value1)){
				if(visited.get(integer2)==false){
					return isLinkedhelper(integer2, value2,graph,visited);
				}
			}			
		}
		
//		Reverse Check
//		for(Integer integer1:graph.get(value2)){
//			if(integer1==value1) result&=true;		
//		}
//		
//		if(!result){
//			for(Integer integer1:graph.get(value2)){
//				isLinked(integer1, value1,graph);
//			}
//		}
		return result;
	}
	
	public void printInput(){
		System.out.println("-----Input-----");
		for(String command:getInput()){
			System.out.println(command);
		}
	}

	public void printOutput(){
		System.out.println("-----Output-----");
		for(String command:getOutput()){
			System.out.println(command);
		}		
	}
	
	public void setInput(List<String> inputs){
		this.input=inputs;
	}
	
	
	public List<String> getInput(){
		return this.input;
	}
	
	public void setOutput(boolean output){
		getOutput().add(String.valueOf(output));
	}
	
	public List<String> getOutput(){
		return this.output;
	}
	
	public HashMap<Integer,Set<Integer>> getGraph(){
		return this.hm;
	}
	
	public void printGraph(){
		System.out.println("----Graph----");
		for(Integer key:getGraph().keySet()){
			System.out.print("Node: "+key+" ");
			System.out.print("Adjacent Nodes: ");
			for(Integer value:getGraph().get(key)){
				System.out.print(value+" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		//Test Case 1:
		List<String> input1=new ArrayList<String>();
		input1.add("add 1 2");
		input1.add("add 2 3");
		input1.add("add 3 4");
		input1.add("is linked 3 1");
		input1.add("remove 3 4");
		input1.add("is linked 1 4");

		Solution sol1=new Solution();
		sol1.MaintainTraverseGraph(input1);
		sol1.printInput();	
		sol1.printOutput();
		sol1.printGraph();
		
		//Test Case 2:
		List<String> input2=new ArrayList<String>();
		input2.add("is linked 1 4");

		Solution sol2=new Solution();
		sol2.MaintainTraverseGraph(input2);
		sol2.printInput();	
		sol2.printOutput();
		sol2.printGraph();

		//Test Case 3:
		List<String> input3=new ArrayList<String>();
		input3.add("remove 1 2");
		input3.add("add 1 2"); 
		input3.add("add 2 3");
		input3.add("add 1 3");
		input3.add("add 3 4");
		input3.add("add 5 6");
		input3.add("is linked 1 1");
		input3.add("is linked 1 4");
		input3.add("is linked 5 6");
		input3.add("is linked 1 6");
		input3.add("remove 1 3");
		input3.add("is linked 4 1");
		input3.add("remove 5 6");
		input3.add("is linked 5 6");

		Solution sol3=new Solution();
		sol3.MaintainTraverseGraph(input3);
		sol3.printInput();	
		sol3.printOutput();
		sol3.printGraph();
		
	}

}
