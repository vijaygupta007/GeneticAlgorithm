package makespan;
import java.util.Random;


public class Selection {
	Main m;
	Selection(Main m){
		this.m = m;
	}
	public int select_pos(){
		return ((new Random().nextInt(m.population_size)+new Random().nextInt(m.population_size))/2);
	}

}
