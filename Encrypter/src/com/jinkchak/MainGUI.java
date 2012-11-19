package com.jinkchak;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class MainGUI {
	private Shell shell;
	private Browser browser;
	
	private Text EncryptionTextBox;
	private Text DecryptionTextBox;
	private Button EncryptButton;
	private Button EncDecryptCheck;
	private Label msgLabel;
	private Label cipherLabel;
	private Label AlgoDetailsLabel;
	
	private Button changeButton;
	private Text pTextBox;
	private Text qTextBox;
	
	private Schmidt_Samoa_Encryptor encryptor;
	
	public MainGUI(Display display)
	{
		encryptor = new Schmidt_Samoa_Encryptor();
		
		shell = new Shell(display);
		initUI();
		// shell.pack();
        shell.setLocation(300, 300);
        shell.setSize(1024, 720);
        shell.setText("Schmidt-Samoa Cryptosystem Demo");
        shell.setBackgroundImage(new Image(display, "white.png"));
        initListeners();
        shell.open();

        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
	}
	private void initListeners() {
		EncryptButton.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) 
			{
				if(EncryptButton.getText().equals("Encrypt"))
				{
					String cipher = encryptor.encrypt(EncryptionTextBox.getText());								
					DecryptionTextBox.setText(cipher);
				}
				else
				{
					String plain = encryptor.decrypt(EncryptionTextBox.getText());
					DecryptionTextBox.setText(plain);
				}
			}
		});
		
		AlgoDetailsLabel.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				AlgoDetailsLabel.setText(encryptor.display());
				changeButton.setVisible(true);
				//pTextBox.setVisible(true);qTextBox.setVisible(true);
				
			}
		});
		changeButton.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(pTextBox.isVisible())
				{
					encryptor.reInitialize(Integer.parseInt(pTextBox.getText()),
							Integer.parseInt(qTextBox.getText()));
					AlgoDetailsLabel.setText(encryptor.display());
					pTextBox.setVisible(false);qTextBox.setVisible(false);
					changeButton.setVisible(false);
				}
				else
				{
					pTextBox.setVisible(true);qTextBox.setVisible(true);
					pTextBox.setText("p:");
					qTextBox.setText("q:");
				}				
			}
		});
		EncDecryptCheck.addSelectionListener(new SelectionAdapter() {
			
            @Override
            public void widgetSelected(SelectionEvent e) 
            {
                if (EncDecryptCheck.getSelection()) 
                {
                	msgLabel.setText("Cipher: ");
					cipherLabel.setText("Message: ");
					EncryptButton.setText("Decrypt");
                } 
                else 
                {
                	msgLabel.setText("Message: ");
					cipherLabel.setText("Cipher: ");
					EncryptButton.setText("Encrypt");
                }
            }
        });		
	}
	
	public void initUI() 
	{
        FormLayout layout = new FormLayout();
        shell.setLayout(layout);
        Label welcomeLabel = new Label(shell,SWT.LEFT);
        welcomeLabel.setFont(new Font(shell.getDisplay(),"Jokerman",20,SWT.ITALIC));
        FormData welcomeLabelData = new FormData(20,20);
        welcomeLabelData.left = new FormAttachment(20);
        welcomeLabelData.right = new FormAttachment(80);
        welcomeLabelData.top = new FormAttachment(5);
        welcomeLabelData.bottom = new FormAttachment(10);
        welcomeLabel.setText("Schmidt-Samoa Cryptosystem");
        welcomeLabel.setLayoutData(welcomeLabelData);
        
        AlgoDetailsLabel = new Label(shell,SWT.LEFT);
        FormData linkLabelData = new FormData(20,40);
        linkLabelData.left = new FormAttachment(80);
        linkLabelData.right = new FormAttachment(95);
        linkLabelData.top = new FormAttachment(20);
        linkLabelData.bottom = new FormAttachment(30);
        AlgoDetailsLabel.setLayoutData(linkLabelData);
        AlgoDetailsLabel.setText("View Algorithm Details");
        
        changeButton = new Button(shell, SWT.PUSH); changeButton.setText("Change");
        changeButton.setVisible(false);
        FormData changeButtonData = new FormData(80, 30);
        changeButtonData.left = new FormAttachment(80);
        changeButtonData.right = new FormAttachment(85);
        changeButtonData.top = new FormAttachment(30);
        changeButtonData.bottom = new FormAttachment(34);
        changeButton.setLayoutData(changeButtonData);
        
        pTextBox = new Text(shell, SWT.SINGLE  | SWT.BORDER);
        FormData TextBoxData = new FormData(20, 50);
        TextBoxData.left = new FormAttachment(80);
        TextBoxData.right = new FormAttachment(85);
        TextBoxData.top = new FormAttachment(34);
        TextBoxData.bottom = new FormAttachment(36);
        pTextBox.setLayoutData(TextBoxData);
        pTextBox.setVisible(false);

        qTextBox = new Text(shell, SWT.SINGLE  | SWT.BORDER);
        TextBoxData = new FormData(20,50);
        TextBoxData.left = new FormAttachment(80);
        TextBoxData.right = new FormAttachment(85);
        TextBoxData.top = new FormAttachment(36);
        TextBoxData.bottom = new FormAttachment(38);
        qTextBox.setLayoutData(TextBoxData);
        qTextBox.setVisible(false);

        EncryptionTextBox = new Text(shell, SWT.MULTI | SWT.BORDER);
        FormData EncTextBoxData = new FormData(100, 100);
        EncTextBoxData.left = new FormAttachment(25);
        EncTextBoxData.right = new FormAttachment(75);
        EncTextBoxData.top = new FormAttachment(20);
        EncTextBoxData.bottom = new FormAttachment(40);
        EncryptionTextBox.setLayoutData(EncTextBoxData);
        
		DecryptionTextBox = new Text(shell, SWT.MULTI | SWT.BORDER);
		 FormData DecTextBoxData = new FormData(100, 100);
		 DecTextBoxData.left = new FormAttachment(25);
		 DecTextBoxData.right = new FormAttachment(75);
		 DecTextBoxData.top = new FormAttachment(50);
		 DecTextBoxData.bottom = new FormAttachment(70);
	     DecryptionTextBox.setLayoutData(DecTextBoxData);
        
		EncDecryptCheck = new Button(shell, SWT.CHECK);
        EncryptButton = new Button(shell, SWT.PUSH);
        EncryptButton.setText("Encrypt");
        FormData encryptButtonData = new FormData(80, 30);
        encryptButtonData.left = new FormAttachment(25);
        encryptButtonData.right = new FormAttachment(30);
        encryptButtonData.top = new FormAttachment(41);
        encryptButtonData.bottom = new FormAttachment(45);
        EncryptButton.setLayoutData(encryptButtonData);
        
      
        FormData checkButtonData = new FormData();
        checkButtonData.left = new FormAttachment(35);
        checkButtonData.right = new FormAttachment(40);
        checkButtonData.top = new FormAttachment(41);
        checkButtonData.bottom = new FormAttachment(44);
        EncDecryptCheck.setLayoutData(checkButtonData);
        EncDecryptCheck.setText("Decrypt");
        
        msgLabel = new Label(shell,SWT.LEFT);
        FormData msgLabelData = new FormData(20,20);
        msgLabelData.left = new FormAttachment(25);
        msgLabelData.right = new FormAttachment(30);
        msgLabelData.top = new FormAttachment(18);
        msgLabelData.bottom = new FormAttachment(20);
        msgLabel.setText("Message: ");
        msgLabel.setLayoutData(msgLabelData);
        
        cipherLabel = new Label(shell,SWT.LEFT);
        FormData cipherLabelData = new FormData(30,30);
        cipherLabelData.left = new FormAttachment(25);
        cipherLabelData.right = new FormAttachment(30);
        cipherLabelData.top = new FormAttachment(48);
        cipherLabelData.bottom = new FormAttachment(50);
        cipherLabel.setText("Cipher: ");
        cipherLabel.setLayoutData(cipherLabelData);
        
        Label copyLabel = new Label(shell,SWT.BOLD|SWT.WRAP);
        FormData cpyrightData = new FormData(180,100);
        cpyrightData.left = new FormAttachment(25);
        cpyrightData.right = new FormAttachment(75);
        cpyrightData.top = new FormAttachment(90);
        cpyrightData.bottom = new FormAttachment(95);
        copyLabel.setLayoutData(cpyrightData);
        copyLabel.setText("© 2012\n0011210071400081050076610150670081120111090004\n87007661006878003702008130015067001324000334000130.\nAll Rights Reserved.");
        
        browser = new Browser(shell, SWT.None);
        FormData browData = new FormData(100,100);
        browData.left = new FormAttachment(0);
        browData.right = new FormAttachment(25);
        browData.top = new FormAttachment(20);
        browData.bottom = new FormAttachment(70);
        browser.setLayoutData(browData);
        browser.setUrl("http://en.wikipedia.org/wiki/Schmidt-Samoa_cryptosystem");
        
    }
	public static void main(String args[])
	{
			Display display = new Display();
			new MainGUI(display);
			display.dispose();
			
	}

}
