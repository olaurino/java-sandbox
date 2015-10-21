[Return to: FAQ index][faq]

# After running "source activate iris; iris", an older version of Iris starts

Since Iris v2.1 beta, users start Iris with

	$ source activate iris
	$ iris
	   
If you had installed Iris v2.0.1 or lower at some point, you may have set "iris" as an alias in your shell config file (`$HOME/.bashrc`, `$HOME/.bash_profile`, or `$HOME/.cshrc`) to startup Iris. Please check your shell config file and see if there's an alias called "iris." Please either remove the alias or rename the alias to something other than "iris." Then source the shell config file, for example:

	$ source ~/.bashrc    # (or ~/.bash_profile or ~/.cshrc)
	   
Now when you run iris, the latest version should open.

[Return to: FAQ index][faq]


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

<!-- external links -->
[specdm]: http://www.ivoa.net/Documents/REC/DM/SpectrumDM-20071029.html "IVOA Spectrum Data Model"
[vao]: http://usvoa.org "Virtual Astronomical Observatory"
[sao]: http://cfa.harvard.edu/sao "Smithsonian Astrophysical Observatory"

<!-- Navigation -->
[toc]:				#toc
[top]:      		#top