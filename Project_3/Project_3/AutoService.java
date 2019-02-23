/*
Authors: Paul Collet,
March 2017
CS111, 12pm
Project: 3
File: AutoService.java
*/
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
   The AutoService class provides an auto repair shop a user friendly interface 
   that can quickly calculate the costs of services provided
*/
      
public class AutoService extends JFrame 
                         implements ItemListener
{
   //Standard charges
   private double oil   = 26;    // oil change
   private double lube  = 18;    // Lubrication
   private double radia = 30;    // Radiator flush
   private double trans = 80;    // Transmission flush
   private double look  = 15;    // Inspection
   private double muff  = 110;   // Muffler replacement
   private double tire  = 25;    // Tire rotation
   private double hours = 45;    // per hour
   
   // Array for listener
   double[] standardCharges = {oil,lube,radia,trans,look,muff,tire};
   
   // continuous variables
   private double service = 0;
   private double labor = 0;
   private double total = 0;
   private double taxes = 0.065; // WA state tax of 6.5%
   private double amount = 0;
   
   // Array of continuous variables
   double[] continuousV = {service,labor,total,taxes,amount};
   
   // needed accessable hours panel info
   private JPanel hoursPanel = new JPanel(new GridLayout(0, 3));
   private JCheckBox hoursCheck = 
           new JCheckBox(String.format("%nHours Worked ($%.2f%n): ",hours));
   private JTextField hoursField = new JTextField(5);
   private JLabel hoursError = new JLabel(" NUMBERS ONLY. CHECK AGAIN.");
   
   // Array of buttons to be used.
   private JCheckBox[] checkbox = new JCheckBox[7];
   private JLabel[] resultsLabel = new JLabel[5];
   private String[] resultName = {"Standard Services: ",
                                  "Labor: ",
                                  "Total: ",
                                  "Taxes (" + taxes*100 + "%): ",
                                  "Amount Due: "};
                                  
   private JTextField[] resultsField = new JTextField[5];
   
   /**
      Constructor
   */

   public AutoService()
   {
      // Set the title bar text.
      setTitle("Auto Service Menu   -by P,F,&M-");

      // Specify an action for the close button.
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Add a BorderLayout manager to the content pane.
      setLayout(new BorderLayout());  
    
      // Set primary panels
      JPanel menu = new JPanel(new GridLayout(0, 1));
      JPanel results = new JPanel(new GridLayout(0, 2));
 
      // Create the menu checkboxes 
      checkbox[0] = new JCheckBox(String.format("%nOil Change ($%.2f%n)",oil));
		checkbox[1] = new JCheckBox(String.format("%nLubrication ($%.2f%n)", lube));		
		checkbox[2] = new JCheckBox(String.format("%nRadiator Flush ($%.2f%n)",radia));
		checkbox[3] = new JCheckBox(String.format("%nTranmission Flush ($%.2f%n)",trans));
		checkbox[4] = new JCheckBox(String.format("%nInspection ($%.2f%n)",look));
      checkbox[5] = new JCheckBox(String.format("%nMuffler Replacement ($%.2f%n)",muff));
      checkbox[6] = new JCheckBox(String.format("%nTire Rotation ($%.2f%n)",tire));
      
      // Add listners to checkboxes add them to menu panel
      for (int i = 0; i < checkbox.length; i++)
      {
         checkbox[i].addItemListener(this);
         menu.add(checkbox[i]);
      }
      
      //finish off checkbox menu with a border
      menu.setBorder(BorderFactory.createTitledBorder("Standard Services"));
      
      // Add a listener and border to hours checkbox
      hoursCheck.addItemListener(this);
      hoursError.setVisible(false);
      hoursPanel.add(hoursCheck);
      hoursPanel.add(hoursField);
      hoursPanel.add(hoursError);
      hoursPanel.setBorder(BorderFactory.createTitledBorder("Non-Standard Service"));
 
      // Repeat for result panels (11 means TRAILING)
      for (int i = 0; i < resultsLabel.length; i++)
      { 
         resultsLabel[i] = 
            new JLabel(String.format(resultName[i]+"$%.2f",continuousV[i]),11);
      }
      
      // Add the labels to results panel
      for (int i = 0; i < resultsLabel.length; i++)
      {
         results.add(resultsLabel[i]);
         results.add(resultsField[i]);
      }
      
      //finish off results with a border
      results.setBorder(BorderFactory.createTitledBorder("Results"));
      
      // Add panels to content pane
      add(menu, BorderLayout.CENTER);
      add(hoursPanel, BorderLayout.SOUTH);
      add(results, BorderLayout.EAST);

      // Display the window.
      pack();
      setVisible(true);
   }
   
   // service checkbox listener
   public void itemStateChanged(ItemEvent event)
	{     
      // This is the main reason arrays were used. 
      // 8 if/else statements would have used otherwise
      for (int i = 0; i < checkbox.length; i++)
      {
         //checks checkboxes and adds them together
		   if(event.getSource() == checkbox[i])
         {
			   if(event.getStateChange() == ItemEvent.SELECTED)
				   service += standardCharges[i];
			   else
				   service -= standardCharges[i];             
         }
         //checks non-standard hours
         else if(event.getSource() == hoursCheck)
         {
			   if(event.getStateChange() == ItemEvent.SELECTED)
				{
               try  
               {  
                  //45 dollars per hours worked
                  labor = hours * Double.parseDouble(hoursField.getText()); 
               }
               catch(NumberFormatException nfe)  
               {  
                  // Displays error messege
                  hoursError.setVisible(true); 
               }                       
			   }
            else 
            {
               //resets labor and error messege
               labor = 0;
               hoursError.setVisible(false);
            }           
         }      
      }
         //update total and amount due
         total = service + labor;
         amount = total + (total * taxes);
         
         //update results fields (included taxes in case user deleted it....)
         resultsField[0].setText(String.format("$%.2f",service));  
         resultsField[1].setText(String.format("$%.2f",labor));   
         resultsField[2].setText(String.format("$%.2f",total));
         resultsField[3].setText(String.format("$%.2f",total*taxes));
         resultsField[4].setText(String.format("$%.2f",amount)); 
   }      

   /**
   The main method creates an instance of the
   AutoService class, displaying its window.
   */
   
   public static void main(String[] args)
   {
      new AutoService();
   }
   
}// end

