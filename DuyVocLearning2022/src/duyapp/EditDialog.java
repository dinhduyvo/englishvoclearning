package duyapp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import duyapp.dtos.Word;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditDialog extends Dialog {

	protected Object result;
	protected Shell shlAddEdit;
	private Text txtWord;
	private Text txtMeaning;

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
		Rectangle parentSize = getParent().getBounds();
		Rectangle shellSize = shlAddEdit.getBounds();
		int locationX = (parentSize.width - shellSize.width)/2+parentSize.x;
		int locationY = (parentSize.height - shellSize.height)/2+parentSize.y;
		shlAddEdit.getShell().setLocation(new Point(locationX, locationY));
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
		shlAddEdit = new Shell(getParent(), SWT.APPLICATION_MODAL);
		shlAddEdit.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shlAddEdit.setImage(SWTResourceManager.getImage(EditDialog.class, "/duyapp/icon.png"));
		shlAddEdit.setSize(450, 156);
		shlAddEdit.setText("Add / Edit Word");
		shlAddEdit.setLayout(null);
		
		Label lblNewLabel = new Label(shlAddEdit, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(55, 23, 32, 15);
		lblNewLabel.setText("Word:");
		
		txtWord = new Text(shlAddEdit, SWT.BORDER);
		txtWord.setBounds(124, 20, 297, 25);
		
		Label lblMeaning = new Label(shlAddEdit, SWT.NONE);
		lblMeaning.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMeaning.setBounds(55, 67, 50, 15);
		lblMeaning.setText("Meaning:");
		
		txtMeaning = new Text(shlAddEdit, SWT.BORDER);
		txtMeaning.setBounds(124, 64, 297, 25);
		
		Button chkRemember = new Button(shlAddEdit, SWT.CHECK);
		chkRemember.setText("Remembered");
		chkRemember.setBounds(124, 111, 93, 16);
		
		Button btnOk = new Button(shlAddEdit, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Word w = new Word();
				w.meaning = txtMeaning.getText();
				w.word = txtWord.getText();
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
		btnOk.setBounds(265, 107, 75, 25);
		btnOk.setText("\uD83D\uDCBE OK");
		
		Button btnCancel = new Button(shlAddEdit, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlAddEdit.close();
			}
		});
		btnCancel.setBounds(346, 107, 75, 25);
		btnCancel.setText("\u274C Cancel");
		
		Label label = new Label(shlAddEdit, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		label.setBounds(-2, 0, 32, 197);
		label.setText("  ");

	}
}
