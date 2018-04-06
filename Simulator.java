import java.util.ArrayList;
import java.util.Iterator;
/*Simulator.java
*Created on 2018年3月25日
*/

public class Simulator {
	private int numberOfProcessor;
	private ArrayList<String> decisionList=new ArrayList<>();
	{
		for(int count=0;count<10;count++){
			decisionList.add("s"+count);
		}
	};
	private long numOfMessage=0;
	private ArrayList<Processor> processorList=new ArrayList<>();
	private int crash;
	private int brokenProcessor;
	public Simulator(int n, int f){
		this.numberOfProcessor=n;
		this.brokenProcessor=f;
	}
	
	public void start(){
		input();
		OptFloodSet();
		output();
	}
	
	private void input(){
		for(int i=1;i<=numberOfProcessor;i++){
			int random=(int) (Math.random()*(decisionList.size()-1));
			String input=decisionList.get(random);
			Processor processor=new Processor("Processor "+i, input);
			processorList.add(processor);
		}
	}
	
	private void OptFloodSet(){
		for(int i=0;i<=brokenProcessor;i++){
			for(Processor processor: processorList){
				for(int n=0;n<processorList.size();n++){
					processorList.get(n).setPrevious();
					if(!processor.getName().equals(processorList.get(n).getName())){
						changeBroken(processorList.get(n));
						if(processorList.get(n).workWell()
								&&processorList.get(n).getNewInfo()==true){
							Iterator<String> itr=processorList.get(n).getPrevious().iterator();
							while(itr.hasNext()){
								if(processorList.get(n).workWell()){
									processor.getSet().add(itr.next());
								}else{
									break;
								}
								changeBroken(processorList.get(n));
							}
							numOfMessage++;
						}
					}
				}
			}
			for(Processor processor: processorList){
				changeBroken(processor);
				if(processor.workWell()&&processor.isSimilar()){
					processor.setNewInfor(false);
				}else{
					processor.setNewInfor(true);
				}
			}
		}
	}
	
	private void output(){
		for(Processor processor: processorList){
			Iterator<String> itr=processor.getSet().iterator();
			if(processor.getSize()==1){
				processor.setDecision(itr.next());
			}else{
				processor.setDecision(decisionList.get(0));
			}
			if(processor.workWell()){
				System.out.println(processor.toString());
			}
		}
	}
	
	private void changeBroken(Processor p){
		int probablity=(int) (Math.random()*100);
		if(probablity<=1&&crash<brokenProcessor){
			p.setState();
			crash++;
		}
	}
	
	public long getNumOfMessage(){
		return numOfMessage;
	}
}
