package energy;

import java.io.FileWriter;
import java.io.IOException;

public class TestingAlgo {
	public void calc_energy(Main m, String s) throws IOException{ 
		double min_=999999999f, sol_makespan;
		sol_makespan = new Fitness(m).get_make_span(m.sol.a);
		int[] a=new int[m.no_of_subtasks];
		for(int i=0; i<m.no_of_machines; i++){
			for(int j=0; j<m.no_of_subtasks; j++){
				m.ms_string[j] = i;
				a[i]=0;
			}
			double temp1 = new Fitness(m).get_make_span(a);
			if(min_ > temp1){
				min_ = temp1;
			}
		}
		m.worst_makespan = min_;
		double ratio = ((double)min_*1.0/sol_makespan);
		System.out.println("\nspeed up_phase2 :----  "+ratio);
		String[] temp = s.split("-");
		FileWriter out_ = new FileWriter("speedup_phase2.txt", true);
		out_.write(temp[temp.length-2]+"\t"+ratio+"\n");
		out_.close();
	}

}
