package com.sinclair.mybaits.minimybatis.session;

import com.sinclair.mybaits.minimybatis.annotation.Entity;
import com.sinclair.mybaits.minimybatis.annotation.Select;
import com.sinclair.mybaits.minimybatis.binding.MimiMapperRegistry;
import com.sinclair.mybaits.minimybatis.executor.MiniCachingExecutor;
import com.sinclair.mybaits.minimybatis.executor.MiniExecutor;
import com.sinclair.mybaits.minimybatis.executor.MiniSimpleExecutor;
import com.sinclair.mybaits.minimybatis.plugin.MiniInterceptor;
import com.sinclair.mybaits.minimybatis.plugin.MiniInterceptorChain;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description 配置类
 * @Date 2021/2/16 10:45
 * @Author by Saiyong.Chen
 */
public class MiniConfiguration {
    /** sql关系映射配置 */
    public static final ResourceBundle sqlMappings;
    /** 全局配置 */
    public static final ResourceBundle properties;
    /** 维护接口与工厂类关系 */
    public static final MimiMapperRegistry MAPPER_REGISTRY = new MimiMapperRegistry();
    /** 维护接口方法与sql关系 */
    public static final Map<String, String> mappedStatements = new HashMap<>();
    /** 插件 */
    private MiniInterceptorChain interceptorChain = new MiniInterceptorChain();

    /** 所有mapper接口 */
    private List<Class<?>> mapperList = new ArrayList<>();
    /** 类所有文件  */
    private List<String> classPaths = new ArrayList<>();

    static {
        sqlMappings = ResourceBundle.getBundle("sql");
        properties = ResourceBundle.getBundle("mybatis");
    }

    public MiniConfiguration() {
        // 1、解析sql.properties
        for (String key : sqlMappings.keySet()) {

            String statement = null;
            Class mapper = null;
            Class pojo = null;

            //sql语句
            statement = sqlMappings.getString(key).split("--")[0];

            try {
                //获取pojo
                String pojoName = sqlMappings.getString(key).split("--")[1];
                pojo = Class.forName(pojoName);

                //获取mapper接口
                mapper = Class.forName(key.substring(0, key.lastIndexOf(".")));
            } catch (Exception e) {
                e.printStackTrace();
            }

            MAPPER_REGISTRY.addMapper(mapper, pojo);
            mappedStatements.put(key, statement);
        }


        //2、 解析接口上的注解
        String mapperPath = properties.getString("mapper.path");
        scanPackage(mapperPath);
        for (Class<?> mapper : mapperList) {
            parsingClass(mapper);
        }

        //3、解析插件
        String pluginPath = properties.getString("plugin.path");
        String[] pluginPaths = pluginPath.split(",");
        if (pluginPaths != null) {
            for (String plugin : pluginPaths) {
                MiniInterceptor interceptor = null;
                try {
                    interceptor = (MiniInterceptor) Class.forName(plugin).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                interceptorChain.addInterceptor(interceptor);
            }
        }
    }

    /**
     * 解析mapper接口上的注解
     * @param mapper
     */
    private void parsingClass(Class<?> mapper) {
        //1、解析类上的注解
        if (mapper.isAnnotationPresent(Entity.class)) {
            for (Annotation annotation : mapper.getAnnotations()) {
                if (annotation.annotationType().equals(Entity.class)) {
                    MAPPER_REGISTRY.addMapper(mapper, ((Entity) annotation).value());
                }
            }
        }

        //2、解析方法上的注解
        Method[] methods = mapper.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Select.class)) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    String statementId = method.getDeclaringClass().getName() + "." + method.getName();
                    mappedStatements.put(statementId, ((Select) annotation).value());
                }
            }
        }
    }

    /**
     * 扫描所有mapper接口
     * @param mapperPath
     */
    private void scanPackage(String mapperPath) {
        String classPath = this.getClass().getResource("/").getPath();
        String mainPath = classPath + mapperPath.replace(".", File.separator);
        doPath(new File(mainPath));
        for (String className : classPaths) {
            className = className.replace(
                        classPath.replace("/", "\\").replaceFirst("\\\\", ""), "")
                    .replace("\\",".").replace(".class", "");

            Class<?> clazz = null;

            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz.isInterface()) {
                mapperList.add(clazz);
            }
        }
    }

    /**
     * 获取目标文件下的所有.class文件
     * @param file
     */
    private void doPath(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                doPath(file1);
            }
        } else {
            if (file.getName().endsWith(".class")) {
                classPaths.add(file.getPath());
            }
        }
    }

    public <T> T getMapper(Class<T> clazz, MiniDefaultSqlSession sqlSession) {
        return MAPPER_REGISTRY.getMapper(clazz, sqlSession);
    }

    /**
     * 根据id获取sql语句
     * @param id
     * @return
     */
    public String getMapperStatement(String id) {
        return mappedStatements.get(id);
    }

    /**
     * 创建一个执行器
     * @return
     */
    public MiniExecutor newExecutor() {
        MiniExecutor executor = null;
        //判断是否开启了缓存，如果开启进行包装
        if (properties.getString("cache.enabled").equals("true")) {
            executor = new MiniCachingExecutor(new MiniSimpleExecutor());
        } else {
            executor = new MiniSimpleExecutor();
        }

        if (interceptorChain.hasPlugin()) {
            executor = (MiniExecutor) interceptorChain.pluginAll(executor);
        }
        return executor;

    }

    /**
     * 判读是否有这个statementid存在的sql语句
     * @param statementId
     * @return
     */
    public boolean hashStatement(String statementId) {

        return mappedStatements.containsKey(statementId);
    }
}
