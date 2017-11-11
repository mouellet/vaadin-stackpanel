# StackPanel Extension for Vaadin 8

StackPanel is a Panel extension that hides or shows the Panel's content when the Panel's header is clicked.

When stacked in a VerticalLayout, it will act as an Accordion but where more then one section can be displayed at a time.

## Download release

Official releases of this add-on are available at Vaadin Directory. For Maven instructions, download and reviews, go to http://vaadin.com/addon/stackpanel

## Building and running demo

git clone https://github.com/mouellet/vaadin-stackpanel.git
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for stackpanel-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your stackpanel-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the stackpanel-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/stackpanel-demo/ to see the application.

### Debugging client-side

The most common way of debugging and making changes to the client-side code is dev-mode. To create debug configuration for it, open stackpanel-demo project properties and click "Create Development Mode Launch" button on the Vaadin tab. Right-click newly added "GWT development mode for stackpanel-demo.launch" and choose Debug As > Debug Configurations... Open up Classpath tab for the development mode configuration and choose User Entries. Click Advanced... and select Add Folders. Choose Java and Resources under stackpanel/src/main and click ok. Now you are ready to start debugging the client-side code by clicking debug. Click Launch Default Browser button in the GWT Development Mode in the launched application. Now you can modify and breakpoints to client-side classes and see changes by reloading the web page. 

Another way of debugging client-side is superdev mode. To enable it, uncomment devModeRedirectEnabled line from the end of DemoWidgetSet.gwt.xml located under stackpanel-demo resources folder and compile the widgetset once by running vaadin:compile Maven target for stackpanel-demo. Refresh stackpanel-demo project resources by right clicking the project and choosing Refresh. Click "Create SuperDevMode Launch" button on the Vaadin tab of the stackpanel-demo project properties panel to create superder mode code server launch configuration and modify the class path as instructed above. After starting the code server by running SuperDevMode launch as Java application, you can navigate to http://localhost:8080/stackpanel-demo/?superdevmode. Now all code changes you do to your client side will get compiled as soon as you reload the web page. You can also access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings. 

 
## Release notes

### Version 2.0.1
- Add support for disabling toggle button

### Version 2.0.0
- Support for Vaadin 8 & Java 8

### Version 1.0.1
- Fixes issue #9

### Version 1.0.0
- Upgrading to Vaadin v7.7.0 

### Version 0.0.6
- Fixes issue #8

### Version 0.0.5
- Added toggle listener

### Version 0.0.4
- Bug fixes

### Version 0.0.3
- Added toggle icon functionality

### Version 0.0.2
- Added span tag for toggle button

### Version 0.0.1
- Initial release

## Roadmap

This component is developed as a hobby with no public roadmap or any guarantees of upcoming releases.

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

StackPanel is written by Mathieu Ouellet

# Developer Guide

## Getting started

For a more comprehensive example, see src/main/java/org/vaadin/addons/stackpanel/demo/DemoUI.java

## Features

### Opened by default, StackPanel can be programmaticaly open and close.

```
StackPanel stackPanel = StackPanel.extend(new Panel());
stackPanel.close();
assertFalse(stackPanel.isOpen());

stackPanel.open();
assertTrue(stackPanel.isOpen());
```

### Toggle icon with +/- by default, but can be set or disabled for each StackPanel.

```
StackPanel stackPanel = StackPanel.extend(new Panel());
stackPanel.setToggleDownIcon(FontAwesome.CARET_SQUARE_O_DOWN);
stackPanel.setToggleUpIcon(FontAwesome.CARET_SQUARE_O_UP);

stackPanel.setToggleIconsEnabled(false);
```
