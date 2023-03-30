
import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Data
public class HelloWord {
    private String message;

    public void init() {
        System.out.println("init方法初始化");
    }


    public void afterPropertiesSet() throws Exception {
        System.out.println("HelloWorld初始化了！");
    }

    public void destroy() {
        System.out.println("destroy方法销毁");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("调用了finalize方法");
    }
}