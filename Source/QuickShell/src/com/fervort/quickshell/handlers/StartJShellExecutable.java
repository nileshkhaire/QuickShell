package com.fervort.quickshell.handlers;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fervort.quickshell.internal.JShellRuntime;
import com.fervort.quickshell.internal.QuickShellUtils;
import com.fervort.quickshell.preferences.QuickShellConfigReader;

/***
 * Handler to start jshell executable, jshell.exe on windows
 * Note :  This is platform dependent feature, may not work on all OS
 * 
 * @author Nilesh Khaire
 *
 */
public class StartJShellExecutable extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		JShellRuntime jshellRuntime = new JShellRuntime();
		String osString =QuickShellUtils.getOS();
		//System.out.println("OS is "+osString);
		if(osString.equals("windows") || osString.equals("linux"))
		{
			String jShellExeutable = QuickShellConfigReader.getJShellExecutable();
			if(jShellExeutable!=null)
			{
				try
				{
					File file = new File(jShellExeutable);
					if(file.exists())
					{
						jshellRuntime.startJShellUsingRuntime(osString,file.getAbsolutePath());
					}else
					{
						MessageDialog.openError(
								window.getShell(),
								"QuickShell Error",
								"JShell Executable is not exist on the path "+file.getAbsolutePath());
					}
				}catch(Exception ex)
				{
					MessageDialog.openError(
							window.getShell(),
							"QuickShell Error",
							"Something went wrong in StartJShellExecutable. "+ex.getLocalizedMessage());
				}
	
			}else
			{
				MessageDialog.openError(
						window.getShell(),
						"QuickShell Error",
						"JShell Executable is not setup. Go to Eclipse Window->Preferences->QuickShell Settings and select jshell executable.");
			}
		}else
		{
			String jShellExternalCommand = QuickShellConfigReader.getValue("JShellExternalCommand");
			
			if(jShellExternalCommand!=null && !jShellExternalCommand.isEmpty())
			{
				try {
					jshellRuntime.startJShellUsingRuntime(osString, jShellExternalCommand);
				} catch (IOException e) {
					
					MessageDialog.openError(
							window.getShell(),
							"QuickShell Error",
							"Something went wrong in custom StartJShellExecutable. "+e.getLocalizedMessage());
				}
			}else
			{
				MessageDialog.openInformation(
						window.getShell(),
						"QuickShell Info",
						"Your operating system ["+osString+ "] is not suppored in this release. You could still use custom command to achieve this functionality."
						+"\nYou can set custom command for that in eclipse QuickShell configuration."
						+"You can read more about this on our Github Home page. Complete path : https://github.com/nileshkhaire/QuickShell/wiki/jshell-custom-external-command");
			}
		}
		return null;
	}
}
