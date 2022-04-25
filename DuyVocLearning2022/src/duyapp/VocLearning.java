package duyapp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.internal.win32.OS;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import duyapp.dtos.Word;
import duyapp.model.FileModel;

public class VocLearning {

	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	protected Shell shell;

	public Label lblWord;

	public Label lblMeaning;

	protected Button chkRemember;

	protected Button btnStop;
	protected MenuItem mntmWord;
	protected MenuItem mntmMeaning;
	protected MenuItem mntmRememberedWord;

	private Display display;

	private boolean runTimer = true;

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
		ShareData.fileModel = new FileModel();
		ShareData.fileModel.createConnection("data.csv");
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
		OS.SetWindowPos(this.shell.handle, OS.HWND_TOPMOST,this.shell.getLocation().x, this.shell.getLocation().y, this.shell.getSize().x, this.shell.getSize().y, 0);
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
		//this.lblDescription.setText((decription != null) ? decription : "");
		this.chkRemember.setSelection(remember);
	}

	public void setDataByIndex() {
		if (ShareData.wordList.size() > 0) {

			if (this.currentIndex == ShareData.wordList.size())
				this.currentIndex = 0;

			Word tmpWord = ShareData.wordList.get(this.currentIndex);
			if (!mntmRememberedWord.getSelection() && tmpWord.remember) {
				this.currentIndex++;
				nextTimer();
				return;
			}
			setData(tmpWord.word, tmpWord.meaning, tmpWord.description, tmpWord.remember);
			if (this.clickPrevious) {
				if (this.currentIndex == 0) {
					this.currentIndex = ShareData.wordList.size() - 1;
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
		this.cpWord.setVisible(this.mntmWord.getSelection());
		this.cpMeaning.setVisible(this.mntmMeaning.getSelection());
		//this.lblDescription.setVisible(this.mntmDescription.getSelection());
	}

	protected void createContents() {
		this.shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL);
		shell.setTouchEnabled(true);
		this.shell.addShellListener((ShellListener) new ShellAdapter() {
			public void shellActivated(ShellEvent e) {
				nextTimer();
			}
		});
		this.shell.setSize(622, 317);
		this.shell.setText("Duy's Vocabulary Learning");
		this.shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		this.shell.setImage(SWTResourceManager.getImage(VocLearning.class, "/duyapp/icon.png"));
		shell.setLayout(new FormLayout());

		Composite composite = new Composite(shell, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 205);
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.left = new FormAttachment(0, 10);
		composite.setLayoutData(fd_composite);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(1, false));
		this.cpWord = new Composite(composite, SWT.NONE);
		GridData gd_cpWord = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_cpWord.heightHint = 92;
		gd_cpWord.widthHint = 576;
		cpWord.setLayoutData(gd_cpWord);
		this.cpWord.setLayout((Layout) new FillLayout(256));
		this.formToolkit.adapt(this.cpWord);
		this.formToolkit.paintBordersFor(this.cpWord);
		this.lblWord = new Label(this.cpWord, SWT.SHADOW_NONE | SWT.CENTER);
		this.lblWord.setForeground(SWTResourceManager.getColor(3));
		this.lblWord.setBackground(SWTResourceManager.getColor(29));
		this.lblWord.setFont(SWTResourceManager.getFont("Times New Roman", 30, 0));
		this.lblWord.setAlignment(16777216);
		this.lblWord.setLayoutData( new GridData( SWT.CENTER, SWT.CENTER, true, true ) );
		this.lblWord.setText("Thank you!");
		this.cpMeaning = new Composite(composite, SWT.NONE);
		GridData gd_cpMeaning = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_cpMeaning.heightHint = 88;
		gd_cpMeaning.widthHint = 576;
		cpMeaning.setLayoutData(gd_cpMeaning);
		this.cpMeaning.setLayout((Layout) new FillLayout(256));
		this.formToolkit.adapt(this.cpMeaning);
		this.formToolkit.paintBordersFor(this.cpMeaning);
		this.lblMeaning = new Label(this.cpMeaning, 16777568);
		this.lblMeaning.setForeground(SWTResourceManager.getColor(14));
		this.lblMeaning.setText("Thank you!");
		this.lblMeaning.setFont(SWTResourceManager.getFont("Tahoma", 23, 0));
		this.lblMeaning.setAlignment(16777216);

		composite_2 = new Composite(shell, SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(100, -6);
		fd_composite_2.top = new FormAttachment(composite, 4);
		fd_composite_2.right = new FormAttachment(0, 596);
		fd_composite_2.left = new FormAttachment(0, 10);
		composite_2.setLayoutData(fd_composite_2);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		composite_2.setLayout(null);
		formToolkit.adapt(composite_2);
		formToolkit.paintBordersFor(composite_2);
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

		this.chkRemember = new Button(composite_2, SWT.CHECK);
		chkRemember.setBounds(10, 9, 106, 21);
		this.chkRemember.setForeground(SWTResourceManager.getColor(0, 128, 0));
		this.chkRemember.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				chkRememberClicked((Button) e.getSource());
			}
		});
		chkRemember.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		formToolkit.adapt(chkRemember, true, true);
		chkRemember.setText("Remember?");
		this.btnPrevious = new Button(composite_2, 0);
		btnPrevious.setBounds(294, 8, 30, 24);
		this.btnPrevious.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				btnPrevious_Clicked(e);
			}
		});
		this.btnPrevious.setFont(SWTResourceManager.getFont("Verdana", 9, 0));
		this.formToolkit.adapt((Control) this.btnPrevious, true, true);
		this.btnPrevious.setText("<<");
		btnStop = new Button(composite_2, 0);
		btnStop.setBounds(330, 8, 81, 25);
		btnStop.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				btnStop_Clicked(e);
			}
		});
		btnStop.setText("PAUSE");
		this.btnNext = new Button(composite_2, 0);
		btnNext.setBounds(417, 8, 30, 24);
		this.btnNext.addSelectionListener((SelectionListener) new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				btnNext_Clicked(e);
			}
		});
		this.btnNext.setFont(SWTResourceManager.getFont("Verdana", 9, 0));
		this.formToolkit.adapt((Control) this.btnNext, true, true);
		this.btnNext.setText(">>");

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmView = new MenuItem(menu, SWT.CASCADE);
		mntmView.setText("\uD83E\uDE9F View");

		Menu menu_1 = new Menu(mntmView);
		mntmView.setMenu(menu_1);

		mntmWord = new MenuItem(menu_1, SWT.CHECK);
		mntmWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				VocLearning.this.setVisibleItems();
			}
		});
		mntmWord.setSelection(true);
		mntmWord.setText("Word");

		mntmMeaning = new MenuItem(menu_1, SWT.CHECK);
		mntmMeaning.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				VocLearning.this.setVisibleItems();
			}
		});
		mntmMeaning.setSelection(true);
		mntmMeaning.setText("Meaning");

		mntmRememberedWord = new MenuItem(menu_1, SWT.CHECK);
		mntmRememberedWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ShareData.wordList = ShareData.fileModel.getList();
					currentIndex = 0;
					nextTimer();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mntmRememberedWord.setText("Remembered Word");

		mntmAdd = new MenuItem(menu, SWT.NONE);
		mntmAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EditDialog dlg = new EditDialog(shell, SWT.CLOSE);

				dlg.open();
			}
		});
		mntmAdd.setText("\u2795 Add");
		
		mntmRemove = new MenuItem(menu, SWT.NONE);
		mntmRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnRemoveClick(e);
			}
		});
		mntmRemove.setText("\u274C Remove");
		try {
			ShareData.wordList = ShareData.fileModel.getList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * When user click on Remember check box
	 * 
	 * @param e
	 */
	private void chkRememberClicked(Button e) {
		ShareData.wordList.get(currentIndex - 1).remember = e.getSelection();
		if (!isSaving) {
			isSaving = true;
			try {
				ShareData.fileModel.saveData(ShareData.wordList);
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
		// VocLearning.this.clickPrevious = true;
		if (currentIndex > 0) {
			currentIndex -= 2;
			if (currentIndex < 0)
				currentIndex = 0;
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
		ShareData.wordList.remove(currentIndex - 1);
		if (!isSaving) {
			isSaving = true;
			try {
				ShareData.fileModel.saveData(ShareData.wordList);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			isSaving = false;
		}
		nextTimer();
	}

	private boolean isSaving = false;
	private Composite composite_2;
	private MenuItem mntmAdd;
	private MenuItem mntmRemove;
	
}
