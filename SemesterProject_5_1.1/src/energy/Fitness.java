package energy;



public class Fitness{
	Main m;
	public Fitness(Main m) {
		this.m = m;
	}
	public double get_fitness_value(int[] a){
		double[] s_t = new double[m.no_of_subtasks];
		double[] e_t = new double[m.no_of_subtasks];
		double[] E = new double[m.no_of_machines];
		double max_=0;
		int temp;
		double[] met = new double[m.no_of_machines];
		for(int i=0; i<m.no_of_subtasks; i++){
			temp = m.ss_string[i];
			double maxx=-1;
			for(int j=0; j<i; j++){///check for all tasks for which i depends on in ss string(the index must be lower in ss string)
				if(m.rel[m.ss_string[j]][temp] != 0){
					int task = m.ss_string[j];
					maxx = max(maxx, e_t[task]+((m.ms_string[temp]==m.ms_string[task])?0:m.rel[task][temp]));
				}
			}
			s_t[temp] = met[m.ms_string[temp]];
			s_t[temp] = max(maxx, s_t[temp]);
			
			int x = a[temp];
			int mach = m.ms_string[temp];
			double est_max = m.est[temp][m.ms_string[temp]];
			double est_l = ((x*(est_max*(1+m.sr[mach]/100)) + (10-x)*(est_max)))/10;///new execution time
			E[mach] += ((x*(est_max*(1+m.sr[mach]/100) )*m.em[mach][0] + (10-x)*est_max*m.em[mach][1]))/10;
			e_t[temp] = s_t[temp]+ est_l;
			met[m.ms_string[temp]] = e_t[temp];
			max_ = max(e_t[temp], max_);
		}
		double[] mach1 = new double[m.no_of_machines];
		for(int i=0; i<m.no_of_subtasks; i++){
			int x = m.ms_string[i];
			mach1[x] += (e_t[i] - s_t[i]);
		}
		for(int i=0; i<m.no_of_machines; i++){
			mach1[i] = max_-mach1[i];
			E[i] += mach1[i]*m.em[i][0];
		}
		double Total=0f;
		for(int i=0; i<m.no_of_machines; i++){
			Total += E[i];
		}
		return Total;
	}

	private static double max(double p1, double p2) {
		if(p1 >= p2){
			return p1;
		}else{
			return p2;
		}
	}

	public double get_make_span(int[] a) {
		double[] s_t = new double[m.no_of_subtasks];
		double[] e_t = new double[m.no_of_subtasks];
		double max_=0;
		int temp;
		double[] met = new double[m.no_of_machines];
		
		for(int i=0; i<m.no_of_subtasks; i++){
			temp = m.ss_string[i];
			double maxx=-1;
			for(int j=0; j<i; j++){///check for all tasks for which i depends on in ss string(the index must be lower in ss string)
				if(m.rel[m.ss_string[j]][temp] != 0){
					int task = m.ss_string[j];
					maxx = max(maxx, e_t[task]+((m.ms_string[temp]==m.ms_string[task])?0:m.rel[task][temp]));
				}
			}
			s_t[temp] = met[m.ms_string[temp]];
			s_t[temp] = max(maxx, s_t[temp]);
			int x = a[temp];
			int mach = m.ms_string[temp];
			double est_max = m.est[temp][m.ms_string[temp]];
			double est_l = ((x*(est_max*(1+m.sr[mach]/100)) + (10-x)*(est_max)))/10;///new execution cost for task 
			e_t[temp] = s_t[temp]+ est_l;
			met[m.ms_string[temp]] = e_t[temp];
			max_ = max(e_t[temp], max_);
		}
		return max_;
	}
	
}
