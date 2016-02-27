package energy;
import java.util.Random;


public class Othersol {
	Main m;
	public Othersol(Main m) {
		this.m = m;
	}
	public void get_sol(int pos) {
		int[] a = new int[m.no_of_subtasks];
		int try_=30;
		while(try_ != 0){
			for(int i=0; i<m.no_of_subtasks; i++){
				a[i]=(new Random()).nextInt(11);
			}
			double calc=0;
			calc = new Fitness(m).get_make_span(a);
			
			if(calc <= m.up_bound){
				for(int i=0; i<m.no_of_subtasks; i++){
					m.population[pos].a[i] = a[i];
				}
				return;
			}
			try_--;
		}
		for(int i=0; i<m.no_of_subtasks; i++){
			m.population[pos].a[i]=0;
		}
	}

}
