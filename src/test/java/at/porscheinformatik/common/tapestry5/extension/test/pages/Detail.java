package at.porscheinformatik.common.tapestry5.extension.test.pages;

import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Gerold Glaser (gla)
 * @since 13.03.2012
 *
 */
public class Detail
{
    @ActivationRequestParameter
    @Property
    private String ingredientDetail;
    
    boolean onActivate(String ingredientDetail)
    {
        this.ingredientDetail = ingredientDetail;
        return true;
    }
}
