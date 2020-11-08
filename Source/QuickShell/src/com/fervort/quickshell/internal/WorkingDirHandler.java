package com.fervort.quickshell.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.IWorkbenchWindow;

import com.fervort.quickshell.preferences.QuickShellConfigReader;
/***
 * 
 * Handles working directory
 * 
 * @author Nilesh Khaire
 *
 */
public class WorkingDirHandler {

	private static String getSetupDir()
	{
		String workDir = QuickShellConfigReader.getQuickShellWorkingDir();
		if(workDir!=null)
		{
			File file= new File(workDir);
			
			if(file.exists() && file.isDirectory())
			{
				return file.getAbsolutePath();
			}
		}
		return null;
	}
	
	public static String getDir(IWorkbenchWindow window)
	{
		String setupWorkDir =getSetupDir();
		
		if(setupWorkDir!=null)
		{
			return setupWorkDir;
		}else
		{
			// Need more testing to use getFullDirectoryPath
			//File qsTempDir = new File(getFullDirectoryPath());
			File qsTempDir = new File(".QuickShellTempDir");
			if(qsTempDir.exists())
			{
				return qsTempDir.getAbsolutePath();
			}else
			{
				boolean isCrated = qsTempDir.mkdir();
				if(isCrated)
				{
					return qsTempDir.getAbsolutePath();
				}
				else
				{
					MessageDialog.openError(
							window.getShell(),
							"QuickShell Error",
							"Couldn't create temp directory on this path: "+qsTempDir.getAbsolutePath());
					return null;
				}
			}
			
		}
	}
	private static String getFullDirectoryPath()
	{
		try {
			String location = getLocationByEclipseAPI();
			if(!location.isEmpty())
			{
				return Paths.get(location,".QuickShellTempDir").toAbsolutePath().toString();
			}	
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception "+e);
			
		}
		return ".QuickShellTempDir"; // This will create folder where eclipse executable is present
	}
	
	private static String getLocationByEclipseAPI() throws IOException
	{
		/*
		// Multiple way to get different location
		Location config =Platform.getConfigurationLocation();
		// It is location inside workspace : /workspace/.metadata/.plugins/org.eclipse.pde.core/Eclipse Application/
		System.out.println("config location "+config.getURL());
		IPath path = Activator.getDefault().getStateLocation();
		// It is inside eclipse installation, multiple workspace/user will share same path
		System.out.println("path "+path);
		*/
		// User location, multiple instances can access same path. We could lock the path.
		Location user = Platform.getUserLocation();
		
		String strPath = FileLocator.toFileURL(user.getURL()).getPath();
		System.out.println("locking");
		user.lock(); // if user directory is not present then code will fail, lock will create user directory
		user.release();
		// should we use createLocation() to create a sub directory ? 
		IPath path = (IPath) new Path(strPath);
		return path.toOSString();
		
		//user.lock() // Locking will increase complexity, no need to lock
		// user.release();
	}
}
