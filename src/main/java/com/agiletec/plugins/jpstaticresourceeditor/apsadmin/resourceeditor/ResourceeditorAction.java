package com.agiletec.plugins.jpstaticresourceeditor.apsadmin.resourceeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.ListOrderedMap;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.exception.ApsException;
import com.agiletec.aps.system.services.baseconfig.ConfigInterface;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.agiletec.apsadmin.system.BaseAction;
import com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor.IResourceeditorManager;
import com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor.ResourceeditorFileWrapper;
//import com.sun.tools.javac.code.Attribute.Array;
import java.util.*;

public class ResourceeditorAction extends BaseAction implements IResourceeditorAction {
	
	public String list () {
		TreeMap<String, ArrayList<String>> fold = new TreeMap<String, ArrayList<String>>();
		String root = this.getFilterFolder();
		boolean listPlugins = false;
		if (root==null||root.trim().length()==0) {
			root = this.getRootFolder()+"static/css";
			listPlugins=true;
		}
		else {
			root = this.getRootFolder()+root.trim().replaceAll("\\.\\./","");
		}
		
		String name = this.getFilterFilename();
		if (name!=null) {
			name = name.replaceAll("\\.\\./","");
		}

		Boolean searchSubfolders = this.getFilterSubfolderType()!=null&&!(this.getFilterSubfolderType().trim().equalsIgnoreCase("none")) ? false : true;
		try {
			fold = this.getJpstaticResourceeditorManager().getCssMap(root,name,searchSubfolders);
		} catch (ApsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.addActionError(this.getText("message.error.reading.path"));
		}
		//this.setCssList(fold);
		
		if (listPlugins) {
			try {
				TreeMap<String, ArrayList<String>> pluginsList = this.getJpstaticResourceeditorManager().getCssMap(this.getRootFolder()+"plugins/", name, true);
				fold.putAll(pluginsList);
			} catch (ApsException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		//TODO: move this step in an action helper
		String pathctx = this.getRootFolder();
		TreeMap<ResourceeditorFileWrapper, ArrayList<ResourceeditorFileWrapper>> tmp = new TreeMap<ResourceeditorFileWrapper, ArrayList<ResourceeditorFileWrapper>>();
		Iterator<String> cssKeys = fold.navigableKeySet().iterator();
		while (cssKeys.hasNext()) {
			String key = cssKeys.next();
			ResourceeditorFileWrapper keyWrapper = new ResourceeditorFileWrapper(key, this.getRootFolder());
			ArrayList<String> css = (ArrayList<String>) fold.get(key);
			ArrayList<ResourceeditorFileWrapper> cssWrapper = new ArrayList<ResourceeditorFileWrapper>();
			for (int i = 0;i<css.size();i++) {
				cssWrapper.add(new ResourceeditorFileWrapper(css.get(i), pathctx));
			}
			tmp.put(keyWrapper, cssWrapper);
		}
		this.setCssList(tmp);
		return SUCCESS;
	}
	//TODO: move this into an helper 
	public ResourceeditorFileWrapper getResource(String filePath) {
		filePath = this.getRootFolder()+filePath.replaceAll("\\.\\./","");
		return new ResourceeditorFileWrapper(new File(filePath), this.getRootFolder());
	}
	
	public String edit () {
		String filePath = this.getRootFolder()+this.getFile();
		this.setStrutsAction(ApsAdminSystemConstants.EDIT);
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
		int strutsAction = this.getStrutsAction();
		if (strutsAction==1) {
			return this.saveNew();
		}
		else {
			if (strutsAction==2){
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
		}
		return INPUT;
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
		this.setStrutsAction(ApsAdminSystemConstants.ADD);
		return SUCCESS;
	}
	
	public String saveNew() {
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
				String[] args = {folder, name};
				this.addActionError(this.getText("error.css.creatingNew",args));
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
		List<String> staticList =  new ArrayList<String>();
		List<String> pluginlist = new ArrayList<String>();
		try {
			staticList = this.getJpstaticResourceeditorManager().getCssFoldersList(rootFolder+"static/css");
			pluginlist = this.getJpstaticResourceeditorManager().getCssFoldersList(rootFolder+"plugins");
			for (int i = 0; i<staticList.size(); i++) {
				String current = staticList.get(i).replaceFirst(rootFolder, "");
				map.add(current);
			}
			for (int i = 0; i<pluginlist.size(); i++) {
				String current = pluginlist.get(i).replaceFirst(rootFolder, "");
				map.add(current);
			}
		} catch (ApsException e) { System.out.println("jpstaticresourceeditor: "+ e ); }
		return map;
	}

	public String getFileSeparator() {
		return System.getProperty("file.separator");
	} 
	
	public int getStrutsAction() {
		return _strutsAction;
	}
	
	public void setStrutsAction(int strutsAction) {
		this._strutsAction = strutsAction;
	}

	public String getFilterFilename() {
		return _filterFilename;
	}

	public void setFilterFilename(String filterFilename) {
		this._filterFilename = filterFilename;
	}

	public String getFilterFolder() {
		return _filterFolder;
	}

	public void setFilterFolder(String filterFolder) {
		this._filterFolder = filterFolder;
	}

	public String getFilterSubfolderType() {
		return _filterSubfolderType;
	}

	public void setFilterSubfolderType(String filterSubfolderType) {
		this._filterSubfolderType = filterSubfolderType;
	}

	public TreeMap<ResourceeditorFileWrapper, ArrayList<ResourceeditorFileWrapper>> getCssList() {
		return this._cssList;
	}
	
	private void setCssList(TreeMap<ResourceeditorFileWrapper, ArrayList<ResourceeditorFileWrapper>> list) {
		this._cssList = list;
	}
	
	private IResourceeditorManager _jpstaticresourceeditorResourceeditorManager;
	private String _file;
	private String _fileContent;
	private String _keepOpen;
	private String _folder;
	private String _filterFilename;
	private String _filterFolder;
	private String _filterSubfolderType;
	private int _strutsAction;
	private TreeMap<ResourceeditorFileWrapper, ArrayList<ResourceeditorFileWrapper>> _cssList;
}
