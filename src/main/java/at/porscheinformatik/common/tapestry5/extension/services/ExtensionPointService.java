package at.porscheinformatik.common.tapestry5.extension.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;

import at.porscheinformatik.common.tapestry5.extension.ContributableExtension;
import at.porscheinformatik.common.tapestry5.extension.annotation.ExtensionPoint;

/**
 * central point that receives contributions for the extension mechanism
 * 
 * @author Gerold Glaser (gla)
 * @since 26.01.2012
 * @tapestrydoc
 */
@UsesMappedConfiguration(key = String.class, value = ContributableExtension.class)
public class ExtensionPointService
{
    /**
     * key = extensionPointId, value = ComponentClass
     */
    private final Map<String, List<Class<?>>> extensions;

    /**
     * @param contributions .
     */
    public ExtensionPointService(Map<String, ContributableExtension> contributions)
    {
        this.extensions = new HashMap<String, List<Class<?>>>();
        for (Map.Entry<String, ContributableExtension> entry : contributions.entrySet())
        {
            if (extensions.containsKey(entry.getValue().getExtensionPoint()))
            {
                extensions.get(entry.getValue().getExtensionPoint()).add(entry.getValue().getComponentClass());
            }
            else
            {
                List<Class<?>> extensionList = new ArrayList<Class<?>>();
                extensionList.add(entry.getValue().getComponentClass());
                extensions.put(entry.getValue().getExtensionPoint(), extensionList);
            }
        }
    }

    /**
     * @see ExtensionPoint#id()
     * @param extensionPointId the id of the extension point
     * @return the extension found for the given extension point id. If no extension is found than the {@link Any}
     *         component is returned. If more than one extension is found an exception is thrown
     */
    public Class<?> getExtension(String extensionPointId)
    {
        if(extensionPointId == null)
        {
            throw new IllegalArgumentException("extensionPointId cannot be null");
        }

        Class<?> result = null;

        List<Class<?>> extensionList = extensions.get(extensionPointId);
        if (extensionList != null)
        {
            if (extensionList.size() > 1)
            {
                throw new RuntimeException(String.format("More than one extension found for key: %s", extensionPointId));
            }
            result = extensionList.get(0);
        }

        if (result == null)
        {
            result = Any.class;
        }
        return result;
    }

}
