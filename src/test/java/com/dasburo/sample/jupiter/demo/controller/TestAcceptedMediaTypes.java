package com.dasburo.sample.jupiter.demo.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test ot verify of the controllers have a POST endpoint, and then verify the POST endpoint can consume both XML and JSON.
 *
 * @see {https://medium.com/@BillyKorando/dynamic-testing-in-junit-5-a-practical-guide-a57e3ceaa240}
 */
public class TestAcceptedMediaTypes {

    private static final MediaType[] SUPPORTED_MEDIA_TYPES = new MediaType[] {MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML};
    private final Log logger = LogFactory.getLog(getClass());

    @TestFactory
    Collection<DynamicTest> testAcceptedMediaTypes() throws Exception {

        // Creating a classpath scanner to find all controller classes in project
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(new DefaultListableBeanFactory(),
                false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        Set<BeanDefinition> beanDefinitons = scanner.findCandidateComponents("com.dasburo.sample.jupiter.demo.controller");
        logger.info("Beans found:");
        beanDefinitons.stream().forEach(bean -> logger.info(bean));
        Set<Object> controllers = new HashSet<>();
        Set<Class<?>> controllersClasses = new HashSet<>();

        // Instantiating controller classes
        for (BeanDefinition beanDefinition : beanDefinitons) {
            String className = beanDefinition.getBeanClassName();
            Class<?> controllerClazz = ClassLoader.getSystemClassLoader().loadClass(className);
            controllersClasses.add(controllerClazz);

            Constructor<?> constructor = controllerClazz.getDeclaredConstructors()[0];
            Object[] arguments = new Object[constructor.getParameterTypes().length];
            int i = 0;
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                arguments[i] = mock(parameterType);
                i++;
            }
            controllers.add(constructor.newInstance(arguments));
        }

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllers.toArray()).build();
        List<DynamicTest> generatedTests = new ArrayList<>();

        // Check if controller has a POST endpoint and call it with all the different
        // mediatypes, throw an error in a 415 (unsupported media type) is returned
        for (Class<?> controllerClazz : controllersClasses) {
            RequestMapping mapping = controllerClazz.getAnnotationsByType(RequestMapping.class)[0];
            StringBuilder builder = new StringBuilder();
            builder.append(mapping.value()[0]);
            for (Method method : controllerClazz.getMethods()) {
                if (method.isAnnotationPresent(PostMapping.class)) {
                    for (MediaType mediaType : SUPPORTED_MEDIA_TYPES) {
                        generatedTests.add(dynamicTest(builder.toString() + " " + mediaType,
                                () -> mockMvc.perform(post(builder.toString()).accept(mediaType).contentType(mediaType))
                                        .andExpect(status().is(IsNot.not(415)))));
                    }
                }
            }

        }

        return generatedTests;
    }
}
