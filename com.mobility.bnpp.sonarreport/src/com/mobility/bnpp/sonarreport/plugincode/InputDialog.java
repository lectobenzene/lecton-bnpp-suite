package com.mobility.bnpp.sonarreport.plugincode;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.mobility.bnpp.sonarreport.corecode.modals.SonarResultsModal;

public class InputDialog extends Dialog {
	private Table table;
	private Text txtName;
	private Text txtKey;
	private TableViewer tableViewer;
	private boolean isOKPressed = false;

	private static SonarResultsModal selectedModal;

	private static ArrayList<SonarResultsModal> sonarClassList;

	public static ArrayList<SonarResultsModal> getSonarClassList() {
		return sonarClassList;
	}

	public static ArrayList<SonarResultsModal> getSonarPackageList() {
		return sonarPackageList;
	}

	private static ArrayList<SonarResultsModal> sonarPackageList;

	protected InputDialog(Shell parentShell) {
		super(parentShell);
		shell = parentShell;
	}

	private static ArrayList<SonarResultsModal> sonarEntireList;
	private Text txtSonarreportpath;
	private Text txtPrevsonarreportpath;
	private Shell shell;

	private String saveToPath;
	private String previousSonarFilePath;

	private Button btnEdit;
	private Button btnAddClass;
	private int indexOfEditedModal;
	private Button btnAddPackage;
	private Label lblKey;
	private Label lblName;
	private Composite compositeTable;
	private Composite composite;
	private Composite container;
	private Composite composite_2;
	private Composite composite_1;
	private Label lblSonarReportLocation;
	private Label lblPreviousSonarReport;
	private Button btnBrowse;
	private Button btnBrowse_1;
	private Button btnRemove;
	private Button btnClearAll;
	private DragSource dragSourceTable;
	private DropTarget dropTargetTable;

	public String getSaveToPath() {
		return saveToPath;
	}

	public void setSaveToPath(String saveToPath) {
		this.saveToPath = saveToPath;
	}

	public String getPreviousSonarFilePath() {
		return previousSonarFilePath;
	}

	public void setPreviousSonarFilePath(String previousSonarFilePath) {
		this.previousSonarFilePath = previousSonarFilePath;
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		sonarEntireList = new ArrayList<SonarResultsModal>();

		if (sonarClassList.isEmpty()) {
			sonarClassList = new ArrayList<SonarResultsModal>();
		} else {
			sonarEntireList.addAll(sonarClassList);
		}
		if (sonarPackageList.isEmpty()) {
			sonarPackageList = new ArrayList<SonarResultsModal>();
		} else {
			sonarEntireList.addAll(sonarPackageList);
		}

		container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());

		composite = new Composite(container, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.left = new FormAttachment(0, 10);
		fd_composite.bottom = new FormAttachment(0, 104);
		fd_composite.right = new FormAttachment(0, 611);
		composite.setLayoutData(fd_composite);

		compositeTable = new Composite(container, SWT.NONE);
		TableColumnLayout tcl_compositeTable = new TableColumnLayout();
		compositeTable.setLayout(tcl_compositeTable);
		FormData fd_compositeTable = new FormData();
		fd_compositeTable.bottom = new FormAttachment(composite, 165, SWT.BOTTOM);
		fd_compositeTable.right = new FormAttachment(0, 477);
		fd_compositeTable.top = new FormAttachment(composite, 6);
		fd_compositeTable.left = new FormAttachment(0, 10);
		compositeTable.setLayoutData(fd_compositeTable);

		tableViewer = new TableViewer(compositeTable, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);

		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableViewerColumn tableViewerColumnName = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumnName = tableViewerColumnName.getColumn();
		tcl_compositeTable.setColumnData(tableColumnName, new ColumnPixelData(222, true, true));
		tableColumnName.setText("Name");

		TableViewerColumn tableViewerColumnKey = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumnKey = tableViewerColumnKey.getColumn();
		tcl_compositeTable.setColumnData(tableColumnKey, new ColumnPixelData(231, true, true));
		tableColumnKey.setText("Key");

		tableViewerColumnName.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				SonarResultsModal modal = (SonarResultsModal) element;
				return modal.getName();
			}
		});

		tableViewerColumnKey.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				SonarResultsModal modal = (SonarResultsModal) element;
				return modal.getKey();
			}
		});

		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(sonarEntireList);
		// tableViewer.setInput(getEntireData());

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				System.out.println("selection changed");
				ISelection selection = event.getSelection();
				IStructuredSelection sel = (IStructuredSelection) selection;
				Object obj = sel.getFirstElement();
				if (obj instanceof SonarResultsModal) {
					selectedModal = (SonarResultsModal) obj;
				}
			}
		});

		dragSourceTable = new DragSource(table, DND.DROP_MOVE);
		Transfer[] transferAgentsTable = new Transfer[] { TextTransfer.getInstance() };
		dragSourceTable.setTransfer(transferAgentsTable);

		dragSourceTable.addDragListener(new DragSourceListener() {

			@Override
			public void dragStart(DragSourceEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				// TODO Auto-generated method stub
				DragSource source = (DragSource) event.widget;
				Table table = (Table) source.getControl();
				TableItem[] selection = table.getSelection();

				StringBuilder strBdrSource = new StringBuilder();
				for (TableItem item : selection) {
					strBdrSource.append(item.getText());
				}

				event.data = strBdrSource.toString();
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				// TODO Auto-generated method stub

			}
		});

		dropTargetTable = new DropTarget(table, DND.DROP_MOVE);
		dropTargetTable.setTransfer(transferAgentsTable);
		dropTargetTable.addDropListener(new DropTargetListener() {

			@Override
			public void dropAccept(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void drop(DropTargetEvent event) {
				// TODO Auto-generated method stub

				TableItem item = (TableItem) event.item;
				System.out.println(item.getText());
				System.out.println(item.getText(1));
				reOrderSonarModal(item.getText());
			}

			@Override
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_INSERT_BEFORE | DND.FEEDBACK_SCROLL;

			}

			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragLeave(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragEnter(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}
		});

		composite_2 = new Composite(container, SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.right = new FormAttachment(100, -10);
		fd_composite_2.left = new FormAttachment(compositeTable, 6);
		fd_composite_2.bottom = new FormAttachment(100, -93);
		fd_composite_2.top = new FormAttachment(composite, 6);

		lblName = new Label(composite, SWT.NONE);
		lblName.setBounds(10, 8, 56, 33);
		lblName.setText("Name");

		lblKey = new Label(composite, SWT.NONE);
		lblKey.setBounds(10, 49, 56, 35);
		lblKey.setText("Key");

		txtName = new Text(composite, SWT.BORDER);
		txtName.setBounds(72, 8, 401, 33);

		txtKey = new Text(composite, SWT.BORDER);
		txtKey.setBounds(72, 49, 401, 35);

		btnAddClass = new Button(composite, SWT.NONE);
		btnAddClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isStringNotEmpty(txtName.getText()) && isStringNotEmpty(txtKey.getText())) {
					if (btnAddClass.getText().equalsIgnoreCase("Modify Class")) {
						sonarClassList.get(indexOfEditedModal).setName(txtName.getText());
						sonarClassList.get(indexOfEditedModal).setKey(txtKey.getText());
					} else {
						setClassData(txtName.getText(), txtKey.getText());
					}

					refreshViewer(tableViewer);
				}
			}

		});
		btnAddClass.setBounds(479, 8, 112, 33);
		btnAddClass.setText("Add Class");

		btnAddPackage = new Button(composite, SWT.NONE);
		btnAddPackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isStringNotEmpty(txtName.getText()) && isStringNotEmpty(txtKey.getText())) {
					if (btnAddPackage.getText().equalsIgnoreCase("Modify Package")) {
						sonarPackageList.get(indexOfEditedModal).setName(txtName.getText());
						sonarPackageList.get(indexOfEditedModal).setKey(txtKey.getText());
					} else {
						setPackageData(txtName.getText(), txtKey.getText());
					}
					refreshViewer(tableViewer);
				}
			}
		});
		btnAddPackage.setBounds(479, 49, 112, 35);
		btnAddPackage.setText("Add Package");
		composite_2.setLayoutData(fd_composite_2);

		btnRemove = new Button(composite_2, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sonarClassList.remove(selectedModal);
				sonarPackageList.remove(selectedModal);
				refreshViewer(tableViewer);
			}
		});
		btnRemove.setBounds(10, 56, 108, 38);
		btnRemove.setText("Remove");

		btnClearAll = new Button(composite_2, SWT.NONE);
		btnClearAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sonarClassList.clear();
				sonarPackageList.clear();
				refreshViewer(tableViewer);
			}
		});
		btnClearAll.setBounds(10, 103, 108, 38);
		btnClearAll.setText("Clear All");

		btnEdit = new Button(composite_2, SWT.NONE);
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (sonarClassList.contains(selectedModal)) {
					indexOfEditedModal = sonarClassList.indexOf(selectedModal);
					editSonarList(sonarClassList);
				} else if (sonarPackageList.contains(selectedModal)) {
					indexOfEditedModal = sonarPackageList.indexOf(selectedModal);
					editSonarList(sonarPackageList);
				}
			}
		});
		btnEdit.setBounds(10, 9, 108, 38);
		btnEdit.setText("Edit");

		composite_1 = new Composite(container, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(compositeTable, 83, SWT.BOTTOM);
		fd_composite_1.top = new FormAttachment(compositeTable, 6);
		fd_composite_1.right = new FormAttachment(composite, 0, SWT.RIGHT);
		fd_composite_1.left = new FormAttachment(0, 10);
		composite_1.setLayoutData(fd_composite_1);

		lblSonarReportLocation = new Label(composite_1, SWT.NONE);
		lblSonarReportLocation.setBounds(10, 8, 177, 25);
		lblSonarReportLocation.setText("Sonar Report Location");

		lblPreviousSonarReport = new Label(composite_1, SWT.NONE);
		lblPreviousSonarReport.setBounds(10, 41, 177, 26);
		lblPreviousSonarReport.setText("Previous Sonar Report Location");

		txtSonarreportpath = new Text(composite_1, SWT.BORDER);
		txtSonarreportpath.setBounds(193, 8, 278, 25);
		txtSonarreportpath.setText(SonarReportGeneration.SAVE_TO + File.separator
				+ SonarReportGeneration.convertStringWithDate(SonarReportGeneration.FILE_NAME));

		txtPrevsonarreportpath = new Text(composite_1, SWT.BORDER);
		txtPrevsonarreportpath.setBounds(193, 41, 278, 26);

		DropTarget dropTarget = new DropTarget(txtPrevsonarreportpath, DND.DROP_MOVE);
		Transfer[] transferAgents = new Transfer[] { FileTransfer.getInstance() };
		dropTarget.setTransfer(transferAgents);
		dropTarget.addDropListener(new DropTargetListener() {

			@Override
			public void dropAccept(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void drop(DropTargetEvent event) {
				if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
					String[] files = (String[]) event.data;
					txtPrevsonarreportpath.setText(files[0]);
				}
			}

			@Override
			public void dragOver(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragLeave(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragEnter(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}
		});

		btnBrowse = new Button(composite_1, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog fileDialogDestination = new DirectoryDialog(shell);
				fileDialogDestination.open();

				String filePath = fileDialogDestination.getFilterPath();
				if (filePath == null || "".equalsIgnoreCase(filePath)) {
					filePath = SonarReportGeneration.SAVE_TO;
				}
				txtSonarreportpath.setText(filePath + File.separator + SonarReportGeneration.convertStringWithDate(SonarReportGeneration.FILE_NAME));
			}
		});
		btnBrowse.setBounds(477, 8, 114, 25);
		btnBrowse.setText("Browse");

		btnBrowse_1 = new Button(composite_1, SWT.NONE);
		btnBrowse_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialogDestination = new FileDialog(shell);
				fileDialogDestination.open();

				String filePath = fileDialogDestination.getFilterPath() + File.separator + fileDialogDestination.getFileName();
				txtPrevsonarreportpath.setText(filePath);
			}
		});
		btnBrowse_1.setBounds(477, 41, 114, 26);
		btnBrowse_1.setText("Browse");

		return container;
	}

	protected void reOrderSonarModal(String name) {
		// Index of the item in the List corresponding to the name "name"
		int index = -1;

		for (SonarResultsModal modal : sonarClassList) {
			if (name.equalsIgnoreCase(modal.getName())) {
				if (sonarClassList.contains(selectedModal)) {
					sonarClassList.remove(selectedModal);
					index = sonarClassList.indexOf(modal);
					sonarClassList.add(index, selectedModal);
				}
				System.out.println("Found in Class List");
				break;
			}
		}

		for (SonarResultsModal modal : sonarPackageList) {
			if (name.equalsIgnoreCase(modal.getName())) {
				if (sonarPackageList.contains(selectedModal)) {
					sonarPackageList.remove(selectedModal);
					index = sonarPackageList.indexOf(modal);
					sonarPackageList.add(index, selectedModal);
				}
				System.out.println("Found in Package List");
				break;
			}
		}
		refreshViewer(tableViewer);
	}

	protected void editSonarList(ArrayList<SonarResultsModal> sonarList) {
		SonarResultsModal modal = null;
		if (sonarList.equals(sonarClassList)) {
			btnAddClass.setText("Modify Class");
			btnAddPackage.setEnabled(false);
			modal = sonarList.get(indexOfEditedModal);
			txtName.setText(modal.getName());
			txtKey.setText(modal.getKey());
		} else if (sonarList.equals(sonarPackageList)) {
			btnAddPackage.setText("Modify Package");
			btnAddClass.setEnabled(false);
			modal = sonarList.get(indexOfEditedModal);
			txtName.setText(modal.getName());
			txtKey.setText(modal.getKey());
		}
	}

	public static void setSonarClassList(ArrayList<SonarResultsModal> sonarClassList) {
		InputDialog.sonarClassList = sonarClassList;
	}

	@Override
	protected void okPressed() {
		isOKPressed = true;
		saveToPath = txtSonarreportpath.getText();
		previousSonarFilePath = txtPrevsonarreportpath.getText();
		super.okPressed();
	}

	public static void setSonarPackageList(ArrayList<SonarResultsModal> sonarPackageList) {
		InputDialog.sonarPackageList = sonarPackageList;
	}

	private ArrayList<SonarResultsModal> getClassData() {
		return sonarClassList;
	}

	private void setClassData(String name, String key) {
		sonarClassList.add(new SonarResultsModal(name, key));
	}

	private ArrayList<SonarResultsModal> getPackageData() {
		return sonarPackageList;
	}

	private void setPackageData(String name, String key) {
		sonarPackageList.add(new SonarResultsModal(name, key));
	}

	private void getTotalData() {
		sonarEntireList.clear();
		sonarEntireList.addAll(getClassData());
		sonarEntireList.addAll(getPackageData());
	}

	private boolean isStringNotEmpty(String text) {
		if (text != null && !"".equalsIgnoreCase(text)) {
			return true;
		} else {
			return false;
		}
	}

	private void refreshViewer(TableViewer tableViewer) {
		getTotalData();
		System.out.println(sonarEntireList.size());
		btnAddClass.setEnabled(true);
		btnAddPackage.setEnabled(true);
		btnAddClass.setText("Add Class");
		btnAddPackage.setText("Add Package");
		txtName.setText("");
		txtKey.setText("");
		tableViewer.refresh();
	}

	public boolean isOKPressed() {
		return isOKPressed;
	}

}
