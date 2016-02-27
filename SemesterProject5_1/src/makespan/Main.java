package makespan;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

class pair {
	public int[] ms;
	public int[] ss;
	public Integer fv = 0;

	public pair(int n) {
		ms = new int[n];
		ss = new int[n];
	}
}

public class Main {
	public int population_size = 100;
	public float mutation_rate = 0.5f;
	public float crossover_rate = 0.5f;
	public int no_of_subtasks;
	public int no_of_machines;
	public int min_fv = 999999999;
	public int counter = 0;
	public pair answ = null;
	public Scanner in_;
	public Scanner inheft_;
	public PrintStream out_;
	public pair[] population;
	public int[] basic_ss;
	public int[] heft_ms;
	public int[] heft_ss;
	public int[][] est;
	public int[][] rel;
	public Main(String file_name) throws IOException {
		out_ = new PrintStream(new FileOutputStream("input1.txt"));
		initialise_all(file_name, this);
		// /main operations of genetic algo
		
		while (counter < 700) {// /stoping condition if minimum fitnessvalue not
								// change for 20 generation
			for (int i = 0; i < population_size; i++) {
				int p1 = new Selection(this).select_pos();
				int p2 = new Selection(this).select_pos();
				// /-------crossover--------------
				new Crossover(this).crossover(p1, p2);
				// /-------mutation---------------
			/*	new Mutation(this).mutation(p1);
				new Mutation(this).mutation(p2);
				 
				p1 = new Selection(this).select_pos();
				p2 = new Selection(this).select_pos();
				*/
				new Crossover1(this).crossover(p1, p2);
				// /-------mutation---------------
				new Mutation(this).mutation(p1);
				new Mutation(this).mutation(p2);
				// /------calc fitnessvalue--------
				population[p1].fv = new Fitnessvalue(this).getfitnessval(p1);
				population[p2].fv = new Fitnessvalue(this).getfitnessval(p2);
			}
			for (int i = 0; i < population_size; i++) {
				if (population[i].fv < min_fv) {///update min_fv value( makespan is fv)
					min_fv = population[i].fv;
					//System.out.println("makespan_reduces--->"+min_fv);
					///-----------strore this optimal value-----------
					for (int k = 0; k < no_of_subtasks; k++) {
						answ.ss[k] = population[i].ss[k];
					}
					for (int k = 0; k < no_of_subtasks; k++) {
						answ.ms[k] = population[i].ms[k];
					}
					answ.fv = min_fv;
					counter = 0;
					///----------------------------------------------
				}
			}
			
			///----print population-------
			//print_population();
			
			counter++;
		}
		
		//new Fitnessvalue(this).test();
	
		System.out.println("PHASE1:-");
		System.out.print("matchstring:- ");
		for (int i = 0; i < no_of_subtasks; i++) {
			System.out.print(answ.ms[i] + " ");
			out_.print(answ.ms[i]+" ");
		}
		System.out.print("\nshedulestring:- ");
		for (int i = 0; i < no_of_subtasks; i++) {
			System.out.print(answ.ss[i] + " ");
			out_.print(answ.ss[i]+" ");
		}
		out_.println();
		//System.out.println(" : " + answ.fv);
		out_.println(answ.fv);
		
		///put heft ms and ss in input1.txt
		for(int i=0; i<no_of_subtasks; i++){
			out_.print(heft_ms[i]+" ");
		}
		out_.println();
		for(int i=0; i<no_of_subtasks; i++){
			out_.print(heft_ss[i]+" ");
		}
		out_.println();
		
		in_.close();
		out_.close();
		new TestingAlgo().calc_makespan(this, file_name);
		
		
		
		
		
	}

	private void initialise_all(String file_name, Main m) throws IOException {
		in_ = new Scanner(new FileInputStream(file_name));
		
		
		///------------------taking input from file--------------------
		while(true){
			if(in_.next().compareTo("No_of_Processor") == 0){
				no_of_machines = in_.nextInt();
				break;
			}
		}
		
		out_.print(no_of_machines+" ");
		while(true){
			if(in_.next().compareTo("t_nst") == 0){
				no_of_subtasks = in_.nextInt();
				break;
			}
		}
		out_.println(no_of_subtasks);
		basic_ss = new int[no_of_subtasks];
		answ = new pair(no_of_subtasks);
		population = new pair[population_size];
		est = new int[no_of_subtasks][no_of_machines];
		rel = new int[no_of_subtasks][no_of_subtasks];
		for (int i = 0; i < no_of_subtasks; i++) {
			in_.next();
			for (int j = 0; j < no_of_machines; j++) {
				est[i][j] = in_.nextInt();
			}
		}
		int x, y, w;
		while (true) {
			if (in_.next().compareTo("task_end") == 0) {
				in_.nextLine();
				break;
			}
			x = in_.nextInt();
			y = in_.nextInt();
			w = in_.nextInt();
			rel[x][y] = w;
		}
		
		
		//System.out.println("-----------estimated cost------------");
		for (int i = 0; i < no_of_subtasks; i++) {
			for (int j = 0; j < no_of_machines; j++) {
				//System.out.print(est[i][j] + " ");
				out_.print(est[i][j]+" ");///write to output file
			}
			out_.println();
			//System.out.println();
		}
		
		
		//System.out.println("----------communication cost----------");
		for (int i = 0; i < no_of_subtasks; i++) {
			for (int j = 0; j < no_of_subtasks; j++) {
				//System.out.print(rel[i][j] + " ");
				out_.print(rel[i][j]+" ");///write to output file 
			}
			out_.println();
			//System.out.println();
		}
	 
		for(int i=0; i<no_of_machines; i++){
			String[] s = in_.nextLine().split(" ");
			if(file_name.contains("Ran")){
				if(s[2]=="75"){
					s[1]="12";
				}else if(s[2]=="47"){
					s[1]="7";
				}else if(s[2]=="44"){
					s[1]="6";
				}
			}
			out_.println(s[1]+" "+s[2]);
		}
		while(true){
			String s1=in_.nextLine();
			//System.out.println(s1);
			if(s1.contains("Energy-End")){		
				break;
			}
		}
		
		int n1;
		n1 = Integer.parseInt(in_.nextLine().split(" ")[1]);
		
		String[][] vol = new String[n1][3];
			for(int i=0; i<n1; i++){
				int n2=Integer.parseInt(in_.nextLine().split(" ")[1]);
				for(int j=0; j<n2; j++){
					String[] s=in_.nextLine().split(" ");
					if(j==0 || j==n2-1){
						if(j==0){
							vol[i][0] = s[1];
						}else{
							vol[i][1] = s[1];
							vol[i][2] = s[2];
						}
					}
				}
			}
		int k=0;
			for(int i=0; i<no_of_machines; i++){
				out_.println(vol[k][0]+" "+vol[k][1]+" "+vol[k][2]);
				//System.out.println(vol[k][0]+" "+vol[k][1]+" "+vol[k][2]);
				k=(k+1)%n1;
			}
			//(new Scanner(System.in)).next();
		// /-----creating basic string for population-----
		new Toposort(this).toposort();
		
		///put ms string obtained from heft to basic_ms
		String heft_sol_filename = file_name.split(".graph")[0]+".graphsch";
		inheft_ = new Scanner(new FileInputStream(heft_sol_filename));
		inheft_.nextLine();
		///for heft_ss
		String[] temp1 = inheft_.nextLine().split(" ");
		heft_ss=new int[no_of_subtasks];
		for(int i=0; i<no_of_subtasks; i++){
			heft_ss[no_of_subtasks-1-i]= Integer.parseInt(temp1[i]);
		}
		///for heft_ms
		String[] temp2 = inheft_.nextLine().split(" ");
		heft_ms=new int[no_of_subtasks];
		for(int i=0; i<no_of_subtasks; i++){
			heft_ms[i]= Integer.parseInt(temp2[i]);
		}
		inheft_.close();
		
		// /-----creating initial population------------
		population[0] = new pair(no_of_subtasks);
		for(int i=0; i<no_of_subtasks; i++){
			population[0].ms[i]=heft_ms[i];
			population[0].ss[i]=heft_ss[i];
		}
		population[0].fv = new Fitnessvalue(m).getfitnessval(0);
		for (int i = 1; i < population_size; i++) {
			if(i==population_size/2){
				for(int j=0; j<no_of_subtasks; j++){
					basic_ss[j]=heft_ss[j];
				}
			}
			population[i] = new Permuation(this).getpermutate();///gives permutation of basic string
			population[i].fv = new Fitnessvalue(m).getfitnessval(i);///calc fitness value
		}
		///-----print poplation-------------
		//print_population();
		
	}
	public void print_population(){
		for (int i = 0; i < population_size; i++) {
			for (int j = 0; j < no_of_subtasks; j++) {
				System.out.print(population[i].ms[j] + " ");
			}
			System.out.print(" : ");
			for (int j = 0; j < no_of_subtasks; j++) {
				System.out.print(population[i].ss[j] + " ");
			}
			System.out.println(" : " + population[i].fv);
		}
	}
}
