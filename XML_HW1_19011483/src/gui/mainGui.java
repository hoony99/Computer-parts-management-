package gui;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class mainGui{
	public static BufferedReader reader;
	public static String savetxt;
	public static String tmpSave;
	public static void main(String[] args) {
		JFrame frame = new JFrame("XML PROJECT");
		JPanel panel = new JPanel();
		JButton load = new JButton("Load");
		JButton make = new JButton("Make");
		JButton find = new JButton("Find");
		JButton save = new JButton("Save");
		JButton print = new JButton("Print");
		JButton insert = new JButton("Insert");
		JButton update = new JButton("Update");
		JButton delete = new JButton("Delete");
		JButton exit = new JButton("Exit");
		
		frame.add(panel);
		panel.add(load);
		panel.add(make);
		panel.add(find);
		panel.add(save);
		panel.add(print);
		panel.add(insert);
		panel.add(update);
		panel.add(delete);
		panel.add(exit);
		
		load.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) { 
				 JFileChooser fileChooser = new JFileChooser(); // 파일 탐색기 객체 생성

			        // 파일 선택 다이얼로그 열기
			        int returnValue = fileChooser.showOpenDialog(null);

			        if (returnValue == JFileChooser.APPROVE_OPTION) {
			            File selectedFile = fileChooser.getSelectedFile(); // 선택한 파일 가져오기

			            try {
			                reader = new BufferedReader(new FileReader(selectedFile));
			                savetxt = null;
			                tmpSave = null;
					        String str;
					        try {
					            while ((str = reader.readLine()) != null) {
					            	if(savetxt == null)
					            		savetxt = str + "\n";
					            	else	savetxt = savetxt + str + "\n";
					            }
					        } catch (IOException e1) {
					            e1.printStackTrace();
					        }
			                JOptionPane.showMessageDialog(null, "로드 성공", "로드 성공!", JOptionPane.INFORMATION_MESSAGE);
			            } catch (FileNotFoundException e1) {
			                JOptionPane.showMessageDialog(null, "로드 실패", "로드 실패!", JOptionPane.INFORMATION_MESSAGE);
			                e1.printStackTrace();
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, "Load canceled", "Load canceled", JOptionPane.INFORMATION_MESSAGE);
			        }
			}			
		});
		make.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JFrame makeframe = new JFrame("Make");
		        JPanel makepanel = new JPanel();
		        JLabel maketxtname = new JLabel("내용 입력");
		        JTextArea maketxt = new JTextArea(26, 71);
		        JButton makeBtn = new JButton("Save");

		        makeframe.add(makepanel);
		        makepanel.add(maketxtname);
		        makepanel.add(maketxt);
		        makepanel.add(makeBtn);

		        makeBtn.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                // JTextArea에서 내용을 가져옵니다.
		                String content = maketxt.getText();

		                JFileChooser fileChooser = new JFileChooser();
		                int returnValue = fileChooser.showSaveDialog(null); // 파일 저장 다이얼로그 열기

		                if (returnValue == JFileChooser.APPROVE_OPTION) {
		                    // 사용자가 파일을 선택한 경우
		                    File selectedFile = fileChooser.getSelectedFile();
		                    String fileName = selectedFile.getAbsolutePath(); // 선택한 파일의 경로 가져오기

		                    try {
		                        // 파일을 생성하고 내용을 저장합니다.
		                        FileWriter writer = new FileWriter(fileName);
		                        writer.write(content);
		                        writer.close();
		                        JOptionPane.showMessageDialog(null, "파일이 저장되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
		                    } catch (IOException ex) {
		                        ex.printStackTrace();
		                        JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		                    }
		                }
		            }
		        });

		        makeframe.setResizable(false);
		        makeframe.setVisible(true);
		        makeframe.setSize(800, 540);
		        makeframe.setLocationRelativeTo(null);
		    }
		});
		find.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if(reader != null) {
		    		JFrame findframe = new JFrame("Find");
			        JPanel findpanel = new JPanel();
			        JLabel findwordname = new JLabel("찾을 단어 입력");
			        JTextArea findword = new JTextArea(1, 55);
			        JTextArea findtxt = new JTextArea(25, 71);
			        JButton findBtn = new JButton("검색");
			        JScrollPane scrollPane = new JScrollPane(findtxt);
			        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			        findframe.add(findpanel);
			        findpanel.add(findwordname);
			        findpanel.add(findword);
			        findpanel.add(findBtn);
			        findpanel.add(scrollPane);
			        
			        findtxt.append(savetxt);
			        
			        findBtn.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                String content = findword.getText();
			                String text = findtxt.getText();
			                int index = text.indexOf(content);

			                DefaultHighlighter.DefaultHighlightPainter painter = 
			                        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
			                findtxt.getHighlighter().removeAllHighlights();

			                while (index >= 0) {
			                    int endIndex = index + content.length();
			                    findtxt.select(index, endIndex);
			                    try {
			                        findtxt.getHighlighter().addHighlight(index, endIndex, painter);
			                    } catch (BadLocationException ex) {
			                        ex.printStackTrace();
			                    }
			                    index = text.indexOf(content, endIndex);
			                }
			            }
			        });

			        findframe.setResizable(false);
			        findframe.setVisible(true);
			        findframe.setSize(800, 540);
			        findframe.setLocationRelativeTo(null);	
		    	}
		    	else {
		    		JOptionPane.showMessageDialog(null, "먼저 파일을 로드하세요.", "오류", JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(reader!=null) {					
					 JFileChooser fileChooser = new JFileChooser();
		             int returnValue = fileChooser.showSaveDialog(null); // 파일 저장 다이얼로그 열기

		             if (returnValue == JFileChooser.APPROVE_OPTION) {
		                    // 사용자가 파일을 선택한 경우
		            	 File selectedFile = fileChooser.getSelectedFile();
		                 String fileName = selectedFile.getAbsolutePath(); // 선택한 파일의 경로 가져오기

		                 try {
		                	 // 파일을 생성하고 내용을 저장합니다.
				             JOptionPane.showMessageDialog(null, "모든 변경 내용이 저장되었습니다.", "저장 성공", JOptionPane.INFORMATION_MESSAGE);
		                     FileWriter writer = new FileWriter(fileName);
		                     writer.write(tmpSave);
		                     writer.close();
		                 } catch (IOException ex) {
		                	 ex.printStackTrace();
		                     JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		                 }
		             }					
				}
				else {
					JOptionPane.showMessageDialog(null, "파일을 먼저 로드하세요.", "저장 실패", JOptionPane.INFORMATION_MESSAGE);
				}
			}			
		});

		print.addActionListener(new ActionListener() { // 구현 완료
			@Override
			public void actionPerformed(ActionEvent e) {
				if(reader != null) {
					JFrame printframe = new JFrame("Print");
					JPanel printpanel = new JPanel();
					JTextArea printtxt = new JTextArea(30,71);
					
					JScrollPane scrollPane = new JScrollPane(printtxt);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

					printframe.add(printpanel);
					printpanel.add(printtxt);
					printpanel.add(scrollPane);

					printtxt.append(savetxt);
					
					printframe.setResizable(false);
					printframe.setVisible(true);
					printframe.setSize(800, 540);
					printframe.setLocationRelativeTo(null);
					
				}
				else { // 로드 하지 않고 출력시도 시
					JOptionPane.showMessageDialog(null, "파일을 먼저 로드하세요", "출력 실패", JOptionPane.ERROR_MESSAGE);
				}
			}			
		});
		insert.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        if (reader != null) {
		            JFrame insertframe = new JFrame("Insert");
		            JPanel insertpanel = new JPanel();
		            JLabel inserttxtname = new JLabel("내용을 추가하고 버튼을 눌러주세요");
		            JTextArea inserttxt = new JTextArea(28, 71);
		            JButton insertBtn = new JButton("Insert");
		            JScrollPane scrollPane = new JScrollPane(inserttxt);
		            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		            insertframe.add(insertpanel);
		            insertpanel.add(inserttxtname);
		            insertpanel.add(insertBtn);
		            insertpanel.add(inserttxt);
		            insertpanel.add(scrollPane);

		            inserttxt.append(savetxt);
		            
		            insertBtn.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
	                        tmpSave = inserttxt.getText();
	                        JOptionPane.showMessageDialog(null, "삽입 성공(save버튼을 눌러 저장하세요)", "삽입 성공", JOptionPane.INFORMATION_MESSAGE);
		                }
		            });

		            insertframe.setResizable(false);
		            insertframe.setVisible(true);
		            insertframe.setSize(800, 540);
		            insertframe.setLocationRelativeTo(null);
		        } else {
		            JOptionPane.showMessageDialog(null, "파일을 먼저 로드하세요", "삽입 실패", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (reader != null) {
		            JFrame updateframe = new JFrame("Update");
		            JPanel updatepanel = new JPanel();
		            JLabel updatetxtname = new JLabel("내용을 수정하고 버튼을 눌러주세요");
		            JTextArea updatetxt = new JTextArea(28, 71);
		            JButton updateBtn = new JButton("Update");
		            JScrollPane scrollPane = new JScrollPane(updatetxt);
		            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		            updateframe.add(updatepanel);
		            updatepanel.add(updatetxtname);
		            updatepanel.add(updateBtn);
		            updatepanel.add(updatetxt);
		            updatepanel.add(scrollPane);

		            updatetxt.append(savetxt);

		            updateBtn.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
	                        tmpSave = updatetxt.getText();
	                        JOptionPane.showMessageDialog(null, "업데이트 성공(save버튼을 눌러 저장하세요)", "삽입 성공", JOptionPane.INFORMATION_MESSAGE);
		                }
		            });

		            updateframe.setResizable(false);
		            updateframe.setVisible(true);
		            updateframe.setSize(800, 540);
		            updateframe.setLocationRelativeTo(null);
		        } else {
		            JOptionPane.showMessageDialog(null, "파일을 먼저 로드하세요", "업데이트 실패", JOptionPane.ERROR_MESSAGE);
		        }
			}			
		});
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(reader != null) {
		            JFrame deleteframe = new JFrame("Delete");
		            JPanel deletepanel = new JPanel();
		            JLabel deletetxtname = new JLabel("내용을 삭제하고 버튼을 눌러주세요");
		            JTextArea deletetxt = new JTextArea(28, 71);
		            JButton deleteBtn = new JButton("Delete");
		            JScrollPane scrollPane = new JScrollPane(deletetxt);
		            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		            deleteframe.add(deletepanel);
		            deletepanel.add(deletetxtname);
		            deletepanel.add(deleteBtn);
		            deletepanel.add(deletetxt);
		            deletepanel.add(scrollPane);

		            deletetxt.append(savetxt);

		            deleteBtn.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
	                        tmpSave = deletetxt.getText();
	                        JOptionPane.showMessageDialog(null, "삭제 성공(save버튼을 눌러 저장하세요)", "삭제 성공", JOptionPane.INFORMATION_MESSAGE);
		                }
		            });

		            deleteframe.setResizable(false);
		            deleteframe.setVisible(true);
		            deleteframe.setSize(800, 540);
		            deleteframe.setLocationRelativeTo(null);
		        }	
				else {
		            JOptionPane.showMessageDialog(null, "파일을 먼저 로드하세요", "업데이트 실패", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}			
		});
		
		frame.setVisible(true);
		frame.setSize(700, 150);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}
