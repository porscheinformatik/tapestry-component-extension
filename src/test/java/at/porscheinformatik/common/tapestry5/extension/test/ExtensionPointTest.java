package at.porscheinformatik.common.tapestry5.extension.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.apache.tapestry5.test.TapestryTestConstants;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

/**
 * @author Gerold Glaser (gla)
 * @since 13.03.2012
 *
 */
@Test
public class ExtensionPointTest extends SeleniumTestCase
{
    
    @BeforeTest(groups = "beforeStartup")
    void beforeStartup(XmlTest xmlTest)
    {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(TapestryTestConstants.WEB_APP_FOLDER_PARAMETER, "src/test/webapp");
        final String startupcommand = System.getProperty("tapestry.browser-start-command");
        if (startupcommand != null)
        {
            parameters.put(TapestryTestConstants.BROWSER_START_COMMAND_PARAMETER, startupcommand);
        }
        xmlTest.setParameters(parameters);
    }
    
    public void testTheExtensionPointWithProvidedExtension()
    {
        openLinks("Page with a provided extension");
        
        // page must have an extension point with hierarchy
        assertText("//h2[@id='componentidwithinheritance']", "Component with Inheritance. Id: 13");
        
        // page must have an extension point without hierarchy
        assertText("//h2[@id='componentidwithoutinheritance']", "Component without Inheritance. Id: 13");
        
        // click the detail to show event bubbling
        clickAndWait("link=" + getText("//a[contains(@href,'Mozzarella')]"));
        
        // check we must on the details page
        assertText("//h3[@id='componentId']", "Mozzarella is always good on a pizza!");
    }
    
    public void testTheExtensionPointWithoutProvidedExtension()
    {
        openLinks("Page without a provided extension");
        
        // check we must on the details page
        assertText("//h1[@id='pageWithoutExtension']", "Show offer extensions but no extension is available");
        
        // all divs must be empty or null
        // first div starting at once
        assertText("//div[1]", "");
        assertText("//div[2]", "");
    }
}
