package at.porscheinformatik.common.tapestry5.extension.services;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import at.porscheinformatik.common.tapestry5.extension.worker.ExtensionPointWorker;

/**
 * @author Gerold Glaser (gla)
 * @since 31.01.2012
 */
public class TapestyExtensionModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(ExtensionPointService.class);
    }

    @Contribute(ComponentClassTransformWorker2.class)
    public static void provideTransformWorkers(
        final OrderedConfiguration<ComponentClassTransformWorker2> configuration,
        final ComponentClassResolver resolver,
        final ExtensionPointService extensionPointService)
    {
        configuration.override("Component", new ExtensionPointWorker(resolver, extensionPointService));
    }
}
