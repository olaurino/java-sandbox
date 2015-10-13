# <a name="top"></a>Smoke Test

The 'smoketest' included in the Iris software package is available to help you verify that Iris has been installed correctly and is working properly on your computer platform.

To launch the Iris smoke test, activate the Iris environment (if not already activated), type "`smoketest`" like shown below:

	$ source activate iris   # activate the Iris environment
	$ iris smoketest

The test will simulate user interaction with various components of Iris; e.g., it opens a SED in the SED Builder and then performs a fit to this SED using the Iris Fitting Tool.

If any of the actions executed by the test fail, an error message is displayed. In some cases, the message returned is very technical; this information should be included in a request for help from [user support][helpdesk]. In other cases, e.g., if the installed version of Iris is incompatible with your operating system, a more user-friendly message will be displayed.

In some rare instances, the smoke test will fail but will hang indefinitely without printing an error message and quitting; this could happen if one of the various components of Iris failed to load in time for the other components to communicate with it. In this case, you can specify a timeout in seconds when you launch the test, which will give the components of Iris this amount of time to communicate before exiting. Increasing the timeout value can help when installing Iris on a system with low memory or an older CPU.

	$ iris smoketest 30

It is also possible, but very unlikely, that the smoke test will fail if the user already has a SAMP hub open (when Iris is launched, it starts a SAMP hub of its own to allow the various components of the application to communicate). In this case, in lieu of the smoke test packaged with Iris, the user should verify that a SAMP client called "Sherpa" is attached to the existing hub when they launch Iris (this can take several seconds after starting the application).

The following is an example of a successful smoke test execution with the default timeout (20s):

	$ iris smoketest




	This test will assess whether your installation is capable of performing some basic operations
	The test should take less than a minute. However, the actual time will depend on your system properties.




	========================================
	Starting Smoke Test with timeout: 20
	========================================
	Waiting for the SAMP controller...
	Starting Sherpa...
	Pinging Sherpa...
	Sherpa response status: samp.ok
	Hub response status: samp.ok
	Creating a Setup for the SED Builder...
	Importing the file...
	Setting up a SAMP SED receiver...
	Waiting for the SAMP SED receiver...
	Broadcasting the SED...
	Sed received, and it looks good!
	Preparing the Sherpa call...
	Calling Sherpa and waiting for results...
	Verifying Sherpa response...
	Running sample model integration...

	===============================
	Everything seems to be working!
	===============================

If you have any other trouble running the smoke test, see [Installation Troubleshooting][download_trouble], or contact the [CXC HelpDesk][helpdesk] with the tag "Iris."


|   |
|--:|
|[[Back to top][top]]|

<!-- external links-->

[oracle]:			http://www.oracle.com/technetwork/java/javase/index-137561.html "Oracle"
[mast]:     		http://mast.stsci.edu/portal/Mashup/Clients/Mast/Portal.html "MAST Portal"
[topcat]:   		http://www.star.bris.ac.uk/~mbt/topcat/ "TOPCAT"
[specview]: 		http://www.stsci.edu/resources/software_hardware/spe%20cview/ "Specview"
[conda_osx]:		http://repo.continuum.io/miniconda/Miniconda-3.8.3-MacOSX-x86_64.sh "OS X Miniconda"
[conda_l64]:		http://repo.continuum.io/miniconda/Miniconda-3.8.3-Linux-x86_64.sh "Linux 64 Miniconda"
[conda_l32]:		http://repo.continuum.io/miniconda/Miniconda-3.8.3-Linux-x86.sh "Linux 32 Miniconda"

<!-- threads -->
[sedstacker]: 		../threads/science/sedstacker/index.html "SED Stacker"
[science]: 			../threads/science/index.html "Shift, Interpolate, and Integrate"
[entry]: 			../threads/entry/index.html "Loading SED Data into Iris"
[fit]: 				../threads/fits/index.html "Modeling and Fiting SED Data"
[importer]: 		../threads/importer/index.html "Building and Managing SEDs"
[plot]: 			../threads/plot/index.html "Visualizing SED Data"
[analysis]: 		../threads/analysis/index.html "Analyzing SED Data in Iris"
[save]: 			../threads/save/index.html "Saving SED Data"
[sdk]: 				../threads/sdk/index.html "Developing Plugins: the Iris Software Development Kit"
[plugin_manager]: 	../threads/plugin_manager/index.html "Plugin Manager"

<!-- reference files -->
[download]: 		../download/index.html "Download and Installation"
[smoke_test]: 		../download/smoke_tests.html "Smoke Test"
[macosx105]:		../download/macosx_test.html "Mac OS X 10.5 Download Instructions"
[download_trouble]: ../bugs/smoke.html
[supported_files]: 	../references/importer_files.html
[models]: 			../references/models.html
[faq]: 				../faq/index.html "FAQs"
[releasenotes]: 	../releasenotes/index.html "Release Notes"
[publications]: 	../publications/index.html "Iris Publications"
[bugs]: 			../bugs/index.html "Bugs and Caveats"

<!-- CXC links -->
[helpdesk]:			/helpdesk/ "CXC HelpDesk"
[sao]:				http://cfa.harvard.edu/sao "Smithsonian Astrophysical Observatory"
[cxc]:				/ "Chandra X-Ray Observatory"
[sherpa]:			/sherpa/ "Sherpa"

<!-- Navigation -->
[toc]:				#toc
[top]:      		#top