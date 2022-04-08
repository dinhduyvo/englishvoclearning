package duyapp;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import duyapp.dtos.Word;
import duyapp.model.FileModel;
import duyapp.model.IDataModel;

public class VocLearning {

	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private IDataModel fileModel;
	protected Shell shell;

	public Label lblWord;

	public Label lblMeaning;

	public Label lblDescription;

	protected Button chkW;

	protected Button chkM;

	protected Button chkD;

	protected Button chkRemember;
	
	protected Button btnStop;

	private Display display;

	private boolean runTimer = true;

	protected List<Word> wordList;

	private int currentIndex = 0;

	private int timeOutNumber = 5000;

	final Font normalFont = SWTResourceManager.getFont("Times New Roman", 30, 0);

	final Font bigFont = SWTResourceManager.getFont("Times New Roman", 50, 0);

	private boolean clickPrevious = false;

	final Runnable timer = new Runnable() {
		public void run() {
			if (VocLearning.this.runTimer) {
				VocLearning.this.setDataByIndex();
				VocLearning.this.display.timerExec(VocLearning.this.timeOutNumber, VocLearning.this.timer);
			}
		}
	};

	private Composite cpWord;

	private Composite cpMeaning;

	private Label lblTimeout;

	private Button btnPrevious;

	private Button btnNext;
	
	public VocLearning() {
		fileModel = new FileModel();
		fileModel.createConnection("data.csv");
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			VocLearning window = new VocLearning();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		this.display = Display.getDefault();
		createContents();
		this.shell.open();
		this.shell.layout();
		while (!this.shell.isDisposed()) {
			if (!this.display.readAndDispatch())
				this.display.sleep();
		}
	}

	public void setData(String word, String meaning, String decription, boolean remember) {
		this.lblWord.setText((word != null) ? word : "");
		if (word.length() <= 6) {
			this.lblWord.setFont(this.bigFont);
		} else {
			this.lblWord.setFont(this.normalFont);
		}
		this.lblMeaning.setText((meaning != null) ? meaning : "");
		this.lblDescription.setText((decription != null) ? decription : "");
		this.chkRemember.setSelection(remember);
	}

	public void setDataByIndex() {
		if (this.wordList.size() > 0) {

			if (this.currentIndex == this.wordList.size())
				this.currentIndex = 0;
			
			Word tmpWord = this.wordList.get(this.currentIndex);
			if (!chkShowRemembered.getSelection() && tmpWord.remember) {
				this.currentIndex++;
				nextTimer();
				return;
			}
			setData(tmpWord.word, tmpWord.meaning, tmpWord.description, tmpWord.remember);
			if (this.clickPrevious) {
				if (this.currentIndex == 0) {
					this.currentIndex = this.wordList.size() - 1;
				} else {
					this.currentIndex--;
				}
			} else {
				this.currentIndex++;
			}
			this.clickPrevious = false;
		}
	}

	
	public void setVisibleItems() {
		this.cpWord.setVisible(this.chkW.getSelection());
		this.cpMeaning.setVisible(this.chkM.getSelection());
		this.lblDescription.setVisible(this.chkD.getSelection());
	}

	protected void createContents() {
		this.shell = new Shell(16464);
		this.shell.addShellListener((ShellListener) new ShellAdapter() {
			public void shellActivated(ShellEvent e) {
				nextTimer();
			}
		});
		this.shell.setSize(601, 344);
		this.shell.setText("Duy's Vocabulary Learning");
		this.shell.setBackground(SWTResourceManager.getColor(37));
		this.shell.setImage(new Image((Device) this.display, "icon.png"));
		shell.setLayout(new FormLayout());
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(0, 579);
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.left = new FormAttachment(0, 10);
		composite.setLayoutData(fd_composite);
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(1, false));
		this.cpWord = new Composite(composite, 2048);
		GridData gd_cpWord = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_cpWord.heightHint = 84;
		gd_cpWord.widthHint = 555;
		cpWord.setLayoutData(gd_cpWord);
		this.cpWord.setLayout((Layout) new FillLayout(256));
		this.formToolkit.adapt(this.cpWord);
		this.formToolkit.paintBordersFor(this.cpWord);
		this.lblWord = new Label(this.cpWord, 16777280);
		this.lblWord.setForeground(SWTResourceManager.getColor(3));
		this.lblWord.setBackground(SWTResourceManager.getColor(29));
		this.lblWord.setFont(SWTResourceManager.getFont("Times New Roman", 30, 0));
		this.lblWord.setAlignment(16777216);
		this.lblWord.setText("Thank you!");
		this.cpMeaning = new Composite(composite, 2048);
		GridData gd_cpMeaning = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_cpMeaning.heightHint = 76;
		gd_cpMeaning.widthHint = 555;
		cpMeaning.setLayoutData(gd_cpMeaning);
		this.cpMeaning.setLayout((Layout) new FillLayout(256));
		this.formToolkit.adapt(this.cpMeaning);
		this.formToolkit.paintBordersFor(this.cpMeaning);
		this.lblMeaning = new Label(this.cpMeaning, 16777568);
		this.lblMeaning.setForeground(SWTResourceManager.getColor(14));
		this.lblMeaning.setText("Thank you!");
		this.lblMeaning.setFont(SWTResourceManager.getFont("Tahoma", 23, 0));
		this.lblMeaning.setAlignment(16777216);
		this.lblDescription = new Label(composite, 0);
		GridData gd_lblDescription = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDescription.widthHint = 554;
		gd_lblDescription.heightHint = 24;
		lblDescription.setLayoutData(gd_lblDescription);
		this.lblDescription.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		this.lblDescription.setFont(SWTResourceManager.getFont("Verdana", 12, 1));
		this.lblDescription.setText("Description");
		
		composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayout(null);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(composite, 0, SWT.RIGHT);
		fd_composite_1.top = new FormAttachment(composite, 6);
		fd_composite_1.left = new FormAttachment(0, 10);
		composite_1.setLayoutData(fd_composite_1);
		formToolkit.adapt(composite_1);
		formToolkit.paintBordersFor(composite_1);
		
		this.chkRemember = new Button(composite_1, SWT.CHECK);
		chkRemember.setBounds(10, 2, 106, 21);
		this.chkRemember.setForeground(SWTResourceManager.getColor(0, 128, 0));
		this.chkRemember.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				chkRememberClicked((Button)e.getSource());
			}
		});
		chkRemember.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		formToolkit.adapt(chkRemember, true, true);
		chkRemember.setText("Remember?");
		this.btnPrevious = new Button(composite_1, 0);
		btnPrevious.setBounds(407, 2, 30, 24);
		this.btnPrevious.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				btnPrevious_Clicked(e);
			}
		});
		this.btnPrevious.setFont(SWTResourceManager.getFont("Verdana", 9, 0));
		this.formToolkit.adapt((Control) this.btnPrevious, true, true);
		this.btnPrevious.setText("<<");
		btnStop = new Button(composite_1, 0);
		btnStop.setBounds(442, 2, 81, 25);
		btnStop.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				btnStop_Clicked(e);
			}
		});
		btnStop.setText("PAUSE");
		this.btnNext = new Button(composite_1, 0);
		btnNext.setBounds(531, 2, 30, 24);
		this.btnNext.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				btnNext_Clicked(e);
			}
		});
		this.btnNext.setFont(SWTResourceManager.getFont("Verdana", 9, 0));
		this.formToolkit.adapt((Control) this.btnNext, true, true);
		this.btnNext.setText(">>");
		
		composite_2 = new Composite(shell, SWT.NONE);
		fd_composite_1.bottom = new FormAttachment(composite_2, -6);
		
		Button btnRemove = new Button(composite_1, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnRemoveClick(e);
			}
		});
		btnRemove.setBounds(111, 1, 75, 25);
		formToolkit.adapt(btnRemove, true, true);
		btnRemove.setText("\u274C Remove");
		composite_2.setLayout(null);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.right = new FormAttachment(composite, 0, SWT.RIGHT);
		fd_composite_2.top = new FormAttachment(0, 269);
		fd_composite_2.left = new FormAttachment(0, 10);
		composite_2.setLayoutData(fd_composite_2);
		formToolkit.adapt(composite_2);
		formToolkit.paintBordersFor(composite_2);
		this.chkW = new Button(composite_2, 32);
		chkW.setBounds(10, 10, 50, 16);
		this.chkW.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				VocLearning.this.setVisibleItems();
			}
		});
		this.chkW.setSelection(true);
		this.formToolkit.adapt((Control) this.chkW, true, true);
		this.chkW.setText("Word");
		this.chkM = new Button(composite_2, 32);
		chkM.setBounds(66, 10, 68, 16);
		this.chkM.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				VocLearning.this.setVisibleItems();
			}
		});
		this.chkM.setSelection(true);
		this.formToolkit.adapt((Control) this.chkM, true, true);
		this.chkM.setText("Meaning");
		this.chkD = new Button(composite_2, 32);
		chkD.setBounds(140, 10, 81, 16);
		this.chkD.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				VocLearning.this.setVisibleItems();
			}
		});
		this.chkD.setSelection(true);
		this.formToolkit.adapt((Control) this.chkD, true, true);
		this.chkD.setText("Description");
		
		chkShowRemembered = new Button(composite_2, SWT.CHECK);
		chkShowRemembered.setBounds(227, 10, 136, 16);
		chkShowRemembered.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					wordList = fileModel.getList();
					currentIndex = 0;
					nextTimer();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		chkShowRemembered.setText("Remembered Word");
		chkShowRemembered.setSelection(true);
		formToolkit.adapt(chkShowRemembered, true, true);
		this.lblTimeout = new Label(composite_2, 0);
		lblTimeout.setBounds(469, 13, 44, 15);
		this.formToolkit.adapt((Control) this.lblTimeout, true, true);
		this.lblTimeout.setText("Timeout");
		final Spinner spinner = new Spinner(composite_2, 2048);
		spinner.setBounds(519, 10, 41, 22);
		spinner.setMaximum(60);
		spinner.setSelection(2);
		spinner.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				VocLearning.this.timeOutNumber = spinner.getSelection() * 1000;
			}
		});
		this.formToolkit.adapt((Composite) spinner);
		this.formToolkit.paintBordersFor((Composite) spinner);
		spinner.setSelection(this.timeOutNumber / 1000);
		try {
			wordList = fileModel.getList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * When user click on Remember check box
	 * @param e
	 */
	private void chkRememberClicked(Button e) {
		wordList.get(currentIndex).remember = e.getSelection();
		if (!isSaving) {
			isSaving = true;
			try {
				fileModel.saveData(wordList);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			isSaving = false;
		}
		nextTimer();
	}
	
	private void btnStop_Clicked(SelectionEvent e) {
		VocLearning.this.runTimer = !VocLearning.this.runTimer;
		if (VocLearning.this.runTimer) {
			VocLearning.this.display.timerExec(VocLearning.this.timeOutNumber, VocLearning.this.timer);
			btnStop.setText("PAUSE");
		} else {
			btnStop.setText("START");
		}
	}
	
	private void btnPrevious_Clicked(SelectionEvent e) {
		//VocLearning.this.clickPrevious = true;
		if (currentIndex > 0) {
			currentIndex -= 2;
			if (currentIndex < 0) currentIndex = 0;
		}
		nextTimer();
	}
	
	private void btnNext_Clicked(SelectionEvent e) {	
		nextTimer();
	}
	
	private void nextTimer() {
		VocLearning.this.display.timerExec(0, VocLearning.this.timer);
	}
	
	private void btnRemoveClick(SelectionEvent e) {
		wordList.remove(currentIndex - 1);
		if (!isSaving) {
			isSaving = true;
			try {
				fileModel.saveData(wordList);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			isSaving = false;
		}
		nextTimer();
	}
	
	private boolean isSaving = false;
	private Button chkShowRemembered;
	private Composite composite_1;
	private Composite composite_2;
}
