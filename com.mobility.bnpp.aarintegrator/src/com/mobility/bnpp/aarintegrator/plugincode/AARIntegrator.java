package com.mobility.bnpp.aarintegrator.plugincode;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import corecode.CentralController;

public class AARIntegrator extends AbstractHandler {

	IProject project;
	private String destinationPath;
	private String sourcePath;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		//debug
		System.out.println("Executing...");

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		destinationPath = getPath();

		EntryDialog dialog = new EntryDialog(shell);
		dialog.open();

		sourcePath = dialog.getPathToFile();

		System.out.println("SourceFilePath : " + sourcePath);
		System.out.println("DestinationPath : " + destinationPath);

		Job job = new Job("Resource Changer") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				CentralController controller = new CentralController(sourcePath, destinationPath);
				controller.updateProject();

				try {
					project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
				} catch (CoreException e) {
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.SHORT);
		job.schedule();

		return null;
	}

	private String getPath() {
		IPath path = null;
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if (selection instanceof IStructuredSelection) {
			Object[] selectedObjects = ((IStructuredSelection) selection).toArray();
			for (Object obj : selectedObjects) {
				System.out.println("Object Selected - " + obj.getClass());
				if (obj instanceof IProject) {
					path = ((IProject) obj).getLocation();
				} else if (obj instanceof IJavaProject) {
					path = ((IJavaProject) obj).getProject().getLocation();
					project = ((IJavaProject) obj).getProject();
				}
			}
		}
		return (path != null) ? path.toOSString() : null;
	}

}
