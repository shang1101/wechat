import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by seyren on 6/16/14.
 */

class Test {
    private int x;
    Test(int x) {
        this.x = x;
    }

    public void add() {
        x++;
    }

    public int getX() {
        return x;
    }
}


public class JsonTest {
    public static void raise(Test x) {
        x.add();
    }

    public static void main(String args[]) {
        Test a = new Test(4);
        raise(a);
        System.out.print(a.getX());
    }
}
