package com.fervort.quickshell.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IMarkSelection;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.fervort.quickshell.internal.ConsoleOpener;
import com.fervort.quickshell.internal.JShellScript;
import com.fervort.quickshell.internal.WorkingDirHandler;
import com.fervort.quickshell.preferences.QuickShellConfigReader;

/***
 * Handler for running selected text as JShell script 
 * @author Nilesh Khaire
 */
public class RunAsJShellScriptHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		if( window != null )
        {
            ISelection selection = (ISelection) window.getSelectionService().getSelection();
            
             
            if (selection instanceof IStructuredSelection)
            {
            	// Will never reach to this condition
            	MessageDialog.openError(
        				window.getShell(),
        				"QuickShell Error",
        				"Wrong Selection");
            } else if (selection instanceof ITextSelection) 
            {
            	
    			ITextSelection ts  = (ITextSelection) selection;
    			//System.out.println(ts.getText());
    			IWorkbenchPart workbenchPart = window.getActivePage().getActivePart();
    			IFile resourcefile = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
    			// resourcefile.getLocation().toOSString() gives complete file path
    			
    			String workDir = WorkingDirHandler.getDir(window);
    			String output = new JShellScript().runAsJShellScript(workDir, ts.getText());
    			
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
    		} else if (selection instanceof IMarkSelection) {
    			
    			MessageDialog.openError(window.getShell(),"QuickShell Error","Wrong Selection. You have selected Mark Selection");
    			/*
    			IMarkSelection ms = (IMarkSelection) selection;
    			System.out.println("IMarkSelection "+ms.getDocument());
    			try {
    			    System.out.println(ms.getDocument().get(ms.getOffset(), ms.getLength()));
    			    
    			} catch (BadLocationException ble) { }
    			*/
    		}
            
        }
		
		return null;
	}
}
