package com.fervort.quickshell.preferences;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
/***
 * 
 * Class to read QuickShell configuration from preferences store
 * 
 * @author Nilesh Khaire
 *
 */
public class QuickShellConfiguration extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public QuickShellConfiguration()
	{
		super(GRID);
		setPreferenceStore(com.fervort.quickshell.Activator.getDefault().getPreferenceStore());
	}
	
	@Override
	public void init(IWorkbench arg0) {
		setDescription("add properties in key value format. Key and value should be seprated by equal sign.");
	}

	@Override
	protected void createFieldEditors() {
		
		addField(new ListEditor( "com.fervort.quickshell.preferencesstore.configuration","QuickShell Configuration",getFieldEditorParent()) {
			
			@Override
	        protected String createList(String[] items) {
				String strReturn ="";
				int i=0;
				for (String string : items) {
					if(i==0)
					{
						strReturn=string;
					}else
					{
						strReturn= strReturn+","+string;
					}
					i++;
				}
	            return strReturn;
	        }

	        @Override
	        protected String getNewInputObject() {
	            InputDialog d = new InputDialog(getShell(), "New Property", "Add a property separated by = sign like (Logging=true)", "",
	                    new IInputValidator() {

	                        @Override
	                        public String isValid(String newText) {
	                            /*
	                        	if (newText.indexOf(' ') != -1 || newText.indexOf(',') != -1) {
	                                return "The input cannot have spaces and commas (,) ";
	                            }
	                            */
	                        	if (newText.indexOf(',') != -1) {
	                                return "The input cannot have commas (,) ";
	                            }
	                        	
	                        	if (newText.indexOf('=') == -1) {
	                                return "= is missing. Invalid key-value pair";
	                            }
	                            return null;
	                        }
	                    });

	            int retCode = d.open();
	            if (retCode == InputDialog.OK) {
	                return d.getValue();
	            }
	            return null;
	        }

	        @Override
	        protected String[] parseString(String stringList) {
	            if(stringList.isEmpty())
	            {
	            	return new String[0];
	            }
	        	return stringList.split(",");
	        }

	        @Override
	        protected void doFillIntoGrid(Composite parent, int numColumns) {
	            super.doFillIntoGrid(parent, numColumns);
	            List listControl = getListControl(parent);
	            GridData layoutData = (GridData) listControl.getLayoutData();
	            layoutData.heightHint = 400;
	        }
			
		}); 
		
	}

}
