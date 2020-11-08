package com.fervort.quickshell.internal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.fervort.quickshell.preferences.QuickShellConfigReader;
/***
 * 
 * Class to execute jshell script.
 * 
 * @author Nilesh Khaire
 *
 */
public class JShellScript {

	public String runAsJShellScript(String workingDir,String selectedText)
	{
	    try {
						
			String jShellPath = QuickShellConfigReader.getJShellExecutable();
			
			if(jShellPath!=null)
			{
				String scriptFile = buildJSHFile(workingDir, selectedText);
				
				File jShellExecutable = new File(jShellPath);
				if(!jShellExecutable.exists())
					return "jshell executable is not exist on path: "+jShellExecutable.getAbsolutePath();
				
	            return executeScript(jShellExecutable, scriptFile);
			}
			else
			{
				return "jshell executable is not set. Go to eclipse Window->Preferences->QuickShell Settings and add jshell executable path";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Can't execute script "+e.getLocalizedMessage();
		} 
	    
	}
	
	public String runJShellScript(String workingDir ,String editorPath)
	{
	    try {
			
	    	String jShellPath = QuickShellConfigReader.getJShellExecutable();
			
			if(jShellPath!=null)
			{
				List<String> fileAsList = Files.readAllLines(Paths.get(editorPath), StandardCharsets.UTF_8);
				String scriptFile = buildJSHFile(workingDir, fileAsList);
				
				File jShellExecutable = new File(jShellPath);
				if(!jShellExecutable.exists())
					return "jshell executable is not exist on path: "+jShellExecutable.getAbsolutePath();
				
	            return executeScript(jShellExecutable, scriptFile);
			}
			else
			{
				return "jshell executable is not set. Go to eclipse Window->Preferences->QuickShell Settings and add jshell executable path";
			}
	
		} catch (Exception e) {
			
			return "Can't execute script "+e.getLocalizedMessage();
		}
	}
	
	private String executeScript(File jShellExecutable,String scriptFile) throws IOException, InterruptedException {
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(jShellExecutable.getAbsolutePath(), scriptFile);

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        
        BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            sb.append(line).append(System.lineSeparator());
        }
        // Error stream is not needed because of processBuilder.redirectErrorStream(true);
        // InputStream error = process.getErrorStream();
        
        int exitCode = process.waitFor();
        
        return sb.toString();
	}
	private String buildJSHFile(String workingDir,String text) throws IOException
	{
		String scriptPath = Paths.get(workingDir,"JShellScript.jsh").toString();
		BufferedWriter writer = new BufferedWriter(new FileWriter(scriptPath));
		writer.write(text);
		writer.write(System.lineSeparator());
		writer.write("/exit");
		writer.close();
		
		return scriptPath;
	}
	
	public  static String buildJSHFile(String workingDir, List<String> fileAsList) throws IOException
	{
		fileAsList.add(System.lineSeparator());
		fileAsList.add("/exit");
		Path path =Files.write(Paths.get(workingDir,"JShellScript.jsh"), fileAsList, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
		
		return path.toString();
	}
}
