# <a name="top"></a>Installation Troubleshooting

The following is a list of known issues that may arise while installing or attempting to run Iris. If you encounter a problem that is not listed here, first check for it on the [Iris Bugs & Caveats page][bugs], and then contact the [CXC Helpdesk][helpdesk] for assistance, if necessary. It is recommended that you include in your bug report the results of the [Iris installation smoke test][smoke].

-----------------------

[I do not have the Java version required to run Iris.](#java_version)

[I exited Iris with a Force Quit or 'kill' command; now, it does not run properly.](#forcequit)

[(OS X) Smoke tests fail with "Error: Sherpa didn't respond to ping."](#libgcc_missing)

-----------------------

### <a name="java_version"></a>I do not have the Java version required to run Iris.

When you run the Iris startup command, the application does not open; the SED Importer startup command fails with an error message. This likely indicates that you do not have the minimum required Java version on your system to run the Iris application; or you do, but your global 'path' variable points to an older Java version which coexists on your system with the up-to-date one. In the case of the former, you should upgrade to at least Java SE 6 (version 1.6) by following the installation instructions for the Java Runtime Environment (JRE) specific to your computer platform, on the [Oracle website][oracle].

In the case of the latter, first check to see if your path points to an older version of Java on your system; if so, contact your systems administrator to find out if a newer version of Java is available elsewhere on your system.

For example, the output below shows that the global path variable is pointed to a directory containing an outdated version of Java.

	% which java
	/usr/bin/java

	% java -version
	java version "1.4.2"
	gij (GNU libgcj) version 4.1.2 20080704 (Red Hat 4.1.2-50)

Checking `/usr/local/bin`, however, reveals that an up-to-date Java version coexists on the system with the older version:

	% /usr/local/bin/java -version
	java version "1.6.0_24"
	Java(TM) SE Runtime Environment (build 1.6.0_24-b07)
	Java HotSpot(TM) Server VM (build 19.1-b02, mixed mode)

Editing the global path variable so that the directory containing Java SE 6 is searched before the directory containing the older version, will allow Iris to properly install.

	% set path = (/usr/local/bin $path)

	% Iris &

Adding the "set path ..." line shown above to your user preferences file for the shell, e.g. `$HOME/.cshrc.user` or `$HOME/.cshrc` for the tcsh shell or `$HOME/.bashrc` or `$HOME/.bash_profile` for bash shell, will make this change permanent; running it in the terminal window will alter the path only temporarily, i.e., for that particular terminal session.

### <a name="forcequit"></a>I exited Iris with a Force Quit or 'kill' command; now it does not run properly.

If the Iris application is killed off either by Force Quit (on Linux or Mac) or the 'kill' command, as opposed to the Iris "File -> Exit" menu option, the Sherpa program may survive as a spurious Python process. As a result, the next time you try to use Iris, it may not work properly. To remedy the problem, you can end the spurious process using 'ps' and 'kill' or using System Monitor. The process name should appear as something like 'python2.7'.

This occurs because Iris is responsible for starting and stopping the Sherpa Python process. If Iris is killed without executing its usual shutdown procedure, Sherpa could be left running. Since Iris uses SAMP to communicate, the spurious Sherpa process can conflict with Iris when it tries to establish a new connection.

You can also confirm the issue by inspecting the Iris log file located in your home directory, `$HOME/.vao/iris/log.txt` (or wherever your `$IRIS_LOG_LOCATION` points too), which should contain an error message indicating the problem.

### <a name="libgcc_missing"></a>Smoke tests fail with "Error: Sherpa didn't respond to ping"

If you get this error, first try running the smoke tests with a longer timeout period, as we do below:

	% iris smoketest 30
       
This should allow enough time for Sherpa to respond to the ping, and for the test to pass.

If the tests still fail, check to see if you have `/usr/local/lib/libgcc_s.1.dylib` on your system:

	% ls /usr/local/lib/libgcc_s.1.dylib
       
If the file doesn't exist, (if it returns something like "`ls: cannot access /usr/local/lib/libgcc_s.1.dylib: No such file or directory`"), then search for your system's `libgcc\_s.1.dylib` library, create a softlink to this directory, and rerun the smoketests:

	% python -c "import time; print time.__file__"

	/anaconda/lib/python2.7/lib-dynload/time.so              # Your result may vary

	% otool -L /anaconda/lib/python2.7/lib-dynload/time.so   # Take your result from above and run 'otool -L <result>'

	/anaconda/lib/python2.7/lib-dynload/time.so:
	/usr/lib/libgcc_s.1.dylib (compatibility version 1.0.0, current version 1.0.0) # create softlink to this file
	/usr/lib/libSystem.B.dylib (compatibility version 1.0.0, current version 111.0.0)

	% sudo ln -s /usr/lib/libgcc_s.1.dylib /usr/local/lib/libgcc_s.1.dylib
       
This will ask you for your password. Type it in when it asks.

If you don't have a `/usr/local/lib directory`, make it:

	% sudo mkdir /usr/local/lib
       
Then try creating the softlink, and rerunning the smoketests.

If you're still having issues, please contact us at the [CXC HelpDesk][helpdesk] with the tag "Iris."

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
