            package makespan;
import java.util.Random;


public class Permuation {
	Main m;
	Permuation(Main m){
		this.m = m;
	}
	public pair getpermutate() {
		pair p=new pair(m.no_of_subtasks);
		///for ss part of chromosome
			int v = (new Random().nextInt(m.no_of_subtasks) + new Random().nextInt(m.no_of_subtasks))/2;
			int ub, lb;
			lb=m.no_of_subtasks;
			for(int i=v+1; i<m.no_of_subtasks; i++){
				if(m.rel[m.basic_ss[v]][m.basic_ss[i]] != 0){
					lb = i;
					break;
				}
			}
			ub=-1;
			for(int i=v; i>=0; i--){
				if(m.rel[m.basic_ss[i]][m.basic_ss[v]] != 0){
					ub=i;
					break;
				}
			}

		
		int new_pos = ub + new Random().nextInt(lb - (ub) -1)+1; 
		for(int i=0; i<m.no_of_subtasks; i++){
			if(i < min(new_pos, v) || i > max(new_pos, v)){
				p.ss[i] = m.basic_ss[i];
			}
		}
		
		if(new_pos > v){
			p.ss[new_pos] = m.basic_ss[v];
			for(int i=v; i<new_pos; i++){
				p.ss[i] = m.basic_ss[i+1];
			}
			
		}else if(new_pos < v){
			p.ss[new_pos] = m.basic_ss[v];
			for(int i=new_pos+1; i<=v; i++){
				p.ss[i] = m.basic_ss[i-1];
			}
		}else{
			p.ss[new_pos] = m.basic_ss[new_pos];
		}
		///------------initialise ms(creating ms from basic_ms)------------------
		if((new Random()).nextInt(100) > 20){
			for(int i=0; i<m.no_of_subtasks; i++){
				p.ms[i] = m.heft_ms[i];
			}
			if((new Random()).nextInt(100) > 40){
				int pos1=new Random().nextInt(m.no_of_subtasks);
				int pos2=new Random().nextInt(m.no_of_subtasks);
				///swap any two position
				int temp=p.ms[pos1];
				p.ms[pos1]=p.ms[pos2];
				p.ms[pos2]=temp;
			}
		}else{
			for(int i=0; i<m.no_of_subtasks; i++){
				p.ms[i] = (new Random().nextInt(m.no_of_machines));
			}
		}
		return p;
	}

	private static int max(int new_pos, int v) {
		if(new_pos > v){
			return new_pos;
		}else{
			return v;
		}
	}

	private static int min(int new_pos, int v) {
		if(new_pos < v){
			return new_pos;
		}else{
			return v;
		}
	}


}
