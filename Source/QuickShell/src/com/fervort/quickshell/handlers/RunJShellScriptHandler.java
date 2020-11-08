package com.fervort.quickshell.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fervort.quickshell.internal.ConsoleOpener;
import com.fervort.quickshell.internal.JShellScript;
import com.fervort.quickshell.internal.WorkingDirHandler;
import com.fervort.quickshell.preferences.QuickShellConfigReader;

/***
 * 
 * Handler to run .jsh file
 * 
 * @author Nilesh Khaire
 *
 */
public class RunJShellScriptHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		if( window != null )
        {
            ISelection selection = (ISelection) window.getSelectionService().getSelection();
            
            if (selection instanceof IStructuredSelection)
            {
                
            	IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    			if(structuredSelection.size()!=1)
    			{
    				MessageDialog.openInformation(window.getShell(),"QuickShell Info","Wrong Selection. You have selected "+structuredSelection.size()+" files. Please select only one file.");
    			}else
    			{ 
    				Object obFirstSelection = structuredSelection.getFirstElement();
    					
   					IResource selectedResource = (IResource)((IAdaptable)obFirstSelection).getAdapter(IResource.class);
    				String filePath =selectedResource.getLocation().toOSString();
    				
    				String workDir = WorkingDirHandler.getDir(window);
        			String output = new JShellScript().runJShellScript(workDir, filePath);
        			
    				String quickShellOutputTarget = QuickShellConfigReader.getValue("JShellOutputTarget");
        			if(quickShellOutputTarget!=null && quickShellOutputTarget.equalsIgnoreCase("Dialog"))
        			{
        				MessageDialog.openInformation(
            				window.getShell(),
            				"QuickShell Output",
            				output);
        			}else
        			{
	    				ConsoleOpener console = new ConsoleOpener(window);
	    				console.openConsole("QuickShellConsole", output);
        			}
    			}
            }

        }
		
		return null;
	}
}
