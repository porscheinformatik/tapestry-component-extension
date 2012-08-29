package at.porscheinformatik.common.tapestry5.extension.test.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import at.porscheinformatik.common.tapestry5.extension.ContributableExtension;
import at.porscheinformatik.common.tapestry5.extension.services.ExtensionPointService;
import at.porscheinformatik.common.tapestry5.extension.test.components.ConcreteExtensionWithInheritance;
import at.porscheinformatik.common.tapestry5.extension.test.components.ConcreteExtensionWithoutInheritance;
import at.porscheinformatik.common.tapestry5.extension.test.pages.PageWithExtension;
import at.porscheinformatik.common.tapestry5.extension.worker.ExtensionPointWorker;

public class AppModule
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

    /**
     * contribute to the extension for consumer
     */
    public static void contributeExtensionPointService(MappedConfiguration<String, ContributableExtension> configuration)
    {
        configuration.add("concreteExtension", new ContributableExtension(PageWithExtension.EXT_POINT_WITH_INHERITANCE,
            ConcreteExtensionWithInheritance.class));

        configuration.add("concreteExtensionWithoutInheritance", new ContributableExtension(
            PageWithExtension.EXT_POINT_WITHOUT_INHERITANCE,
            ConcreteExtensionWithoutInheritance.class));
    }
}
