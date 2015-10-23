# <a name="top"></a> Iris Bugs & Caveats

[Fitting](#fit) | [Multiple Users](#multiple_users) | [Science Tools](#sci_tools) | [Graphics](#graphics) | [SED Data Types](#sed_data) | [Visualization/Interaction](#visuals)

The following is a list of known limitations of the Iris software. If you encounter a software bug or analysis caveat that is not listed here, please report it to the [CXC Helpdesk][helpdesk].

For troubleshooting the Iris installation, see the [Troubleshooting page][download_trouble].

### <a name="fit"></a> Fitting

#### General Fitting

  * The internal units for fitting in Iris are photons/sec/cm2/angstrom and Angstroms, and so the best-fit parameters values will always be in terms of $photons/s/cm^{2}/Angstrom$ and $Angstroms$, regardless of the user-defined unit settings. 

  * If one has the Model Window (the one with the list of models) and the Fit Window (the one with the optimization and statistics drop-down menu) both opened, some changes in the Model window (e.g. Thaw all, Freeze all) will not affect the Fit Window. The fit will be performed with an out-of-date model. If you have to change the model, it is preferable to close the Fit Window and open it again by clicking on "Fit" in the Model window. 

  * Clicking "Stop" or "Dismiss" while calculating the fit statistics in either the "Fit" or "Confidence" tab will disconnect Sherpa from the SAMP hub. This prevents any more fitting routines within Iris. You can reconnect Sherpa to Iris from the command line:
(in a new Terminal window or tab)

		% /bin/bash       # if in a C-shell
		$ source activate iris
		$ sherpa-samp
  
    Wait 10 seconds for Sherpa to reconnect, then you may continue you fitting session. 

  * When a SED is edited (e.g. by adding or removing segments) during a fitting session, the fitting window needs to be closed and the fitting session restarted from scratch. This is due to a bug in the fitting component. A dialog box will warn the user that if they change an SED during a fitting session, they should click OK when asked to close the fitting window itself. Clicking "Cancel" would leave the fitting manager in an undesired state, and out of sync with the rest of the Iris components.

    **WORKAROUND:** If this happens, users can save the fitting session, close the fitting window, and then load the saved file in a new session. Due to the nature of the bug, no warnings can be displayed when the user starts the fitting tool but edits the SED before performing any actual fit.

  * The Fitting Tool help bar is disabled. This is because the fitting tool help docs built in the Fitting Tool are for Specview; there is no information on fitting specifically in Iris. So to not confuse the readers with Specview-only operations, the help tool is left disabled.

#### Fitting Data with Custom Models

  * When a spectral model component is deleted from the Custom Model Manager, the Sherpa model expression being used for fitting, shown in the Fitting Tool window, is not automatically updated; it needs to be edited manually by the user. 

  * When fitting with a template model, the search method is automatically switched to a grid search over the templates included in the model. The template parameters are interpolated using the default K-nearest neighbor (KNN) algorithm in Sherpa 4.7, Iris v2.1's fitting engine. 

  * All custom table and template data files must be in ASCII format. Table and template models do not read from FITS files in Iris 2.0.1. 

  * The data contained in custom table and template models must have the same units that Iris uses internally for modeling and fitting. The x-axis units must be in Angstroms, and the y-axis units must be in photons/s/cm2/Angstrom. (These units match the internal units used for fitting in the original Specview, and which are still used in the Iris components derived from Specview. These components prepare data and model for fitting with Sherpa.) 

  * User-definied templates, table models, and functions are not listed by their Component IDs in the Components panel. They are listed as "template," "tablemodel," and "usermodel," respectively, no matter how many user-defined fitting functions are added to the Componets list. For example, if you added two user-defined functions named "modified_blackbody" and "scc," they will appear as "usermodel.c1" and "usermodel.c2" in the Components panel.

### <a name="multiple_users"></a> Multiple Users

  * Iris cannot currently be launched by multiple users on the same system.

### <a name="sci_tools"></a> Science Tools

#### Integration

  * Too few points in the region of interest of the Integrated SED will result in NaNs. In order to obtain good results you need to have a "good" density of points in the interval over which you are integrating. We are not sure how we can define "good." However, problems arise when one interpolates a whole SED with more than 1.5k bins: with too many points the SED Viewer chokes. 

  * Integration under model components: In order to integrate under model components in the Science Tools, the user must keep the Fitting Tool window open. 

  * Integration under model components: Only one filter/passband can be added to the list for each SED. It is not currently possible to calculate the flux for the same filter with different combinations of model components. This shortcoming of the GUI can be worked around in this way: one can save the results for each component/combination to file, input a different model expression, click calculate, and then save the new results with a different name.

#### SED Stacker 

  * Sometimes the Y-Units for normalizing by Integration may reset to the default unit after switching between Stacks. Please double check that the normalization units are correct before normalizing a Stack.

### <a name="graphics"></a> Graphics

  * Some of the Iris windows will not be fully rendered on systems with a very low screen size or screen resolution, in particular netbooks. 

  * OS X users may experience some issues with the internal windows in Iris. Internal Frames misbehave with some versions of Java on OS X. Gray lines may appear around windows after moving or resizing the windows. This does not impact the functionality of Iris, only the appearance. This is most notable on Java 1.8. These lines will disappear if the user focuses another window and clicks back on the original the window.

### <a name="sed_data"></a> SED Data Types

  * Iris is mainly a photometric SED analysis tool, but does provide support for short spectroscopic segments (<~1000 points); larger segments will take a long time to display and analyze. For more info, see the [SED Data Type FAQs entry][faq_sed].

### <a name="visuals"></a> Visualization/Interaction

#### Accessing Metadata

  * The single-point metadata display first tab (marked "Point metadata" when one rightclicks on a plotted point and get a tree representation) seems to be broken and might be removed in the next release. 

  * The Segment metadata table, both in single-point mode (right click on point) and entire SED mode (Metadata button) also uses some column names taken from that same old Spectrum Data Model. Column names in all-capitals are in fact FITS keywords that are associated to the corresponding utypes in that old Spectrum Data Model. Not all utypes have an associated FITS keyword, so in those cases, the software that builds the table uses as column name the 'name' of the corresponding field in the matadata structure. That may lead to entire sets of metadata to be ignored because these names may not be unique to each type. An example is the column named "Unit" in NED SEDs. There is a field named "Unit" in the Characterization.FluxAxis" element, and another field with same name in the Characterization.SpectralAxis" element. And likely in other places as well. There is no way for the table-building software to tell apart one from the other, once they are taken out of context (that is, striped out from the fully qualified type). Thus the software currently is retaining the last one it finds when traversing the metadata tree. For the interim solution I put in this release for the missing characterization entries, I used in the characterization spatial and time axis columns the fully qualified utype as column name. That is not very practical for the user (the strings are too long), but ensures unambiguous names, and also ensures that every utype gets its own column. 

  * Handling of other elements in the same table, such as SpectralAxis and FluxAxis, is still done by the old FITS/element name mapping. 

  * Applying an aperture correction to a Segment is not the same as applying an aperture correction to a single Photomeric Point. Setting the ratio of a Segment back to 1.0 will not set the fluxes to their original values. Whatever number is input into the ratio field is muliplied by the number that was *previously* in the field (i.e., if the number in the field was "0.25" and you change it to "1.0," the ratio will be "1.0 * 0.25 = 0.25.").

#### Missing error bars 

  * Spectral coordinate errors are ignored if present in the input SED data. This means that errors on the X axis are not displayed nor taken into account for modeling.

#### Unexpected changes to data display

  * Model components which are deleted in the *Fit -> Component* window, are not removed automatically from the model expression. 

  * In the window(s) that give individual access to spectral model parameters (double click on a row with a spectral component in the list displayed by the Fitting Tool), the Apply button is used just to apply changes in the Fit check box. Any other change, achieved by typing a value in a numeric or text field, is immediately applied as soon as the Return/Enter key, or the Tab key, are pressed. The change is also applied if, right after typing the new value, the mouse cursor is clicked on another field. 

  * When the user deletes a model component in the model editor window, Iris automatically renames and renumbers the remaining model components. This may oblige the user to then rewrite the model expression. For example, if the user creates the models:

		powerlaw.c1
		brokenpowerlaw.c2
		logparabola.c3
	   
    and defines the model expression as "c2+c3", then the model that is fit to the data is a broken power-law plus a log-parabola model. If the user deletes the power-law component c1, then the remaining models are automatically renamed:
   
		brokenpowerlaw.c1
		logparabola.c2
	   
  * The model expression, however, remains "c2+c3" now a log-parabola model plus a model component that does not exist. The user must rewrite the model expression as "c1+c2" before proceeding with the fit. 

  * Highlighting and deleting multiple models from the Components list simultaneously may result in the deletion of unhighlighted models. Delete models from the Components list one-at-a-time for desirable results. 

  * Fitting sessions for multiple SEDs are allowed in Iris. However, switching between sessions (selecting the SEDs currently being fit from the SED Builder) may cause the "Statistics" drop-down menu to disappear. To bring the "Statistics" menu back, close the "Fit Statistics and Optimization Methods" window, and reopen it from the main Fitting Tool window (i.e., click the "Fit" button). 

  * When switching from a populated SED to an empty SED, the Viewer and the Fit windows will not update.

|   |
|--:|
|[[Back to top][top]]|

<!-- extra links -->
[faq_sed]:			../faq/sed.html "FAQ SED Data Type"

<!-- threads -->
[sedstacker]: 		../threads/science/sedstacker/index.html "SED Stacker"
[science]: 			../threads/science/index.html "Shift, Interpolate, and Integrate"
[entry]: 			../threads/entry/index.html "Loading SED Data into Iris"
[fit]: 				../threads/fit/index.html "Modeling and Fiting SED Data"
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