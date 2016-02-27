 package makespan;
import java.util.Random;

public class Crossover1 {
	Main m;
	Crossover1(Main m){
		this.m = m;
	}
	
	public void crossover(int p1, int p2){
		if ((new Random()).nextInt(100) < (int) m.crossover_rate * 100) {
			return;
		}
		///store original value
		int ms1[]=new int[m.no_of_subtasks];
		for (int i = 0; i < m.no_of_subtasks; i++) {
			ms1[i] = m.population[p1].ms[i];
		}
		int ms2[]=new int[m.no_of_subtasks];
		for (int i = 0; i < m.no_of_subtasks; i++) {
			ms2[i] = m.population[p2].ms[i];
		}
		
		int try_=1;
		int t;
		while(try_-- != 0){
			for (int i = 0; i < m.no_of_subtasks; i++) {
				m.population[p1].ms[i]=ms1[i];
			}
			for (int i = 0; i < m.no_of_subtasks; i++) {
				m.population[p2].ms[i]=ms2[i];
			}
			t = ((new Random()).nextInt(m.no_of_subtasks) + (new Random())
					.nextInt(m.no_of_subtasks)) / 2;
			
			for (int i = t; i < m.no_of_subtasks; i++) {
				int temp = m.population[p1].ms[i];
				m.population[p1].ms[i] = m.population[p2].ms[i];
				m.population[p2].ms[i] = temp;
			}
			
			int child1 = new Fitnessvalue(m).getfitnessval(p1);
			int child2 = new Fitnessvalue(m).getfitnessval(p2);
			if(child1 < m.population[p1].fv || child2 < m.population[p2].fv){
				if(child1 > m.population[p1].fv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[p1].ms[i]=ms1[i];
					}
					
				}else{
					m.population[p1].fv = child1;
				}
				if(child2 > m.population[p2].fv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[p2].ms[i]=ms2[i];
					}
				}else{
					m.population[p2].fv = child2;
				}
				return;
			}
			
		}
		
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[p1].ms[i]=ms1[i];
		}
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[p2].ms[i]=ms2[i];
		}
	}

}
