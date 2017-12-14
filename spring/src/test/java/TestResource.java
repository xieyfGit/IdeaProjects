import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * Spring提供了一个相较于JDK更方便得资源访问工具接口：org.springframework.core.io.Resource
 * Spring为Resource接口提供了很多实现类，如：ClassPathResource\FileSystemResource\UrlResource等等
 *
 * ♥考虑到对于不同路径类型得文件需要使用对应得实现类比较麻烦，Spring支持资源类型前缀识别而不用去管具体实现类
 *  DefaultResourceLoader：不支持Ant风格
 * ※ classpath类型      classpath:spring-simple.xml
 * ※ File类型           file:/com/yf/spring/ioc/spring-simple.xml
 * ※ http类型           http://www.baidu.com/spring-core.xml
 * ※ ftp类型            ftp://www/baidu/com/spring-core.xml
 * ※ 无前缀类型      无前缀 com/yf/spring/ioc/spring-simple.xml
 *
 * ♥PathMatchingResourcePatternResolver.getResources 方法支持Ant格式匹配符,且需要指定前缀为：classpath*:
 * ※ ？        表示匹配文件名中的一个任意字符  如：Classpath:com/t?st.xml
 * ※ *        表示匹配文件名中的多个任意字符   如：File://D:/conf/*.xml
 * ※ **       表示匹配文件名中的多级目录       如：Classpath:com/** /test.xml
 */
public class TestResource {
    @Test
    public void test() {
        fromClassPath();
    }

    //类路径下加载文件
    private void fromClassPath() {
        Resource resource = null;
        ResourceLoader loader = null;
//         resource = new ClassPathResource("cls.properties");
//         resource = new DefaultResourceLoader().getResource("classpath:cls.properties");

        try {
//            resource = new PathMatchingResourcePatternResolver().getResources("classpath*:*.properties")[0];
            resource = new PathMatchingResourcePatternResolver().getResources("file:/D:/IdeaProjects/spring/out/production/**/*.properties")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(properties.get("cls"));
    }

    //ResourcePatternResolver支持带资源前缀以及Ant风格路径表达式
    private void getResource() {
//        ResourceLoader resourceLoader
    }
}
