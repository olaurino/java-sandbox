# <a name="top"></a> How to Install Iris Plugins

------------------------------------------------------------------------

## <a name="overview"></a>Overview

#### Synopsis:

Iris plugins are external components that can add functionality to Iris.
For example, the ASDC Data Center, one of the data retrieval engines in
Iris, was a third-party plugin before Iris 2.0. Users may install
third-party plugins into Iris, or [create their own plugins][sdk].
This thread explains how to install plugins into Iris.

A note on third-party plugins: Since they are developed independently of
Iris, you shouldn't expect the plugins to have the same quality standard
of Iris. Also, plugins might have bugs that might impair the Iris
functionality. Please make sure that you trust the plugin authors, since
plugins can run arbitrary code on your system.

**Last Update:** 07 May 2015 - Updated for Iris 2.1 beta.

------------------------------------------------------------------------

## Plugin Manager: Installing and Uninstalling Plugins to Iris

Installing the plugin is simple and straightforward. In order to install
and uninstall plugins, you use the Iris Plugin Manager. This component
is available from the Tools-&gt;Plugin Manager menu.

![Iris screenshot image](./imgs/plugin-manager_small.png)

### Installing a plugin

To install a jar file containing plugins you click on the Load button. A
new window will allow you to either load a jar file from disk or provide
a URL address to the jar file.

![Iris screenshot image](./imgs/load-an-input-file.png)

Click on Load to close the window and let Iris load the plugins included
in the jar file. The plugin manager shows the list of installed plugins
and their metadata:

![Iris screenshot image](./imgs/plugin-manager-loaded2.png)

Notice that a jar file can contain an arbitrary number of plugins, and
each plugin can contribute any number of menus of buttons. Menus will be
added to either the File menu or the Tools menu. The new buttons and
menus should become available as soon as you install the plugin.

If we built the default Iris plugin and loaded it through the Plugin
Manager, we see the "Test Component" desktop Icon appear. When we click
on it, we get a new frame with our fun little message:

![Iris screenshot image](./imgs/hello-iris-world-ONLY2_small.png)

------------------------------------------------------------------------

### Uninstalling a plugin

If you want to uninstall the plugins in a jar file, simply right-click
on the name of the jar file and then click on "Remove".

|   |
|--:|
|[[Back to top][top]]|

------------------------------------------------------------------------

## <a name="history"></a> History

| Date          | Change							   |
|---------------|--------------------------------------|
|  27 Nov 2012  | created							   |
|  02 Oct 2013  | Added how to generate, develop and build custom plugins |
|  10 Oct 2013  | Moved generating, developing and building an Iris plugin to ["Creating Iris Plugins"][sdk] |
|  02 Dec 2013  | Updated for Iris 2.0.1 |
|  07 May 2015  | Updated for Iris 2.1 beta. |

----------------------------------

|   |
|--:|
|[[Back to top][top]]|


<!-- external links -->
[topcat]: http://www.star.bris.ac.uk/~mbt/topcat/#docs "TOPCAT"
[svo]:   http://svo2.cab.inta-csic.es/theory/fps/index.php?mode=voservice 

<!-- threads -->
[sedstacker]: 		../../threads/science/sedstacker/index.html "SED Stacker"
[science]: 			../../threads/science/index.html "Shift, Interpolate, and Integrate"
[entry]: 			../../threads/entry/index.html "Loading SED Data into Iris"
[fit]: 				../../threads/fits/index.html "Modeling and Fiting SED Data"
[importer]: 		../../threads/importer/index.html "Building and Managing SEDs"
[plot]: 			../../threads/plot/index.html "Visualizing SED Data"
[analysis]: 		../../threads/analysis/index.html "Analyzing SED Data in Iris"
[save]: 			../../threads/save/index.html "Saving SED Data"
[sdk]: 				../../threads/sdk/index.html "Developing Plugins: the Iris Software Development Kit"
[plugin_manager]: 	../../threads/plugin_manager/index.html "Plugin Manager"

<!-- extras (Iris models) -->
[brokenpowerlaw]:   ../../references/models.html#brokenpowerlaw "brokenpowerlaw"
[blackbody]:		../../references/models.html#blackbody "blackbody"

<!-- reference files -->
[download]: 		../../download/index.html "Download and Installation"
[smoke_test]: 		../../download/smoke_tests.html "Smoke Test"
[macosx105]:		../../download/macosx_test.html "Mac OS X 10.5 Download Instructions"
[download_trouble]: ../../bugs/smoke.html
[supported_files]: 	../../references/importer_files.html
[models]: 			../../references/models.html
[faq]: 				../../faq/index.html "FAQs"
[releasenotes]: 	../../releasenotes/index.html "Release Notes"
[publications]: 	../../publications/index.html "Iris Publications"
[bugs]: 			../../bugs/index.html "Bugs and Caveats"

<!-- CXC links -->
[helpdesk]:			/helpdesk/ "CXC HelpDesk"
[sao]:				http://cfa.harvard.edu/sao "Smithsonian Astrophysical Observatory"
[cxc]:				/ "Chandra X-Ray Observatory"
[sherpa]:			/sherpa/ "Sherpa"

<!-- Navigation -->
[toc]:				#toc
[top]:      		#top