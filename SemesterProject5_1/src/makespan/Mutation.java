package makespan;
import java.util.Random;

public class Mutation {
	Main m;
	Mutation(Main m){
		this.m = m;
	}
	public void mutation(int pos) {
		if ((new Random()).nextInt(100) < (int) m.mutation_rate * 100) {
			return;
		}
		///store original data
		int[] a=new int[m.no_of_subtasks];
		for(int i=0; i<m.no_of_subtasks; i++){
			a[i] = m.population[pos].ss[i];
		}
		int[] ms=new int[m.no_of_subtasks];
		for(int i=0; i<m.no_of_subtasks; i++){
			ms[i] = m.population[pos].ms[i];
		}
		int try_=1;
		while(try_-- != 0){
	
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[pos].ss[i]=a[i];
		}
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[pos].ms[i]=ms[i];
		}
		
		// /for ss part
		int v = (new Random().nextInt(m.no_of_subtasks) + new Random()
				.nextInt(m.no_of_subtasks)) / 2;
		int ub, lb;
		lb = m.no_of_subtasks;
		for (int i = v + 1; i < m.no_of_subtasks; i++) {
			if (m.rel[m.population[pos].ss[v]][m.population[pos].ss[i]] != 0) {
				lb = i;
				break;
			}
		}
		ub = -1;
		for (int i = v; i >= 0; i--) {
			if (m.rel[m.population[pos].ss[i]][m.population[pos].ss[v]] != 0) {
				ub = i;
				break;
			}
		}

		int new_pos = ub + new Random().nextInt(lb - (ub) - 1) + 1;
		int temp = m.population[pos].ss[v];
		if (new_pos > v) {
			for (int i = v; i < new_pos; i++) {
				m.population[pos].ss[i] = m.population[pos].ss[i + 1];
			}
		} else if (new_pos < v) {
			for (int i = v; i > new_pos; i--) {
				m.population[pos].ss[i] = m.population[pos].ss[i - 1];
			}

		}
		m.population[pos].ss[new_pos] = temp;

		// /for ms part
		temp = m.population[pos].ms[new_pos];
		m.population[pos].ms[new_pos] = m.population[pos].ms[v];
		m.population[pos].ms[v] = temp;
		
		int newfv = new Fitnessvalue(m).getfitnessval(pos);
		if(newfv < m.population[pos].fv){
			m.population[pos].fv = newfv;
			return;
		}
		}
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[pos].ss[i]=a[i];
		}
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[pos].ms[i]=ms[i];
		}
	}
}
