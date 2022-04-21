package duyapp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import duyapp.dtos.Word;

public class EditDialog extends Dialog {

	protected Object result;
	protected Shell shlAddEdit;
	private Text txtWord;
	private Text txtMeaning;
	private Text txtDesc;
	private Label lblRemember;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EditDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAddEdit.open();
		shlAddEdit.layout();
		Display display = getParent().getDisplay();
		while (!shlAddEdit.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlAddEdit = new Shell(getParent(), getStyle());
		shlAddEdit.setSize(450, 300);
		shlAddEdit.setText("Add / Edit Word");
		shlAddEdit.setLayout(null);
		
		Label lblNewLabel = new Label(shlAddEdit, SWT.NONE);
		lblNewLabel.setBounds(21, 23, 32, 15);
		lblNewLabel.setText("Word:");
		
		txtWord = new Text(shlAddEdit, SWT.BORDER);
		txtWord.setBounds(107, 20, 297, 21);
		
		Label lblMeaning = new Label(shlAddEdit, SWT.NONE);
		lblMeaning.setBounds(21, 67, 50, 15);
		lblMeaning.setText("Meaning:");
		
		Label lblDescription = new Label(shlAddEdit, SWT.NONE);
		lblDescription.setBounds(21, 115, 63, 15);
		lblDescription.setText("Description:");
		
		txtMeaning = new Text(shlAddEdit, SWT.BORDER);
		txtMeaning.setBounds(107, 64, 297, 21);
		
		txtDesc = new Text(shlAddEdit, SWT.BORDER);
		txtDesc.setBounds(107, 112, 297, 21);
		
		lblRemember = new Label(shlAddEdit, SWT.NONE);
		lblRemember.setBounds(21, 164, 80, 15);
		lblRemember.setText("Remember?");
		
		Button chkRemember = new Button(shlAddEdit, SWT.CHECK);
		chkRemember.setBounds(107, 164, 93, 16);
		
		Button btnOk = new Button(shlAddEdit, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Word w = new Word();
				w.meaning = txtMeaning.getText();
				w.word = txtWord.getText();
				w.description = txtDesc.getText();
				w.remember = chkRemember.getSelection();
				ShareData.wordList.add(w);
				try {
					ShareData.fileModel.saveData(ShareData.wordList);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				shlAddEdit.close();
			}
		});
		btnOk.setBounds(107, 210, 75, 25);
		btnOk.setText("OK");
		
		Button btnCancel = new Button(shlAddEdit, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlAddEdit.close();
			}
		});
		btnCancel.setBounds(188, 210, 75, 25);
		btnCancel.setText("Cancel");

	}
}
