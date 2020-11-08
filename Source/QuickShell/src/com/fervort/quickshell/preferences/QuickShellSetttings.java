package com.fervort.quickshell.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/***
 * 
 * Read QuickShell settings 
 * 
 * @author Nilesh Khaire
 *
 */
public class QuickShellSetttings extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public QuickShellSetttings() {
		super(GRID);
		setPreferenceStore(com.fervort.quickshell.Activator.getDefault().getPreferenceStore());
	}
	
	@Override
	public void init(IWorkbench workbench) {
		
		//setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "com.fervort.quickshell"));
		setDescription("QuickShell Configuration");
	}

	@Override
	protected void createFieldEditors() {
		
		//addField(new StringFieldEditor("com.fervort.quickshell.preferencesstore.setting.enovia.host", "Enovia Host:", getFieldEditorParent()));
		
		addField(new FileFieldEditor("com.fervort.quickshell.preferencesstore.setting.jshellexecutable", "Select jshell Executable", getFieldEditorParent()));
		addField(new DirectoryFieldEditor("com.fervort.quickshell.preferencesstore.setting.quickshellworkingdir", "QuickShell Working Dir", getFieldEditorParent()));
		
		/*
		For secure data 
		addField(new StringFieldEditor("com.fervort.quickshell.preferencesstore.settings.enovia.password", "Enovia Userpassword:", getFieldEditorParent()) 
		{
				@Override
			    protected void doFillIntoGrid(Composite parent, int numColumns) {
			        super.doFillIntoGrid(parent, numColumns);
			        getTextControl().setEchoChar('*');
			    }
		}
		);
					
		addField(new IntegerFieldEditor("com.fervort.quickshell.preferencesstore.setting.temp", "Log Path:", getFieldEditorParent()));
		addField(new BooleanFieldEditor("com.fervort.quickshell.preferencesstore.settings.enovia.enablelogs", "Enable Logs", getFieldEditorParent()));
		// addField(new StringButtonFieldEditor("com.fervort.quickshell.preferencesstore.settings.enovia.logpath", "Log Path:", getFieldEditorParent()));
		*/
		
		
	}

}
