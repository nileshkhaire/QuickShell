package com.fervort.quickshell.internal;

import java.io.IOException;

/***
 * Class to execute jshell executable. It is platform dependent
 * @author Nilesh Khaire
 *
 */
public class JShellRuntime {

	public void startJShellUsingRuntime(String osString,String jShellExecutable) throws IOException
	{
		if(osString.equals("windows"))
		{
		    Process process = Runtime.getRuntime().exec("cmd.exe /C start \"jshell powered by QuickShell\" \""+jShellExecutable+"\" ");
		    //process.exitValue();
		}
		else
		{
			// Execute as it is.
			Process process = Runtime.getRuntime().exec(jShellExecutable);
		}
	}
}
