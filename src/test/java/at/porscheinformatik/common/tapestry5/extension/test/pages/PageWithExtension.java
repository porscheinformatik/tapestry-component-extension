package at.porscheinformatik.common.tapestry5.extension.test.pages;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.services.PageRenderLinkSource;

import at.porscheinformatik.common.tapestry5.extension.annotation.ExtensionPoint;
import at.porscheinformatik.common.tapestry5.extension.test.components.AbstractExtension;

/**
 * @author Gerold Glaser (gla)
 * @since 13.03.2012
 */
public class PageWithExtension
{
    public static final String EXT_POINT_WITH_INHERITANCE = "extPointWithInheritance";

    public static final String EXT_POINT_WITHOUT_INHERITANCE = "extPointWithoutInheritance";

    @ExtensionPoint(id = EXT_POINT_WITH_INHERITANCE)
    @Component(parameters = "id=prop:id")
    private AbstractExtension extensionPointWithInheritance;

    @ExtensionPoint(id = EXT_POINT_WITHOUT_INHERITANCE)
    @Component(parameters = "id=prop:id")
    private Object extensionPointWithoutInheritance;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    /**
     * event triggered from the
     * 
     * @param seletIngredient the selected ingredient
     * @return the detail page of the ingredient
     */
    Object onActionFromExtensionPointWithInheritance(String seletIngredient)
    {
        return pageRenderLinkSource.createPageRenderLinkWithContext(Detail.class, seletIngredient);
    }

    public Long getId()
    {
        return 13L;
    }
}
