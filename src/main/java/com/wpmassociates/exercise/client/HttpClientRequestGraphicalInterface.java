package com.wpmassociates.exercise.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.gson.GsonBuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

public class HttpClientRequestGraphicalInterface extends JFrame {
	
	private static final int TEXTAREA_ROWS = 8;
	private static final int TEXTAREA_COLUMNS = 20;

	private static String partnerId = null;
	private static String duration = null;
	private static String adContent = null;
	private static Map<String, String> partnerAd = new TreeMap<String, String>(CommonCode.getComparator());
   
   private static Logger logger = Logger.getLogger(HttpClientRequestGraphicalInterface.class.getName());
   
   private static JFrame frame = null;
	
	public static void main(String...inputs) {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					frame = new HttpClientRequestGraphicalInterface();
					frame.setSize(600, 200);
					frame.setLocation(300, 200);
					frame.setTitle("Graphical interface for HTTP request");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			}
		);
	}
   
   
   public HttpClientRequestGraphicalInterface ()
   {
		final JTextField idField = new JTextField(6);
		final JTextField durationField = new JTextField(6);
		final JTextArea adContentArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
		idField.setColumns(2);
		JPanel northPanel  = new JPanel(); 
		northPanel.setLayout(new GridLayout(1, 3, 10, 5));
		northPanel.add(new JLabel("Partner id ", SwingConstants.RIGHT));
        northPanel.add(idField);
		northPanel.add(new JLabel("Duration (in days) ", SwingConstants.CENTER));
        northPanel.add(durationField);
		northPanel.add(new JLabel("Ad content below", SwingConstants.LEFT));
		add(northPanel, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(adContentArea);
		add(scrollPane, BorderLayout.CENTER);
		JPanel southPanel = new JPanel();
		JButton sendButton = new JButton("Send");
		southPanel.add(sendButton);
		sendButton.addActionListener( 
		new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				logger.info("in actionPerformed method");
				partnerId = idField.getText();
				duration = durationField.getText();
				adContent = adContentArea.getText();
				partnerAd.put("partner_id", partnerId);
				partnerAd.put("duration", duration);
				partnerAd.put("ad_content", adContent);
				Set<String> keySet = partnerAd.keySet();
				for (String key : keySet) 
					logger.info("Key " + key + " value " + partnerAd.get(key));
				
				String response = CommonCode.makePostRequest(partnerAd);
				adContentArea.setText("Response is " + response);
			}
		}
	  );

      add(southPanel, BorderLayout.SOUTH);
      pack();

   }
}
  