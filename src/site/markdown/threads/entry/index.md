# <a name="top"></a> Loading Data into Iris

------------------------------------------------------------------------

## <a name="overview"></a> Overview

#### Synopsis:

In order to visualize and analyze a wide-band SED of an astronomical
object, multiple SED segments and/or photometric points from various
observatories and file locations must be gathered and co-plotted; Iris
features multiple entry points for SED data to facilitate this process.
You can load data files from your local disk into Iris, import data
directly from NED, or transmit data from a SAMP-enabled application.

It is expected that users will need to input SED data in various file
formats in order to construct a multi-segment SED of a source.
Consequently, the SED Builder tool within Iris is equipped to convert
user SED data files in non-native formats into one of the two file
formats natively supported by Iris, IVOA-compliant VOTable or FITS.
Independent of how each data set is loaded and from where, Iris can
simultaneously plot multiple SED segments together in the main display.

This thread demonstrates the various ways to read SED data into Iris to
begin your data analysis session.

------------------------------------------------------------------------

### Contents

-   **[Supported SED Data Types and Formats](index.html#intro)**
-   **[Starting Iris](index.html#start)**
-   **[Loading SED Data](index.html#load)**
    -   [Loading SED Data from local File or URL](index.html#from_file)
    -   [Importing SED Data from NED](index.html#from_VO)
    -   [Transmitting Data from a SAMP-enabled
        Application](index.html#samp)
    -   [Using the ASDC Data plugin to query multiple
        catalogs](index.html#asdc)
-   **[History](index.html#history)**

------------------------------------------------------------------------

## <a name="intro"></a> Supported SED Data Types and Formats

Files in any of the following formats may be input to Iris for analysis.
However, as Iris natively supports only IVOA-compliant FITS and VOTable
format, when a file is loaded in a non-native format, an extra step is
required of the user in order to convert the data to a compatible
format, using the SED Builder.

  -   ASCII
  -   CSV
  -   FITS (standard and non-standard)
  -   VOTable (standard and non-standard)
  -   IPAC
  -   TST

The SED Builder tool accepts as 'non-standard' any binary or text-based
file that does not conform to the IVOA SED, Spectrum and Photometry Data
Model; these include FITS and VOTable files which do not conform to the
IVOA standards. To learn more about the SED Builder and how to use it to
convert your SED data into a compatible format, refer to the [Building a
SED in Iris][importer] section of the [Iris How-to
Guide][guide].

An Iris input file should contain a table of photometric data, where one
or more of the columns lists measured source flux, flux density, or
luminosity values, with optional, associated columns listing the errors
on the flux values; and, if loading a spectral segment (as opposed to a
photometric point(s)), an additional column listing the spectral
coordinate values, either wavelength, frequency, or energy.

*For a description of each supported file format listed here, see
[Supported Input File Formats][importer_files].*

**Note**: *While the time coordinate associated with a photometric or
spectroscopic measurement is a fundamental piece of information, the
explicit dependence on time is ignored in the context of SED analysis in
Iris. (The time *t* of the observation is always contained in the
[metadata][../plot/index.html#metadata) accompanying every measurement
supported by Iris.)*

|   |
|--:|
|[[Back to top][top]]|

------------------------------------------------------------------------

## <a name="start"></a> Starting Iris

After downloading and installing Iris according to the procedure
outlined in the [Download & Installation page][download], the GUI
may be started from the Unix command line as follows:

``` {.highlight}
$ /bin/bash   # for C-shell users ONLY

$ source activate iris
$ iris
```

![snapshot of Iris GUI](./imgs/iris_desktop_small.jpg)

You can also start the application by opening a folder manager window on
Linux or the Finder on Mac, navigating to the Iris 2.1 directory, and
double-clicking on the "Iris" executable.

This opens the desktop interface with icons that launch the various
tools used by the application, as well as a link to this on-line help
documentation.

All of the windows launched by Iris will be confined within this
desktop. Each window can be iconified and its icon will stay in the Iris
desktop itself, so that it will not take up space in your applications
bar.

The Iris desktop takes on the same look and feel as your native desktop,
and the behaviour is consistent with your native environment. For
example, on Mac OS X, iconified windows appear as miniatures at the
bottom-center of the desktop, while on Linux, they could be draggable
buttons placed on the bottom-left of the desktop.

The SED Builder desktop can itself be resized, maximized and iconified
in your native desktop.

|   |
|--:|
|[[Back to top][top]]|

------------------------------------------------------------------------

## <a name="load"></a> Loading SED Data

You are provided with three options for loading data: from a local file
or remote URL file location, searching NED for SED data associated with
the entered target name, and from SAMP-enabled applications.

### <a name="from_file"></a> Loading SED Data from local File or URL

A SED data file stored on your local disk or at a URL address may be
uploaded into the Iris main display simply by starting the application
and selecting the *Load File* icon on the Iris desktop.

![Iris GUI snapshot](./imgs/load_file.png)

After browsing for and selecting your SED data file on disk, or entering
the URL address where the file is located, simply select the appropriate
file format from the drop-down menu, and then either "Load Spectrum/SED"
or "Load Photometry Catalog." The former option is appropriate for a
data file consisting of one or more segments of data, and the latter,
for a file consisting of a photometric point(s). If a segment is loaded
and it is already in one of the formats natively supported by Iris, it
will automatically be displayed in the *Iris Visualizer*; otherwise, you
will have to complete an extra step of entering configuration
information in order to convert it for upload into the display.
Likewise, if a photometry catalog is loaded, you will have to enter
several pieces of information in order to 'tell' Iris how to display the
points. The "[Building a SED in Iris][importer]" section of the [Iris
How-to Guide][guide] demonstrates the use of these two
loading options.

IVOA-compliant SED files are available from SED services such as the
[NASA/IPAC Extragalactic Database
(NED)][ned], which you can upload
into the application (recall the option to directly import SEDs into
Iris from NED, in particular). The Iris package includes sample SED
files in the "examples" subdirectory of the installation directory,
which were downloaded from NED by searching its photometry data archives
for SEDs of the following objects: 3C 273, 3C 295, 3C 66A, M31, M87, NGC
1068, and NGC 4151.

The sample files in the "examples" directory are shown below:


``` {.highlight}
$ ls <basedir>/miniconda/envs/iris/opt/iris/examples/
3c273.xml
3c295.xml
3c66a.xml
m31.xml
m87.xml
ngc1068.xml
ngc4151.xml
```

|   |
|--:|
|[[Back to top][top]]|

------------------------------------------------------------------------

### <a name="from_VO"></a> Importing SED Data from NED

As an alternative to loading data from file, SED data from NED may be
directly imported into Iris via the "Get an SED from the NED Service"
option in the *Load File* window.

![Iris GUI snapshot](./imgs/load_file_get_ned.png)

The entered target name will be resolved by NED, and any associated SED
data found in the NED photometry archive will be returned by the web
service and loaded into the tool. (The "Change Endpoint" input can be
used to change the NED service URL which is queried; however, this not
usually needed.)

After entering the name of the desired target and selecting "Import NED
SED", if the search was successful the SED data for the source will
appear automatically in the *Iris Visualizer*, and an entry for the
imported SED will be added to the SED Builder window.

[![Iris GUI
snapshot](./imgs/entry_3c066a_plot_small.jpg)](./imgs/entry_3c066a_plot.png)

This example query returns a collection of photometric data points
obtained by various astronomical observatories, all contributed to the
NED photometry data archive in association with blazar 3C66A. The
metadata associated with one or multiple selected data points may be
viewed with the "Metadata" button in the Iris main display, as
demonstrated in the "[Visualizing SED Data][plot]" section of the
[Iris How-to Guide][guide].

|   |
|--:|
|[[Back to top][top]]|

------------------------------------------------------------------------

### <a name="samp"></a> Transmitting Data from a SAMP-enabled Application

In the lower-left corner of the Iris desktop there is an icon that shows
the status of the Simple Application Messaging Protocol (SAMP)
connection, which the tool uses to communicate with other interoperable
applications.

![\[snapshot of Iris GUI\]](./imgs/samp_icon.png)

SAMP is a Virtual Observatory protocol that allows desktop applications
to communicate with each other. If you use other SAMP-enabled Virtual
Observatory applications that manipulate tables of astronomical data,
such as Topcat or Aladin, you can transmit tables of data from these
external applications to Iris.

To transmit data to Iris from another SAMP-enabled desktop application,
simply broadcast the table from the external application. If in the SED
Builder window of Iris there are no open SEDs, a new SED will be
created.

[![Transmitting a table from Topcat to
Iris](./imgs/topcat_example_3c066a_small.jpg)](./imgs/topcat_example_3c066a.png)

Note that the "Broadcast SED" button in the SED Builder window can be
used to transmit a SED *from* Iris to another SAMP-enabled application.

|   |
|--:|
|[[Back to top][top]]|

------------------------------------------------------------------------

### <a name="asdc"></a> Using the ASDC Data plugin to query multiple catalogs

The ASDC IRIS Plugin is a software interface developed at the ASI
Science Data Center (ASDC) as part of an on-going collaboration between
the ASDC and the IRIS developers at CfA. It works as an interface to
retrieve spectral data from the ASDC SED server and use it within the
IRIS tool.

  * [Installation Instuctions](#asdc_install)
  * [Usage](#asdc_usage)
  * [ASDC Catalog List](#asdc_catalogs)

### <a name="asdc_install"></a> Installation Instructions

If an icon named "ASDC Data" is already present in the IRIS main window,
this means that the plugin is already pre-installed and properly
configured on your IRIS version. If this is the case, you may skip the
following instructions and go directly to the "Usage" section.

If the icon marked with the red arrow in fig. 1 is not present, the
corresponding plugin can be found in the IRIS directory:

``` {}
<basedir>/miniconda/envs/iris/opt/iris/lib/AsdcIrisPlugin-1.1.3.jar
```

To install it, just go to "Plugin Manager" under the "Tools" menu bar,
copy the above path to the "Plugin Directory" and "Load" the plugin. A
new icon named "ASDC Data" will appear in the IRIS main window.

![Iris GUI Screenshot](./imgs/asdc_icon.png)

### <a name="asdc_usage"></a> Usage

To launch the plugin, click on the "ASDC Data" icon in the IRIS main
window. A new window, named "ASDC Catalog query" will appear. This is
the main interface to query the ASDC catalogs server, load ASDC
multi-frequency data on IRIS and display it as spectral energy
distributions (SEDs).

![Iris GUI Screenshot](./imgs/asdc_catalog_query.png)

The name of the cosmic source for which SED data is required can be
entered in the "Target Name" box. The system will automatically retrieve
the corresponding equatorial coordinates using the NED or the Simbad
name server. Alternatively, if the source's coordinates are known, these
can be directly inserted in the "Ra" and "Dec" boxes, in decimal
degrees.

Data can be queried according to observation time, by selecting the
start and end date of observation (Tstart Date/Time and TStop Date/Time
boxes). Time intervals can be specified in the common
year-month-day-hour-minute-second format (yyyy-MM-dd hh:mm:ss), or as
Modified Julian Day (MJD), using the "Date Format" box.

Many different catalogs can be queried. They are grouped in the
"Catalogs Available" box according to their wavelength domain. Each
domain (Radio, Infrared, Optical UV, Soft X Ray, Hard X Ray and Gamma
Ray) includes several catalogs, which can be viewed by clicking on the
arrow to the left of each entry. The full catalog list is reported in
Appendix A1. To select or deselect a catalog, just check or uncheck the
corresponding box.

Each catalog has a default search radius, which depends on the precision
of the coordinates listed in the chosen catalog. Be aware that search
radii can vary substantially depending on catalog type. For example,
optical catalogs typically have radii of a few arc-seconds, while
gamma-ray catalogs can have search radii that can be as large as one
degree.

![Iris GUI Screenshot](./imgs/asdc_catalogs_available.png)

The "Search Radius" box allows users to vary the circular region within
which data belonging to each catalog are searched. To do this, just
select a catalog from the "Catalogs Available" box. The name of the
selected catalog will appear in the "Catalog Name" box, and the default
error radius in the "Search Radius" one (see fig. 3 for an example). A
new search radius in arc-minutes for the selected catalog can now be
defined editing the "Search Radius" box. The search radius is centered
on the input coordinates.

### <a name="asdc_catalogs"></a>ASDC Catalog List

The complete list of the catalogs accessible via the ASDC IRIS Plugin as
of June 2013, is reported below in the table. This list may of course
change in time as more catalogs are published in the ASDC webpages. The
first column indicates the catalog name, the second one the group to
which the catalog belongs, and the last one the frequency domain of the
catalog entries. Infrared, Optical and UV fluxes have been corrected for
the Galactic AV, following the extinction recipes in Cardelli et al.
(1989, ApJ, 345, 245) and Fitzpatrick (1999, PASP, 111, 63). The Soft
X-ray fluxes have been corrected for the Galactic NH, modeling the
corresponding X-ray spectrum with an absorbed power law. More
information about the ASDC catalogs can be found on the [ASDC webpages](http://tools.asdc.asi.it/SED/docs/SED_catalogs_reference.html)


<!-- for the table -->
<style type="text/css">
table.tableizer-table {
  font-size: 12px;
  border: 2px solid black;
  border-collapse: collapse;
  background-color: #F5F5F5;
} 
.tableizer-table td {
  padding: 4px 7px 4px 7px;
  margin: 5px;
  border: 1px solid black;
}
.tableizer-table th {
  background-color: #104E8B; 
  color: #FFFFFF;
  font-weight: bold;
  padding: 10px;
}
</style>


<table align="center">
 <tr>
  <td>

   <table align="center" class="tableizer-table">
   <tr class="tableizer-firstrow">
<th>Catalog Name</th>
<th>Group</th>
<th>Waveband</th>
</tr>

 <tr>
<td>AT20GCAT (flux 5 GHz)</td>
<td>AT</td>
<td>Radio</td>
</tr>
 <tr>
<td>AT20GCAT (flux 8 GHz)</td>
<td>AT</td>
<td>Radio</td>
</tr>
 <tr>
<td>ATCAPMN (flux 3.6 cm)</td>
<td>AT</td>
<td>Radio</td>
</tr>
 <tr>
<td>AT20GCAT (flux 8 GHz)</td>
<td>AT</td>
<td>Radio</td>
</tr>
 <tr>
<td>ATCAPMN (flux 3.6 cm)</td>
<td>AT</td> <td>Radio</td>
</tr>
 <tr>
<td>ATCAPMN (flux 6 cm) </td>
<td>AT</td>
<td>Radio</td>
</tr>
 <tr>
<td>ATPMNCAT </td>
<td>AT</td>
<td>Radio</td>
</tr>
 <tr>
<td>CLASSSCAT </td>
<td>Radio</td>
<td>Radio</td>
</tr>
 <tr>
<td>CRATES </td>
<td>Radio</td>
<td>Radio</td>
</tr>
 <tr>
<td>DIXON </td>
<td>Radio</td>
<td>Radio</td>
</tr>
 <tr>
<td>FIRST </td>
<td>Radio</td>
<td>Radio</td>
</tr>
 <tr>
<td>GB6 </td>
<td>GBT </td>
<td>Radio</td>
</tr>
 <tr>
<td>GB87CAT </td>
<td>GBT </td>
<td>Radio</td>
</tr>
 <tr>
<td>NORTH20CM (flux 20 cm) </td>
<td>GBT </td>
<td>Radio</td>
</tr>
 <tr>
<td>NORTH20CM (flux 6 cm) </td>
<td>GBT </td>
<td>Radio</td>
</tr>
 <tr>
<td>NORTH20CM (flux 80 cm) </td>
<td>GBT </td>
<td>Radio</td>
</tr>
 <tr>
<td>JVASPOL</td>
<td> Radio</td>
<td> Radio</td>
</tr>
 <tr>
<td>KUEHR</td>
<td> Radio</td>
<td> Radio</td>
</tr>
 <tr>
<td>NIEPPOCAT</td>
<td> Radio</td>
<td> Radio</td>
</tr>
 <tr>
<td>NVSS</td>
<td> Radio</td>
<td> Radio</td>
</tr>
 <tr>
<td>PKSCAT90</td>
<td> Radio</td>
<td> Radio</td>
</tr>
 <tr>
<td>PMN</td>
<td> Radio</td>
<td> Radio</td>
</tr>
 <tr>
<td>ERCSC030</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>ERCSC044</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>ERCSC070</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>ERCSC100</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>ERCSC143</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>ERCSC217</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>ERCSC353</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>ERCSC545</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>ERCSC857</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F030</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F044</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F070</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F100</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F143</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F217</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F353</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F545</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>PCCS1F857</td>
<td>Planck</td>
<td>Radio</td>
</tr>
 <tr>
<td>SUMSS</td>
<td>Radio</td>
<td>Radio</td>
</tr>
 <tr>
<td>VLANEP</td>
<td>Radio</td>
<td>Radio</td>
</tr>
 <tr>
<td>VLSS</td>
<td>Radio</td>
<td>Radio</td>
</tr>
 <tr>
<td>WENSS</td>
<td>Radio</td>
<td>Radio</td>
</tr>
 <tr>
<td>WMAP5 (Freq. 23e9 Hz)</td>
<td>WMAP</td>
<td>Radio</td>
</tr>
 <tr>
<td>WMAP5 (Freq. 33e9 Hz)</td>
<td>WMAP</td>
<td>Radio</td>
</tr>
 <tr>
<td>WMAP5 (Freq. 41e9 Hz)</td>
<td>WMAP</td>
<td>Radio</td>
</tr>
 <tr>
<td>WMAP5 (Freq. 61e9 Hz)</td>
<td>WMAP</td>
<td>Radio</td>
</tr>
 <tr>
<td>WMAP5 (Freq. 94e9 Hz)</td>
<td>WMAP</td>
<td>Radio</td>
</tr>
 <tr>
<td>AKARIBSC 065</td>
<td> AKARI/FIS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>AKARIBSC 090</td>
<td> AKARI/FIS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>AKARIBSC 140</td>
<td> AKARI/FIS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>AKARIBSC 160</td>
<td> AKARI/FIS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>AKARIPSC 09</td>
<td> AKARI/IRC</td>
<td>Infrared</td>
</tr>
 <tr>
<td>AKARIPSC 18</td>
<td> AKARI/IRC</td>
<td>Infrared</td>
</tr>
 <tr>
<td>IRASFSC 12</td>
<td>IRAS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>IRASFSC 25</td>
<td>IRAS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>IRASFSC 60</td>
<td>IRAS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>IRASFSC100</td>
<td>IRAS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>IRASPSC 12</td>
<td>IRAS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>IRASPSC 25</td>
<td>IRAS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>IRASPSC 60</td>
<td>IRAS</td>
<td>Infrared</td>
</tr>
 <tr>
<td>IRASPSC100</td>
<td>IRAS</td>
<td>Infrared</td>
</tr>

 </table>
   </td>
 
   <td>
    <table>
    <tr><td> </td></tr>
    </table>
   </td>

   <td>
   <table align="center" class="tableizer-table">
   <tr class="tableizer-firstrow">
<th>Catalog Name</th>
<th>Group</th>
<th>Waveband</th>
</tr>

 <tr>
<td>WISE03_ext</td>
<td>WISE</td>
<td>Infrared</td>
</tr>
 <tr>
<td>WISE03_point</td>
<td>WISE</td>
<td>Infrared</td>
</tr>
 <tr>
<td>WISE05_ext</td>
<td>WISE</td>
<td>Infrared</td>
</tr>
 <tr>
<td>WISE05_point</td>
<td>WISE</td>
<td>Infrared</td>
</tr>
 <tr>
<td>WISE12_ext</td>
<td>WISE</td>
<td>Infrared</td>
</tr>
 <tr>
<td>WISE12_point</td>
<td>WISE</td>
<td>Infrared</td>
</tr>
 <tr>
<td>WISE22_ext</td>
<td>WISE</td>
<td>Infrared</td>
</tr>
 <tr>
<td>WISE22_point</td>
<td>WISE</td>
<td>Infrared</td>
</tr>

 <tr>
<td>GALEXAISFUV</td>
<td>GALEX</td>
<td>Optical UV</td>
</tr>
 <tr>
<td>GALEXAISNUV</td>
<td>GALEX</td>
<td>Optical UV</td>
</tr>
 <tr>
<td>GALEXMISFUV</td>
<td>GALEX</td>
<td>Optical UV</td>
</tr>
 <tr>
<td>GALEXMISNUV</td>
<td>GALEX</td>
<td>Optical UV</td>
</tr>
 <tr>
<td>UVOTPLKSED</td>
<td>Swift</td>
<td>Optical UV</td>
</tr>

 <tr>
<td>ARIEL3A</td>
<td>Ariel V</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>ASCASIS (0.5-12 keV)</td>
<td> ASCA</td>
<td> Soft X Ray</td>
</tr>
 <tr>
<td>WFCCAT</td>
<td>BeppoSAX</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>WFCCAT FULL</td>
<td> BeppoSAX</td>
<td> Soft X Ray</td>
</tr>
 <tr>
<td>CHANDRASRC (0.2-0.5 keV)</td>
<td>CHANDRA</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>CHANDRASRC (0.5-1.2 keV)</td>
<td>CHANDRA</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>CHANDRASRC (1.2-2.0 keV)</td>
<td>CHANDRA</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>CHANDRASRC (2.0-7.0 keV)</td>
<td>CHANDRA</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>IPC</td>
<td>Einstein</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>IPCSLEW</td>
<td>Einstein</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>CMA</td>
<td>EXOSAT</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>BMW</td>
<td>ROSAT</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>RASS</td>
<td>ROSAT</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>SKYROSAT</td>
<td>ROSAT</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>WGACAT2</td>
<td>ROSAT</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>1SWXRT (0.1-10 keV)</td>
<td>Swift</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>1SWXRT (0.1-2.4 keV)</td>
<td>Swift</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>1SWXRT (2-10 keV)</td>
<td>Swift</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>XRTGRBDEEP</td>
<td>Swift</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>UHURU4</td>
<td>UHURU</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>TWOXMMIDR3</td>
<td>XMM</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>XMMSL1D5</td>
<td>XMM</td>
<td>Soft X Ray</td>
</tr>
 <tr>
<td>XMMSL1D5 (0.2-12 keV)</td>
<td> XMM</td>
<td>Soft X Ray</td>
</tr>

 <tr>
<td>A4 (13-25 keV)</td>
<td>HEAO-1</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>A4 (25-40 keV)</td>
<td>HEAO-1</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>A4 (40-80 keV)</td>
<td>HEAO-1</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>A4 (80-180 keV)</td>
<td>HEAO-1</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>IBISSG4CAT (20-40 keV)</td>
<td>INTEGRAL</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>IBISSG4CAT (40-100 keV)</td>
<td>INTEGRAL</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>MAXIGSC</td>
<td>Hard X Ray</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>BAT39MCAT (10-150 keV)</td>
<td>Swift</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>BAT39MCAT (15-30 keV)</td>
<td>Swift</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>BAT54MCAT (15-150 keV)</td>
<td>Swift</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>BAT54MCAT (15-50 keV)</td>
<td>Swift</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>BAT60AGN (15-55 keV)</td>
<td>Swift</td>
<td>Hard X Ray</td>
</tr>
 <tr>
<td>SWBAT70M (14-195 keV)</td>
<td>Swift</td>
<td>Hard X Ray</td>
</tr>

 <tr>
<td>AGILE Grid</td>
<td>Gamma Ray</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>EGRET3</td>
<td>Gamma Ray</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi1FGL (200 Mev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi1FGL (2Gev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi1FGL (600 Mev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi1FGL (60Gev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi1FGL (6Gev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi2FGL (200 Mev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi2FGL (2Gev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi2FGL (600 Mev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi2FGL (60Gev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr>
<td>Fermi2FGL (6Gev)</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 <tr height="50">
<td>Fermi2FglLC</td>
<td>Fermi</td>
<td>Gamma Ray</td>
</tr>
 </table>

  </td>
 </tr>

</table>

<!-- end of table -->

|   |
|--:|
|[[Back to top][top]]|

------------------------------------------------------------------------

## <a name="history"></a> History

|Date       |Changes                                                                     |
|:----------|:---------------------------------------------------------------------------|
|08 Aug 2011|updated for Iris Beta 2.5													 |
|25 Sep 2011|updated for Iris 1.0													 	 |
|25 May 2012|updated for Iris 1.1														 |
|02 Jan 2013|updated for Iris 1.2														 |
|21 Jun 2013|updated for Iris 2.0. Added ASDC plugin documentation						 |
|05 Aug 2013|updated figures for Iris 2.0. Integrated ASDC documentation to the website. |
|02 Dec 2013|Updated for Iris 2.0.1														 |
|07 May 2015|Updated for Iris 2.1 beta.												 	 |

-----------------------------------

|   |
|--:|
|[[Back to top][top]]|

<!-- external links -->
[ned]: http://vo.ned.ipac.caltech.edu/SED_Service "NED SED Service"

<!-- threads -->
[sedstacker]: 		../../threads/science/sedstacker/index.html "SED Stacker"
[science]: 			../../threads/science/index.html "Shift, Interpolate, and Integrate"
[entry]: 			../../threads/entry/index.html "Loading SED Data into Iris"
[fit]: 				../../threads/fits/index.html "Modeling and Fiting SED Data"
[importer]: 		../../threads/importer/index.html "Building and Managing SEDs"
[plot]: 			../../threads/plot/index.html "Visualizing SED Data"
[analysis]: 		../../threads/analysis/index.html "Analyzing SED Data in Iris"
[save]: 			../../threads/save/index.html "Saving SED Data"
[sdk]: 				../../threads/sdk/index.html "Developing Plugins: the Iris Software Development Kit"
[plugin_manager]: 	../../threads/plugin_manager/index.html "Plugin Manager"

<!-- reference files -->
[download]: 		../../download/index.html "Download and Installation"
[smoke_test]: 		../../download/smoke_tests.html "Smoke Test"
[macosx105]:		../../download/macosx_test.html "Mac OS X 10.5 Download Instructions"
[download_trouble]: ../../bugs/smoke.html
[supported_files]: 	../../references/importer_files.html
[models]: 			../../references/models.html
[faq]: 				../../faq/index.html "FAQs"
[releasenotes]: 	../../releasenotes/index.html "Release Notes"
[publications]: 	../../publications/index.html "Iris Publications"
[bugs]: 			../../bugs/index.html "Bugs and Caveats"

<!-- CXC links -->
[helpdesk]:			/helpdesk/ "CXC HelpDesk"
[sao]:				http://cfa.harvard.edu/sao "Smithsonian Astrophysical Observatory"
[cxc]:				/ "Chandra X-Ray Observatory"
[sherpa]:			/sherpa/ "Sherpa"

<!-- Navigation -->
[toc]:				#toc
[top]:      		#top