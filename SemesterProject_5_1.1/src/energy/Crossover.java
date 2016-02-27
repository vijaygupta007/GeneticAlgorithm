package energy;
import java.util.Random;


public class Crossover {
	Main m;
	public Crossover(Main m) {
		this.m = m;
	}
	public void crossover(int pos1, int pos2) {
		double check = ((new Random()).nextDouble()+(new Random()).nextDouble())/2;
		if(check > m.Crossover_rate){
		int try_=100;///if new solution makespan not comes less than upperbound then it try up to max 100 times 
		
		while(try_ != 0){
			int[] a1 = new int[m.no_of_subtasks];
			int[] a2 = new int[m.no_of_subtasks];
			for(int i=0; i<m.no_of_subtasks; i++){
				a1[i] = m.population[pos1].a[i];
				a2[i] = m.population[pos2].a[i];
			}
			int cut_pos = ((new Random()).nextInt(m.no_of_subtasks)+(new Random()).nextInt(m.no_of_subtasks))/2;
			for(int i=cut_pos; i<m.no_of_subtasks; i++){
				int t = a1[i];
				a1[i] = a2[i];
				a2[i] = t;
			}
			double calc1=0f;
			calc1 = new Fitness(m).get_make_span(a1);
			
			double calc2=0f;
			calc2 = new Fitness(m).get_make_span(a2);			
			
			if(calc1 <= m.up_bound && calc2 <= m.up_bound){
				if(new Fitness(m).get_fitness_value(a1) < m.population[pos1].fitv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[pos1].a[i] = a1[i];
					}
				}
				if(new Fitness(m).get_fitness_value(a2) < m.population[pos2].fitv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[pos2].a[i] = a2[i];
					}
				}
				
				return;
			}else if(calc1 <= m.up_bound){
				if(new Fitness(m).get_fitness_value(a1) < m.population[pos1].fitv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[pos1].a[i] = a1[i];
					}
				}
				return;
			}else if(calc2 <= m.up_bound){
				if(new Fitness(m).get_fitness_value(a2) < m.population[pos2].fitv){
					for(int i=0; i<m.no_of_subtasks; i++){
						m.population[pos2].a[i] = a2[i];
					}
				}
				return;
			}
			try_--;
		}
		}
	}

}
