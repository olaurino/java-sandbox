[Return to: FAQ index][faq]

# I am not able to fit SED data with zero-value uncertainties - why?

When a SED is fit using a chi-squared statistic in Iris, any data points with associated zero-value errors are excluded from the fit. If the fit is done with least-squares or maximum likelihood statistics, then the points with uncertainties set to zero are included - because the uncertainties are not used anyway by these statistics.

This design choice is intended as a safeguard against yielding potentially misleading fit results in the analysis of your SED data. Iris interprets a zero-value error not to mean that the uncertainty on the associated data value is actually zero, but that a measure of the uncertainty is not available for that particular photometric point.

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