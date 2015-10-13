# Future Direction

Iris version 2.1 provides new features that meet a few of the scientific requirements written at the time of Iris' inception. However, there remain several capabilities that need to be implemented. These additional SED building, analysis, and visualization functions will continue to be added in incremental releases.

Iris will continue to be maintained and enhanced by the Chandra X-ray Observatory software development group at the Smithsonian Astrophysical Observatory. We encourage our users to send us comments, suggestions, and concerns so that we can expand Iris to meet the needs of the science community. Please use the [CXC HelpDesk][helpdesk] with the tag "Iris" for feedback and suggestions.

### Short-term Additions to Iris

  * Increased template fitting capabilities, including:
    * deriving photometric redshifts from templates
    * include stellar/galactic templates in Iris distribution
    * providing a list of best-fit templates from user-input tolerance level
  * Users will be able to perform simple mathematical operations on a selected subset of points or spectral segments, e.g., to average multiple fluxes or spectral segments or perform an aperture correction with a given spatial model of the source.
  * Conlvolve SEDs with a generic function of the spectral coordinates; the user can define or be provided with a set of common analytical functions and instrumental profiles for this purpose, or use the best-fit model of the SED.

### Long-term Additions to Iris

#### Modeling and Fitting

  * Iris will include Bayesian statistics and Monte Carlo-Markov Chain (MCMC) fitting capabilities
  * Re-engineer the Fitting Tool so that all fitting capabilities are in one frame with multiple tabs, similar to the Science Tools frame. It will show descriptions of preset models and their functional forms, as well as making the fitting procedure more seamless.
  * Users will be able to visualize the parameter space after fitting an SED.
  * For template fitting: Iris will output a list of best-fit templates that fall within a user-defined tolerance level.
  * Users will be able to input their paramter values in the units of their choice (i.e. spectral parameter values won't have to be in Angstroms and flux values won't have to be in photons/s/cm**2/Angstrom). Iris will return the best-fit parameters in the user's preferred units.
  * Iris will be able to use and properly handle upper-limit fluxes in fits.
  * Iris will save a Sherpa script of the steps taken in a fitting session so that users can easily reproduce the same fit and analyze their data further in Sherpa.

#### Visualization and Interactive Editing

  * Iris will recognize and plot upper and lower flux limits in the SED Viewer.
  * Users will be able to define their own visualization preferences, including control over the colors and shape of points/segments, the default plot units, and other visualization capabilities.
  * Iris will plot the individual model components with the overall model during fitting sessions.
  * Iris will include PostScript as an option for saving the plot window to file.

#### SED Analysis

  * Iris will support the conversion of an aggregate SED to a rebinned SED; i.e., it will be able to bin a raw collection of SED segments and/or photometric points and provide the flux as a function of equally spaced spectral coordinate points, on a linear or logarithmic scale.
  * Users will be able to apply galactic extinction laws to their SEDs.
  * Astrophysics features to be added to the tool include the evaluation of the bolometric luminosity for a source from the measured flux, and an estimate of the distance provided by the user; and the ability for the user to change the default values of the cosmological parameters.

#### Infrastructure

  * Iris will support bulk analysis through a command-line interface. For example, the user would be able to load multiple data files at a time, apply corrections to multiple data sets, simultaneously fitting multiple SEDs with a model, and more.
  * Include a Python command-line interface in Iris. Users would have a Python interpreter connected to Iris from which they could build, edit, visualize and model using an Iris-Python API.
  * A web-based version of Iris is a thought. The intention would be to let users work on the same data sets together, quickly share their analysis, and not have to have an actual hard-copy of Iris on the users' computers.
  
  
<!-- Links -->
[helpdesk]: https://cxc.harvard.edu/helpdesk "CXC Helpdesk"