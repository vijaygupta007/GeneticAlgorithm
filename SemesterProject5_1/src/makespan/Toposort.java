package makespan;

public class Toposort {
	public static int[] in;
	Main m;
	public Toposort(Main m) {
		this.m = m;
	}
	public void toposort() {
		in = new int[m.no_of_subtasks];
		for(int i=0; i<m.no_of_subtasks; i++){
			for(int j=0; j<m.no_of_subtasks; j++){
				if(m.rel[i][j] == 0){continue;}
				in[j]++;
			}
		}
		boolean check;
		boolean[] visit = new boolean[m.no_of_subtasks];
		int k=0;
		while(true){
			check = true;
			for(int i=0; i<m.no_of_subtasks; i++){
				if(in[i]==0 && (visit[i] == false)){
					m.basic_ss[k++]=i;
					visit[i]=true;
					check = false;
					for(int j=0; j<m.no_of_subtasks; j++){
						if(m.rel[i][j] != 0){
							in[j]--;
						}
					}
				}
			}
			if(check){
				break;
			}
				
		}

		/*System.out.println("------------toposort------------");
		for(int i=0; i<m.no_of_subtasks; i++){
			System.out.print(m.basic_ss[i]+" ");
		}
		System.out.println();*/
	}
	
}
