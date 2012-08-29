package at.porscheinformatik.common.tapestry5.extension.test.components;

import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.Loop;

/**
 * @author Gerold Glaser (gla)
 * @since 13.03.2012
 *
 */
public class ConcreteExtensionWithInheritance extends AbstractExtension
{
    @Component(parameters = {"source=values", "value=currentIngredient"})
    private Loop<String> ingredients;
    
    // event is not handled in the component - should be caught on page
    @Component(parameters={"context=currentIngredient"})
    private ActionLink ingredientDetail;

    @Property
    private String currentIngredient;

    /**
     * event just to make tapestry happy. Event will bubbled and handled in the hierarchy.
     * @param seletIngredient the selected ingredient
     * @return false to bubble the event
     */
    @OnEvent(component="ingredientDetail")
    Object onIngredientDetail(String seletIngredient)
    {
        return false;
    }

    @Cached
    public String[] getValues()
    {
        return new String[]{"Oregano", "Mozzarella", "Prosciutto", "Salami"};
    }
}
