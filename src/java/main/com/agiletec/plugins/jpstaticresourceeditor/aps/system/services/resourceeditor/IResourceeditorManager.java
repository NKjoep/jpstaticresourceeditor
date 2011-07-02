package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.agiletec.aps.system.exception.ApsException;

public interface IResourceeditorManager {

	public Boolean writeCss (String filePath, String fileContent) throws ApsException;	
	
	public String readCss(String filePath) throws ApsException;
	
	public ArrayList<String> getCssList(String path);

	public ArrayList<String> cssPluginsFiles();

}
