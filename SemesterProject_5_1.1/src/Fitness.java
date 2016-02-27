import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;



public class Fitness{
	@SuppressWarnings("unchecked")
	public static Float get_fitness_value(int[] a){
		float[] s_t = new float[Main.no_of_subtasks];
		float[] e_t = new float[Main.no_of_subtasks];
		float[] E = new float[Main.no_of_machines];
		float max_=0;
		int temp;
		float[] met = new float[Main.no_of_machines];
		for(int i=0; i<Main.no_of_subtasks; i++){
			temp = Main.ss_string[i];
			Map<Float, Integer> map = new HashMap<Float, Integer>();
			for(int j=0; j<i; j++){///check for all tasks for which i depends on in ss string(the index must be lower in ss string)
				if(Main.rel[Main.ss_string[j]][temp] != 0){
					map.put(met[Main.ms_string[Main.ss_string[j]]], Main.ss_string[j]);
				}
			}
			s_t[temp] = met[Main.ms_string[temp]];
			
			for (Float key : map.keySet()) {     
			    Integer value = map.get(key);
			    s_t[temp] = s_t[temp] + max(0f, (e_t[value]  - s_t[temp]))+((Main.ms_string[temp]==Main.ms_string[value])?0:Main.rel[value][temp]);
			}
			
			int x = a[temp];
			int mach = Main.ms_string[temp];
			float est_max = Main.est[temp][Main.ms_string[temp]];
			float est_l = ((x*(est_max*(1+Main.sr[mach]/100)) + (10-x)*(est_max)))/10;
			
			//System.out.println("temp: "+temp+" mach: "+mach+" a[temp]: "+x);
			E[mach] += ((x*(est_max*(1+Main.sr[mach]/100) )*Main.em[mach][0] + (10-x)*est_max*Main.em[mach][1]))/10;
			//System.out.println(" -- end");
			
			e_t[temp] = s_t[temp]+ est_l;
			
			met[Main.ms_string[temp]] = e_t[temp];
			max_ = max(e_t[temp], max_);
		}
		float[] mach1 = new float[Main.no_of_machines];
		for(int i=0; i<Main.no_of_subtasks; i++){
			int x = Main.ms_string[i];
			mach1[x] += (e_t[i] - s_t[i]);
		}
		for(int i=0; i<Main.no_of_machines; i++){
			mach1[i] = met[i]-mach1[i];
			E[i] += mach1[i]*Main.em[i][0];
		}
		float Total=0f;
		for(int i=0; i<Main.no_of_machines; i++){
			Total += E[i];
		}
		return Total;
	}

	private static Float max(Float p1, Float p2) {
		if(p1 >= p2){
			return p1;
		}else{
			return p2;
		}
	}

	public static float get_make_span(int[] a) {
		float[] s_t = new float[Main.no_of_subtasks];
		float[] e_t = new float[Main.no_of_subtasks];
		float max_=0;
		int temp;
		float[] met = new float[Main.no_of_machines];
		for(int i=0; i<Main.no_of_subtasks; i++){
			temp = Main.ss_string[i];
			Map<Float, Integer> map = new HashMap<Float, Integer>();
			for(int j=0; j<i; j++){///check for all tasks for which i depends on in ss string(the index must be lower in ss string)
				if(Main.rel[Main.ss_string[j]][temp] != 0){
					map.put(met[Main.ms_string[Main.ss_string[j]]], Main.ss_string[j]);
				}
			}
			s_t[temp] = met[Main.ms_string[temp]];
			
			for (Float key : map.keySet()) {     
			    Integer value = map.get(key);
			    s_t[temp] = s_t[temp] + max(0f, (e_t[value]  - s_t[temp]))+((Main.ms_string[temp]==Main.ms_string[value])?0:Main.rel[value][temp]);
			}
			
			int x = a[temp];
			int mach = Main.ms_string[temp];
			float est_max = Main.est[temp][Main.ms_string[temp]];
			float est_l = ((x*(est_max*(1+Main.sr[mach]/100)) + (10-x)*(est_max)))/10;
			e_t[temp] = s_t[temp]+ est_l;
			
			met[Main.ms_string[temp]] = e_t[temp];
			max_ = max(e_t[temp], max_);
		}
		System.out.println();
		return max_;
	}
}
