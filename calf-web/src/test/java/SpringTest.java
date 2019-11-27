import com.github.doscene.calf.service.sys.SysPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <h1>PACKAGE_NAME</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(locations = {"classpath:applicationContext-mapper.xml",
        "classpath:applicationContext-service.xml",
        //"classpath:applicationContext-shiro.xml",
        //"classpath:applicationContext-redis.xml",
        "classpath:applicationContext-activiti.xml"})
public class SpringTest {
    @Autowired
    SysPermissionService sysPermissionService;

    @Test
    public void t1() {
        sysPermissionService.getSysPermissionByParentId("1");
    }
}
