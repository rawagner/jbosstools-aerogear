/*******************************************************************************
 * Copyright (c) 2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.aerogear.hybrid.ios.core.xcode;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.jboss.tools.aerogear.hybrid.core.HybridCore;
import org.jboss.tools.aerogear.hybrid.core.engine.HybridMobileLibraryResolver;
import org.jboss.tools.aerogear.hybrid.core.internal.util.FileUtils;

public class IosLibraryResolver extends HybridMobileLibraryResolver {
	
	private HashMap<IPath, URL> files = new HashMap<IPath, URL>();
	
	private void initFiles() {
		IPath templatePrjRoot = libraryRoot.append("bin/templates/project");
		if("3.0.0".equals(version)){
			files.put(new Path("cordova"), getEngineFile(libraryRoot.append("bin/templates/project/cordova/")));
		}
		else{
			files.put(new Path("cordova"), getEngineFile(libraryRoot.append("bin/templates/scripts/cordova/")));
		}
		files.put(new Path(VAR_APP_NAME), getEngineFile(templatePrjRoot.append("__TESTING__")));
		files.put(new Path(VAR_APP_NAME+"/"+VAR_APP_NAME+"-Info.plist"), getEngineFile(templatePrjRoot.append("__TESTING__/__TESTING__-Info.plist")));
		files.put(new Path(VAR_APP_NAME+"/"+VAR_APP_NAME+"-Prefix.pch") , getEngineFile(templatePrjRoot.append("__TESTING__/__TESTING__-Prefix.pch")));
		files.put(new Path(VAR_APP_NAME+".xcodeproj/project.pbxproj"), getEngineFile(templatePrjRoot.append("__TESTING__.xcodeproj/project.pbxproj")));
		files.put(new Path(VAR_APP_NAME+"/Classes/AppDelegate.h"), getEngineFile(templatePrjRoot.append("__TESTING__/Classes/AppDelegate.h")));
		files.put(new Path(VAR_APP_NAME+"/Classes/AppDelegate.m"), getEngineFile(templatePrjRoot.append("__TESTING__/Classes/AppDelegate.m")));
		files.put(new Path(VAR_APP_NAME+"/Classes/MainViewController.h"), getEngineFile(templatePrjRoot.append("__TESTING__/Classes/MainViewController.h")));
		files.put(new Path(VAR_APP_NAME+"/Classes/MainViewController.m"), getEngineFile(templatePrjRoot.append("__TESTING__/Classes/MainViewController.m")));
		files.put(new Path(VAR_APP_NAME+"/main.m"), getEngineFile(templatePrjRoot.append("__TESTING__/main.m")));
		
		files.put(new Path("CordovaLib"), getEngineFile(libraryRoot.append("CordovaLib")));
	}

	@Override
	public URL getTemplateFile(IPath destination) {
		if(files.isEmpty()) initFiles();
		Assert.isNotNull(destination);
		Assert.isTrue(!destination.isAbsolute());
		return files.get(destination);
	}
	
	@Override
	public IStatus isLibraryConsistent() {
		if(files.isEmpty()) initFiles();
		Iterator<IPath> paths = files.keySet().iterator();
		while (paths.hasNext()) {
			IPath key = paths.next();
			URL url = files.get(key);
			if(url != null  ){
				File file = new File(url.getFile());
				if( file.exists()){
					continue;
				}
			}
			return new Status(IStatus.ERROR, HybridCore.PLUGIN_ID, NLS.bind("Library for iOS platform is not compatible with this tool. File for path {0} is missing.",key.toString()));
		}
		return Status.OK_STATUS;
	}
 
	private URL getEngineFile(IPath path){
		File file = path.toFile();
		if(!file.exists()){
			HybridCore.log(IStatus.ERROR, "missing iOS engine file " + file.toString(), null );
		}
		return FileUtils.toURL(file);
	}

	@Override
	public void preCompile(IProgressMonitor monitor) throws CoreException {
		
	}

	@Override
	public boolean needsPreCompilation() {
		return false;
	}
}
