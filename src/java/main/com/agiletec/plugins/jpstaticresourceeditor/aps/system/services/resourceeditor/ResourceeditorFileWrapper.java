package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.File;

public class ResourceeditorFileWrapper  implements IResourceeditorFileWrapper {

	public ResourceeditorFileWrapper (File f, String context) {
		this.setFile(f);
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

	@Override
	public String getPath() {
		return this._file.getAbsolutePath().replaceFirst(this._contextPath, "");
	}

	private File _file;
	private String _contextPath;
}
