package plugincode;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.mobility.bnpp.showfolder.Activator;

/**
 * The Central control of the plugin.
 * 
 * @author Saravana
 *
 */
public class ShowInSystem extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Set OS type, if not set
		if (Activator.OS_TYPE == null) {
			Activator.getOSType();
		}

		String location = getPath();

		try {
			if (location != null) {
				if (Activator.OS_TYPE.equalsIgnoreCase("WIN")) {
					Runtime.getRuntime().exec("explorer /select, " + location);
				} else if (Activator.OS_TYPE.equalsIgnoreCase("MAC")) {
					Runtime.getRuntime().exec("open -R " + location);
				} else {
					// TODO Not handled for simplicity. Might be handled in future
				}
			} else {
				// TODO Not handled for simplicity. Might be handled in future
			}
		} catch (IOException e) {
			// TODO Not handled for simplicity. Might be handled in future
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Used to get the path of the current selection
	 * 
	 * @return The path of the selected object
	 */
	private String getPath() {
		IPath path = null;
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if (selection instanceof IStructuredSelection) {
			Object[] selectedObjects = ((IStructuredSelection) selection).toArray();
			for (Object obj : selectedObjects) {
				if (obj instanceof IJavaElement) {
					IResource res = ((IJavaElement) obj).getResource();
					if (res == null) {
						path = ((IJavaElement) obj).getPath();
					} else {
						path = ((IJavaElement) obj).getResource().getLocation();
					}
				} else if (obj instanceof IResource) {
					path = ((IResource) obj).getLocation();
				}
			}
		}
		return (path != null) ? path.toOSString() : null;
	}

}
