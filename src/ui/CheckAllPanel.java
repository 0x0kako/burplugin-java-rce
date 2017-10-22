package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import enumeration.Version;





public class CheckAllPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField cmdJTextField;
	private JButton exeJButton;
	private JButton fileJButton;
	private JButton pythonJButton;
	private JTextPane textPane;
	private String host;
	private Version version;
	private String message="";  
	private  File targetFile; 

	public CheckAllPanel() {
		setSize(600, 460);

		JLabel cmdJLabel = new JLabel("�����ļ�·��");

		cmdJTextField = new JTextField();
		cmdJTextField.setColumns(7);
		fileJButton = new JButton("�����ļ�ѡ��");
		pythonJButton = new JButton("python");
		Font f=new Font("����", Font.PLAIN, 10);
		pythonJButton.setFont(f);
		exeJButton = new JButton("ִ��");
		exeJButton.addActionListener(this);
		cmdJTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					new Thread(new Runnable() {
						@Override
						public void run() {
							request();
						}
					}).start();

				}
			}
		});
		
		fileJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				jButton1ActionActionPerformed(event);  
			}
		});
		
		
		
		pythonJButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					pythonActionPerformed(event);
				} catch (IOException | BadLocationException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}  
			}
			
		});
		textPane = new JTextPane();
		textPane.setText("******�������������ѧϰ������ֹ�����κηǷ���Ϊ******\n���汾֧��weblogic�����л�©������ִ�м��ļ��ϴ���elasticsearch java����Զ������ִ�м��ļ��ϴ�\nelasticsearchgroov����Զ������ִ�м��ļ��ϴ�\nstruts2-005��struts2-009��struts2-013��struts2-016��struts2-019��struts2-020��struts2-devmode��\nstruts2-032��struts2-033��struts2-037��struts2-045��struts2-048��struts2-052 ��struts2-053ȫ��RCE©����֤��֧��������֤��\nStruts2©����֤��Ҫpython��������Ҫ������֧��.���python��ť��ʼ����ʼ��python���\n\n�����ʼ��ʧ���밴�����²��谲װ��⣬\n1��ִ�� $[python]/Scrips/easy_install pip\n2��requestsģ�� ��װ���� pip install requests\n3��termcolorģ�鰲װ������ pip install termcolor");
		textPane.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textPane);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addComponent(cmdJLabel)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cmdJTextField, GroupLayout.PREFERRED_SIZE, 360, Short.MAX_VALUE).addGap(20)
								.addComponent(pythonJButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(20)
								.addComponent(fileJButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE).addGap(20)
								.addComponent(exeJButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(cmdJLabel)
								.addComponent(cmdJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
										.addComponent(pythonJButton)
										.addComponent(fileJButton)
								.addComponent(exeJButton))
						.addGap(10).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addContainerGap()));
		setLayout(groupLayout);
		setVisible(true);
	}
	//��ʼ��python����
	private void pythonActionPerformed(ActionEvent event) throws IOException, BadLocationException, InterruptedException{
		textPane.setText("��ʼ��װ  pip..........\n");
		String prefix = process("python -c \"import sys; print sys.prefix\" ");
		String eastInstall = prefix +"/Scripts/easy_install";
		
//		textPane.setText("��ʼ��װ  pip����������������������");
		processWritePane(eastInstall+" pip");
		
		Thread.sleep(1000);
		System.out.println("��ʼ��װ  requests��������������������");
//		textPane.setText("��ʼ��װ  requests����������������������");
		processWritePane("pip install requests ");
		
		Thread.sleep(1000);
//		textPane.setText("��ʼ��װ  termcolor����������������������");
		System.out.println("��ʼ��װ  termcolor����������������������");
		processWritePane("pip install  termcolor ");
		
	}
	
	private String process(String cmd) throws IOException{
		
		Process process = Runtime.getRuntime().exec(cmd);  
		InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        SimpleAttributeSet red = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,12);
        StyleConstants.setForeground(red, Color.RED);
        Document docs = textPane.getDocument();//����ı�����
        String line = null;
        String str = null;
        
        try {
                while ((line = bufferedReader.readLine()) != null) {
                	System.out.println(line);
                	str =  line;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
		return str;
	}
	
	private String processWritePane(String cmd) throws IOException, BadLocationException{
		
		Process process = Runtime.getRuntime().exec(cmd);  
		InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        SimpleAttributeSet red = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,12);
        StyleConstants.setForeground(red, Color.RED);
        Document docs = textPane.getDocument();//����ı�����
        String line = null;
        
        try {
                while ((line = bufferedReader.readLine()) != null) {
                	docs.insertString(docs.getLength(), line+"\n", attrset);//���ı�����׷��
                	System.out.println(line);
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
		return line;
	}
	
	//��ȡ�ļ�  
    private void jButton1ActionActionPerformed(ActionEvent event) {  
        JFileChooser dlg = new JFileChooser();   
        /** 
         * ���öԻ�������,��򿪷���ҲҪ��Ӧ��ȷ���򿪷���������ʾ���� 
         */  
        dlg.setDialogType(JFileChooser.OPEN_DIALOG);  
        //�趨�����ͺ��dlg.showOpenDialog(null)��������ʹ�òŻ���ʾ����Ի���  
//      dlg.setDialogType(JFileChooser.SAVE_DIALOG);  
        //�趨�����ͺ��dlg.showDialog(null, "haha")��������ʹ�ã����ҵ�һ����ť��ʾΪ�ڶ�������"haha".  
//      dlg.setDialogType(JFileChooser.CUSTOM_DIALOG);  
          
          
        //��һ���������������2 �������ǵ�һ����ť�����֣������Ǵ򿪻򱣴��ˣ�  
//      dlg.showDialog(null, "haha");  
       int value =  dlg.showOpenDialog(null);  
//      dlg.showSaveDialog(null);  
       if(value==JFileChooser.APPROVE_OPTION){
    	   File file=dlg.getSelectedFile(); 
           if(file==null){  
           	JOptionPane.showMessageDialog(this, "�ļ�������");
           }else{  
               //�õ�����ļ�����������  
               System.out.println("getAbsolutePath--"+file.getAbsolutePath());  
               System.out.println("getName--"+file.getName());  
               System.out.println("isFile--"+file.isFile());  
               System.out.println("isDirectory--"+file.isDirectory());  
               System.out.println("getPath--"+file.getPath());  
               System.out.println("getParent--"+file.getParent());  
               cmdJTextField.setText(file.getPath());
               this.targetFile=file;  
               this.message=file.getPath();  
                 
           }  
       }else{
    	   return;
       }   
       
    } 

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == exeJButton) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					request();
				}
			}).start();

		}
	}

	private void request() {
		try {
			textPane.setText("");
			String command = cmdJTextField.getText().trim();
			if (host != null ) {

				textPane.setText("���Ե�...\n");

//				ExploitService service = (ExploitService) Class
//						.forName("com.axtx.service.impl." + version.name() + "ExploitServiceImpl").newInstance();

//				textPane.setText(service.doExecuteCMD(host, command));
				if(version.name().equals("CheckAll")){
//					URL resource = getClass().getResource("/resource/s-scan.py");
					 InputStream sourceURL = getClass().getResourceAsStream("/resource/s-scan.py");
//					File file = new File(sourceURL);
//					readFileByLines(file);
					File acc = new File("e:/acc.py");
					if(!acc.exists()){
						copyFile(sourceURL,"e:/acc.py");
						Thread.sleep(500);
					}
					
//					String url = getClass().getClassLoader().getResource("/").getPath();
//					String url = resource.getPath();
					Process process = Runtime.getRuntime().exec("python "+ "e:/acc.py" +" "+host);  
					InputStream inputStream = process.getInputStream();
			        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			        SimpleAttributeSet attrset = new SimpleAttributeSet();
			        SimpleAttributeSet red = new SimpleAttributeSet();
			        StyleConstants.setFontSize(attrset,12);
			        StyleConstants.setForeground(red, Color.RED);
			        
			        
			        Document docs = textPane.getDocument();//����ı�����
			        String line;
			        
			        try {
			                while ((line = bufferedReader.readLine()) != null) {
			                	System.out.println(line);
			                	if(line.startsWith("[32m")){
			                		line= line.replace("[32m", "").replace("[0m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8");  
			                		docs.insertString(docs.getLength(), newStr+"\n", attrset);//���ı�����׷��
			                	}else if(line.startsWith("[31m")){
			                		line= line.replace("[31m", "").replace("[0m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8");  
			                		docs.insertString(docs.getLength(), newStr+"\n", red);//���ı�����׷��
			                	}else{
			                		String newStr = new String(line.getBytes(), "UTF-8");  
			                		docs.insertString(docs.getLength(), newStr+"\n", red);//���ı�����׷��
			                	}
			                	
			                	
			                }
			        } catch (IOException e) {
			                e.printStackTrace();
			        }
					process.waitFor();  
				}else if(version.name().equals("Batch")){
//					URL resource = getClass().getResource("/resource/s-scan.py");
					InputStream sourceURL = getClass().getResourceAsStream("/resource/s-scan.py");
					
//					File file = new File(resource.toURI());
//					readFileByLines(file);
					File acc = new File("e:/acc.py");
					if(!acc.exists()){
						copyFile(sourceURL,"e:/acc.py");
						Thread.sleep(500);
					}
					
					
					Process process = Runtime.getRuntime().exec("python "+ "e:/acc.py" +" -f " +" "+cmdJTextField.getText());
					InputStream inputStream = process.getInputStream();
			        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			        SimpleAttributeSet attrset = new SimpleAttributeSet();
			        SimpleAttributeSet red = new SimpleAttributeSet();
			        StyleConstants.setFontSize(attrset,12);
			        StyleConstants.setForeground(red, Color.RED);
			        Document docs = textPane.getDocument();//����ı�����
			        String line;
			        
			        try {
			                while ((line = bufferedReader.readLine()) != null) {
			                	System.out.println(line);
			                	if(line.startsWith("[32m")){
			                		
			                		line= line.replace("[32m", "").replace("[0m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8"); 
			                		docs.insertString(docs.getLength(), newStr+"\n", attrset);//���ı�����׷��
			                	}else if(line.startsWith("[31m")){
			                		line= line.replace("[31m", "").replace("[0m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8"); 
			                		docs.insertString(docs.getLength(), newStr+"\n", red);//���ı�����׷��
			                	}else{
			                		line= line.replace("[32m", "").replace("[0m", "").replace("[36m", "");
			                		String newStr = new String(line.getBytes(), "UTF-8"); 
			                		docs.insertString(docs.getLength(), newStr+"\n", attrset);//���ı�����׷��
			                	}
			                	
			                	
			                }
			        } catch (IOException e) {
			                e.printStackTrace();
			        }
					process.waitFor(); 
				}

			} else {
				JOptionPane.showMessageDialog(this, "������ִ������");
			}
		} catch (Exception exp) {
			textPane.setText(exp.getMessage());
		}
	}

	public void setReqestUrl(String host) {
		this.host = host;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
	
	 public static void readFileByLines(File file) {
//	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
	            while ((tempString = reader.readLine()) != null) {
	                // ��ʾ�к�
	                System.out.println("line " + line + ": " + tempString);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }
	 
	 
	 public static void copyFile( InputStream inStream, String newPath) { 
	       try { 
	           int bytesum = 0; 
	           int byteread = 0; 
//	               InputStream inStream = new FileInputStream(oldfile);      //����ԭ�ļ� 
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1444]; 
	               int length; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   bytesum += byteread;            //�ֽ��� �ļ���С 
	                   System.out.println(bytesum); 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close(); 
	       }  catch (Exception e) { 
	           System.out.println("���Ƶ����ļ���������"); 
	           e.printStackTrace(); 
	       } 
	   } 
}
