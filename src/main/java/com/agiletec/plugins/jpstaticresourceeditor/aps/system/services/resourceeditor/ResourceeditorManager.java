package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.core.io.ResourceEditor;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsException;
import com.agiletec.aps.system.exception.ApsSystemException;
//import com.sun.tools.javac.code.Attribute.Array;

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
					FileWriter outFile = new FileWriter(yourNewFile);
					PrintWriter out = new PrintWriter(outFile);
					out.println(fileContent);
					out.close();
					return true;
				}
				catch (Exception e) {
					throw new ApsSystemException("jpstaticresourceeditor: Error writing the css file", e);
				}
			}
			else {
				throw new ApsSystemException("jpstaticresourceeditor: Error writing the css file");
			}
		}
		return false;
	}

	public String readCss(String filePath) throws ApsException {
		if (filePath.trim().length()>0) {
			File f = new File(filePath);
			if (f.exists()) {
				File file = f;
				StringBuffer contents = new StringBuffer();
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String text = null;
						while ((text = reader.readLine()) != null) {
							contents.append(text).append(System.getProperty("line.separator"));
						}
				}
				catch (Exception e) {
					throw new ApsException("jpstaticresourceeditor: Error while reading the file "+f.getAbsolutePath(), e);
				}
				finally {
					try {
						if (reader != null) {
							reader.close();
						}
					}
					catch (Exception e) {
						throw new ApsException("jpstaticresourceeditor: Error while reading the file "+f.getAbsolutePath(), e);
					}
				}
				return contents.toString();
			}
		}
		return null;
	}

	public ArrayList<String> getCssList(String path, String namePattern) {
		ArrayList<String> list = null;
		File f = new File(path);
		if (f.exists() && f.canRead() && f.isDirectory() && f.list().length>0) {
			list = new ArrayList<String>();
			File[] children = f.listFiles();
			for (int i=0; i<children.length; i++) {
				File currentFile = children[i];
				String currentName = currentFile.getName().toLowerCase();
				boolean nameCheck = namePattern!=null?false:true;
				if (namePattern!=null&&namePattern.length()>0) {
					if (currentName.contains(namePattern.toLowerCase())) { 
						nameCheck = true;
					}
				}
				if (currentName.endsWith(".css")&&nameCheck) {
					list.add(currentFile.getAbsolutePath());
				}
			}
		}
		return list;
	}

	// Process all files and directories under dir
	protected void visitAllDirsAndFiles(File file, Map<String, ArrayList<String>> map, String filenamePattern) {
		if (file.canRead()) {
			if (file.isDirectory() && !file.getName().equalsIgnoreCase("administration")) {
				if (file.list().length>0) {
					map.put(file.getAbsolutePath(), new ArrayList<String>());
					String[] children = file.list();
					Arrays.sort(children);
					for (int i=0; i<children.length; i++) {
						visitAllDirsAndFiles(new File(file, children[i]), map, filenamePattern);
					}
					if (map.get(file.getAbsolutePath()).size()==0) {
						map.remove(file.getAbsolutePath());
					}
				}
			}
			else {
				if (file.getName().toLowerCase().endsWith(".css")) {
					if (filenamePattern==null||filenamePattern.length()==0) {
						ArrayList<String> list = map.get(file.getParent());
						list.add(file.getAbsolutePath());
						map.put(file.getParent(), list);						
					}
					else {
						if (file.getName().toLowerCase().contains(filenamePattern.toLowerCase())) {
							ArrayList<String> list = map.get(file.getParent());
							list.add(file.getAbsolutePath());
							map.put(file.getParent(), list);						
						}
					}
				}
			}
		}
	} 

	@Override
	public Boolean delete(String fileToDelete) throws ApsException {
		Boolean check = false;
		File file = new File(fileToDelete);
		if (file.exists() && !file.isDirectory() && file.canWrite()) {
			file.renameTo(new File(file.getAbsoluteFile()+".deleted"));
			check = true;
		}
		else {
			throw new ApsException("jpstaticresourceeditor: cannot delete file"+fileToDelete);
		}
		return check;
	}

	@Override
	public File create(String folder, String name) throws ApsException {
		if (folder!=null && name!=null) {
			if (!folder.endsWith("/") || !folder.endsWith("\\")) {
				folder = folder+"/";
			}
			if (!name.endsWith(".css")) {
				name = name+".css";
			}
			try {
				File file = new File(folder+name);
				Boolean creationIsOK = file.createNewFile();
				if (creationIsOK) {
					return file;
				}
				else {
					throw new ApsException("jpstaticresourceeditor: cannot istatiate file: "+folder+name);
				}			
			}
			catch (IOException e) {
				throw new ApsException("jpstaticresourceeditor: cannot istatiate file: "+folder+name);
			}
		}
		else {
			throw new ApsException("jpstaticresourceeditor: cannot istatiate file: "+folder+name);
		}
	}

	@Override
	public List<String> getCssFoldersList(String root) throws ApsException {
		if (root != null && root.trim().length()>0) {
			List <String> folderList = new ArrayList<String>();
			Map<String, ArrayList<String>> m = this.getCssMap(root,null, true);
			folderList.addAll(m.keySet());
			return folderList;
		}
		return null;
	}
	
	public TreeMap<String, ArrayList<String>> getCssMap(String searchFolder, String searchName, Boolean browseSubfolders) throws ApsException {
		File rootFolder = new File(searchFolder);
		if (!(rootFolder.isDirectory()||rootFolder.canWrite())) throw new ApsException("jpstaticresourceeditor: cannot read folder"+searchFolder);
		TreeMap<String, ArrayList<String>> m = new TreeMap<String, ArrayList<String>>();
		visitAllDirsAndFiles(rootFolder, m, searchName);
		return m;
	}
}
