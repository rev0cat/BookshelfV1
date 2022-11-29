package book;

import book.mapper.BookMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.function.Consumer;

public class SQLUtils {
    private SQLUtils(){}
    private static SqlSessionFactory factory;
    static {
        try {
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public SqlSession getSession(){
        return factory.openSession(true);
    }
    public static void doSqlWork(Consumer<BookMapper> consumer){
        try (SqlSession sqlSession = factory.openSession(true)){
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            consumer.accept(bookMapper);
        }
    }
}
