package energy;
  import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class pair{
	int[] a;
	double fitv=0;
	pair(int n){
		a = new int[n];
	}
}
public class Main {
	 Scanner in_;
	 int no_of_machines=0;
	 int no_of_subtasks=0;
	 double Crossover_rate=0.5f;
	 double Mutation_rate=0.5f;
	 pair[] population;
	 pair sol;
	 double[][] est;/// execution cost matrix
	 double[][] em;/// energy matrix
	 double[][] vol;/// voltage matrix
	 double[][] rel;///communication cost matrix
	 double[] sr;//(sr[i] is the % due which the machine become slower when execute in Vmin)
	 int[] ms_string;
	 int[] ss_string;
	 int[] heft_ms;
	 int[] heft_ss;
	 double worst_makespan;
	 double heft_makespan;
	 double heft_energy;
	 double opt_makespan=0f;
	 double up_bound=0f;
	 double inc_rate = 15f;//--------------------------------
	 int population_size = 100;
	 double max_fitness=99999999999f;///take it as sential value assume that all  fitness value must be below this
	 double EMAX=0f;
	 FileWriter out_;
	 public boolean check=false, check1=false;
	public Main(String file_name) throws IOException{
		
		initialisation_all(this);
		int counter=0;
		while(counter<500){
			for(int i=0; i<population_size; i++){
				int pos1=new Selection(this).select_pos();
				int pos2=new Selection(this).select_pos();
				new Crossover(this).crossover(pos1, pos2);
				new Mutate(this).mutate(pos1);
				new Mutate(this).mutate(pos2);
				population[pos1].fitv = new Fitness(this).get_fitness_value(population[pos1].a);
				population[pos2].fitv = new Fitness(this).get_fitness_value(population[pos2].a);
				//print_population(this);
			}
			for(int i=0; i<population_size; i++){
				if(population[i].fitv < max_fitness){
					//System.out.println(population[i].fitv +"  "+ max_fitness);
					//(new Scanner(System.in)).next();
					max_fitness = population[i].fitv;
					for(int j=0; j<no_of_subtasks; j++){
						sol.a[j] = population[i].a[j];
					}
					sol.fitv = population[i].fitv;
					counter=0;
				}
			}
			counter++;
		}
		
		System.out.println("\nPHASE2: ");
		System.out.print("low_bound:- "+opt_makespan+"\tup_bound:- "+up_bound+"\nEnergy:- "+new Fitness(this).get_fitness_value(sol.a));
		System.out.print("\tphase2_makespan:- "+new Fitness(this).get_make_span(sol.a)+"\nvoltage:-");
		for(int i=0; i<no_of_subtasks; i++){
			System.out.print(sol.a[i]+" ");
		}
		
		////-----------output crc and makespan at phase1 ------------------------
		String[] temp = file_name.split("-");
		String temp1 =temp[temp.length-2];
			FileWriter mp1 = new FileWriter("makespan_phase1.txt", true);
			mp1.write(temp1+"\t"+opt_makespan+"\n");
			mp1.close();
			
		///-------output crc and energy of phase 1
		FileWriter ep1 = new FileWriter("energy_phase1.txt", true);
		ep1.write(temp1+"\t"+EMAX+"\n");
		ep1.close();
		
		///-------output crc and makespan at phase 2-----------------
		FileWriter mp2 = new FileWriter("makespan_phase2.txt", true);
		///----------modified---------
		mp2.write(temp1+"\t"+new Fitness(this).get_make_span(sol.a)+"\n");
		mp2.close();
		
		///-------output crc and energy at phase 2-------------------
		FileWriter ep2 = new FileWriter("energy_phase2.txt", true);
		ep2.write(temp1+"\t"+sol.fitv+"\n");
		ep2.close();

		///------speed up calculate----------------------------------
		new TestingAlgo().calc_energy(this, file_name);
		System.out.println("\n------------------------------------");
		
		///------output heft solution-------------------------------
		int[] a = new int[no_of_subtasks];
		for(int i=0; i<no_of_subtasks; i++){
			a[i]=0;
			ms_string[i] = heft_ms[i];
			ss_string[i] = heft_ss[i];
		}
		heft_makespan = new Fitness(this).get_make_span(a);
		heft_energy = new Fitness(this).get_fitness_value(a);
		///-----------heft speedup------------------------------
		double ratio = (worst_makespan/heft_makespan);
		
		FileWriter hsp = new FileWriter("heft_speedup1.txt", true);
		hsp.write(temp1+"\t"+ratio+"\n");
		hsp.close();
		
		
		System.out.print("Heft Solution: \nms: ");
		for(int i=0; i<no_of_subtasks; i++){
			System.out.print(heft_ms[i]+" ");
		}
		System.out.print("\nss: ");
		for(int i=0; i<no_of_subtasks; i++){
			System.out.print(heft_ss[i]+" ");
		}
		System.out.println("\nheft_makespan: "+heft_makespan+" heft_energy: "+heft_energy);
		System.out.println("heft_speesup:---  "+ratio+"\n\n");
		
		FileWriter hout = new FileWriter("heft_makespan.txt", true);
		hout.write(temp1+"\t"+heft_makespan+"\n");
		hout.close();
		hout = new FileWriter("heft_energy.txt", true);
		hout.write(temp1+"\t"+heft_energy+"\n");
		hout.close();
	}

	private  void initialisation_all(Main m) throws FileNotFoundException {
		in_ = new Scanner(new FileInputStream("input1.txt"));
		//out_ = new DataOutputStream(new FileOutputStream("output.txt"));
		no_of_machines = in_.nextInt();
		no_of_subtasks = in_.nextInt();
		population = new pair[population_size];
		est = new double[no_of_subtasks][no_of_machines];
		em = new double[no_of_machines][2];
		vol = new double[no_of_machines][2];
		ms_string = new int[no_of_subtasks];
		ss_string = new int[no_of_subtasks];
		sr = new double[no_of_machines];
		rel = new double[no_of_subtasks][no_of_subtasks];
		for(int i=0; i<no_of_subtasks; i++){
			for(int j=0; j<no_of_machines; j++){
				est[i][j]=in_.nextDouble();/// computational matrix
			}
		}
		int c2=0;
		for(int i=0; i<no_of_subtasks; i++){
			//System.out.println("Line:---> "+c2++);
			for(int j=0; j<no_of_subtasks; j++){
				rel[i][j]=in_.nextDouble();/// relational matrix
			}
		}
		for(int i=0; i<no_of_machines; i++){
			em[i][0] = in_.nextDouble();/// energy min
			em[i][1] = in_.nextDouble();/// energy max
		}
		for(int i=0; i<no_of_machines; i++){
			vol[i][1] = in_.nextDouble();/// voltage max
			vol[i][0] = in_.nextDouble();/// voltage min
			sr[i] = 100-in_.nextDouble();/// defines slower rate at Vmin level
		}
		for(int i=0; i<no_of_subtasks; i++){
			ms_string[i] = in_.nextInt();/// input ms string(index represent tasks no and value reperesent machine no)
			System.out.print(ms_string[i]+" ");
		}
		
		System.out.println();
		for(int i=0; i<no_of_subtasks; i++){
			ss_string[i] = in_.nextInt();/// input ss string(it represent the order in which substasks executed)
		}
		in_.next();
		///input heft ms and heft ss
		heft_ms = new int[no_of_subtasks];
		for(int i=0; i<no_of_subtasks; i++){
			heft_ms[i]=in_.nextInt();
		}
		heft_ss = new int[no_of_subtasks];
		for(int i=0; i<no_of_subtasks; i++){
			heft_ss[i]=in_.nextInt();
		}
		
		int[] a = new int[no_of_subtasks];
		for(int i=0; i<no_of_subtasks; i++){
			a[i]=0;
		}
		
		opt_makespan =  new Fitness(m).get_make_span(a);
		up_bound = opt_makespan*(1+inc_rate);///we calculate upper bound and inc_rate define above 
		/// calc EMAX
		EMAX = new Fitness(m).get_fitness_value(a);
		
		System.out.println("\nphase1_makespan: "+opt_makespan+"     phase1_energy: "+EMAX);
		
		sol = new pair(no_of_subtasks);
		/// create initial population
		for(int i=0; i<population_size; i++){ 
			population[i] = new pair(no_of_subtasks);
			new Othersol(m).get_sol(i);
			population[i].fitv = new Fitness(m).get_fitness_value(population[i].a);
			if(population[i].fitv < max_fitness){
				max_fitness = population[i].fitv;
				for(int j=0; j<no_of_subtasks; j++){
					sol.a[j] = population[i].a[j];
				}
				sol.fitv = population[i].fitv;
			}
		}
		//print_population(m);
		
	}
	
	public  void print_population(Main m){
		///print generation
		for(int i=0; i<population_size; i++){
			for(int j=0; j<no_of_subtasks; j++){
				System.out.print(population[i].a[j]+" ");
			}
			System.out.println(" : "+population[i].fitv+" : "+new Fitness(m).get_make_span(population[i].a));
			
		}
	}
}
