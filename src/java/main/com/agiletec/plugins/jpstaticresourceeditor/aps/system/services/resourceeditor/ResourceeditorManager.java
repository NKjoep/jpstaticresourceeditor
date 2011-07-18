package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsException;
import com.agiletec.aps.system.exception.ApsSystemException;

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

	public ArrayList<String> getCssList(String path) {
		ArrayList<String> list = null;
		File f = new File(path);
		if (f.exists() && f.canRead() && f.isDirectory() && f.list().length>0) {
			list = new ArrayList<String>();
			String[] children = f.list();
			for (int i=0; i<children.length; i++) {
				File currentFile = new File(children[i]);
				if (currentFile.getName().toLowerCase().endsWith(".css")) {
					list.add(currentFile.getAbsolutePath());
				}
			}
		}
		return list;
	}

	// Process all files and directories under dir
	public ArrayList<String> searchCssInDirectory(String cssPath) {
		File f = new File(cssPath);
		ArrayList<String> list = null;
		if (f.exists() && f.canRead()) {
			list = new ArrayList<String>();
			visitAllDirsAndFiles(f, list);
		}
		return list;
	}

	public Map<String, ArrayList<String>> getCssMap(String path) {
		File f = new File(path);
		Map<String, ArrayList<String>> map = null;
		if (f.exists() && f.canRead()) {
			map = new TreeMap<String, ArrayList<String>>();
			visitAllDirsAndFiles(f, map);
		}
		return map;
	}

	protected void visitAllDirsAndFiles(File file, Map<String, ArrayList<String>> map) {
		if (file.canRead()) {
			if (file.isDirectory()) {
				if (file.list().length>0) {
					map.put(file.getAbsolutePath(), new ArrayList<String>());
					String[] children = file.list();
					for (int i=0; i<children.length; i++) {
						visitAllDirsAndFiles(new File(file, children[i]), map);
					}
					if (map.get(file.getAbsolutePath()).size()==0) {
						map.remove(file.getAbsolutePath());
					}
				}
			}
			else {
				if (file.getName().toLowerCase().endsWith(".css")) {
					ArrayList<String> list = map.get(file.getParent());
					list.add(file.getAbsolutePath());
					map.put(file.getParent(), list);
				}
			}
		}
	} 
	
	// Process all files and directories under dir
	protected void visitAllDirsAndFiles(File file, ArrayList<String> list) {
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] children = file.list();
				for (int i=0; i<children.length; i++) {
					visitAllDirsAndFiles(new File(file, children[i]), list);
				}
			}
			else {
				if (file.getName().toLowerCase().endsWith(".css")) {
					list.add(file.getAbsolutePath());
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
	public List<String> getCssFoldersList(String root) {
		if (root != null && root.trim().length()>0) {
			List <String> folderList = new ArrayList<String>();
			Collection<String> m = getCssMap(root).keySet();
			folderList.addAll(m);
			return folderList;
		}
		return null;
	}
}
