/*******************************************************************************
 * Copyright (c) 2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *       Red Hat, Inc. - initial API and implementation
 *******************************************************************************/
package org.jboss.tools.aerogear.hybrid.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugOptionsListener;
import org.eclipse.osgi.service.debug.DebugTrace;
import org.jboss.tools.aerogear.hybrid.core.natures.HybridAppNature;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HybridCore implements BundleActivator, DebugOptionsListener {

	/**
	 * Plugin ID
	 */	
	public static final String PLUGIN_ID = "org.jboss.tools.earogear.hybrid.core";
	
	private static BundleContext context;
	public static boolean DEBUG=false;
	private static DebugTrace TRACE;
	
	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		HybridCore.context = bundleContext;
		Hashtable<String,Object> props = new Hashtable<String, Object>();
		props.put(org.eclipse.osgi.service.debug.DebugOptions.LISTENER_SYMBOLICNAME, PLUGIN_ID);
		context.registerService(DebugOptionsListener.class.getName(), this, props);

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		HybridCore.context = null;
	}
	
	public static List<IProject> getHybridProjects(){
		ArrayList<IProject> hybrids = new ArrayList<IProject>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			try {
				if(project.hasNature(HybridAppNature.NATURE_ID)){
					hybrids.add(project);
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hybrids;
	}

	
	
	
	@Override
	public void optionsChanged(DebugOptions options) {
		if(TRACE==null)
			TRACE = options.newDebugTrace(PLUGIN_ID);
		DEBUG = options.getBooleanOption(PLUGIN_ID+"/debug", false);	
	}
	
	public static void trace( String message){
		System.out.println(message);//TODO: Remove this eventually?
		if( !DEBUG ) return;
		TRACE.trace(null, message);
	
	}
	
	

}
