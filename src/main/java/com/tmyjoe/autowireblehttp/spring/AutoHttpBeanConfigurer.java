package com.tmyjoe.autowireblehttp.spring;

import com.tmyjoe.autowireblehttp.AutowirebleHttp;
import com.tmyjoe.autowireblehttp.AutoHttpClientFactory;
import com.tmyjoe.autowireblehttp.DefaultHttpClientProvider;
import com.tmyjoe.autowireblehttp.HttpClientProvider;
import com.tmyjoe.autowireblehttp.util.Asserts;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * AutowirebleHttp configuration bean class.
 *
 * @author Tomoya
 */
public class AutoHttpBeanConfigurer
    implements BeanFactoryPostProcessor, InitializingBean, DisposableBean {

    private static final Log logger = LogFactory.getLog(AutoHttpBeanConfigurer.class);

    private String componentScanBase;
    private HttpClientProvider httpClientProvider;
    private AutoHttpClientFactory autoHttpClientFactory;
    private List<LifecycleCallback> lifecycleCallbacks;

    public AutoHttpBeanConfigurer(String componentScanBase) {
        Asserts.notNull(componentScanBase, "componentScanBase must be provided as constructor arg.");
        this.componentScanBase = componentScanBase;
    }

    public AutoHttpBeanConfigurer(String componentScanBase, HttpClientProvider httpClientProvider) {
        this(componentScanBase);
        this.httpClientProvider = httpClientProvider;

    }

    public void postProcessBeanFactory(
        ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Set<Class<?>> autoHttpInterfaces = scanAnnotatedInterface();
        for (Class<?> clazz : autoHttpInterfaces) {
            configurableListableBeanFactory
                .registerResolvableDependency(clazz, autoHttpClientFactory.createBean(clazz));
            logger.trace(clazz.getName() + " registered");
        }
    }

    private Set<Class<?>> scanAnnotatedInterface() {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage(componentScanBase));
        Set<Class<?>> annotatedInterface = reflections.getTypesAnnotatedWith(AutowirebleHttp.class);
        if (annotatedInterface.isEmpty()) {
            logger.info("@AutoHttp annotated interface not found in under the package : "
                + componentScanBase);
        }
        return annotatedInterface;
    }

    public void destroy() throws Exception {
        for (LifecycleCallback callback : lifecycleCallbacks) {
            callback.destroy();
        }
    }

    public void afterPropertiesSet() throws Exception {
        HttpClientProvider provider = httpClientProvider == null ? new DefaultHttpClientProvider() : httpClientProvider;
        this.autoHttpClientFactory = new AutoHttpClientFactory(provider);
        if (provider instanceof LifecycleCallback) {
            this.lifecycleCallbacks = Arrays.asList((LifecycleCallback) provider);
        }

        for (LifecycleCallback callback : lifecycleCallbacks) {
            callback.afterPropertiesSet();
        }
    }
}
