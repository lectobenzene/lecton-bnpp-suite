package com.mobility.bnpp.resourcechanger.plugincode;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.mobility.bnpp.resourcechanger.corecode.ResourceSwapper;


public class ResourceChanger extends AbstractHandler implements IHandler {

	IPath path = null;
	IProject project = null;
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if(selection instanceof IStructuredSelection){
			Object[] selectedObjects = ((IStructuredSelection) selection).toArray();
			for(Object obj : selectedObjects){
				if(obj instanceof IJavaProject){
					project = ((IJavaProject)obj).getProject();
					path = project.getLocation();
				}
			}
		}
		
		Job job = new Job("Resource Changer"){

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				ResourceSwapper swapper = new ResourceSwapper(path.toOSString());
				swapper.runSwapper();
				
				try {
					project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
				} catch (CoreException e) {
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.LONG);
		job.schedule();
		
		return null;

	}
}
