package com.fervort.quickshell.internal;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/***
 * Internal class to open QuickShell console using eclipse console api
 * 
 * @author Nilesh Khaire
 *
 */
public class ConsoleOpener {

	IWorkbenchWindow window;
	public ConsoleOpener(IWorkbenchWindow window)
	{
		this.window = window;
	}
	public void openConsole(String consoleName,String output)
	{
		try {
			MessageConsole quickShellConsole = findConsole(consoleName);
			quickShellConsole.clearConsole();
			MessageConsoleStream out = quickShellConsole.newMessageStream();
			out.println(output);
			
			IWorkbenchPage page = window.getActivePage();
			String id = IConsoleConstants.ID_CONSOLE_VIEW;
			IConsoleView view = (IConsoleView) page.showView(id);
			view.display(quickShellConsole);
		
		} catch (PartInitException e) {
			e.printStackTrace();
			MessageDialog.openError(window.getShell(),"QuickShell Error"
					,"Exception. Couldn't show output on the console. "+e.getLocalizedMessage());
		}
	}
	
	/***
	 * Credit : https://wiki.eclipse.org/FAQ_How_do_I_write_to_the_console_from_a_plug-in%3F
	 * 
	 * @param name of console
	 * @return Console obj
	 */
	private MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	}
}
