package anotation;

import java.lang.annotation.*;

/**
 *
 * @author ITExplore
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MDO {

    public String name() default "";

}
