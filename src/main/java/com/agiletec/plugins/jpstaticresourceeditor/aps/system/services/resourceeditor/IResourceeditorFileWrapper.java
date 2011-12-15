package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.File;
import java.util.ArrayList;

import com.agiletec.aps.system.exception.ApsException;

public interface IResourceeditorFileWrapper {


	public String getAbsolutePath();	
	
	public String getName();
	
	public File getFile();

	public String getPath();

	int compareTo(ResourceeditorFileWrapper o);
	
}
