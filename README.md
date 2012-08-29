Tapestry Component Extension
============================

Dynamically inject components into another module.

## Usage

You should provide a place in your page where the extension can be injected.
The target page (Target.tml) should reserve the place:
	
	
	<div t:id="extensionPointWithInheritance"></div>
	
In the Java class register the component extension as a normal component. It has not to be from type Object you can
also provide a base class. The providing component can get parameters like a normal tapestry component.

	@ExtensionPoint(id = "extensionPointId")
    @Component(parameters = "id=prop:id")
    private Object extensionPointWithoutInheritance;
    
In the providing Module you must contribute your extension to the placeholder. This is done with following contribution:

    public static void contributeExtensionPointService(MappedConfiguration<String, ContributableExtension> configuration)
    {
    	// extensionPointId to be replace with the contributed id
        configuration.add("concreteExtension", new ContributableExtension(extensionPointId,
        	// the component which is contributed
            ConcreteExtensionWithInheritance.class));
    }
    
For activation add the at.porscheinformatik.common.tapestry5.extension.services.TapestyExtensionModule as a Tapestry Module.

Please see the test package with the webapp for further information.