# <a name="top"></a>Iris Models

The following models are provided by Iris for fitting to SED data. Models can be combined together in complex mathematical expressions to better model features of a SED. Models are assumed to be suitable for modeling the continuum, unless it is specifically noted that the model is for modeling spectral lines.

The *[Custom Model Manager interface][custom_models]* allows you to import into Iris your custom table, template, and Python user models, for use with the Iris Fitting Tool. Refer to the "[Modeling and Fitting SED Data][fit]" section of the Iris How-to Guide to learn how to load your own models into Iris and use them to fit SED data in Iris.

### Links to Function Definitions

 <div align="center">
  <table width="100%">
   <td>
    <a href="#absorptionedge">absorptionedge</a><br/>
 	<a href="#absorptiongaussian">absorptiongaussian</a><br/>
 	<a href="#absorptionlorentz">aborptionlorentz</a><br/>
 	<a href="#aborsptionvoigt">absorptionvoigt</a><br/>
 	<a href="#accretiondisk">accretiondisk</a><br/>
 	<a href="#atten">atten</a><br/>
 	<a href="#beta1d">beta1d</a><br/>
 	<a href="#blackbody">blackbody</a><br/>
 	<a href="#box1d">box1d</a><br/>
 	<a href="#bremsstrahlung">bremsstrahlung</a><br/>
 	<a href="#brokenpowerlaw">brokenpowerlaw</a><br/>
 	<a href="#ccm">ccm</a><br/>
   </td>
   <td>
	<a href="#const1d">const1d</a><br/>
	<a href="#cos">cos</a><br/>
	<a href="#dered">dered</a><br/>
	<a href="#edge">edge</a><br/>
	<a href="#emissiongaussian">emissiongaussian</a><br/>
	<a href="#emissionlorentz">emissionlorentz</a><br/>
	<a href="#emissionvoigt">emissionvoigt</a><br/>
	<a href="#erf">erf</a><br/>
	<a href="#erfc">erfc</a><br/>
	<a href="#exp">exp</a><br/>
	<a href="#exp10">exp10</a><br/>
	<a href="#fm">fm</a><br/>
   </td>
   <td>
	<a href="#gauss1d">gauss1d</a><br/>
	<a href="#lmc">lmc</a><br/>
	<a href="#log">log</a><br/>
	<a href="#log10">log10</a><br/>
	<a href="#logabsorption">logabsorption</a><br/>
	<a href="#logemission">logemission</a><br/>
	<a href="#logparabola">logparabola</a><br/>
	<a href="#lorentz1d">lorentz1d</a><br/>
	<a href="#normbeta1d">normbeta1d</a><br/>
	<a href="#normgauss1d">normgauss1d</a><br/>
	<a href="#opticalgaussian">opticalgaussian</a><br/>
	<a href="#poisson">poisson</a><br/>
   </td>
   <td>
	<a href="#polynomial">polynomial</a><br/>
	<a href="#powerlaw">powerlaw</a><br/>
	<a href="#recombination">recombination</a><br/>
	<a href="#seaton">seaton</a><br/>
	<a href="#sin">sin</a><br/>
	<a href="#sm">sm</a><br/>
	<a href="#smc">smc</a><br/>
	<a href="#sqrt">sqrt</a><br/>
	<a href="#stephi1d">stephi1d</a><br/>
	<a href="#steplo1d">steplo1d</a><br/>
	<a href="#tan">tan</a><br/>
	<a href="#xgal">xgal</a><br/>
   </td>
  </table>
 </div>

-----------------------------

<h3 id="absorptionedge">absorptionedge</h3>
<dd>
<p>
  A model of interstellar absorption, taking the functional form:
</p>

<div>
     <pre>
       f(x) = exp[-tau * (x / edgew)**index], where x &gt; edgew <br/>
       f(x) = 0,  where x &lt;= edgew          
     </pre>
</div>
 
 <p>
  Parameters:
 </p>

 <pre>
  edgew		Absorption edge (in Angstroms)
  tau		Optical depth
  index		index
 </pre>
</dd>

|   |
|--:|
|[[Back to top][top]]|



<h3 id="absorptiongaussian">absorptiongaussian</h3>
<dd>
 <p>
  A Gaussian model of an absorption feature (i.e., equivalent width),
  taking the functional form:
 </p>

<div>
     <pre>
       sigma = pos * fwhm / c / 2.354820044
       ampl = ewidth / sigma / 2.50662828

       f(x) = 1 - ampl * exp [-((x - pos) / sigma)**2 / 2] 
     </pre>
 </div>

<p>
  Parameters:
</p>
<div>
<pre>
  fwhm		The FWHM in Angstroms
  pos		Center of the Gaussian, in Angstroms
  ewidth	Equivalent width	
</pre>
</div>

</dd>

|   |
|--:|
|[[Back to top][top]]|




<h3 id="absorptionlorentz">absorptionlorentz</h3>
<dd>

 <p>
  A Lorentz model of an absorption feature, taking the functional
  form:
 </p>

     <div>
      <pre>
       f(x) = 1.0 - ewidth / ((1.0 + 4.0 * ((1.0/x - 1.0/pos) * pos *
          2.9979e5/fwhm)**2) * 1.571 * fwhm * pos/2.9979e5)

      </pre>
     </div>

<p>
  Parameters:
</p>

<div><pre>
  fwhm		The FWHM in Angstroms 
  pos		Center of the feature, in Angstroms 
  ewidth	Equivalent width
</pre></div>


</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="absorptionvoigt">absorptionvoigt</h3>
<dd>

<p>
  A Voigt model of an absorption feature; using the absorbed
  Gaussian to model the core, and the absorbed Lorentzian to model the
  wings of an absorption feature.
</p>
<p>
  The approximation presented in Astrophysical Formulae (K. R. Lang,
  1980, 2nd ed., p. 220) is used.  This approximation works best when
  the ratio between the FWHM of the Gaussian and Lorentzian
  sub-components is near unity.
</p>
<p>
  Parameters:
</p>

<div><pre>
  center	Center of the feature, in Angstroms
  ew		Equivalent width
  fwhm		The FWHM in Angstroms
  lg		Ratio of Lorenztian to Gaussian FWHMs
</pre></div>


</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="accretiondisk">
accretiondisk
</h3>
<dd>

<p>
  A model of emission due to an accretion disk, taking the functional
  form:
</p>

     <div><pre>
       f(x) = ampl * (x / norm)**(-beta) * exp (-ref / x)
     </pre></div>


<p>
  Parameters:
</p>

<div><pre>
  ref		Center of the spectral feature, in Angstroms	
  beta		index
  ampl		Amplitude of the feature
  norm		Normalization
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="atten">atten</h3>
<dd>
<p>
  This model calculates the transmission of the interstellar medium
  using the description of the ISM absorption of Rumph, Bowyer, &amp;
  Vennes 1994, AJ 107, 2108. It includes neutral He autoionization
  features. Between 1.2398 and 43.655 Angstroms (i.e. in the 0.28-10
  keV range) the model also accounts for metals as described in
  Morrison &amp; MacCammon 1983, ApJ 270, 119.
</p>
<p>
  The code uses the best available photoionization cross-sections to
  date from the atomic data literature and combines them in an
  arbitrary mixture of the three ionic species: HI, HeI, and HeII.
</p>
<p>
  The model assumes that the data are expressed in Angstroms.
</p>
<p>
  This model provided courtesy of Pat Jelinsky.
</p>
<p>
  Parameters:
</p>
<div><pre>
  hcol		N(HI) column (atoms cm^-2) 
  heiRatio	N(HeI)/N(HI)               
  heiiRatio	N(HeII)/N(HI)    
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="beta1d">
beta1d
</h3>
<dd>

<p>
  A Lorentz model with a varying power law, also known as a 1-D Beta
  model:
</p>
<div><pre>
       f(x) = f(r) = A*(1+[(x-xpos)/r_o]**2)**(-3*beta+1/2)
</pre></div>
<p>
  Parameters:
</p>

<div><pre>
  r0		core radius r_o 
  beta		beta index
  xpos		offset from x = 0
  ampl		amplitude A at x = xpos
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="blackbody">blackbody</h3>
<dd>
<p>
  The blackbody function, taking the functional form:
</p>

<div><pre>
       f(x) = (ampl * refer**5 * [exp(1.438786E8 / T / refer) - 1]) / 
	      (x**5 * [exp(1.438786E8 / T / x) - 1])
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  refer		Position of peak of blackbody curve, in Angstroms
  ampl		Amplitude of the blackbody function
  temperature	Temperature of the blackbody, in Kelvins
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="box1d">box1d</h3>
<dd>
<p>
  A box model:
</p>

<div><pre>
       f(x) = A if xlow &lt;= x &lt;= xhi
</pre></div>

<div><pre>
       f(x) = 0 otherwise
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  xlow		low cut-off
  xhi		high cut-off
  ampl		amplitude A  
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="bremsstrahlung">bremsstrahlung</h3>
<dd>

<p>
  The bremsstrahlung function, taking the functional form:
</p>
<div><pre>
       f(x) = ampl * (refer / x)**2 * exp (-1.438779E8 / x / T)
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  refer		Reference position, in Angstroms
  ampl		Amplitude of the bremsstrahlung function
  temperature	Temperature, in Kelvins
</pre></div>  


</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="brokenpowerlaw">brokenpowerlaw</h3>
<dd>

<p>
  A broken power law, taking the functional form:
</p>
    
<div><pre>
    f(x) = ampl * (x / refer) ** index1   
</pre></div>

<p>
  if x &lt; refer, and 
</p>

<div><pre>
       f(x) = ampl * (x / refer) ** index2   
</pre></div>

<p>
  if x &gt;= refer.
</p>

<p>
  Parameters:
</p>

<div><pre>
  refer		Position of the break, in Angstroms
  ampl		Amplitude
  index1	Index of first power law
  index2	Index of second power law
</pre></div>


</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="ccm">ccm</h3>
<dd>

<p>
  The interstellar extinction function, according to Cardelli,
  Clayton, and Mathis extinction curve (ApJ, 1989, 345, 245).
</p>

<p>
  Parameters:
</p>

<div><pre>
  ebv		E(B-V)		
  r		R
</pre></div>



</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="const1d">const1d</h3>
<dd>

<p>
  A constant amplitude model:
</p>

<div><pre>
       f(x) = A
</pre></div>

<p>
  A is limited to being &gt; 0. To model negative constant amplitudes, 
  multiply by -1.
</p>

<p>
  Parameters:
</p>

<div><pre>
  c0		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="cos">cos</h3>
<dd>

<p>
  A cosine model:
</p>

<div><pre>
       f(x) = A cos[2pi(x-x_off)/P]
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  period	period P, in same units as x
  offset	x offset x_off
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="dered">dered</h3>
<dd>

<p>
  This dereddening model uses the analytic formula for the mean extension 
  law described in Cardelli, Clayton, &amp; Mathis 1989, ApJ 345, 245:
</p>

<div><pre>
       A(lambda) = E(B-V) (aR_v+b) = 1.086 tau(lambda)
</pre></div>

<p>
  where tau(lambda) is the wavelength-dependent optical depth,
</p>

<div><pre>
       I(lambda) = I(0) exp[-tau(lambda)] ,
</pre></div>

<p>
  and a and b are computed using wavelength-dependent formulae which we 
  will not reproduce here, for the wavelength range 1000 A - 3.3 microns. 
  The relationship between the color excess and the column density is
</p>

<div><pre>
       E(B-V) = [ N_(Hgal) (10^20 cm^-2) ]/58.0
</pre></div>

<p>
  (Bohlin, Savage, &amp; Drake 1978, ApJ 224, 132). The value of the ratio of 
  total to selective extinction, R_v, is initially set to 3.1, the 
  standard value for the diffuse ISM. The final model form is:
</p>

<div><pre>
       I(lambda) = I(0) exp[-N_(Hgal)(aR_v+b)/58.0/1.086]
</pre></div>

<p>
  This model provided courtesy of Karl Forster.  The model assumes
  that the data are expressed in Angstroms.
</p>

<p>
  Parameters:
</p>

<div><pre>
  rv		total to selective extinction ratio R_v
  nhgal		absorbing column density N(H_gal)
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="edge">edge</h3>
<dd>
<p>
  A phenomenological photoabsorption edge model as a function of
  wavelength:
</p>

<div><pre>
       f'(x) = f(x)
</pre></div>

<p>
  if x &gt; lambda_b, and
</p>

<div><pre>
       f'(x) = f(x) exp[-A(x/lambda_b)**3]
</pre></div>

<p>
  otherwise.
</p>

<div><pre>
  space		energy (0) or wavelength (1)
  thresh	edge position E_b or lambda_b 
  abs		absorption coefficient A
</pre></div>


<p>
  Note: the "space" parameter should be kept equal to 1, as Iris
  always fits models to data using wavelength (in Angstroms) as the
  spectral coordinate.
</p>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="emissiongaussian">
emissiongaussian
</h3>
<dd>

<p>
  A Gaussian model of an emission feature, where:
</p>

<div><pre>
       sigma = pos * fwhm / c / 2.354820044
       delta = (x - pos) / sigma
</pre></div>

<p>
  if skew = 1,
</p>

<div><pre>
       f(x) = flux * exp (- delta**2 / 2) / sigma / 2.50662828
</pre></div>

<p>
  and, if skew != 1 and x &lt;= pos,
</p>

<div><pre>
       f(x) = 2 * flux * exp(- delta**2 /2)/ sigma /2.50662828/(1+skew)
</pre></div>

<p>
  and, if skew != 1 and x &gt; pos,
</p>
<div><pre>
       f(x) = 2 * flux * exp(- delta**2 /2/ skew**2)/ sigma /2.50662828/(1+skew)
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  fwhm		FWHM, in Angstroms
  pos		Center of feature, in Angstroms
  flux		Amplitude of Gaussian
  skew		skew
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="emissionlorentz">
emissionlorentz
</h3>
<dd>

<p>
  A Lorentz model of an emission feature, where:
</p>

<div><pre>
       f(x) = flux * pos * fwhm / c / 
              ([abs(x - pos)]**kurt + 
	      (pos * fwhm / c / 2)**2) / 6.283185308 
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  fwhm		FWHM, in Angstroms
  pos		Center of feature, in Angstroms
  flux		Amplitude of Lorentzian
  kurt		kurtosis
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="emissionvoigt">
emissionvoigt
</h3>
<dd>

<p>
  A model of an emission feature, where a Gaussian modeling the core
  is added to a Lorentzian modeling the wings.  The approximation
  presented in Astrophysical Formulae (K. R. Lang, 1980, 2nd ed.,
  p. 220) is used.  This approximation works best when the ratio
  between the FWHM of the Gaussian and Lorentzian sub-components is
  near unity.
</p>

<p>
  Parameters:
</p>

<div><pre>
  center	Center of the emission feature, in Angstroms
  flux		Amplitude of Voigt function
  fwhm		FWHM, in Angstroms
  lg		Ratio of Lorenztian to Gaussian FWHMs
</pre></div>	

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="erf">erf</h3>
<dd>

<p>
  The error function:
</p>

<div><pre>
       f(x) = A erf[(x-x_0)/sigma]
</pre></div>

<p>
  where
</p>

<div><pre>
       erf(y)=(2/sqrt(pi)) Int_0**y (exp(-t**2)) dt
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  ampl		amplitude A
  offset	offset x_off
  sigma		scaling factor sigma
</pre></div>

<p>
  erf is the complement of erfc, the complementary error function:
</p>

<div><pre>
       erfc(y) = 1 - erf(y)
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="erfc">
erfc
</h3>
<dd>

<p>
  The complementary error function:
</p>

<div><pre>
       f(x) = A erfc[(x-x_0)/sigma]
</pre></div>

<p>
  where
</p>

<div><pre>
       erfc(y)=(2/sqrt(pi)) Int_y**Inf (exp(-t**2)) dt
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  ampl		amplitude A
  offset	offset x_off
  sigma		scaling factor sigma
</pre></div>

<p>
  erfc is the complement of erf, the error function:
</p>

<div><pre>
       erfc(y) = 1 - erf(y)
</pre></div>
</dd>

|   |
|--:|
|[[Back to top][top]]|



<h3 id="exp">exp</h3>
<dd>
<p>
  The exponential function:
</p>
<div><pre>
       f(x) = A exp[C(x-x_off)]
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  offset	offset x_off
  coeff		coefficient C
  ampl		amplitude A
</pre></div>
</dd>

|   |
|--:|
|[[Back to top][top]]|




<h3 id="exp10">exp10</h3>
<dd>

<p>
  The exponential function, base 10:
</p>

<div><pre>
       f(x) = A 10**[C(x-x_off)]
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  offset	offset x_off
  coeff		coefficient C
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="gauss1d">
gauss1d
</h3>
<dd>

<p>
  An unnormalized Gaussian model:
</p>
<div><pre>
       f(x) = A exp[-f(x-x_o/F)**2]
</pre></div>

<p>
  The constant f = 2.7725887 = 4log2 relates the full-width at 
  half-maximum F to the Gaussian sigma so that F=sqrt(8log2)*sigma.
</p>

<p>
  Parameters:
</p>

<p>
  fwhm		full-width at half-maximum F
  pos		mean position x_o
  ampl		amplitude A
</p>

<p>
  This model is suitable for modeling spectral lines.
</p>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="log">
log
</h3>
<dd>

<p>
  The natural logarithm function:
</p>
<div><pre>
       f(x) = A log[C(x-x_off)]
</pre></div>
<p>
  Parameters:
</p>

<div><pre>
  offset	offset x_off
  coeff		coefficient C
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="log10">
log10
</h3>
<dd>

<p>
  The common (base 10) logarithm function:
</p>

<div><pre>
       f(x) = A log_10[C(x-x_off)]
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  offset	offset x_off
  coeff		coefficient C
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="logabsorption">
logabsorption
</h3>
<dd>
<p>
  A logarithmic absorption model, taking the functional form:
</p>

<div><pre>
       alpha = log(2) / log(1 + fwhm / 2 / c)
</pre></div>

<p>
  if x &gt;= pos,
</p>

<div><pre>
       f(x) = exp [-(tau * (x / pos)**alpha)]    
</pre></div>

<p>
  and if x &lt; pos,
</p>
<div><pre>
       f(x) = exp [-(tau * (x / pos)**(-1.0*alpha))]
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  fwhm		FWHM of the feature, in Angstroms
  pos		Center of the feature, in Angstroms
  tau		Optical depth
</pre></div>

  
</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="logemission">
logemission
</h3>
<dd>

<p>
  A logarithmic emission model, taking the functional form:
</p>

<div><pre>
       arg = log (2) / log(1 + fwhm / 2 / c)
       fmax = (arg - 1) * flux / 2 / c 
</pre></div>

<p>
  If skew = 1 and x &lt; pos,
</p>
<div><pre>
       f(x) = fmax * (x / pos)**arg
</pre></div>

<p>
  and, if skew = 1 and x &gt;= pos,
</p>

<div><pre>    
       f(x) = fmax * (x / pos)**(-1.0*arg)
</pre></div>

<p>
  If skew != 1,
</p>

<div><pre>
       arg1 = log (2) / log (1 + skew * fwhm / 2 / c)
       fmax = (arg - 1) * flux / c / [1 + (arg - 1) / (arg1 - 1)]
</pre></div>

<p>
  and if x &lt;= pos,
</p>

<div><pre>
       f(x) = f = fmax * (x / pos)**arg
</pre></div>

<p>
  and if x &gt; pos
</p>

<div><pre>
       f(x) = fmax * (x / pos)**(-1.0*arg1)
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  fwhm		FWHM of the feature, in Angstroms
  pos		Center of the feature, in Angstroms
  flux		Amplitude of the function
  skew		skew
</pre></div>


</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="logparabola">
logparabola
</h3>
<dd>


<p>
  The logparabola function, particularly useful for modeling
  high-energy continuum for blazars.
</p>

<div><pre>
      f(x) = ampl * (x / ref)**[-(c1 + c2 * log10(x / ref))]
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  ref		Reference position, in Angstroms
  c1		Index
  c2		Curvature of parabola
  ampl		Amplitude of logparabola function
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="lorentz1d">
lorentz1d
</h3>
<dd>

<p>
  The normalized Lorentz function:
</p>

<div><pre>
       f(x) = (A/pi) (F/2)/[(F/2)**2 + (x-x_o)**2] ,
</pre></div>

<p>
  where
</p>

<div><pre>
       Int_(-Inf)**(+Inf) f(x) dx = A
</pre></div>

<p>
  This means the normalization is equal to the total flux integrated
  under the curve.
</p>

<p>
  Parameters:
</p>

<div><pre>
  fwhm		full-width at half-maximum F
  pos		mean position x_o
  ampl		amplitude A
</pre></div>

<p>
  This model is suitable for modeling spectral lines.
</p>  

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="normbeta1d">
normbeta1d
</h3>
<dd>

<p>
  A normalized 1-D beta function appropriate for use fitting line 
  profiles:
</p>

<div><pre>
        f(x) = A * [1 + ((x-x_0)**2/w**2)] ** (-alpha)
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  pos		line centroid x_0 
  width		line width w
  index		index alpha
  ampl		line amplitude A - equal to the value of the
		constant for which the integral of the model is
		equal to 1
</pre></div>

<p>
  This model is suitable for modeling spectral lines.
</p>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="normgauss1d">
normgauss1d
</h3>
<dd>

<p>
  The normalized Gaussian function:
</p>
<div><pre>
       f(x) = [A/sqrt(pi/f)/F] exp[-f(x-x_o/F)**2]
</pre></div>
<p>
  where
</p>

<div><pre>
       Int_(-Inf)**(+Inf) dx f(x) = A
</pre></div>

<p>
  This means the normalization is equal to the total flux integrated
  under the curve.
</p>

<p>
  The constant f = 2.7725887 = 4log2 relates the full-width at 
  half-maximum F to the Gaussian sigma so that F=sqrt(8log2)*sigma.
</p>

<p>
  Parameters:
</p>

<div><pre>
  fwhm		full-width at half-maximum F
  pos		mean position x_o
  ampl		amplitude A
</pre></div>


<p>
  This model is suitable for modeling spectral lines.
</p>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="opticalgaussian">opticalgaussian</h3>
<dd>

<p>
  A Gaussian model of an absorption feature, with optical depth as a
  parameter, taking the functional form:
</p>

<div><pre>
       sigma = pos * fwhm / c / 2.354820044
       ampl = equiv_width / sigma / 2.50662828
</pre></div>

<div><pre>
       f(x) = exp(-tau * exp(-((x - pos) / sigma)**2 / 2))
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  fwhm		The FWHM in Angstroms
  pos		Center of the Gaussian, in Angstroms
  tau		Optical depth
  limit		
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3>poisson</h3>
<dd>

<p>
  A model expressing the ratio of two Poisson distributions of mean
  mu, one for which the random variable is x, and the other for which
  the random variable is equal to mu itself:
</p>

<div><pre>
       f(x) = A (mu!/x!) mu**(x-mu)
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  mean		mean mu
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="polynomial">polynomial</h3>
<dd>

<p>
  A 1-D polynomial of order &lt;= 5:
</p>

<div><pre>
       f(x) = sum_(i=0)**5 c_i (x-x_off)**i ,
</pre></div>

<p>
  where the coefficients c_i are the parameters numbered i+1, and x_off 
  is parameter number 7.
</p>
<p>
  Note that there is a degeneracy in the parameters, so it is
  recommended to set at least one of c_0 or x_off to zero and freeze
  it; thawing both may lead to unpredicted results.
</p>

<p>
  Note also that all coefficients except c_0 are default frozen, so
  that the default polynomial model is a constant.
</p>

<p>
  Parameters:
</p>

<div><pre>
  c0		coefficient c_0
  c1		coefficient c_1
  c2		coefficient c_2
  c3		coefficient c_3
  c4		coefficient c_4
  c5		coefficient c_5
  offset	offset for x x_off
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="powerlaw">powerlaw</h3>
<dd>

<p>
  A power law function, taking the functional form:
</p>
<div><pre>
       f(x) = ampl * (x / refer) ** index
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  refer		Reference position, in Angstroms
  ampl		Amplitude
  index		Index of power law
</pre></div>
</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3>
recombination
</h3>
<dd>

<p>
  A model of the continuum emission due to recombination, taking the
  functional form:
</p>

<p>
  If x &gt;= refer,
</p>

<div><pre>
       f(x) = ampl * exp(-(x - refer)**2 / 
              (refer * fwhm / c / 2.354820044)**2 / 2)
</pre></div>

<p>
  and if x &lt; refer,
</p>
<div><pre>
       f(x) = ampl * (refer / x)**2 * exp -(1.440E8 * (1/x - 1/refer)/T)
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  refer		Reference position, in Angstroms
  ampl		Amplitude
  temperature	Temperature, in Kelvins
  fwhm		FWHM, in Angstroms
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 is="sin">sin</h3>
<dd>

<p>
  A sine model:
</p>

<div><pre>
       f(x) = A sin[2pi(x-x_off)/P]
</pre></div>
<p>
  Parameters:
</p>
<div><pre>
  period	period P, in same units as x
  offset	x offset x_off
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="sqrt">sqrt</h3>
<dd>

<p>
  A square-root model:
</p>
<div><pre>
       f(x) = A sqrt(x-x_off)
</pre></div>
<p>
  Parameters:
</p>

<div><pre>
  offset	offset x_off
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="stephi1d">
stephi1d
</h3>
<dd>
<p>
  A step model:
</p>
<div><pre>
       f(x) = A if x &gt; x_cut
</pre></div>
<p>
  and
</p>

<div><pre>
       f(x) = 0 otherwise.
</pre></div>

<p>
  Parameters:
</p>
<div><pre>
  xcut		cut-off x_cut
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="steplo1d">
steplo1d
</h3>
<dd>

<p>
  A step model:
</p>
<div><pre>
       f(x) = A if x &lt; x_cut
</pre></div>
<p>
  and
</p>

<div><pre>
       f(x) = 0 otherwise.
</pre></div>

<p>
  Parameters:
</p>
<div><pre>
  xcut		cut-off x_cut
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="tan">
tan
</h3>
<dd>

<p>
  A tangent model:
</p>
<div><pre>
       f(x) = A sin[2pi(x-x_off)/P]
</pre></div>

<p>
  Parameters:
</p>

<div><pre>
  period	period P, in same units as x
  offset	x offset x_off
  ampl		amplitude A
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="xgal">xgal</h3>
<dd>

<p>
  This model is the extragalactic extinction function of Calzetti,
  Kinney and Storchi-Bergmann, 1994, ApJ, 429, 582.
</p>
<p>
  Parameters:
</p>
<div><pre>
  ebv		E(B-V)
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="seaton">seaton</h3>
<dd>
<p>
  This model is the galactic extinction from Seaton, M. J. 1979, MNRAS
  187, 73P.  The formulae are based on an adopted value of R = 3.20.
</p>
<p>
  This function implements Seaton's function as originally
  implemented in STScI's Synphot program.
</p>
<p>
  For wavelengths &gt; 3704 Angstrom, the function interpolates
  linearly in 1/lambda in Seaton's table 3. For wavelengths
  &lt; 3704 Angstrom, the class uses the formulae from Seaton's
  table 2. The formulae match at the endpoints of their respective
  intervals. There is a mismatch of 0.009 mag/ebmv at nu=2.7
  (lambda=3704 Angstrom). Seaton's tabulated value of 1.44 mag at
  1/lambda = 1.1 may be in error; 1.64 seems more consistent with
  his other values.
</p>
<p>
  Wavelength range allowed is 0.1 to 1.0 microns; outside this
  range, the class extrapolates the function.
</p>
<p>
  Parameters:
</p>
<div><pre>
  ebv		E(B-V)
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="smc">smc</h3>
<dd>
<p>
  This model is the extinction curve for the SMC, as given in
  Prevot et al., 1984, A&amp;A, 132, 389-392.
</p>
<p>
  Parameters:
</p>
<div><pre>
  ebv		E(B-V)
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="sm">sm</h3>
<dd>
<p>
  This model is the galactic extinction curve according to
  Savage &amp; Mathis, 1979, ARA&amp;A, 17, 73-111.
</p>
<p>
  Parameters:
</p>

<div><pre>
  ebv		E(B-V)
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="lmc">lmc</h3>
<dd>
<p>
  This model is the extinction curve for the LMC, as given in
  Howart, 1983 MNRAS, 203, 301.
</p>

<p>
  Parameters:
</p>
<div><pre>
  ebv		E(B-V)
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|

<h3 id="fm">fm</h3>
<dd>
<p>
  This model is the Fitzpatrick and Massa extinction curve with Drude
  UV bump (ApJ, 1988, 328, 734).
</p>
<p>
  Parameters:
</p>
<div><pre>
  ebv		E(B-V)
  x0		Offset
  width		Width of Drude bump
  c1		Coefficient 1
  c2		Coefficient 2
  c3		Coefficient 3
  c4		Coefficient 4
</pre></div>

</dd>

|   |
|--:|
|[[Back to top][top]]|


<!-- extra links -->
[custom_models]:	../threads/fits/index.html#custom_models "Custom Model Manager"

<!-- external links-->



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