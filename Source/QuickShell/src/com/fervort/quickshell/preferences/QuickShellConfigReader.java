package com.fervort.quickshell.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import com.fervort.quickshell.Activator;

/***
 * 
 * Plugin will access this class to read settings as well as preferences
 * 
 * @author Nilesh Khaire
 *
 */
public class QuickShellConfigReader {

	private static IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore(); 
	public static String getValue(String strKey)
	{
		String strProperties = prefStore.getString("com.fervort.quickshell.preferencesstore.configuration");
		
		if(strProperties.contains(strKey))	
		{
			String[] aProperties = strProperties.split(",");
			for (String string : aProperties) {
				if(string.contains("="))
				{
					String[] strKeyValue = string.split("=");
					if(strKeyValue[0].trim().equalsIgnoreCase(strKey))
					{
						return strKeyValue[1];
					}
				}
				
			}
		}
		return null;
	}
	
	public static String getJShellExecutable()
	{
		String jShellExecutable = prefStore.getString("com.fervort.quickshell.preferencesstore.setting.jshellexecutable");
		return jShellExecutable.isEmpty()?null:jShellExecutable;
	}
	
	public static String getQuickShellWorkingDir()
	{
		String quickShellWorkingDir =prefStore.getString("com.fervort.quickshell.preferencesstore.setting.quickshellworkingdir");
		return quickShellWorkingDir.isEmpty()?null:quickShellWorkingDir;
	}
}
