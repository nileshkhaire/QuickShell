package com.fervort.quickshell.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fervort.quickshell.internal.TerminalOpener;
import com.fervort.quickshell.internal.WorkingDirHandler;
import com.fervort.quickshell.preferences.QuickShellConfigReader;

/***
 * Handler to start jshell in eclipse terminal
 * 
 * @author Nilesh Khaire
 *
 */
public class StartJShellInEclipseTerminal extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		String jShellExeutable = QuickShellConfigReader.getJShellExecutable();
		if(jShellExeutable!=null)
		{
			try
			{
				File file = new File(jShellExeutable);
				if(file.exists())
				{
					TerminalOpener terminalOpener = new TerminalOpener();
					String workDir =WorkingDirHandler.getDir(window);
					if(workDir!=null)
					{
						terminalOpener.openJShellTerminal(window,workDir,file.getAbsolutePath());
					}
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
		}
		else
		{
			MessageDialog.openError(
					window.getShell(),
					"QuickShell Error",
					"JShell Executable is not setup. Go to Eclipse Window->Preferences->QuickShell Settings and select jshell executable.");
		}

		return null;
	}
}
