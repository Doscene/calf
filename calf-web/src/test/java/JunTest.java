import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;

/**
 * <h1>PACKAGE_NAME</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
public class JunTest {

    public static void main(String[] args) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = FileSystems.getDefault().getPath("D:\\a.txt");
            log.debug("@@@:Path:" + path);
            WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                try {
                    key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        System.out.println(event.context().toString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化activiti引擎表
     */
    @Test
    public void createTable() {
        //创建引擎配置类
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        configuration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
        configuration.setJdbcUrl("jdbc:oracle:thin:@//127.0.0.1:1521/orcl");
        configuration.setJdbcUsername("calf");
        configuration.setJdbcPassword("root");

        //不自动创建表，需要表存在 DB_SCHEMA_UPDATE_FALSE = "false";
        //先删除表，再创建表 DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";
        //如果表不存在，先创建表 DB_SCHEMA_UPDATE_TRUE = "true";
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //创建工作流核心对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
    }

    @Test
    public void t4() {

    }

}
