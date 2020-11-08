package com.fervort.quickshell.internal;

/***
 * 
 * All QuickShell utility method goes here
 * 
 * @author Nilesh Khaire
 *
 */
public class QuickShellUtils {
	
	public static String getOS()
	{
	    String osname = System.getProperty("os.name").toLowerCase();
	    if (osname.contains("windows"))
	    {
	      return "windows";
	    }
	    else if (osname.contains("linux"))
	    {
	      return "linux";
	    }
	    else if (osname.contains("sunos"))
	    {
	      return "solaris";
	    }
	    else if (osname.contains("mac") || osname.contains("darwin")) 
	    {
	      return "mac";
	    }
	    else {
	    	
	    	return osname;
	    }
	    	
	}
}
