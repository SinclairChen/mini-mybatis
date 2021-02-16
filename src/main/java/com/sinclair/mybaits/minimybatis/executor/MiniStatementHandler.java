package com.sinclair.mybaits.minimybatis.executor;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.sinclair.mybaits.minimybatis.session.MiniConfiguration;

/**
 * @Description 封装jdbc statement， 用于操作数据库
 * @Date 2021/2/16 10:39
 * @Author by Saiyong.Chen
 */
public class MiniStatementHandler {

    private MiniResultSetHandler resultSetHandler = new MiniResultSetHandler();

    public <T> T query(String statement, Object[] parameter, Class pojo) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(statement);
            MiniParameterHandler parameterHandler = new MiniParameterHandler(preparedStatement);
            parameterHandler.setParameters(parameter);
            preparedStatement.execute();

            return resultSetHandler.handle(preparedStatement.getResultSet(), pojo);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * 从配置文件中获取连接信息
     * @return
     */
    private Connection getConnection() {

        String driver = MiniConfiguration.properties.getString("jdbc.driver");
        String url =  MiniConfiguration.properties.getString("jdbc.url");
        String username = MiniConfiguration.properties.getString("jdbc.username");
        String password = MiniConfiguration.properties.getString("jdbc.password");
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
