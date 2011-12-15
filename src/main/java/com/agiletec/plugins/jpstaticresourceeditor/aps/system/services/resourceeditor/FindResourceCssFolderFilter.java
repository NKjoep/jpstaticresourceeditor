package com.agiletec.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

public class FindResourceCssFolderFilter implements IOFileFilter {

	public FindResourceCssFolderFilter(String namePattern) {
		if (namePattern!=null) {
			this.namePattern = namePattern.toLowerCase();			
		}
		else {
			this.namePattern = null;
		}
	}
	
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()&&file.canExecute()&&file.canRead()&&file.canWrite()) {
			String[] containingFiles = file.list();
			for (int i = 0;i<containingFiles.length;i++) {
				File current = new File(containingFiles[i]);
				String currentName = current.getName().toLowerCase();
				boolean nameCheck = true;				
				if (this.namePattern!=null) { nameCheck = currentName.contains(this.namePattern); }
				if (!current.isDirectory() && currentName.endsWith(".css") && nameCheck) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean accept(File file, String namePattern) {
		if (file.isDirectory()&&file.canExecute()&&file.canRead()&&file.canWrite()) {
			String[] containingFiles = file.list();
			for (int i = 0;i<containingFiles.length;i++) {
				File current = new File(containingFiles[i]);
				String currentName = current.getName().toLowerCase();
				boolean nameCheck = true;				
				if (this.namePattern!=null) { nameCheck = currentName.contains(namePattern); }
				if (!current.isDirectory() && currentName.endsWith(".css") && nameCheck) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String namePattern;

}
