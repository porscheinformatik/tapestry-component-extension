package at.porscheinformatik.common.tapestry5.extension.test.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * this component does not inherit from a specific object. In the extension point definition object type is enough.
 * 
 * @author Gerold Glaser (gla)
 * @since 13.03.2012
 */
public class ConcreteExtensionWithoutInheritance
{
    @Parameter(required = true)
    @Property
    private Long id;
}
