package org.eclipse.ui.examples.readmetool;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.help.WorkbenchHelp;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import java.io.*;

/**
 * This class is the only page of the Readme file resource creation wizard.  
 * It subclasses the standard file resource creation page class, 
 * and consequently inherits the file resource creation functionality.
 *
 * This page provides users with the choice of creating sample headings for
 * sections and subsections.  Additionally, the option is presented to open
 * the file immediately for editing after creation.
 */
public class ReadmeCreationPage extends WizardNewFileCreationPage {
	private IWorkbench	workbench;
	
	// widgets
	private Button  sectionCheckbox;
	private Button  subsectionCheckbox;
	private Button  openFileCheckbox;

	// constants
	private static int nameCounter = 1;
/**
 * Creates the page for the readme creation wizard.
 *
 * @param workbench  the workbench on which the page should be created
 * @param selection  the current selection
 */
public ReadmeCreationPage(IWorkbench workbench, IStructuredSelection selection) {
	super("sampleCreateReadmePage1", selection); //$NON-NLS-1$
	this.setTitle(MessageUtil.getString("Create_Readme_File")); //$NON-NLS-1$
	this.setDescription(MessageUtil.getString("Create_a_new_Readme_file_resource")); //$NON-NLS-1$
	this.workbench = workbench;
}
/** (non-Javadoc)
 * Method declared on IDialogPage.
 */
public void createControl(Composite parent) {
	// inherit default container and name specification widgets
	super.createControl(parent);
	Composite composite = (Composite)getControl();
	
	WorkbenchHelp.setHelp(composite, IReadmeConstants.CREATION_WIZARD_PAGE_CONTEXT);
	
	GridData data = (GridData)composite.getLayoutData();
	this.setFileName("sample" + nameCounter + ".readme"); //$NON-NLS-1$ //$NON-NLS-2$

	new Label(composite,SWT.NONE);	// vertical spacer

	// sample section generation group
	Group group = new Group(composite,SWT.NONE);
	group.setLayout(new GridLayout());
	group.setText(MessageUtil.getString("Automatic_sample_section_generation")); //$NON-NLS-1$
	group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

	// sample section generation checkboxes
	sectionCheckbox = new Button(group,SWT.CHECK);
	sectionCheckbox.setText(MessageUtil.getString("Generate_sample_section_titles")); //$NON-NLS-1$
	sectionCheckbox.setSelection(true);
	sectionCheckbox.addListener(SWT.Selection,this);
	
	subsectionCheckbox = new Button(group,SWT.CHECK);
	subsectionCheckbox.setText(MessageUtil.getString("Generate_sample_subsection_titles")); //$NON-NLS-1$
	subsectionCheckbox.setSelection(true);
	subsectionCheckbox.addListener(SWT.Selection,this);

	new Label(composite,SWT.NONE);	// vertical spacer

	// open file for editing checkbox
	openFileCheckbox = new Button(composite,SWT.CHECK);
	openFileCheckbox.setText(MessageUtil.getString("Open_file_for_editing_when_done")); //$NON-NLS-1$
	openFileCheckbox.setSelection(true);

	setPageComplete(validatePage());
	
}
/**
 * Creates a new file resource as requested by the user. If everything
 * is OK then answer true. If not, false will cause the dialog
 * to stay open.
 *
 * @return whether creation was successful
 * @see ReadmeCreationWizard#performFinish()
 */
public boolean finish() {
	// create the new file resource
	IFile newFile = createNewFile();
	if (newFile == null)
		return false;	// ie.- creation was unsuccessful

	// Since the file resource was created fine, open it for editing
	// if requested by the user
	try {
	if (openFileCheckbox.getSelection()) {
		IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = dwindow.getActivePage();
		if (page != null)
			page.openEditor(newFile);
	}
	} catch (PartInitException e) {
		e.printStackTrace();
		return false;
	}
	nameCounter++;
	return true;
}
/** 
 * The <code>ReadmeCreationPage</code> implementation of this
 * <code>WizardNewFileCreationPage</code> method 
 * generates sample headings for sections and subsections in the
 * newly-created Readme file according to the selections of self's
 * checkbox widgets
 */
protected InputStream getInitialContents() {
	if (!sectionCheckbox.getSelection())
		return null;

	StringBuffer sb = new StringBuffer();
	sb.append(MessageUtil.getString("SAMPLE_README_FILE")); //$NON-NLS-1$
	sb.append(MessageUtil.getString("SECTION_1")); //$NON-NLS-1$
	sb.append(MessageUtil.getString("SECTION_1_BODY_1")); //$NON-NLS-1$
	
	if (subsectionCheckbox.getSelection()) {
		sb.append(MessageUtil.getString("Subsection_1_1")); //$NON-NLS-1$
		sb.append(MessageUtil.getString("Subsection_1_1_Body_1")); //$NON-NLS-1$
	}
	
	sb.append(MessageUtil.getString("SECTION_2")); //$NON-NLS-1$
	sb.append(MessageUtil.getString("SECTION_2_BODY_1")); //$NON-NLS-1$
	sb.append(MessageUtil.getString("SECTION_2_BODY_2")); //$NON-NLS-1$
	
	if (subsectionCheckbox.getSelection()) {
		sb.append(MessageUtil.getString("Subsection_2_1")); //$NON-NLS-1$
		sb.append(MessageUtil.getString("Subsection_2_1_BODY_1")); //$NON-NLS-1$
		sb.append(MessageUtil.getString("Subsection_2_2")); //$NON-NLS-1$
		sb.append(MessageUtil.getString("Subsection_2_2_BODY_1")); //$NON-NLS-1$
	}
	
	return new ByteArrayInputStream(sb.toString().getBytes());
}
/** (non-Javadoc)
 * Method declared on WizardNewFileCreationPage.
 */
protected String getNewFileLabel() {
	return MessageUtil.getString("Readme_file_name"); //$NON-NLS-1$
}
/** (non-Javadoc)
 * Method declared on WizardNewFileCreationPage.
 */
public void handleEvent(Event e) {
	Widget source = e.widget;

	if (source == sectionCheckbox) {
		if (!sectionCheckbox.getSelection())
			subsectionCheckbox.setSelection(false);
		subsectionCheckbox.setEnabled(sectionCheckbox.getSelection());
	}
	
	super.handleEvent(e);
}
}
