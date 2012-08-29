package at.porscheinformatik.common.tapestry5.extension.test.pages;

import org.apache.tapestry5.annotations.Component;

import at.porscheinformatik.common.tapestry5.extension.annotation.ExtensionPoint;
import at.porscheinformatik.common.tapestry5.extension.test.components.AbstractExtension;

/**
 * This pages offers an extension point but does not get one contributed
 * 
 * @author Gerold Glaser (gla)
 * @since 26.01.2012
 */
public class PageWithoutExtension
{
    @ExtensionPoint(id = "PageWithoutExtensionPoint")
    @Component(parameters = "id=literal:13")
    private AbstractExtension extensionPoint;

    @ExtensionPoint(id = "PageWithoutExtensionPoint2")
    @Component()
    private Object extensionPoint2;
}
