package com.agiletec.plugins.jpstaticresourceeditor.apsadmin.resourceeditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.FileHandler;

import sun.misc.Regexp;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.exception.ApsException;
import com.agiletec.aps.system.services.baseconfig.BaseConfigManager;
import com.agiletec.aps.system.services.baseconfig.ConfigInterface;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.apsadmin.system.BaseAction;
import com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor.IResourceeditorManager;
import com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor.ResourceeditorFileWrapper;


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
		String filePath = this.getRootFolder()+this.getFileToEdit();
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
			String[] args = {this.getFileToEdit()};
			this.addActionError(this.getText("error.css.reading", args)); 
		}
		return FAILURE;
	}	
	
	public String save() {
		String filePath = this.getRootFolder()+this.getFileToEdit();
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
			String[] args = {this.getFileToEdit()};
			this.addActionError(this.getText("error.css.writing", args));
			return INPUT;
		}
	}

	public String createNew() {
		return SUCCESS;
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
			path = this.getRootFolder()+path.replaceAll("../", "");
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
			path = this.getRootFolder()+path.replaceAll("../", "");
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
	
	public String getFileToEdit() {
		return _fileToEdit;
	}

	public void setFileToEdit(String fileToEdit) {
		this._fileToEdit = fileToEdit.replace("..", "").replace("./", "");
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

	private IResourceeditorManager _jpstaticresourceeditorResourceeditorManager;
	private String _fileToEdit;
	private String _fileContent;
	private String _keepOpen;

}
