package at.porscheinformatik.common.tapestry5.extension.test.components;

import org.apache.tapestry5.annotations.Parameter;

/**
 * @author Gerold Glaser (gla)
 * @since 13.03.2012
 *
 */
public class AbstractExtension
{
    @Parameter(required = true)
    private Long id;

    public Long getId()
    {
        return id;
    }
}
