import java.util.Random;


public class Mutate {

	public static void mutate(int pos1) {
		float check = ((new Random()).nextFloat()+(new Random()).nextFloat())/2;
		if(check >= Main.Mutation_rate){
		int try_= 1;
		int[] a1 = new int[Main.no_of_subtasks];
		a1 = Main.population[pos1].a;
		while(try_ != 0){
			int pos = (new Random()).nextInt(Main.no_of_subtasks);
			int prev=a1[pos];
			a1[pos] = (new Random()).nextInt(11);
			
			float calc1=0;
			calc1 = Fitness.get_make_span(a1);
			
			if(calc1 <= Main.up_bound){
				Main.population[pos1].a = a1;
				return;
			}
			a1[pos]=prev;
			try_--;
		}
		}
	}

}
