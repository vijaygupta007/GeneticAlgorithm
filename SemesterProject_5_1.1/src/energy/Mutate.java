package energy;
import java.util.Random;


public class Mutate {
	Main m;
	public Mutate(Main m) {
		this.m = m;
	}
	public void mutate(int pos1) {
		double check = ((new Random()).nextDouble()+(new Random()).nextDouble())/2;
		if(check >= m.Mutation_rate){
		int try_= 30;
		int[] a1 = new int[m.no_of_subtasks];
		for(int i=0; i<m.no_of_subtasks; i++){
			a1[i] = m.population[pos1].a[i];
		}
		while(try_ != 0){
			int pos = (new Random()).nextInt(m.no_of_subtasks);
			int prev=a1[pos];
			a1[pos] = (new Random()).nextInt(11);
			
			double calc1=0;
			calc1 = new Fitness(m).get_make_span(a1);
			
			if(calc1 <= m.up_bound){
				if(new Fitness(m).get_fitness_value(a1) < m.population[pos1].fitv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[pos1].a[i] = a1[i];
					}
				}
				return;
			}
			a1[pos]=prev;
			try_--;
		}
		}
	}

}
