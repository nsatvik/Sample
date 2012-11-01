package com.jinkchak;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
	
	private Text EncryptionTextBox;
	private Text DecryptionTextBox;
	private Button EncryptButton;
	private Button EncDecryptCheck;
	private Label msgLabel;
	private Label cipherLabel;
	
	private Schmidt_Samoa_Encryptor encryptor;
	
	public MainGUI(Display display)
	{
		encryptor = new Schmidt_Samoa_Encryptor();
		
		shell = new Shell(display);
		initUI();
		// shell.pack();
        shell.setLocation(300, 300);
        shell.setSize(500, 500);
        shell.setText("Schmidt-Samoa Cryptosystem Demo");
        shell.setBackgroundImage(new Image(display, "image.jpg"));
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
        
        Label copyLabel = new Label(shell,SWT.BOLD);
        copyLabel.setText("© 2012 Code Khsetra Inc.\nAll Rights Reserved.\nwww.facebook.com/code-kshetra");
        
    }
	public static void main(String args[])
	{
			Display display = new Display();
			new MainGUI(display);
			display.dispose();
			
	}

}
