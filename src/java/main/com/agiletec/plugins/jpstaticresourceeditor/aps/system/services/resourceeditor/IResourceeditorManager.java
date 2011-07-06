package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.util.ArrayList;
import java.util.Map;

import com.agiletec.aps.system.exception.ApsException;

public interface IResourceeditorManager {

	public Boolean writeCss (String filePath, String fileContent) throws ApsException;	
	
	public String readCss(String filePath) throws ApsException;
	
	public ArrayList<String> getCssList(String path);
	
	public Map<String, ArrayList<String>> getCssMap(String path);

}
