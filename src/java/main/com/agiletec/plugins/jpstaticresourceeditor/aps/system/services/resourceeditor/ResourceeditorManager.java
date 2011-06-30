package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsException;

public class ResourceeditorManager extends AbstractService implements IResourceeditorManager {
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public Boolean writeCss (String filePath, String fileContent) throws ApsException {	
		if (filePath.trim().length()>0 && fileContent.trim().length()>0) {
			File file = new File(filePath);
			if (file!=null && file.isFile() && file.canWrite()) {
				file.delete();
				File yourNewFile = new File(filePath);
				try {
					yourNewFile.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					FileWriter outFile = new FileWriter(yourNewFile);
					PrintWriter out = new PrintWriter(outFile);
					out.println(fileContent);
					out.close();
					
					return true;
					
				} catch (IOException e){
					  e.printStackTrace();
			}			
			}
		}
		return false;
	}
	
	public String readCss(String filePath) {
		if (filePath.trim().length()>0) {
			File f = new File(filePath);
			if (f.exists()) {
				
				File file = f;
		        StringBuffer contents = new StringBuffer();
		        BufferedReader reader = null;

		        try {
		            reader = new BufferedReader(new FileReader(file));
		            String text = null;

		            // repeat until all lines is read
		            while ((text = reader.readLine()) != null) {
		                contents.append(text)
		                    .append(System.getProperty(
		                        "line.separator"));
		            }
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (reader != null) {
		                    reader.close();
		                }
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        
				return contents.toString();
			}
		}
		return null;
	}
	
	public ArrayList<String> getCssList(String path) {
		ArrayList<String> list = searchCssInDirectory(path);
		return list;
	}

	public ArrayList<String> cssPluginsFiles() {
		ArrayList files = new ArrayList<String>();
		return files;
	}
	
	
	// Process all files and directories under dir
	public ArrayList<String> searchCssInDirectory(String cssPath) {
		File f = new File(cssPath);
		ArrayList<String> list = new ArrayList<String>();
		
		visitAllDirsAndFiles(f, list);
		for(int i = 0;i<list.size();i++) {
			String current = list.get(i);
			//System.out.println("in list: "+current);
		}
		return list;
	}
	
	public void read(File file, ArrayList<String> list) {
		if (!file.isDirectory()) {
			if (file.getName().toLowerCase().endsWith(".css")) {
				//System.out.println("file: "+ file.getAbsolutePath());
				//System.out.println("adding..");
				list.add(file.getAbsolutePath());
			}
		}
	}
	
	// Process all files and directories under dir
	public void visitAllDirsAndFiles(File dir, ArrayList<String> list) {
		this.read(dir, list);
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            visitAllDirsAndFiles(new File(dir, children[i]), list);
	        }
	    }
	}
	
}
