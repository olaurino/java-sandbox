# Iris Release Notes


### Iris v2.1 Release Notes

The following updates have been made since the 2.0.1 patch release:

#### New Features

  * \#44: SED Stacker: Users can statistically combine a group of SEDs into a single, representative SED. The group of SEDs, called a "Stack," is made up of SEDs already loaded in the SED Builder. The tool provides optional bulk redshifting and normalization of the SEDs before combination. Users combine the SEDs together using average, weighted average, or a summation of the SEDs, with linear or logarithmic binning.
Enhancements
  * \#12: Units were reviewed to ensure that the different Iris components support the same set of units, and to address users requests to deal with additional units. 

    The following units where added to the SED Importer:

    * mm
    * uJy
    * Watt/m2/nm
    * Watt/cm2/um
    * Rayleigh/A
    * 1/um
    * km/s @ CO (11.5 GHz)
    * km/s @ 21 cm	

    GeV was added to the SED Viewer. 

    photon/cm2/s was removed from SED Importer as this unit is not supported by the SED Viewer. 

  * \#17: Updated NED logo to latest version.
  * \#34: SEDs can now be saved in ASCII format in addition to VOTable and FITS
  * \#37: Upgraded Sherpa to version 4.7. This means users can combine templates with other templates and with regular models, and templates can be fitted continuously.
  * \#41: Users can now integrate models in addition to data, including individual model components or arbitrary combinations of components.
  * \#42: The model expression is now written in the fit results ASCII file.
  * \#53 and #58: New log locations and conventions.

  * Logs are now created in $HOME/.vao/iris. The GUI logs are created in $HOME/.vao/iris/logs. The directory is different because the GUI logs are created for each session and their number grows over time. Users can change the location and name of the log files by exporting the environment variables IRIS\_LOG\_LOCATION and IRIS\_LOG\_NAME. A timestamp for the session start is added to the log file name. The timestamp is now human readable.
    * \#83 - Updated the "About Iris" window to reflect change in ownership from VAO to SAO.
    * \#84 - Updated the Iris logo.

#### Bug Fixes

  * \#1: Iris startup script now correctly sets IRIS_DOC to the correct version number.
  * \#30: Users can now add new data to redshifted SEDs.
  * \#54: The output units for integration were mistakenly indicated as Jy (flux density) although the output was flux. Also, the flux was calculated integrating over the wavelength space, which resulted in units that could not easily be translated into meaningful physical units.
  * \#55: The fitting tools now displays a warning message when the selected SED is empty, instead of doing nothing.
  * \#59: Regression from bug fix #52 (see Caveats) that prevented SED Viewer to update SEDs when new Segments were added/removed.
  * \#60: Fixed Cancel button behavior in confirmation dialog box when closing the Fitting tools window. The window does not disappear anymore when users cancel the action of closing the window itself.
  * \#61 and #62: Fixed bugs related to the way sherpa-samp checks the connection to the SAMP hub. The fix reduces the resources required to poll the SAMP hub, and avoids sherpa-samp to disconnect from the hub at random times during the Iris execution, which could lead to incomplete tasks in Iris.
  * \#71 - Fixed bug where the flux errors weren't sorted correctly when redshifting multiple-segment SEDs.
  * \#80 - Passbands no longer disappear from the integration panel Results list. If a user adds a user-defined passband, then adds photometry filters, the passband no longer disappears from the results list until the user clicks "Calculate."
Caveats and Known Bugs

-----------------------

*The following is a list of caveats and bugs that were documented this release. For the complete report of caveats and known bugs, please view the list in [Bugs & Caveats][bugs]. If you experience any other issues with Iris, please send us a message at the [CXC HelpDesk][helpdesk].*

  * Integration under model components: Only one filter/passband can be added to the list for each SED. It is not currently possible to calculate the flux for the same filter with different combinations of model components. This shortcoming of the GUI can be worked around in this way: one can save the results for each component/combination to file, input a different model expression, click calculate, and then save the new results with a different name.
  
  * Internal Frames misbehave with some versions of Java on OS X. Gray lines may appear around windows after moving or resizing the windows. This does not impact the functionality of Iris, only the appearance. This is most notable on Java 1.8.
  
  * The Fitting Tool help bar is disabled. This is because the fitting tool help docs built in the Fitting Tool are for Specview; there is no information on fitting specifically in Iris. So to not confuse the readers with Specview-only operations, the help tool is left disabled.
  
  * When switching from a populated SED to an empty SED, the Viewer and the Fit windows will not update.
  
  * When a SED is edited (e.g. by adding or removing segments) during a fitting session, the fitting window needs to be closed and the fitting session restarted from scratch. This is due to a bug in the fitting component. A dialog box will warn the user that if they change an SED during a fitting session, they should click OK when asked to close the fitting window itself. Clicking "Cancel" would leave the fitting manager in an undesired state, and out of sync with the rest of the Iris components. 

    **WORKAROUND:** If this happens, users can save the fitting session, close the fitting window, and then load the saved file in a new session. Due to the nature of the bug, no warnings can be displayed when the user starts the fitting tool but edits the SED before performing any actual fit. 
   
  * If the user has two fitting sessions open, and has fitted their SED without closing the Fit Statistics and Optimization Method window for either SED, switching back and forth will cause one of the "Statistics" drop-down menus to disappear. 

    **WORKAROUND:** Close the Fit Statistics window and re-open it from the main Fitting window (i.e., click the "Fit" button).

-------------------------

The following bugs and caveats will be addressed before the Iris 2.1 release.

  * In SED Stacker, sometimes the Y-Units for normalizing by Integration may reset to the default unit after switching between Stacks.
  
-------------------------

### Previous Release Notes

  * [v2.0.1](/iris/v2.0.1/releasenotes/index.html)
  * [v2.0](/iris/v2.0/releasenotes/index.html)
  * [v1.2](/iris/v1.2/releasenotes/index.html)
  * [v1.1](/iris/v1.1/releasenotes/index.html)
  * [v1.0](/iris/v1.0/releasenotes/index.html)
  

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