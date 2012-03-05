package stealthefish.plugins.jpstaticresourceeditor.aps.system.services.resourceeditor;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

public class FindResourceCssFileFilter implements IOFileFilter {

	public FindResourceCssFileFilter(String namePattern) {
		if (namePattern!=null) {
			this.namePattern = namePattern.toLowerCase();			
		}
		else {
			this.namePattern = null;
		}
	}

	@Override
	public boolean accept(File file) {
		if (file.getName().trim().toLowerCase().endsWith(".css")) {
			if (this.namePattern==null) {
				return true;
			}
			else {
				if (file.getName().trim().toLowerCase().contains(this.namePattern)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean accept(File file, String namePattern) {
		if (file.getName().trim().toLowerCase().endsWith(".css")) {
			if (namePattern==null) {
				return true;
			}
			else {
				if (file.getName().trim().toLowerCase().contains(namePattern.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String namePattern;
}
