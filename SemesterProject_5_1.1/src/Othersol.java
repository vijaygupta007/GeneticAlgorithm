import java.util.Random;


public class Othersol {
	public static void get_sol(int pos) {
		int[] a = new int[Main.no_of_subtasks];
		int try_=100;
		while(try_ != 0){
			for(int i=0; i<Main.no_of_subtasks; i++){
				a[i]=(new Random()).nextInt(11);
			}
			float calc=0;
			calc = Fitness.get_make_span(a);
			
			if(calc <= Main.up_bound){
				Main.population[pos].a = a;
				return;
			}
			try_--;
		}
		for(int i=0; i<Main.no_of_subtasks; i++){
			Main.population[pos].a[i]=0;
		}
	}

}
