[Return to: FAQ index][faq]

# Which fit statistics and optimization methods are available?

A Sherpa fit in Iris can be done using a least-squares statistic, chi-squared (with various methods for estimating the variances used by chi-squared), or with either of two maximum likelihood statistics that are useful when the data have low numbers of counts. The Sherpa fit statistics and optimization methods available in Iris are listed below with brief descriptions; refer to the [Statistics][sherpa_stats] and Optimization[sherpa_opt] pages of the Sherpa website for a full and detailed explanation of each.

## Fit Optimization Methods

  * **Levenberg-Marquardt** - a method to find the best-fit model parameter values, by finding the local minimum in parameter space; the functions to be minimized are nonlinear least-squares functions of the model parameters.
  * **Nelder-Mead** - a method to find the best-fit model parameter values, by finding the local minimum in parameter space; a direct search method is used to continually move "downhill" in parameter space, until settling in a local minimum.
  * **Monte Carlo** - a method using the differential evolution algorithm, to find a global minimum in parameter space; the search is seeded with randomly selected starting points, and can continue the search for the true minimum where the other methods could get stuck in local minima that do not represent the actual best fit. (Most useful for complex models and fits that could explore complicated regions of parameter space.)

## Fit Statistics

  * **least-squares** - Sum of the squares of the differences between data and model values.
  * **Cash** - A maximum likelihood function based on Poisson statistics. More suitable for counts data than for fluxes.
  * **C-stat** - A maximum likelihood function similar to Cash, but with a chi-squared-like probability distribution. More suitable for counts data than for fluxes.
  * **chi2datavar** - Chi-squared statistic with variance computed from the data. If measured errors are provided, the variance is taken from these errors; else, the variance is computed from the y-values of the data points. This is the preferred chi-squared option to be used in Iris.
  * **chi2gehrels** - Chi-squared statistic, where the variance is computed with a function from Gehrels et al. Suitable for low counts data (e.g., X-ray data) to correct for bias in using chi-squared.
  * **chi2modvar** - Chi-squared statistic, where the variance is computed from the model values instead of data.
  * **chi2constvar** - Chi-squared statistic, where the variance is set to be a constant value. That constant is the average of the y-values of the data points.
  * **chi2xspecvar** - Chi-squared statistic, where the variance is computed as the X-ray spectral fitting program XSPEC would compute the variance (i.e., where the variance would be less than one, reset it to one). More suitable for low counts data (e.g., X-ray data).

The Iris default fitting method and statistic are "neldermead" and "leastsq", respectively, which represent good choices for a robust, quick, initial fit of a relatively simple model to a data set covering potentially many orders of magnitude in flux and/or wavelength.

[Return to: FAQ index][faq]


<!-- extra links -->
[sherpa_stats]:		/sherpa/statistics/index.html "Sherpa Statistics"
[sherpa_opt]:		/sherpa/methods/index.html "Sherpa Optimization Methods"

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