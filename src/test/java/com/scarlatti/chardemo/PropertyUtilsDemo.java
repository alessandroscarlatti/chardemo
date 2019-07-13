package com.scarlatti.chardemo;

import org.apache.commons.beanutils.ConstructorUtils;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static com.scarlatti.chardemo.PropertyUtilsDemo.PerformanceTest.perform;
import static org.apache.commons.beanutils.BeanUtils.setProperty;

/**
 * @author Alessandro Scarlatti
 * @since Saturday, 7/13/2019
 */
public class PropertyUtilsDemo {

    @Test
    public void propertyUtilsDemoWithRaw() {
        Pojo pojo = new Pojo();

        perform(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    pojo.setProp1("Asdf" + i);
                } catch (Exception e) {
                    throw new RuntimeException("Error setting property", e);
                }
            }
        });
    }

    @Test
    public void propertyUtilsDemoWithConstructorRaw() {
        perform(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    Pojo pojo = new Pojo();
                } catch (Exception e) {
                    throw new RuntimeException("Error constructing bean", e);
                }
            }
        });
    }

    @Test
    public void propertyUtilsDemoWithConstructorBeanUtils() {
        perform(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
//                    Pojo pojo = Pojo.class.getDeclaredConstructor().newInstance();
                    Pojo pojo = ConstructorUtils.invokeConstructor(Pojo.class, new Object[]{});
                } catch (Exception e) {
                    throw new RuntimeException("Error constructing bean", e);
                }
            }
        });
    }

    @Test
    public void propertyUtilsDemoWithBeanUtils() {
        Pojo pojo = new Pojo();

        // warmup
        for (int i = 0; i < 10000; i++) {
            try {
                setProperty(pojo, "Prop1", "Warmup" + i);
            } catch (Exception e) {
                throw new RuntimeException("Error setting property", e);
            }
        }

        perform(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    setProperty(pojo, "Prop1", "Asdf" + i);
                } catch (Exception e) {
                    throw new RuntimeException("Error setting property", e);
                }
            }
        });

        perform(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    setProperty(pojo, "Prop1", "Asdf" + i);
                } catch (Exception e) {
                    throw new RuntimeException("Error setting property", e);
                }
            }
        });

        Pojo pojo2 = new Pojo();
        perform(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    setProperty(pojo, "Prop1", "Asdf" + i);
                } catch (Exception e) {
                    throw new RuntimeException("Error setting property", e);
                }
            }
        });
    }

    public interface PerformanceTest {
        void run();

        static void perform(PerformanceTest performanceTest) {
            Instant start = Instant.now();
            try {
                performanceTest.run();
            } finally {
                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                System.out.println(duration);
            }
        }
    }

    public static class Pojo {
        private String prop1;
        private String prop2;

        public String getProp1() {
            return prop1;
        }

        public void setProp1(String prop1) {
            this.prop1 = prop1;
        }

        public String getProp2() {
            return prop2;
        }

        public void setProp2(String prop2) {
            this.prop2 = prop2;
        }
    }
}
