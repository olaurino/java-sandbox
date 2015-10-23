# What's new in Iris?

<table cellspacing="5" cellpadding="5">
      <thead>
      <tr class="newsbar">
        <th align="center" width="10%">Date</th>
        <th align="center">Item</th>
      </tr>
      </thead>



    <tbody>

      <tr>
        <td valign="top">May 07, 2015</td>
        <td valign="top">
	  <p>
            <strong>Iris v2.1 Released</strong>
	  </p>
	  
	  <ul>
	    <li>
	      Created SED Stacker, a tool for statistically combining groups of SEDs.
	    </li>
	    <li>
	      Added capability to integrate under fitted model components from
	      the Science Tool.
	    </li>
	    <li>
	      Upgraded to Sherpa 4.7. This allows users to arbitrarily combine
	      template libraries and table models with other libraries, models,
	      and functions in the Model Expression in the Fitting Tool.
	    </li>
	    <li>
	      SEDs can now be saved in ASCII format. The ASCII file may be read
	      directly back into Iris.
	    </li>
	  </ul>
	  
	  <p>
	    Please read the <a href="./v2.1/releasenotes/index.html">Release Notes</a>
	    for a full list of bug fixes and enhancements.
	  </p>
	  
        </td>
      </tr>                      

      <tr>
        <td valign="top">Dec. 03, 2013</td>
        <td valign="top">
	<p>
	  <strong>Iris v2.0.1 Patch Released</strong>
	</p>
        <ul>
          <li>
	    Added tooltips for Fitting Tool models in Preset Components list
	  </li>
          <li>
	    Fixed bug where AB and ST magnitude errors were converted
            incorrectly
	  </li>
          <li>
	    Fixed bug where model parameter results were printed twice
            in text file (from File -&gt; Write to text file)
	  </li>
          <li>
	    Fixed documentation URL displayed when wrong Java version is found 
	  </li>
	</ul>
        <p>
	  Please <strong><a href="download/index.html#download">download the latest
	      release</a></strong> here.
        </p>
        </td>
      </tr>
      <tr>
        <td valign="top">Dec. 03, 2013</td>
        <td valign="top">
	  <p>
	    <strong>CAVEAT:</strong> Supported SED Data Types
	  </p>
          <p>
	    In light of recent helpdesk tickets, we want to fully inform
	    our users that Iris is best suited for broad-band
	    photometric SED analysis. It does provide support for short
	    spectroscopic segments (&lt;~1000 points), but larger segments
	    will take a long time to display and analyze. We are
	    evaluating support for larger spectra in future
	    releases. For more information, please see the <a href="./faqs/index.html">FAQs</a>.
	  </p>
        </td>
      </tr>
    </tbody>
  </table>