package at.porscheinformatik.common.tapestry5.extension;

/**
 *  the contributable extension to hold the id of the target page and the component to be injected
 */
public class ContributableExtension
{
    /**
     * the id of the extensionPoint
     */
    private final String extensionPoint;

    /**
     * the component class that should be used in the extension point
     */
    private final Class<?> componentClass;

    public ContributableExtension(String extensionPoint, Class<?> componentClass)
    {
        super();
        this.extensionPoint = extensionPoint;
        this.componentClass = componentClass;
    }

    /**
     * @return the extensionPoint
     */
    public String getExtensionPoint()
    {
        return extensionPoint;
    }

    /**
     * @return the componentClass
     */
    public Class<?> getComponentClass()
    {
        return componentClass;
    }

}
