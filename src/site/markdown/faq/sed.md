[Return to: FAQ index][faq]

# Which SED data types are supported by Iris?

The Spectral Energy Distribution (SED) of a source refers to the measured flux, flux density, or luminosity of the source per unit wavelength, frequency, or energy. An aggregate SED can be built by combining photometric points and/or spectroscopic segments from various observatories across a wide spectral range; this is one of the functions of the Iris SED analysis tool. **Iris fully supports photometric data, and provides partial support for spectroscopic data.** The distinction between a photometric point and a spectroscopic segment is explained below:

<br/>
<dd>
<strong>Photometric points</strong>
<p>
A photometric point is defined by three numbers (s, f(s), t), namely a spectral coordinate <em>s</em> (either a wavelength, frequency, or energy), a flux <em>f(s)</em> (or flux density, or luminosity) measured at that spectral coordinate, and the time <em>t</em> of the observation.
</p>
</dd>

<dd>
<strong>Spectroscopic segments</strong>
<p>
A spectroscopic segment consists of a relatively tightly spaced collection of adjacent spectral coordinates and corresponding fluxes (or luminosities): (<em>s<sub>i</sub>, f(s<sub>i</sub></em>).
</p>
</dd>
<br/>


**Iris is mainly a photometric SED analysis tool**, but does provide support for short spectroscopic segments (< ~1000 points); larger segments may take a long time to display and analyze.

_**Note:** While the time coordinate associated to a photometric or spectroscopic measurement is a fundamental piece of information, we ignore the explicit dependence in the context of SED analysis in Iris._

[Return to: FAQ index][faq]

<!-- links -->
[faq]:		./index.html

