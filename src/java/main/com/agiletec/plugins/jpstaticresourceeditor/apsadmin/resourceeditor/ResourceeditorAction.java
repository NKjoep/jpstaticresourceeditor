package com.agiletec.plugins.jpstaticresourceeditor.apsadmin.resourceeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.exception.ApsException;
import com.agiletec.aps.system.services.baseconfig.ConfigInterface;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.aps.util.SelectItem;
import com.agiletec.apsadmin.system.BaseAction;
import com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor.IResourceeditorManager;
import com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor.ResourceeditorFileWrapper;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;


public class ResourceeditorAction extends BaseAction implements IResourceeditorAction {
	
	public String list () {
		Boolean check = true;
		if (check) {
			return SUCCESS;
		}
		else {
			return FAILURE;
			
		}
	}
	
	public String edit () {
		String filePath = this.getRootFolder()+this.getFile();
		//System.out.println(new Date().getTime() + " editing: "+filePath);
		String cssContent;
		try {
			cssContent = this.getJpstaticResourceeditorManager().readCss(filePath);
			if (cssContent!=null && cssContent.trim().length()>0) {
				this.setFileContent(cssContent);
				return SUCCESS;
			}
		} catch (ApsException e) {
			//e.printStackTrace();
			String[] args = {this.getFile()};
			this.addActionError(this.getText("error.css.reading", args)); 
		}
		return FAILURE;
	}	
	
	public String save() {
		String filePath = this.getRootFolder()+this.getFile();
		String fileContent = this.getFileContent();
		try {
			this.getJpstaticResourceeditorManager().writeCss(filePath, fileContent);
			if (this.getKeepOpen()!=null) {
				return INPUT;
			}
			else {
				return SUCCESS;
			}
		}
		catch (ApsException e) {
			String[] args = {this.getFile()};
			this.addActionError(this.getText("error.css.writing", args));
			return INPUT;
		}
	}
	
	public String delete() {
		String fileToDelete = this.getFile();
		if (fileToDelete!=null && fileToDelete.trim().length()>0) {
			fileToDelete = fileToDelete.replaceAll("\\.\\./", "");
			fileToDelete = this.getRootFolder()+fileToDelete;			
			try {
				this.getJpstaticResourceeditorManager().delete(fileToDelete);
				return SUCCESS;
			}
			catch (ApsException e) {
				String[] args = {fileToDelete};
				this.addActionError(this.getText("error.css.deleting",args));
				return INPUT;
			}
		}
		else {
			this.addActionError(this.getText("error.css.deleting.fileNull"));
			return INPUT;
		}
		
	}
	
	public String createNew() {
		String folder = this.getFolder();
		String name = this.getFile();
		String content = this.getFileContent();
		if (folder!=null && folder.trim().length()>0 && name!=null && name.trim().length()>0) {
			//TODO ADD control over the folder 
			folder = this.getRootFolder()+folder.replaceAll("\\.\\./","");
			name = name.replaceAll("\\.\\./","");
			try {
				File newFile = this.getJpstaticResourceeditorManager().create(folder, name);
				this.getJpstaticResourceeditorManager().writeCss(newFile.getAbsolutePath(), content);
				return SUCCESS;
			}
			catch (ApsException e) {
				this.addActionError(this.getText("error.css.creatingNew"));
				return INPUT;
			}
		}
		return INPUT;
	}

	private String getRootFolder() {
		String folderPath = "";
		ConfigInterface configService = (ConfigInterface) ApsWebApplicationUtils.getBean(SystemConstants.BASE_CONFIG_MANAGER, this.getRequest());
		String parResourcesDiskRootFolder = configService.getParam(SystemConstants.PAR_RESOURCES_DISK_ROOT);
		folderPath = parResourcesDiskRootFolder;
		return folderPath;
	}
	
	public ArrayList<ResourceeditorFileWrapper> getCssFiles(String path) {
		if (path==null || path.trim().length()==0) {
			path = this.getRootFolder()+"static/css";
		}
		else {
			path = this.getRootFolder()+path.replaceAll("\\.\\./", "");
		}
		ArrayList<String> filenamesList = this.getJpstaticResourceeditorManager().getCssList(path);
		ArrayList<ResourceeditorFileWrapper> files = new ArrayList<ResourceeditorFileWrapper>();
		for (int i = 0;i<filenamesList.size();i++) {
			String current = filenamesList.get(i);
			files.add(new ResourceeditorFileWrapper(new File(current), this.getRootFolder()));
		}
		return files;
	}
	
	public Map<String, ArrayList<ResourceeditorFileWrapper>> getCssFilesMap(String path) {
		Map<String, ArrayList<ResourceeditorFileWrapper>> returnMap = new TreeMap<String, ArrayList<ResourceeditorFileWrapper>>();
		if (path==null || path.trim().length()==0) {
			path = this.getRootFolder()+"static/css";
		}
		else {
			path = this.getRootFolder()+path.replaceAll("\\.\\./", "");
		}
		Map<String, ArrayList<String>> tmpMap = this.getJpstaticResourceeditorManager().getCssMap(path);
		if (tmpMap.size()>0) {
			String context = this.getRootFolder();
			while(!(tmpMap.size()==0)) {
				String nextKey = tmpMap.keySet().iterator().next();
				ArrayList<String> nextList = tmpMap.get(nextKey);
				ArrayList<ResourceeditorFileWrapper> returnList = new ArrayList<ResourceeditorFileWrapper>();
				for (int i = 0;i<nextList.size();i++) {
					File f = new File(nextList.get(i));
					returnList.add(new ResourceeditorFileWrapper(f, context));
				}
				returnMap.put(new ResourceeditorFileWrapper(new File(nextKey), context).getPath(), returnList);
				tmpMap.remove(nextKey);
			}
		}
		return returnMap;
	}

	public ArrayList<ResourceeditorFileWrapper> getCssFiles() {
		return this.getCssFiles(null);
	}
	
	public IResourceeditorManager getJpstaticResourceeditorManager() {
		return _jpstaticresourceeditorResourceeditorManager;
	}

	public void setJpstaticresourceeditorResourceeditorManager(IResourceeditorManager jpstaticresourceeditorResourceeditorManager) {
		this._jpstaticresourceeditorResourceeditorManager = jpstaticresourceeditorResourceeditorManager;
	}
	
	public String getFile() {
		return this._file;
	}

	public void setFile(String file) {
		this._file = file.replace("\\.\\./", "");
	}

	public String getFileContent() {
		return _fileContent;
	}
	
	public void setFileContent(String string) {
		this._fileContent = string;
	}

	public String getKeepOpen() {
		return _keepOpen;
	}

	public void setKeepOpen(String keepOpen) {
		this._keepOpen = keepOpen;
	}

	public String getFolder() {
		return _folder;
	}
	
	public void setFolder(String folder) {
		this._folder = folder;
	}

	public List<String> getCssFoldersMap() {
		String rootFolder = this.getRootFolder();
		List<String> map = new ArrayList<String>();
		List<String> staticList = this.getJpstaticResourceeditorManager().getCssFoldersList(rootFolder+"static/css");
		List<String> pluginlist = this.getJpstaticResourceeditorManager().getCssFoldersList(rootFolder+"plugins");
		for (int i = 0; i<staticList.size(); i++) {
			String current = staticList.get(i).replaceFirst(rootFolder, "");
			map.add(current);
		}
		for (int i = 0; i<pluginlist.size(); i++) {
			String current = pluginlist.get(i).replaceFirst(rootFolder, "");
			map.add(current);
		}
		return map;
	}

	private IResourceeditorManager _jpstaticresourceeditorResourceeditorManager;
	private String _file;
	private String _fileContent;
	private String _keepOpen;
	private String _folder;
}
