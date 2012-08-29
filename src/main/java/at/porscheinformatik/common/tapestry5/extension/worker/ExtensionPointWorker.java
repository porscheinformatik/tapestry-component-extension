package at.porscheinformatik.common.tapestry5.extension.worker;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.MixinClasses;
import org.apache.tapestry5.annotations.Mixins;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.internal.KeyValue;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.transform.ReadOnlyComponentFieldConduit;
import org.apache.tapestry5.internal.transform.TransformMessages;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.ioc.Orderable;
import org.apache.tapestry5.ioc.internal.services.StringLocation;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.internal.util.TapestryException;
import org.apache.tapestry5.model.ComponentModel;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.model.MutableEmbeddedComponentModel;
import org.apache.tapestry5.plastic.ComputedValue;
import org.apache.tapestry5.plastic.FieldConduit;
import org.apache.tapestry5.plastic.InstanceContext;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import at.porscheinformatik.common.tapestry5.extension.annotation.ExtensionPoint;
import at.porscheinformatik.common.tapestry5.extension.services.ExtensionPointService;


/**
 * Finds fields with the {@link org.apache.tapestry5.annotations.Component} annotation and updates the model. Also
 * checks for the {@link Mixins} and {@link MixinClasses} annotations and uses them to update the {@link ComponentModel}
 * .
 */
public class ExtensionPointWorker implements ComponentClassTransformWorker2
{
    private final ComponentClassResolver resolver;

    private final ExtensionPointService extensionPointService;

    public ExtensionPointWorker(ComponentClassResolver resolver, ExtensionPointService extensionPointService)
    {
        super();
        this.resolver = resolver;
        this.extensionPointService = extensionPointService;
    }

    public void transform(final PlasticClass plasticClass, final TransformationSupport support,
        final MutableComponentModel model)
    {
        for (PlasticField field : plasticClass.getFieldsWithAnnotation(Component.class))
        {
            transformField(plasticClass, model, field);
        }
    }

    private void transformField(final PlasticClass transformation, final MutableComponentModel model,
        final PlasticField field)
    {
        Component annotation = field.getAnnotation(Component.class);

        field.claim(annotation);

        String annotationId = annotation.id();

        final String fieldName = field.getName();

        String id = InternalUtils.isNonBlank(annotationId) ? annotationId : InternalUtils.stripMemberName(fieldName);

        String type = getType(field);

        Location location = new StringLocation(String.format("%s.%s", transformation.getClassName(), fieldName), 0);

        MutableEmbeddedComponentModel embedded = model.addEmbeddedComponent(id, annotation.type(), type, annotation
            .inheritInformalParameters(), location);

        addParameters(embedded, annotation.parameters());

        updateModelWithPublishedParameters(embedded, annotation);

        convertAccessToField(field, id);

        addMixinClasses(field, embedded);
        addMixinTypes(field, embedded);
    }

    private String getType(final PlasticField field)
    {
        String type = field.getTypeName();

        final ExtensionPoint extensionPoint = field.getAnnotation(ExtensionPoint.class);

        if (extensionPoint != null)
        {
            // every extension point must have an id
            final String extensionId = extensionPoint.id();
            // if the class is attribute is provided - it is a list
            if (extensionId == null || extensionId == "")
            {
                throw new RuntimeException(String.format("Component extension on field %s does not have an id",
                    field.getName()));
            }
            // only one extension point
            final Class<?> extensionClassName = extensionPointService.getExtension(extensionId);
            type = extensionClassName.getName();
        }

        return type;
    }

    private void convertAccessToField(final PlasticField field, final String id)
    {
        String fieldName = field.getName();

        ComputedValue<FieldConduit<Object>> computedConduit = createProviderForEmbeddedComponentConduit(fieldName, id);

        field.setComputedConduit(computedConduit);
    }

    private ComputedValue<FieldConduit<Object>> createProviderForEmbeddedComponentConduit(final String fieldName,
        final String id)
    {
        return new ComputedValue<FieldConduit<Object>>()
        {
            public FieldConduit<Object> get(final InstanceContext context)
            {
                final ComponentResources resources = context.get(ComponentResources.class);

                return new ReadOnlyComponentFieldConduit(resources, fieldName)
                {
                    public Object get(final Object instance, final InstanceContext context)
                    {
                        return resources.getEmbeddedComponent(id);
                    }
                };
            }
        };
    }

    private void updateModelWithPublishedParameters(final MutableEmbeddedComponentModel embedded,
        final Component annotation)
    {
        String names = annotation.publishParameters();

        if (InternalUtils.isNonBlank(names))
        {
            List<String> published = CollectionFactory.newList(TapestryInternalUtils.splitAtCommas(names));
            embedded.setPublishedParameters(published);
        }

    }

    private void addMixinClasses(final PlasticField field, final MutableEmbeddedComponentModel model)
    {
        MixinClasses annotation = field.getAnnotation(MixinClasses.class);

        if (annotation == null)
            return;

        boolean orderEmpty = annotation.order().length == 0;

        if (!orderEmpty && annotation.order().length != annotation.value().length)
            throw new TapestryException(TransformMessages.badMixinConstraintLength(annotation, field.getName()), model,
                null);

        for (int i = 0; i < annotation.value().length; i++)
        {
            String[] constraints = orderEmpty ? InternalConstants.EMPTY_STRING_ARRAY : TapestryInternalUtils
                .splitMixinConstraints(annotation.order()[i]);

            model.addMixin(annotation.value()[i].getName(), constraints);
        }
    }

    private void addMixinTypes(final PlasticField field, final MutableEmbeddedComponentModel model)
    {
        Mixins annotation = field.getAnnotation(Mixins.class);

        if (annotation == null)
            return;

        for (String typeName : annotation.value())
        {
            Orderable<String> typeAndOrder = TapestryInternalUtils.mixinTypeAndOrder(typeName);
            String mixinClassName = resolver.resolveMixinTypeToClassName(typeAndOrder.getTarget());
            model.addMixin(mixinClassName, typeAndOrder.getConstraints());
        }
    }

    private void addParameters(final MutableEmbeddedComponentModel embedded, final String[] parameters)
    {
        for (String parameter : parameters)
        {
            KeyValue kv = TapestryInternalUtils.parseKeyValue(parameter);

            embedded.addParameter(kv.getKey(), kv.getValue());
        }
    }
}
