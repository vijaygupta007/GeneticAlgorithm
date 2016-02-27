  package makespan;

import java.io.FileWriter;
import java.io.IOException;

public class TestingAlgo {
	public void calc_makespan(Main m, String s) throws IOException{
		int  t=0, min_=999999999;
		for(int i=0; i<m.no_of_machines; i++){
			for(int j=0; j<m.no_of_subtasks; j++){
				m.population[0].ms[j]=i;
			}
			m.population[0].ss =  m.answ.ss;
			t = new Fitnessvalue(m).getfitnessval(0);
			if(t < min_){
				min_ = t;
			}
		}
		double ratio = (((double)min_*1.0)/m.answ.fv);
		String[] temp = s.split("-");
		System.out.print("\nspeed up_phase1 :---  "+"  ==  "+ratio);
		
		FileWriter out_ = new FileWriter("speedup_phase1.txt", true);
		out_.write(temp[temp.length-2]+"\t"+ratio+"\n");
		out_.close();
	}
}
