package com.github.doscene.calf.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>com.github.doscene.calf.common.utils</h1>
 * 实现mybatis热部署
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
@Component
public class MapperHotSwap {
    private SqlSessionFactoryBean sqlSessionFactoryBean;
    private SqlSessionFactory sqlSessionFactory;
    private Configuration configuration;

    public MapperHotSwap(SqlSessionFactoryBean sqlSessionFactoryBean, SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactoryBean = sqlSessionFactoryBean;
        this.sqlSessionFactory = sqlSessionFactory;
        this.configuration = sqlSessionFactory.getConfiguration();
    }


    public void start() {
        new WatchThread().start();
    }

    class WatchThread extends Thread {
        WatchThread() {
            this.setName("mapper-hot-swap-thread");
        }

        @Override
        public void run() {
            startWatch();
        }

        /**
         * 启动监听
         *
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private void startWatch() {
            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();
                getWatchPaths().forEach(p -> {
                    try {
                        Paths.get(p).register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
                    } catch (Exception e) {
                        log.error("ERROR: 注册xml监听事件", e);
                        throw new RuntimeException("ERROR: 注册xml监听事件", e);
                    }
                });
                while (true) {
                    WatchKey watchKey = watcher.take();
                    Set<String> set = new HashSet<>();
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        set.add(event.context().toString());
                    }
                    // 重新加载xml
                    reloadXml(set);
                    boolean valid = watchKey.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Mybatis的xml监控失败!");
                log.info("Mybatis的xml监控失败!", e);
            }
        }

        /**
         * 加载需要监控的文件父路径
         *
         * @return java.util.Set<java.lang.String>
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private Set<String> getWatchPaths() {
            Set<String> set = new HashSet<>();
            Arrays.stream(getResource()).forEach(r -> {
                try {
                    log.info("资源路径:{}", r.toString());
                    set.add(r.getFile().getParentFile().getAbsolutePath());
                } catch (Exception e) {
                    log.info("获取资源路径失败", e);
                    throw new RuntimeException("获取资源路径失败");
                }
            });
            log.info("需要监听的xml资源: {}", set);
            return set;
        }

        /**
         * 获取配置的mapperLocations
         *
         * @return org.springframework.core.io.Resource[]
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private Resource[] getResource() {
            return (Resource[]) getFieldValue(sqlSessionFactoryBean, "mapperLocations");
        }

        /**
         * 删除xml元素的节点缓存
         *
         * @param nameSpace xml中命名空间
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private void clearMap(String nameSpace) {
            log.info("清理Mybatis的namespace={}在mappedStatements、caches、resultMaps、parameterMaps、keyGenerators、sqlFragments中的缓存",nameSpace);
            Arrays.asList("mappedStatements", "caches", "resultMaps", "parameterMaps", "keyGenerators", "sqlFragments").forEach(fieldName -> {
                Object value = getFieldValue(configuration, fieldName);
                if (value instanceof Map) {
                    Map<?, ?> map = (Map) value;
                    List<Object> list = map.keySet().stream().filter(o -> o.toString().startsWith(nameSpace + ".")).collect(Collectors.toList());
                    log.info("需要清理的元素: {}", list);
                    list.forEach(k -> map.remove((Object) k));
                }
            });
        }

        /**
         * 清除文件记录缓存
         *
         * @param resource xml文件路径
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private void clearSet(String resource) {
            log.info("清理mybatis的资源{}在容器中的缓存", resource);
            Object value = getFieldValue(configuration, "loadedResources");
            if (value instanceof Set) {
                Set<?> set = (Set) value;
                set.remove(resource);
                set.remove("namespace:" + resource);
            }
        }

        /**
         * 获取对象指定属性
         *
         * @param obj       对象信息
         * @param fieldName 属性名称
         * @return java.lang.Object
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private Object getFieldValue(Object obj, String fieldName) {
            log.info("从{}中加载{}属性", obj, fieldName);
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                Object value = field.get(obj);
                field.setAccessible(accessible);
                return value;
            } catch (Exception e) {
                log.info("ERROR: 加载对象中[{}]", fieldName, e);
                throw new RuntimeException("ERROR: 加载对象中[" + fieldName + "]", e);
            }
        }

        /**
         * 重新加载set中xml
         *
         * @param set 修改的xml资源
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private void reloadXml(Set<String> set) {
            log.info("需要重新加载的文件列表: {}", set);
            List<Resource> list = Arrays.stream(getResource())
                    .filter(p -> set.contains(p.getFilename()))
                    .collect(Collectors.toList());
            log.info("需要处理的资源路径:{}", list);
            list.forEach(r -> {
                try {
                    clearMap(getNamespace(r));
                    clearSet(r.toString());
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(r.getInputStream(), configuration,
                            r.toString(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    log.info("ERROR: 重新加载[{}]失败", r.toString(), e);
                    throw new RuntimeException("ERROR: 重新加载[" + r.toString() + "]失败", e);
                } finally {
                    ErrorContext.instance().reset();
                }
            });
            log.info("成功热部署文件列表: {}", set);
        }

        /**
         * 获取xml的namespace
         *
         * @param resource xml资源
         * @return java.lang.String
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private String getNamespace(Resource resource) {
            log.info("从{}获取namespace", resource.toString());
            try {
                XPathParser parser = new XPathParser(resource.getInputStream(), true, null, new XMLMapperEntityResolver());
                return parser.evalNode("/mapper").getStringAttribute("namespace");
            } catch (Exception e) {
                log.info("ERROR: 解析xml中namespace失败", e);
                throw new RuntimeException("ERROR: 解析xml中namespace失败", e);
            }
        }
    }


}
