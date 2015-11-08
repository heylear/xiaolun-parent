package tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caosh on 2015/10/31.
 */
public class RegexTest {

    public static void main(String[] args) {
        Matcher m = Pattern.compile("([a-z]+)(Type(\\w+)Id(\\d+))?").matcher("seller");
        if (m.find()) {
            for (int i = 0; i < m.groupCount(); i++) {
                System.out.println("group[" + (i + 1) + "]=" + m.group(i + 1));
            }
        }

    }
}
