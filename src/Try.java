import java.util.ArrayList;
import java.util.Collections;

public class Try {

	public static void main(String[] args) {
		ArrayList<Integer> reg = new ArrayList<Integer>();
		reg.add(1);
		reg.add(2);
		reg.add(3);
		reg.add(4);
		reg.add(5);

		for(int i = reg.size()-1; i >=2 ; i--)
			reg.remove(i);
		System.out.println(reg);
	}
}
