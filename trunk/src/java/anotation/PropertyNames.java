package anotation;

import java.lang.annotation.*;

/**
 *
 * @author ITExplore
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropertyNames {

    public String name() default "";

}
