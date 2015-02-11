import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.mobility.bnpp.integratemultilang.ExcelParser;

import static com.mobility.bnpp.integratemultilang.ExcelParser.*;

import com.mobility.bnpp.integratemultilang.ExcelParser.Language;
import com.mobility.bnpp.integratemultilang.InputDialog;


public class IntegrateMultiLang extends AbstractHandler {

	
	private static String easyBankingSource;
	private static String helloBankingSource;
	private static String projectSource;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow window =  HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IPath path = null;
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		System.out.println("is selection empty ? : "+selection.isEmpty());
		if(selection instanceof IStructuredSelection){
			System.out.println("It is an instance");
			Object[] selectedObjects = ((IStructuredSelection) selection).toArray();
			System.out.println("array size is "+selectedObjects.length);
			for(Object obj : selectedObjects){
				System.out.println("Object[] exists");
				if(obj instanceof IJavaProject){
					System.out.println("Object is instance of IPROJEct");
					path = ((IJavaProject)obj).getProject().getLocation();
				}
			}
		}
		//IWorkspace workspace = ResourcesPlugin.getWorkspace();
		//IProject project = workspace.getRoot().getProject();
		//IPath path = project.getLocation();
		
		InputDialog myDialog = new InputDialog(window.getShell());
		myDialog.open();
		
		if(myDialog.isOkPressed()){
			easyBankingSource = myDialog.getEasyBankingSource();
			helloBankingSource = myDialog.getHelloBankingSource();
			projectSource = path.toString();
			
			Job job = new Job("Multi-Lang Integration"){
				
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					ExcelParser excel = new ExcelParser(projectSource, easyBankingSource, helloBankingSource);
					excel.integrateMultiLang();
					excel.copyNormalStringsToCarStrings();
					excel.changeCarStrings();
					System.out.println("ALL DONE - BACK TO THE PLUGIN CLASS");
					
					return Status.OK_STATUS;
				}
			};
			//job.setUser(true);
			job.setPriority(Job.LONG);
			job.schedule();
		}
		
		
		return null;
	}
}
