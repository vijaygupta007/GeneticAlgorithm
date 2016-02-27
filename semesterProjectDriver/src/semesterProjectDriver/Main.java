package semesterProjectDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws IOException {
		initilisation();
	//	File folder = new File("Graphs_fft1");
		File folder = new File("Input Graphs_Unprocessed");
		for(File file : folder.listFiles()){
			if(file.getName().contains(".graph~") == false && file.getName().contains(".graphsch") == false && file.getName().contains(".graph") == true ){
				if((new File(folder.getAbsolutePath()+"\\"+file.getName().split(".graph")[0]+".graphsch")).exists() ){
				/*	int t=0;
					if(file.getName().contains("152")){
						t = 152;
					}else if(file.getName().contains("350")){
						t = 350;
					}else if(file.getName().contains("560")){
						t = 560;
					}*/
					//double startTime = System.currentTimeMillis();
					System.out.println("file name: -----------  "+file.getName());
					new makespan.Main(folder.getAbsolutePath()+"\\"+file.getName());
					new energy.Main(file.getName());
				//	double endTime=System.currentTimeMillis();
				//	double time_=((double)endTime - startTime)/(1000*60*24);
				
					/*System.out.println("Timetaken:-  "+time_);
					FileWriter out_ = new FileWriter("time.txt", true);
					out_.write(t+"\t"+time_+"\n");
					out_.close();*/
				}
			}
			
		}
		calc_avg("time.txt", "time1.txt");
		calc_avg("heft_speedup1.txt", "heft_speedup11.txt");
		calc_avg("speedup_phase1.txt", "speedup_phase11.txt");
		calc_avg("speedup_phase2.txt", "speedup_phase22.txt");
		calc_avg("makespan_phase1.txt", "makespan_phase11.txt");
		calc_avg("makespan_phase2.txt", "makespan_phase22.txt");
		calc_avg("energy_phase1.txt", "energy_phase11.txt");
		calc_avg("energy_phase2.txt", "energy_phase22.txt");
		calc_avg("heft_makespan.txt", "heft_makespan1.txt");
		calc_avg("heft_energy.txt", "heft_energy1.txt");
	}
	
	public static void calc_avg(String file_name1, String file_name2) throws IOException{
		
		Map<Float, Float> map = new HashMap<Float, Float>();
		Map<Float, Integer> count = new HashMap<Float, Integer>();
		Scanner in = new Scanner(new FileInputStream(file_name1));
		while(in.hasNext()){
			Float key = in.nextFloat();
			Float value = in.nextFloat();
			if(map.containsKey(key)){
				map.put(key, map.get(key)+value);
				count.put(key, count.get(key)+1);
			}else{
				map.put(key, value);
				count.put(key, 1);
			}
			
		}
		in.close();
		FileWriter out_ = new FileWriter(file_name2, true);
		Iterator entries1 = map.entrySet().iterator();
		SortedSet<Float> keys = new TreeSet<Float>(map.keySet());
		for (Float key : keys) { 
			out_.write(key+"\t"+(map.get(key)/count.get(key))+"\n");
		}
		out_.close();
		
		map.clear();
		count.clear();
	}
	public static void clear_file_data(String file1) throws IOException{
		FileOutputStream o = new FileOutputStream(file1);
		o.close();
	}
	public static void initilisation() throws IOException{
		clear_file_data("time.txt");
		clear_file_data("time1.txt");
		clear_file_data("heft_makespan.txt");
		clear_file_data("heft_energy.txt");
		clear_file_data("heft_makespan1.txt");
		clear_file_data("heft_energy1.txt");
		clear_file_data("makespan_phase1.txt");
		clear_file_data("makespan_phase11.txt");
		clear_file_data("makespan_phase2.txt");
		clear_file_data("makespan_phase22.txt");
		clear_file_data("energy_phase1.txt");
		clear_file_data("energy_phase11.txt");
		clear_file_data("energy_phase2.txt");
		clear_file_data("energy_phase22.txt");
		clear_file_data("speedup_phase1.txt");
		clear_file_data("speedup_phase11.txt");
		clear_file_data("speedup_phase2.txt");
		clear_file_data("speedup_phase22.txt");
		clear_file_data("heft_speedup1.txt");
		clear_file_data("heft_speedup11.txt");
	}
}
