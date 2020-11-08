package com.fervort.quickshell.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.tm.terminal.view.core.TerminalServiceFactory;
import org.eclipse.tm.terminal.view.core.interfaces.ITerminalService;
import org.eclipse.tm.terminal.view.core.interfaces.constants.ITerminalsConnectorConstants;
import org.eclipse.tm.terminal.view.ui.view.TerminalsView;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/***
 * 
 * Eclipse terminal opener
 * 
 * @author Nilesh Khaire
 *
 */
public class TerminalOpener {

	private static String ENCODING = "UTF-8";
	private static String TERMINAL_VIEW_ID = "org.eclipse.tm.terminal.view.ui.TerminalsView";
	private static String DELEGATE_ID = "org.eclipse.tm.terminal.connector.local.launcher.local";
	
	public void openJShellTerminal(final IWorkbenchWindow window,final String workingDir,final String jShellExecutable)
	{
		IWorkbench workbench = PlatformUI.getWorkbench();
		
		Display display = workbench.getDisplay();
		display.asyncExec( new Runnable () {
		
		@Override
        public void run (){
				
			try {   
				TerminalsView terminalView = (TerminalsView) window.getActivePage().showView(TERMINAL_VIEW_ID);
				
				terminalView.show(null);
				terminalView.setFocus();
				
				// Define the terminal properties
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(ITerminalsConnectorConstants.PROP_TITLE, "QuickShell Terminal");
				properties.put(ITerminalsConnectorConstants.PROP_ENCODING, ENCODING);
				properties.put(ITerminalsConnectorConstants.PROP_DELEGATE_ID, DELEGATE_ID);
				// workingDir could be commented. It is not necessary to open a terminal
				properties.put(ITerminalsConnectorConstants.PROP_PROCESS_WORKING_DIR, workingDir);
				//properties.put(ITerminalsConnectorConstants.PROP_PROCESS_MERGE_ENVIRONMENT, true);
				//properties.put(ITerminalsConnectorConstants.PROP_LOCAL_ECHO, false);
				//properties.put(ITerminalsConnectorConstants.PROP_FORCE_NEW, true);
				
				properties.put(ITerminalsConnectorConstants.PROP_PROCESS_PATH, jShellExecutable);
				
				
				ITerminalService.Done done = new ITerminalService.Done() {
				    
					@Override
					public void done(IStatus arg0) {
						System.out.println("Terminal opened :)");
					}
				};
				
				ITerminalService terminal = TerminalServiceFactory.getService();
				
				if(terminal!=null)
				{
						terminal.openConsole(properties, done);
				}
				
			} catch (PartInitException e) {
				e.printStackTrace();
				MessageDialog.openError(
						window.getShell(),
						"QuickShell Error",
						"Something went wrong in openJShellTerminal. "+e.getLocalizedMessage());
				//System.out.println("Exception "+e);
			}
        } 
		});
	}
}
