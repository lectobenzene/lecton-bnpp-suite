package com.mobility.bnpp.aarintegrator.plugincode;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EntryDialog extends Dialog {
	private Label lblAarFile;
	private Text txtPath;
	private Button btnBrowse;
	private Shell shell;

	private String pathToFile;

	public String getPathToFile() {
		return pathToFile;
	}

	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new FormLayout());

		lblAarFile = new Label(composite, SWT.NONE);
		FormData fd_lblAarFile = new FormData();
		fd_lblAarFile.left = new FormAttachment(0, 10);
		fd_lblAarFile.top = new FormAttachment(0, 10);
		lblAarFile.setLayoutData(fd_lblAarFile);
		lblAarFile.setText("AAR File");

		txtPath = new Text(composite, SWT.BORDER);
		fd_lblAarFile.right = new FormAttachment(txtPath, -6);
		FormData fd_txtPath = new FormData();
		fd_txtPath.left = new FormAttachment(0, 64);
		fd_txtPath.top = new FormAttachment(0, 10);
		txtPath.setLayoutData(fd_txtPath);

		btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialogDestination = new FileDialog(shell);
				fileDialogDestination.open();

				String filePath = fileDialogDestination.getFilterPath() + File.separator + fileDialogDestination.getFileName();
				txtPath.setText(filePath);
			}
		});
		fd_txtPath.right = new FormAttachment(100, -86);
		FormData fd_btnBrowse = new FormData();
		fd_btnBrowse.left = new FormAttachment(txtPath, 6);
		fd_btnBrowse.top = new FormAttachment(0, 6);
		fd_btnBrowse.right = new FormAttachment(100, -10);
		btnBrowse.setLayoutData(fd_btnBrowse);
		btnBrowse.setText("Browse");

		return composite;
	}

	@Override
	protected void okPressed() {
		pathToFile = txtPath.getText();
		super.okPressed();
	}

	protected EntryDialog(Shell parentShell) {
		super(parentShell);
		this.shell = parentShell;
	}
}
