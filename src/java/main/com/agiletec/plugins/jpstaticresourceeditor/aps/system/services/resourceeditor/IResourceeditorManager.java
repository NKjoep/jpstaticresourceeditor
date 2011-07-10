package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.agiletec.aps.system.exception.ApsException;

public interface IResourceeditorManager {

	public String readCss(String filePath) throws ApsException;
	
	public Boolean writeCss (String filePath, String fileContent) throws ApsException;	
	
	public Boolean delete(String fileToDelete) throws ApsException;

	public File create(String folder, String name) throws ApsException;
	
	public ArrayList<String> getCssList(String path);
	
	public Map<String, ArrayList<String>> getCssMap(String path);

	public List<String> getCssFoldersList(String root);

}
