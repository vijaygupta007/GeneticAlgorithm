 package makespan;
import java.util.Random;

public class Crossover {
	Main m;
	Crossover(Main m){
		this.m = m;
	}
	
	public void crossover(int p1, int p2){
		if ((new Random()).nextInt(100) < (int) m.crossover_rate * 100) {
			return;
		}
		///store original value
		int[] a1 = new int[m.no_of_subtasks];
		for (int i = 0; i < m.no_of_subtasks; i++) {
			a1[i] = m.population[p1].ss[i];
		}
		int[] a2 = new int[m.no_of_subtasks];
		for (int i = 0; i < m.no_of_subtasks; i++) {
			a2[i] = m.population[p2].ss[i];
		}
		int try_=1;
		int t;
		boolean[] visit = new boolean[m.no_of_subtasks];
		while(try_-- != 0){
			for (int i = 0; i < m.no_of_subtasks; i++) {
				m.population[p1].ss[i] = a1[i];
			}
			for (int i = 0; i < m.no_of_subtasks; i++) {
				m.population[p2].ss[i] = a2[i];
			}
			t = ((new Random()).nextInt(m.no_of_subtasks) + (new Random())
					.nextInt(m.no_of_subtasks)) / 2;
			
			for(int i=0; i<m.no_of_subtasks; i++){
				visit[i]=false;
				//System.out.print(a2[i]+"-");
			}
			for (int i = t; i < m.no_of_subtasks; i++) {
				visit[a1[i]] = true;
				
			}
			//System.out.println();
			int k = t;
			for (int i = 0; i < m.no_of_subtasks; i++) {
				if (visit[a2[i]]) {
					//System.out.println(m.no_of_subtasks+"->" +a2[i]+" k: "+k+" t: "+t);
					m.population[p1].ss[k++] = a2[i];
					
				}
			}
			
			for(int i=0; i<m.no_of_subtasks; i++){
				visit[i]=false;
			}
			
			for (int i = t; i < m.no_of_subtasks; i++) {
				visit[a2[i]] = true;

			}
			k = t;
			for (int i = 0; i < m.no_of_subtasks; i++) {
				if (visit[a1[i]]) {
					m.population[p2].ss[k++] = a1[i];
				}
			}
			
			int child1 = new Fitnessvalue(m).getfitnessval(p1);
			int child2 = new Fitnessvalue(m).getfitnessval(p2);
			if(child1 < m.population[p1].fv || child2 < m.population[p2].fv){
				if(child1 > m.population[p1].fv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[p1].ss[i]=a1[i];
					}
					
				}else{
					m.population[p1].fv = child1;
				}
				if(child2 > m.population[p2].fv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[p2].ss[i]=a2[i];
					}
				}else{
					m.population[p2].fv = child2;
				}
				return;
			}
			
		}
		
		
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[p1].ss[i]=a1[i];
		}
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[p2].ss[i]=a2[i];
		}
	}

}
