import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <h1>PACKAGE_NAME</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public class JunitTest {
    @Test
    public void t1() throws IOException {
        Process p = Runtime.getRuntime().exec("ping " + "baidu.com");
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.length() != 0)
                sb.append(line + "\r\n");
        }
        System.out.println("本次指令返回的消息是：");
        if (sb.indexOf("\"100% loss\"") > 0) {
            System.out.println("网络连接不通");
        } else {
            System.out.println("网络连接正常");
        }
        System.out.println(sb.toString());
    }
    @Test
    public void t2() throws IOException {
        new JunitTest().t1();
        new JunitTest().t1();
    }
}
