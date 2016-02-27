package makespan;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Fitnessvalue {
	@SuppressWarnings("unchecked")
	Main m;
	Fitnessvalue(Main m){
		this.m = m;
	}
	public int getfitnessval(int pos){
		int[] s_t = new int[m.no_of_subtasks];
		int[] e_t = new int[m.no_of_subtasks];
		int temp, max_=0;
		int[] met = new int[m.no_of_machines];
		for(int i=0; i<m.no_of_subtasks; i++){
			temp = m.population[pos].ss[i];
			int maxx=-1;
			for(int j=0; j<i; j++){///check for all tasks for which i depends on in ss string(the index must be lower in ss string)
				if(m.rel[m.population[pos].ss[j]][temp] != 0){
					int task =  m.population[pos].ss[j];
					maxx = max(maxx, e_t[m.population[pos].ss[j]]+((m.population[pos].ms[temp]==m.population[pos].ms[task])?0:m.rel[task][temp]));
					
				}
			}
			
			s_t[temp] = met[m.population[pos].ms[temp]];
			s_t[temp] = max(maxx, s_t[temp]);
			e_t[temp] = s_t[temp]+m.est[temp][m.population[pos].ms[temp]];
			met[m.population[pos].ms[temp]] = e_t[temp];
			max_ = max(e_t[temp], max_);
			
		}
	
		return max_;
		
	}
	public void test(){
		int[] s_t = new int[m.no_of_subtasks];
		int[] e_t = new int[m.no_of_subtasks];
		int temp, max_=0;
		int[] met = new int[m.no_of_machines];
		for(int i=0; i<m.no_of_subtasks; i++){
			temp = m.answ.ss[i];
			int maxx=-1;
			for(int j=0; j<i; j++){///check for all tasks for which i depends on in ss string(the index must be lower in ss string)
				if(m.rel[m.answ.ss[j]][temp] != 0){
					int task =  m.answ.ss[j];
					maxx = max(maxx, e_t[m.answ.ss[j]]+((m.answ.ms[temp]==m.answ.ms[task])?0:m.rel[task][temp]));
					
				}
			}
			
			s_t[temp] = met[m.answ.ms[temp]];
			s_t[temp] = max(maxx, s_t[temp]);
			e_t[temp] = s_t[temp]+m.est[temp][m.answ.ms[temp]];
			met[m.answ.ms[temp]] = e_t[temp];
			max_ = max(e_t[temp], max_);
			
		}
		System.out.println("TaskNo        StartTime        EndTime");
		for(int i=0; i<m.no_of_subtasks; i++){
			System.out.println(i+"  ->           "+s_t[i]+"            "+e_t[i]);
		}
	}

	private static Integer max(Integer p1, Integer p2) {
		if(p1 >= p2){
			return p1;
		}else{
			return p2;
		}
	}
}
