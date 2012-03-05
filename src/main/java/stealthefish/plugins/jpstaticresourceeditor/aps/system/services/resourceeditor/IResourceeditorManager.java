package stealthefish.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.agiletec.aps.system.exception.ApsException;

public interface IResourceeditorManager {

	public String readCss(String filePath) throws ApsException;
	
	public Boolean writeCss (String filePath, String fileContent) throws ApsException;	
	
	public Boolean delete(String fileToDelete) throws ApsException;

	public File create(String folder, String name) throws ApsException;
	
	public List<String> getCssFoldersList(String root) throws ApsException;

	public TreeMap<String, ArrayList<String>> getCssMap(String filterFolder, String filterFilename, Boolean filterSubfolderType) throws ApsException;
}
