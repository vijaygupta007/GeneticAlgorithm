import java.util.Random;


public class Selection {
	public static int select_pos(){
		return ((new Random().nextInt(Main.population_size)+new Random().nextInt(Main.population_size))/2);
	}

}
