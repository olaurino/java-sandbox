# Iris How-to Guide

The Iris How-to Guide serves as a roadmap for navigating the Iris SED analysis tool. It provides instructions for downloading, installing, and running the Iris application; describes the individual features and capabilities of Iris; and ties it all together in an example SED analysis session.

** NEW **

  * Users can now integrate under fitted model components.
  * Groups of SEDs may be statistically combined together to create averaged SEDs with the new Iris tool, SED Stacker..
  * The version of Sherpa packaged with Iris has been updated to 4.7. This means template libraries and table models can now be combined with other libraries, models, and functions as the Model Expression in the Fiting Tool.
  * SEDs can now be saved in ASCII format.

----------------

## Download and Installation

  * [Download and install Iris][download]
  * [Troubleshooting][download_trouble]

## Introduction to Iris

  * [Loading SED Data into Iris][entry]
  * [Building and Managing SEDs][importer]
  * [Visualizing SED Data][plot]
  * [Shifting, Interpolating and Integrating SED Data][science]
  * [Modeling and Fitting SED Data][fit]
  * [Saving SED Data][save]
  * [Stastically Combining SEDs][sedstacker]

## Iris Usage Examples

  * [Analyzing SED Data in Iris][analysis] - Run through a full science thread by importing, editing, and fitting a blazar SED.

## Iris Plugins

  * [The Plugin Manager][plugin_manager]
  * [Developing Iris Plugins][sdk]

## Videos

  * Iris Video Tutorial - Explore all of Iris' features in two 10-minute videos.
    * [Part I](https://www.youtube.com/watch?v=GMaLgJfxJHI) - Building and Editing the SED
    * [Part II](https://www.youtube.com/watch?v=sok9Q3HZusc) - Modeling and Fitting the SED
    Please note that the videos were made for Iris v2.0 and do not show all the new features introduced in Iris 2.1 beta. The videos will be updated for the Iris v2.1 release.
  * [Overview of Iris features](https://www.youtube.com/watch?v=pF5ivLshPyw)
  * [Statistically Combine SEDs with Iris]()



<!-- threads -->
[sedstacker]: 		./threads/science/sedstacker/index.html "SED Stacker"
[science]: 			./threads/science/index.html "Shift, Interpolate, and Integrate"
[entry]: 			./threads/entry/index.html "Loading SED Data into Iris"
[fit]: 				./threads/fit/index.html "Modeling and Fiting SED Data"
[importer]: 		./threads/importer/index.html "Building and Managing SEDs"
[plot]: 			./threads/plot/index.html "Visualizing SED Data"
[analysis]: 		./threads/analysis/index.html "Analyzing SED Data in Iris"
[save]: 			./threads/save/index.html "Saving SED Data"
[sdk]: 				./threads/sdk/index.html "Developing Plugins: the Iris Software Development Kit"
[plugin_manager]: 	./threads/plugin_manager/index.html "Plugin Manager"

<!-- reference files -->
[download]: 		./download/index.html "Download and Installation"
[smoke_test]: 		./download/smoke_tests.html "Smoke Test"
[macosx105]:		./download/macosx_test.html "Mac OS X 10.5 Download Instructions"
[download_trouble]: ./bugs/smoke.html
[supported_files]: 	./references/importer_files.html
[models]: 			./references/models.html
[faq]: 				./faq/index.html "FAQs"
[releasenotes]: 	./releasenotes/index.html "Release Notes"
[publications]: 	./publications/index.html "Iris Publications"
[bugs]: 			./bugs/index.html "Bugs and Caveats"

<!-- CXC links -->
[helpdesk]:			/helpdesk/ "CXC HelpDesk"
[sao]:				http://cfa.harvard.edu/sao "Smithsonian Astrophysical Observatory"
[cxc]:				/ "Chandra X-Ray Observatory"
[sherpa]:			/sherpa/ "Sherpa"