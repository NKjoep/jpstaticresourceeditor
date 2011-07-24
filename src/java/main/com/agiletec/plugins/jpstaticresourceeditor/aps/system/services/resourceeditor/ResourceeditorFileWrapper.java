package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.tools.hat.internal.util.ArraySorter;

public class ResourceeditorFileWrapper  implements IResourceeditorFileWrapper, Comparable<ResourceeditorFileWrapper> {
	
	public ResourceeditorFileWrapper (File f, String context) {
		this.setFile(f);
		this.setContextPath(context);
	}

	public ResourceeditorFileWrapper(String f, String context) {
		this.setFile(new File(f));
		this.setContextPath(context);
	}

	private void setContextPath(String context) {
		this._contextPath = context;
	}
	
	private void setFile(File f2) {
		this._file = f2;
	}


	@Override
	public String getAbsolutePath() {
		// TODO Auto-generated method stub
		return this._file.getAbsolutePath();
	}

	@Override
	public String getName() {
		return this._file.getName();
	}

	@Override
	public File getFile() {
		return this._file;
	}
	
	public long getSize () {
		return this.getFile().length();
	}

	@Override
	public String getPath() {
		return this._file.getAbsolutePath().replaceFirst(this._contextPath, "");
	}
	
	@Override
	public int compareTo(ResourceeditorFileWrapper o) {
		if (o.getAbsolutePath().equals(this.getAbsolutePath())) return 0;
		
		String[] s = {o.getAbsolutePath(), this.getAbsolutePath()};
		Arrays.sort(s);
		
		if (s[0].equals(o.getAbsolutePath())) { 
			return -1;
		}
		return 1;
	}

	private File _file;
	private String _contextPath;
}
