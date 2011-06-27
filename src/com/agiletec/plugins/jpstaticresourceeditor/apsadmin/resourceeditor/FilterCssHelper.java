package com.agiletec.plugins.jpstaticresourceeditor.apsadmin.resourceeditor;

import java.io.File;
import java.io.FileFilter;

public class FilterCssHelper implements FileFilter {
	private final String okExtension = ".css";

	public boolean accept(File file) {
		if (!file.isDirectory()) {
			if (file.getName().toLowerCase().endsWith(okExtension)) {
				return true;
			}
		}
		return false;
	}

}

/*
//It is also possible to filter the list of returned files.
//This example does not return any files that start with `.'.
FilenameFilter filter = new FilenameFilter() {
 public boolean accept(File dir, String name) {
     return !name.startsWith(".");
 }
};
children = dir.list(filter);
*/