package at.porscheinformatik.common.tapestry5.extension.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.COMPONENT;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.PAGE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.UseWith;

/**
 * Extension Point annotation to inform Tapestry about a specific place holder
 * 
 * @author Gerold Glaser (gla)
 * @since 08.02.2012
 * @tapestrydoc
 */
@Target(FIELD)
@Documented
@Retention(RUNTIME)
@UseWith({COMPONENT, PAGE})
public @interface ExtensionPoint
{

    /**
     * The id of the extension point. Needed to identify the place holder for extension.
     */
    String id() default "";
}
