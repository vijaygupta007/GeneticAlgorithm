  import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

class pair{
	int[] a;
	float fitv=0;
	pair(int n){
		a = new int[n];
	}
}
public class Main {
	static Scanner in_;
	static int no_of_machines=0;
	static int no_of_subtasks=0;
	static float Crossover_rate=0.5f;
	static float Mutation_rate=0.5f;
	static pair[] population;
	static pair sol;
	static float[][] est;/// execution cost matrix
	static float[][] em;/// energy matrix
	static float[][] vol;/// voltage matrix
	static float[][] rel;///communication cost matrix
	static float[] sr;//(sr[i] is the % due which the machine become slower when execute in Vmin)
	static int[] ms_string;
	static int[] ss_string;
	static float opt_makespan=0f;
	static float up_bound=0f;
	static float inc_rate = 0.3f;
	static int population_size = 20;
	static float max_fitness=9999999f;///take it as sential value assume that all  fitness value must be below this
	static float EMAX=0f;
	Main() throws FileNotFoundException{
		sol = new pair(Main.no_of_subtasks);
		initialisation_all();
		int counter=0;
		while(counter<100){
			for(int i=0; i<population_size; i++){
				int pos1=Selection.select_pos();
				int pos2=Selection.select_pos();
				Crossover.crossover(pos1, pos2);
				Mutate.mutate(pos1);
				Mutate.mutate(pos2);
				population[pos1].fitv = Fitness.get_fitness_value(Main.population[pos1].a);
				population[pos2].fitv = Fitness.get_fitness_value(Main.population[pos2].a);
				print_population();
			}
			for(int i=0; i<population_size; i++){
				if(population[i].fitv < max_fitness){
					//System.out.println(population[i].fitv +"  "+ max_fitness);
					//(new Scanner(System.in)).next();
					max_fitness = population[i].fitv;
					sol.a = population[i].a;
					sol.fitv = population[i].fitv;
					counter=0;
				}
			}
			counter++;
		}
		System.out.println("solution: ");
		System.out.println("EMAX: "+EMAX+" ormaksp: "+Main.opt_makespan+" upbound: "+up_bound);
		for(int i=0; i<Main.no_of_subtasks; i++){
			System.out.print(sol.a[i]+" ");
		}
		System.out.print(" E: "+sol.fitv);
		System.out.println(" makespan: "+Fitness.get_make_span(sol.a));
		
	}
	public static void main(String args[]) throws FileNotFoundException{
		new Main();	
	}

	private static void initialisation_all() throws FileNotFoundException {
		in_ = new Scanner(new FileInputStream("input1.txt"));
		//out_ = new DataOutputStream(new FileOutputStream("output.txt"));
		no_of_machines = in_.nextInt();
		no_of_subtasks = in_.nextInt();
		population = new pair[population_size];
		est = new float[no_of_subtasks][no_of_machines];
		em = new float[no_of_machines][2];
		vol = new float[no_of_machines][2];
		ms_string = new int[no_of_subtasks];
		ss_string = new int[no_of_subtasks];
		sr = new float[no_of_machines];
		rel = new float[no_of_subtasks][no_of_subtasks];
		for(int i=0; i<no_of_subtasks; i++){
			for(int j=0; j<no_of_machines; j++){
				est[i][j]=in_.nextFloat();/// computational matrix
				//System.out.print(est[i][j]+" ");
			}
			//System.out.println();
		}
		for(int i=0; i<no_of_subtasks; i++){
			for(int j=0; j<no_of_subtasks; j++){
				rel[i][j]=in_.nextFloat();/// relational matrix
				//System.out.print(rel[i][j]+" ");
			}
			//System.out.println();
		}
		
		for(int i=0; i<no_of_machines; i++){
			em[i][0] = in_.nextFloat();/// energy min
			em[i][1] = in_.nextFloat();/// energy max
			//System.out.println(em[i][0]+" "+em[i][1]);
		}
		for(int i=0; i<no_of_machines; i++){
			vol[i][0] = in_.nextFloat();/// voltage min
			vol[i][1] = in_.nextFloat();/// voltage max
			sr[i] = in_.nextFloat();/// defines slower rate at Vmin level
			//System.out.println(vol[i][0]+" "+vol[i][1]+" "+sr[i]);
		}
		
		for(int i=0; i<no_of_subtasks; i++){
			ms_string[i] = in_.nextInt();/// input ms string(index represent tasks no and value reperesent machine no)
			//System.out.print(ms_string[i]+" ");
		}
		
		for(int i=0; i<no_of_subtasks; i++){
			ss_string[i] = in_.nextInt();/// input ss string(it represent the order in which substasks executed)
			//System.out.print(ss_string[i]+" ");
		}
		opt_makespan = in_.nextInt();
		
		up_bound = opt_makespan*(1+inc_rate);///we calculate upper bound and inc_rate define above 
		//System.out.println("\n"+opt_makespan+ "  up_bound: "+up_bound);
		/// calc EMAX
		int[] a = new int[Main.no_of_subtasks];
		for(int i=0; i<Main.no_of_subtasks; i++){
			a[i]=0;
		}
		EMAX = Fitness.get_fitness_value(a);
		System.out.println("EMAX: "+EMAX);
		
		/// create initial population
		for(int i=0; i<population_size; i++){
			population[i] = new pair(no_of_subtasks);
			Othersol.get_sol(i);
			population[i].fitv = Fitness.get_fitness_value(Main.population[i].a);
			if(population[i].fitv < max_fitness){
				max_fitness = population[i].fitv;
				sol.a = population[i].a;
				sol.fitv = population[i].fitv;
			}
		}
		print_population();
	}
	
	public static void print_population(){
		///print generation
		for(int i=0; i<population_size; i++){
			for(int j=0; j<no_of_subtasks; j++){
				System.out.print(population[i].a[j]+" ");
			}
			System.out.println(" : "+population[i].fitv+" : "+Fitness.get_make_span(population[i].a));
			
		}
	}
}
