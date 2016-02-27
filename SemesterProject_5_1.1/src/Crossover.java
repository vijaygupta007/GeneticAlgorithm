import java.util.Random;


public class Crossover {
	public static void crossover(int pos1, int pos2) {
		float check = ((new Random()).nextFloat()+(new Random()).nextFloat())/2;
		if(check > Main.Crossover_rate){
		int try_=1;///if new solution makespan not comes less than upperbound then it try up to max 100 times 
		
		while(try_ != 0){
			int[] a1 = new int[Main.no_of_subtasks];
			int[] a2 = new int[Main.no_of_subtasks];
			for(int i=0; i<Main.no_of_subtasks; i++){
				a1[i] = Main.population[pos1].a[i];
				a2[i] = Main.population[pos2].a[i];
			}
			int cut_pos = ((new Random()).nextInt(Main.no_of_subtasks)+(new Random()).nextInt(Main.no_of_subtasks))/2;
			for(int i=cut_pos; i<Main.no_of_subtasks; i++){
				int t = a1[i];
				a1[i] = a2[i];
				a2[i] = t;
			}
			float calc1=0f;
			calc1 = Fitness.get_make_span(a1);
			
			float calc2=0f;
			calc1 = Fitness.get_make_span(a2);			
			
			if(calc1 <= Main.up_bound && calc2 <= Main.up_bound){
				for(int i=0; i<Main.no_of_subtasks; i++){
				   	Main.population[pos1].a[i]=a1[i];
				    Main.population[pos2].a[i]=a2[i];
				}
				return;
			}else if(calc1 <= Main.up_bound){
				for(int i=0; i<Main.no_of_subtasks; i++){
					Main.population[pos1].a[i]=a1[i];
				}
			}else if(calc2 <= Main.up_bound){
				for(int i=0; i<Main.no_of_subtasks; i++){
					 Main.population[pos2].a[i]=a2[i];
				}
			}
			try_--;
		}
		}
	}

}
