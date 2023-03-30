package the.beacon.emsvue.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置虚拟路径访问，以避免文件上传后由于服务器不暴露真实资源路径的保护措施而访问不到的问题
        registry.addResourceHandler("/photos/**").addResourceLocations("file:D:\\Java\\ems-vue\\src\\main\\resources\\static\\photos\\");
    }
}
