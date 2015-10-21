# <a name="top"></a>Supported Input File Formats

<a name="toc"></a>
[ASCII](#ascii) | [CSV](#csv) | [FITS](#fits) | [VOTable](#vot) | [IPAC](#ipac) | [TST](#tst)

The SED Builder tool within Iris allows the user to convert SED data written in a non-native format into one which is recognized by Iris. The input file formats supported by the SED Builder, listed below, are those which are supported by common VO tools. For each, a simple, generic assumption is made: data is arranged in a tabular format, with all rows having the same number of columns, and possibly with a header where metadata is stored. The way in which both data and metadata are stored depends on the specific format.

  * [ASCII](#ascii) - text file with columns separated by spaces and/or tabs
  * [CSV](#csv) - text file with columns separated by commas (the first row may contain the name of the columns)
  * [FITS](#fits) - consists of a series of Header Data Units (HDUs), each containing two components: an ASCII text header and the binary data. The header contains a series of header keywords that describe the data in a particular HDU and the data component immediately follows the header.
  * [VOTABLE](#vot) - (text or binary) XML standard for the interchange of data represented as a set of tables. Consists of an unordered set of rows, each of a uniform structure, as specified in the table metadata. Each row in a table is a sequence of table cells, and each of these contains either a primitive data type, or an array of such primitives.
  * [IPAC](#ipac) - a custom bar-separated text format by IPAC
  ( [TST](#tst) - Tab Separated Table (comments are ignored, metadata is in key, value pairs)
Since the SED Builder uses the STIL library to read files, STIL being the library on the top of which TOPCAT is implemented, the following documentation about the detailed specification of the supported formats is derived from the [TOPCAT documentation](http://www.star.bris.ac.uk/~mbt/topcat/sun253/inFormats.html).

## <a name="fits"></a>FITS

FITS binary and ASCII table extensions can be read. For normal FITS files, header cards in the table's HDU header will be made available as table parameters. Only header cards which are not used to specify the table format itself are visible as parameters (e.g. NAXIS, TTYPE* etc cards are not). HISTORY and COMMENT cards are run together as one multi-line value.

## <a name="vot"></a>VOTable

VOTable is an XML-based format for tabular data endorsed by the International Virtual Observatory Alliance; while the tabular data which can be encoded is by design close to what FITS allows, it provides for much richer encoding of structure and metadata. SED Builder should be able to read any table which conforms to the VOTable 1.0, 1.1 or 1.2 specifications. This includes tables in which the cell data are included in-line as XML elements (VOTable/TABLEDATA format), or included/referenced as a FITS table (VOTable/FITS) or included/referenced as a raw binary stream (VOTable/BINARY). STIL does not attempt to be fussy about input VOTable documents, and it will have a good go at reading VOTables which violate the standards in various ways.

## <a name="ascii"></a>ASCII Table

In many cases tables are stored in some sort of unstructured plain text format, with cells separated by spaces or some other delimiters. There is a wide variety of such formats depending on what delimiters are used, how columns are identified, whether blank values are permitted and so on. It is impossible to cope with them all, but TOPCAT attempts to make a good guess about how to interpret a given ASCII file as a table, which in many cases is successful. In particular, if you just have columns of numbers separated by something that looks like spaces, you should be just fine.

Here are the detailed rules for how the ASCII-format tables are interpreted:

 - Bytes in the file are interpreted as ASCII characters.

 - Each table row is represented by a single line of text.

 - Lines are terminated by one or more contiguous line termination
   characters: line feed (0x0A) or carriage return (0x0D).

 - Within a line, fields are separated by one or more whitespace
   characters: space (" ") or tab (0x09).

 - A field is either an unquoted sequence of non-whitespace
   characters, or a sequence of non-newline characters between matching
   single (') or double ('') quote characters - spaces are therefore
   allowed in quoted fields.

 - Within a quoted field, whitespace characters are permitted and are
   treated literally.

 - Within a quoted field, any character preceded by a backslash
   character ("\") is treated literally. This allows quote characters
   to appear within a quoted string.

 - An empty quoted string (two adjacent quotes) or the string "null" 
   (unquoted) represents the null value

 - All data lines must contain the same number of fields (this is the
   number of columns in the table)

 - The data type of a column is guessed according to the fields that
   appear in the table. If all the fields in one column can be parsed as 
   integers (or null values), then that column will turn into an
   integer-type column. The types that are tried, in order of
   preference, are: Boolean, Short Integer, Long, Float, Double, String

 - Empty lines are ignored.

 - Anything after a hash character "#" (except one in a quoted string)
   on a line is ignored as far as table data goes; any line which
   starts with a "!" is also ignored. However, lines which start with
   a "#" or "!" at the start of the table (before any data lines) will
   be interpreted as metadata as follows:

   The last "#"/"!"-starting line before the first data line may
   contain the column names. If it has the same number of fields as
   there are columns in the table, each field will be taken to be the
   title of the corresponding column. Otherwise, it will be taken as a
   normal comment line.

   Any comment lines before the first data line not covered by the
   above will be concatenated to form the "description" parameter of
   the table.
   
	#
	# Here is a list of some animals.
	#
	# RECNO  SPECIES         NAME         LEGS   HEIGHT/m
	  1      pig             "Pigling Bland"  4  0.8
	  2      cow             Daisy        4      2
	  3      goldfish        Dobbin       ""     0.05
	  4      ant             ""           6      0.001
	  5      ant             ""           6      0.001
	  6      ant             ''           6      0.001
	  7      "queen ant"     'Ma\'am'     6      2e-3
	  8      human           "Mark"       2      1.8
      
   In this case it will identify the following columns:

	Name       Type

	RECNO      Short
	SPECIES    String
	NAME       String
	LEGS       Short
	HEIGHT/m   Float

   It will also use the text "Here is a list of some animals" as the Description parameter of the table. Without any of the comment lines, it would still interpret the table, but the columns would be given the names col1..col5.

If you understand the format of your files but they do not exactly match the criteria above, a good approach to take is to write a simple, free-standing program or script which will convert them into the format described here; you may find Perl or awk suitable languages to use for this purpose.

## <a name="ipac"></a>IPAC

The Infrared Processing and Analysis Center at CalTech uses a text-based format for storage of tabular data, defined here. Tables can store column name, type, units and null values, as well as table parameters. They typically have a filename extension ".tbl" and are used for Spitzer data amongst other things. An example looks like this:


	\title='Animals'
	\ This is a table with some animals in it.
	|   RECNO |    SPECIES |          NAME |    LEGS | HEIGHT   |
	|    char |       char |          char |     int | double   |
	|         |            |               |         | m        |
	|         |            |          null |         |          |
			  1          pig   Pigling Bland         4        0.8
			  2          cow           Daisy         4          2
			  3     goldfish          Dobbin         0       0.05
			  4          ant            null         6      0.001

Comma-Separated Values

Comma-separated value ("CSV") format is a common semi-standard text-based format in which fields are delimited by commas. Spreadsheets and databases are often able to export data in some variant of it. The intention is that TOPCAT can read tables in the version of the format spoken by MS Excel amongst other applications, though the documentation on which it was based was not obtained directly from Microsoft.

The rules for data which it understands are as follows:

 - Each row must have the same number of comma-separated fields.

 - Whitespace (space or tab) adjacent to a comma is ignored.

 - Adjacent commas, or a comma at the start or end of a line
  (whitespace apart) indicates a null field.

 - Lines are terminated by any sequence of carriage-return or newline 
   characters ('\r' or '\n') (a corollary of this is that blank lines
   are ignored).

 - Cells may be enclosed in double quotes; quoted values may contain
   linebreaks (or any other character); a double quote character
   within a quoted value is represented by two adjacent double quotes.

 - The first line may be a header line containing column names rather
   than a row of data. Exactly the same syntactic rules are followed for
   such a row as for data rows.

## <a name="tst"></a>TST

Tab-Separated Table, or TST, is a text-based table format used by a number of astronomical tools including Starlink's GAIA and ESO's SkyCat on which it is based. A definition of the format can be found in Starlink Software Note 75. The implementation here ignores all comment lines: special comments such as the "#column-units:" are not processed.

An example looks like this:

    Simple TST example; stellar photometry catalogue.

    A.C. Davenhall (Edinburgh) 26/7/00.

    Catalogue of U,B,V colours.
    UBV photometry from Mount Pumpkin Observatory, 
    see Sage, Rosemary and Thyme (1988). 

    # Start of parameter definitions. 
    EQUINOX: J2000.0 
    EPOCH: J1996.35

    id_col: -1
    ra_col: 0
    dec_col: 1

    # End of parameter definitions.
    ra<tab>dec<tab>V<tab>B_V<tab>U_B
     <tab> <tab> <tab> <tab>
    5:09:08.7<tab> -8:45:15<tab>  4.27<tab>  -0.19<tab>  -0.90
    5:07:50.9<tab> -5:05:11<tab>  2.79<tab>  +0.13<tab>  +0.10
    5:01:26.3<tab> -7:10:26<tab>  4.81<tab>  -0.19<tab>  -0.74
    5:17:36.3<tab> -6:50:40<tab>  3.60<tab>  -0.11<tab>  -0.47
    [EOD]

|   |
|--:|
|[[Back to top][top]]|

<!-- external links-->

[oracle]:			http://www.oracle.com/technetwork/java/javase/index-137561.html "Oracle"
[mast]:     		http://mast.stsci.edu/portal/Mashup/Clients/Mast/Portal.html "MAST Portal"
[topcat]:   		http://www.star.bris.ac.uk/~mbt/topcat/ "TOPCAT"
[specview]: 		http://www.stsci.edu/resources/software_hardware/spe%20cview/ "Specview"
[conda_osx]:		http://repo.continuum.io/miniconda/Miniconda-3.8.3-MacOSX-x86_64.sh "OS X Miniconda"
[conda_l64]:		http://repo.continuum.io/miniconda/Miniconda-3.8.3-Linux-x86_64.sh "Linux 64 Miniconda"
[conda_l32]:		http://repo.continuum.io/miniconda/Miniconda-3.8.3-Linux-x86.sh "Linux 32 Miniconda"

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